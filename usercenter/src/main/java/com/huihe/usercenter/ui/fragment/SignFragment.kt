package com.huihe.usercenter.ui.fragment

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.mapapi.map.*
import com.baidu.mapapi.model.LatLng
import com.eightbitlab.rxbus.Bus
import com.huihe.usercenter.R
import com.huihe.boyueentities.protocol.user.SignReq
import com.huihe.usercenter.injection.component.DaggerUserComponent
import com.huihe.usercenter.injection.module.UserModule
import com.huihe.usercenter.presenter.SignPresenter
import com.huihe.usercenter.presenter.view.SignView
import com.huihe.usercenter.service.LocationService
import com.huihe.usercenter.ui.activity.MineLookTaskDetailActivity
import com.huihe.usercenter.ui.adapter.RvPhotoAdapter
import com.jph.takephoto.model.TResult
import com.kotlin.base.common.AppManager
import com.kotlin.base.ext.*
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.ui.fragment.BaseTakePhotoFragment
import com.kotlin.base.utils.DensityUtils
import com.kotlin.base.utils.GeometryUtil
import com.kotlin.base.utils.LogUtils
import com.kotlin.provider.constant.UserConstant
import com.kotlin.provider.event.LookTaskEvent
import com.kotlin.provider.event.MeRefreshEvent
import com.qiniu.android.storage.UploadManager
import kotlinx.android.synthetic.main.fragment_sign.*
import org.jetbrains.anko.support.v4.toast

class SignFragment : BaseTakePhotoFragment<SignPresenter>(), SignView {
    var id: String? = null
    var latitude: Double? = null
    var longitude: Double? = null
    var IsTakeLook: Boolean = false
    val TAG: String = SignFragment::class.java.simpleName
    var isFirstLoc = true
    var mLocationListener: BDLocationListener = BDLocationListener()
    var req: SignReq?=null
    var rvPhotoAdapter : RvPhotoAdapter?=null
    var photoList : MutableList<String>?=null
    var imgUrl : MutableList<String>?=null
    var mLocalFilResult: TResult? = null
    lateinit var mBaiduMap: BaiduMap
    lateinit var locationService: LocationService
    var mUploadManager: UploadManager? = null
    override fun injectComponent() {
        DaggerUserComponent.builder().activityComponent(mActivityComponent).userModule(
            UserModule()
        ).build().inject(this)
        mPresenter?.mView = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return initInflater(context!!, R.layout.fragment_sign, container!!)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        id = arguments?.getString(UserConstant.KEY_ID)
        latitude = arguments?.getDouble(UserConstant.KEY_LATITUDE)?:0.0
        longitude = arguments?.getDouble(UserConstant.KEY_LONGITUDE)?:0.0
        IsTakeLook = arguments?.getBoolean(UserConstant.KEY_IS_TAKE_LOOK, false) ?: false
        req = SignReq(id=id)
        initView()
    }

    private fun initView() {
        imgUrl = mutableListOf()
        initUpdateManager()
        rvPhoto.vertical(4,DensityUtils.dp2px(context,15f))
        rvPhotoAdapter = RvPhotoAdapter(context!!)
        rvPhoto.adapter = rvPhotoAdapter
        photoList = mutableListOf<String>()
        photoList?.add(rvPhotoAdapter!!.isUpdate)
        rvPhotoAdapter?.setOnItemClickListener(object :BaseRecyclerViewAdapter.OnItemClickListener<String>{
            override fun onItemClick(view: View, item: String, position: Int) {
                if (rvPhotoAdapter!!.isUpdate.equals(item)){
                    showAlertView()
                }
            }
        })
        rvPhotoAdapter?.setData(photoList!!)
        initPosition()
    }

    private fun initUpdateManager() {
        mUploadManager = UploadManager()
    }

    override fun takeSuccess(result: TResult?) {
        super.takeSuccess(result)
        this.mLocalFilResult = result
        mPresenter.getUploadToken()
    }

    override fun onGetUploadTokenResult(result: String?) {
        mUploadManager?.put(
            mLocalFilResult?.image?.originalPath, null, result,
            { key, info, response ->
                var mRemoteFileUrl = response?.get("hash") as String
                photoList?.add(photoList?.size!!-1,mLocalFilResult?.image?.originalPath?:"")
                imgUrl?.add(mRemoteFileUrl)
                rvPhotoAdapter?.notifyDataSetChanged()
            }, null
        )
    }

    private fun initPosition() {
        isFirstLoc = true
        locationService = LocationService(context)
        locationService.setLocationOption(locationService.defaultLocationClientOption)
        mBaiduMap = mMapView.getMap()
        val mapStatus = MapStatus.Builder().zoom(15f).build()
        val mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus)
        mBaiduMap.setMapStatus(mMapStatusUpdate)
        mMapView.showScaleControl(false)
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true)
        // 定位图层显示方式
        val mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL
        mBaiduMap.setMyLocationConfiguration(MyLocationConfiguration(mCurrentMode, true, null))
        locationService.registerListener(mLocationListener)
        activity?.requestLocaPermission {
            locationService.start()
        }
    }

    inner class BDLocationListener : BDAbstractLocationListener() {

        override fun onConnectHotSpotMessage(s: String?, i: Int) {
            super.onConnectHotSpotMessage(s, i)
            LogUtils.i(TAG, "onConnectHotSpotMessage -> s = " + s!!)
            LogUtils.i(TAG, "onConnectHotSpotMessage -> i = $i")
        }

        override fun onLocDiagnosticMessage(i: Int, i1: Int, s: String?) {
            super.onLocDiagnosticMessage(i, i1, s)
            LogUtils.i(TAG, "onLocDiagnosticMessage -> s = " + s!!)
            LogUtils.i(TAG, "onLocDiagnosticMessage -> i = $i")
            LogUtils.i(TAG, "onLocDiagnosticMessage -> i1 = $i1")
        }

        override fun onReceiveLocation(location: BDLocation?) {
            val locManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                // 未打开位置开关，可能导致定位失败或定位不准，提示用户或做相应处理
                activity?.requestLocaPermission {
                    locationService.start()
                }
            }

            // 如果bdLocation为空或mapView销毁后不再处理新数据接收的位置
            if (location == null || mMapView == null)
                return
            val locData = MyLocationData.Builder() // 定位数据
                .accuracy(location.radius) // 定位精度bdLocation.getRadius()
                .direction(location.direction)  // 此处设置开发者获取到的方向信息，顺时针0-360
                .latitude(location.latitude) // 经度
                .longitude(location.longitude) // 纬度
                .build() // 构建

            mBaiduMap.setMyLocationData(locData) // 设置定位数据
            // 是否是第一次定位
            if (isFirstLoc) {
                isFirstLoc = false
                var curLatlng = LatLng(
                    location.latitude,
                    location.longitude
                )
                val u = MapStatusUpdateFactory.newLatLngZoom(curLatlng, 18f)
                mBaiduMap.animateMapStatus(u)
                val distance = GeometryUtil.GetLongDistance(
                    curLatlng.longitude,
                    curLatlng.latitude,
                    longitude?:0.0,
                    latitude?:0.0
                )
                req?.positionLatitude = curLatlng.latitude
                req?.positionLongitude = curLatlng.longitude
                if (distance>500){
                    req?.signInStatus = 1
                    tvSign.text = resources.getString(R.string.signed_error)
                    tvSign.setBackgroundResource(R.drawable.cycle_red_bg)
                }else{
                    req?.signInStatus = 2
                    tvSign.text = resources.getString(R.string.sign)
                    tvSign.setBackgroundResource(R.drawable.cycle_main_bg)
                }
                tvSign.onClick {
                    if(checkInput()){
                        req?.imgUrl = imgUrl?.getString(";")
                        mPresenter.sign(req,IsTakeLook)
                    }

                }
            }


        }
    }

    private fun checkInput(): Boolean {
        if (imgUrl.isNullOrEmpty()){
            toast(resources.getString(R.string.sign_input_tip))
            return false
        }
        return true
    }

    override fun onSignSuccess() {
        Bus.send(LookTaskEvent(null))
        Bus.send(MeRefreshEvent())
        AppManager.instance.finishActivity(MineLookTaskDetailActivity::class.java)
        AppManager.instance.finishActivity(activity!!)
    }

    override fun onDestroy() {
        locationService?.unregisterListener(mLocationListener)
        locationService?.stop()
        mMapView?.onDestroy()
        super.onDestroy()
    }

    override fun onPause() {
        mMapView?.onPause()
        super.onPause()
    }

    override fun onResume() {
        mMapView?.onResume()
        super.onResume()
    }
}
