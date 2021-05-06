package com.huihe.module_home.ui.adpter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zhouwei.library.CustomPopWindow
import com.example.zhouwei.library.CustomPopWindow.PopupWindowBuilder
import com.huihe.module_home.R
import com.huihe.module_home.data.protocol.HouseDetail
import com.huihe.module_home.data.protocol.ItemHouseDetail
import com.huihe.module_home.injection.module.CustomersModule
import com.huihe.module_home.ui.activity.HouseDetailActivity
import com.huihe.module_home.ui.activity.HouseFollowActivity
import com.huihe.module_home.ui.activity.HouseLogActivity
import com.huihe.module_home.ui.activity.HouseTakeLookActivity
import com.huihe.module_home.ui.holder.*
import com.kotlin.base.ext.callPhone
import com.kotlin.base.ext.initInflater
import com.kotlin.base.ext.onClick
import com.kotlin.base.ui.adapter.BaseRecyclerViewAdapter
import com.kotlin.base.utils.YuanFenConverter
import com.kotlin.base.widgets.GridDividerItemDecoration
import com.kotlin.provider.constant.HomeConstant
import com.youth.banner.Banner
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.layout_house_detail_banner_item.view.*
import kotlinx.android.synthetic.main.layout_house_rewarks_info_item.view.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.toast


class HouseDetailRvAdapter(mContext: Context?) :
    BaseRecyclerViewAdapter<ItemHouseDetail, RecyclerView.ViewHolder>(mContext!!) {

    var banner: Banner<HouseDetail.ImagUrlsBean, ImageAdapter>? = null
    var callPopWindow:CustomPopWindow?=null
    var mId:String?=""
    override fun getItemViewType(position: Int): Int {
        when (position) {
            0 -> {
                return CustomersModule.HouseDetailType.BANNER
            }
            1 -> {
                return CustomersModule.HouseDetailType.BASIC_INFO
            }
            2 -> {
                return CustomersModule.HouseDetailType.ITEM_DETAIL_INFO
            }
            3 -> {
                return CustomersModule.HouseDetailType.ITEM_REWARKS_INFO
            }
            4 -> {
                return CustomersModule.HouseDetailType.ITEM_OWNER_INFO
            }
            5 -> {
                return CustomersModule.HouseDetailType.ITEM_PHOTO
            }
            6 -> {
                return CustomersModule.HouseDetailType.ITEM_REFERURL
            }
            else -> return CustomersModule.HouseDetailType.MAP
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        // CustomersModule.HouseDetailType.MAP
        var holder: RecyclerView.ViewHolder =
            MapHolder(initInflater(mContext, R.layout.layout_house_detail_map_item, parent))
        when (viewType) {
            CustomersModule.HouseDetailType.BANNER -> holder = BannerHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_detail_banner_item,
                    parent
                )
            )
            CustomersModule.HouseDetailType.BASIC_INFO -> holder = HouseBasicInfoHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_basic_info_item,
                    parent
                )
            )
            CustomersModule.HouseDetailType.ITEM_DETAIL_INFO -> holder = ItemHouseDetailHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_detail_info_item,
                    parent
                )
            )
            CustomersModule.HouseDetailType.ITEM_REWARKS_INFO -> holder = ItemHouseRewarksHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_rewarks_info_item,
                    parent
                )
            )
            CustomersModule.HouseDetailType.ITEM_OWNER_INFO -> holder = ItemHouseOwnerInfoHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_owner_info_item,
                    parent
                )
            )
            CustomersModule.HouseDetailType.ITEM_PHOTO -> holder = ItemPhotoHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_photo_item,
                    parent
                )
            )
            CustomersModule.HouseDetailType.ITEM_REFERURL -> holder = ItemRefeurlHolder(
                initInflater(
                    mContext,
                    R.layout.layout_house_referurl_item,
                    parent
                )
            )

            else -> {
                holder =
                    MapHolder(initInflater(mContext, R.layout.layout_house_detail_map_item, parent))
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        var itemHouseDetail = dataList[position]
        when (getItemViewType(position)) {
            CustomersModule.HouseDetailType.BANNER -> {
                initBanner(holder, itemHouseDetail)
            }
            CustomersModule.HouseDetailType.BASIC_INFO -> {
                initBasicInfo(holder, itemHouseDetail)
            }
            CustomersModule.HouseDetailType.ITEM_DETAIL_INFO -> {
                initDetailInfoList(holder, itemHouseDetail)
            }
            CustomersModule.HouseDetailType.ITEM_REWARKS_INFO -> {
                var rewarksHolder: ItemHouseRewarksHolder = holder as ItemHouseRewarksHolder
                rewarksHolder.itemView.tvHouseDetailRewarks.text = itemHouseDetail.rewarks
            }
            CustomersModule.HouseDetailType.ITEM_OWNER_INFO -> {
                initOwnerInfoList(holder, itemHouseDetail)
            }
            CustomersModule.HouseDetailType.ITEM_PHOTO -> {
                initPhotos(holder, itemHouseDetail)
            }
            CustomersModule.HouseDetailType.ITEM_REFERURL -> {
                initReferurl(holder, itemHouseDetail)
            }
        }
    }

    private fun initDetailInfoList(
        holder: RecyclerView.ViewHolder,
        itemHouseDetail: ItemHouseDetail
    ) {
        var detailHolder: ItemHouseDetailHolder = holder as ItemHouseDetailHolder
        var rvList = detailHolder.itemView as RecyclerView
        rvList.layoutManager = LinearLayoutManager(mContext)
        rvList.isNestedScrollingEnabled = false
        var houseDetailRvAdapter = HouseDetailRvItemAdapter(mContext)
        rvList.adapter = houseDetailRvAdapter
        houseDetailRvAdapter.setData(itemHouseDetail?.detailInfoList!!)
    }

    private fun initOwnerInfoList(
        holder: RecyclerView.ViewHolder,
        itemHouseDetail: ItemHouseDetail
    ) {
        var detailHolder: ItemHouseOwnerInfoHolder = holder as ItemHouseOwnerInfoHolder
        var rvList = detailHolder.itemView as RecyclerView
        rvList.layoutManager = LinearLayoutManager(mContext)
        rvList.isNestedScrollingEnabled = false
        var houseDetailRvAdapter = HouseDetailOwnerRvAdapter(mContext)
        rvList.adapter = houseDetailRvAdapter
        houseDetailRvAdapter.setData(itemHouseDetail?.ownerInfoList!!)
    }

    private fun initBanner(
        holder: RecyclerView.ViewHolder,
        itemHouseDetail: ItemHouseDetail
    ) {
        var bannerHolder: BannerHolder = holder as BannerHolder
        banner =
            bannerHolder.itemView.houseDetailBanner as Banner<HouseDetail.ImagUrlsBean, ImageAdapter>?
        bannerHolder.itemView.houseDetailBanner?.apply {
            adapter = ImageAdapter(mContext, itemHouseDetail.bannerList)
            indicator = CircleIndicator(mContext)
            setIndicatorSelectedColorRes(R.color.main_color)
            setIndicatorGravity(IndicatorConfig.Direction.CENTER)
            setBannerGalleryEffect(
                mContext.resources.getDimensionPixelOffset(R.dimen.dp_6),
                mContext.resources.getDimensionPixelOffset(R.dimen.dp_6)
            )
            setBannerRound(6f)
        }
    }

    private fun initBasicInfo(
        holder: RecyclerView.ViewHolder,
        itemHouseDetail: ItemHouseDetail
    ) {
        var basicHolder: HouseBasicInfoHolder = holder as HouseBasicInfoHolder
        basicHolder.tvTitle.text =
            "${itemHouseDetail?.basicInfo?.villageName}-${itemHouseDetail?.basicInfo?.building}-${itemHouseDetail?.basicInfo?.hNum}"
        basicHolder.tvPrice.text = "${itemHouseDetail?.basicInfo?.price}万"
        var amount = itemHouseDetail?.basicInfo?.floorage?.toBigDecimal()
            ?.let { itemHouseDetail?.basicInfo?.price?.div(it) }
        var argePrice = YuanFenConverter.getRoundFloor(amount)
        basicHolder.tvArgePrice.text = "${argePrice}万/m²"
        basicHolder.tvFloorage.text = "${itemHouseDetail?.basicInfo?.floorage}m²"
        basicHolder.tvHShape.text = "${itemHouseDetail?.basicInfo?.hShape}"
        var split = itemHouseDetail?.basicInfo?.label?.split(";")
        basicHolder.tags.tags =
            if (split != null && split.size > 5) split?.subList(0, 4) else getNotNullData(
                split
            )
        basicHolder.tvOwnerName.text = itemHouseDetail?.basicInfo?.ownerName

        basicHolder.tvTel.onClick {
            showTelListDialog(itemHouseDetail?.basicInfo?.ownerTel,basicHolder.tvTel)
        }
        basicHolder.tvFollow.onClick {
            var intent = Intent(mContext, HouseFollowActivity::class.java)
            intent.putExtra(HomeConstant.KEY_HOUSE_ID,mId)
            mContext.startActivity(intent)
        }
        basicHolder.tvTakeLook.onClick {
            var intent = Intent(mContext, HouseTakeLookActivity::class.java)
            intent.putExtra(HomeConstant.KEY_HOUSE_ID,mId)
            mContext.startActivity(intent)
        }
        basicHolder.tvLog.onClick {
            var intent = Intent(mContext, HouseLogActivity::class.java)
            intent.putExtra(HomeConstant.KEY_HOUSE_ID,mId)
            mContext.startActivity(intent)
        }
    }

    private fun showTelListDialog(ownerTel: String?, view: View) {
        var split = ownerTel?.split(";")?: mutableListOf()
        split = split?.toMutableList()
        if (split?.isEmpty()){
            (mContext as Activity).toast("暂无电话")
            return
        }
        val contentView =
            LayoutInflater.from(mContext).inflate(R.layout.layout_tel_dialog, null, false)
        var telRvAdapter = TelRvAdapter(mContext)
        telRvAdapter.setOnItemClickListener(object :BaseRecyclerViewAdapter.OnItemClickListener<String>{
            override fun onItemClick(view: View, tel: String, position: Int) {
                callPhone(mContext,tel)
            }
        })
        (contentView as RecyclerView).apply {
            layoutManager = LinearLayoutManager(mContext)
            adapter = telRvAdapter
        }
        telRvAdapter.setData(
            split
        )
        callPopWindow = PopupWindowBuilder(mContext)
            .setView(contentView)
            .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
            .setBgDarkAlpha(0.7f) // 控制亮度
            .setFocusable(true)
            .setOutsideTouchable(true)
            .create()
            .showAsDropDown(view, 0, 10)
    }


    private fun initPhotos(holder: RecyclerView.ViewHolder, itemHouseDetail: ItemHouseDetail) {
        var viewHolder = holder as ItemPhotoHolder
        var imagUrls = itemHouseDetail.imagUrls
        var houseDetailPhotoRvAdapter = HouseDetailPhotoRvAdapter(mContext)
        var linearLayoutManager = GridLayoutManager(mContext, 4)
        viewHolder.mRvHousePhoto.apply {
            layoutManager = linearLayoutManager
            isNestedScrollingEnabled = false
            adapter = houseDetailPhotoRvAdapter
            if (itemDecorationCount == 0)
                addItemDecoration(
                    GridDividerItemDecoration(
                        mContext,
                        mContext.resources.getDimensionPixelSize(R.dimen.dp_15),
                        true
                    )
                )
        }
        houseDetailPhotoRvAdapter.setData(imagUrls)
    }

    private fun initReferurl(holder: RecyclerView.ViewHolder, itemHouseDetail: ItemHouseDetail) {
        var viewHolder = holder as ItemRefeurlHolder
        var imagUrls = itemHouseDetail.imagUrls
        var houseDetailPhotoRvAdapter = HouseDetailPhotoRvAdapter(mContext)
        var linearLayoutManager = GridLayoutManager(mContext, 4)
        viewHolder.mRvHousePhoto.apply {
            layoutManager = linearLayoutManager
            isNestedScrollingEnabled = false
            adapter = houseDetailPhotoRvAdapter
            if (itemDecorationCount == 0)
                addItemDecoration(
                    GridDividerItemDecoration(
                        mContext,
                        mContext.resources.getDimensionPixelSize(R.dimen.dp_15),
                        true
                    )
                )
        }
        houseDetailPhotoRvAdapter.setData(imagUrls)

    }

    private fun getNotNullData(split: List<String>?): List<String> {
        return split ?: mutableListOf()
    }

    fun onStart() {
        //开始轮播
        banner?.start()
    }

    fun onStop() {
        //停止轮播
        banner?.stop()
    }

    fun onDestroy() {
        //销毁
        banner?.destroy()
        try {
            callPopWindow?.dissmiss()
        } catch (e: Exception) {
        }
    }

    fun setId(id: String?) {
        mId =id
    }

}