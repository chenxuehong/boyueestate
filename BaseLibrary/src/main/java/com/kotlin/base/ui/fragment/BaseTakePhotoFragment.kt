package com.kotlin.base.ui.fragment

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import com.bigkoo.alertview.AlertView
import com.bigkoo.alertview.OnItemClickListener
import com.jph.takephoto.app.TakePhoto
import com.jph.takephoto.app.TakePhotoImpl
import com.jph.takephoto.compress.CompressConfig
import com.jph.takephoto.model.CropOptions
import com.jph.takephoto.model.TResult
import com.kotlin.base.data.protocol.ErrorBean
import com.kotlin.base.presenter.BasePresenter
import com.kotlin.base.utils.DateUtils
import com.tbruyelle.rxpermissions2.Permission
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.functions.Consumer
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.toast
import java.io.File
import java.lang.Error
import java.security.Permissions
import javax.inject.Inject

abstract class BaseTakePhotoFragment<T : BasePresenter<*>> : BaseMvpFragment<T>(),
    TakePhoto.TakeResultListener {

    @Inject
    lateinit var a:ErrorBean
    private lateinit var mTakePhoto: TakePhoto
    private lateinit var mTempFile: File
    private lateinit var rxPermissions: RxPermissions

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        mTakePhoto = TakePhotoImpl(this, this)
        mTakePhoto.onCreate(savedInstanceState)
        rxPermissions = RxPermissions(activity!!)
        a = ErrorBean()
    }

    /*
    获取图片，成功回调
 */
    override fun takeSuccess(result: TResult?) {
        Log.d("TakePhoto", result?.image?.originalPath)
        Log.d("TakePhoto", result?.image?.compressPath)
    }

    /*
        获取图片，取消回调
     */
    override fun takeCancel() {
    }

    /*
        获取图片，失败回调
     */
    override fun takeFail(result: TResult?, msg: String?) {
        Log.e("takePhoto", msg)
    }


    /*
        错误信息提示，默认实现
     */
    override fun onError(text: String) {
        super.onError(text)
        toast(text)
    }

    /*
    弹出选择框，默认实现
    可根据实际情况，自行修改
 */
    protected fun showAlertView(isCrop:Boolean=false) {
        AlertView("选择图片", "", "取消", null, arrayOf("拍照", "相册"), context,
            AlertView.Style.ActionSheet, OnItemClickListener { _, position ->
                mTakePhoto.onEnableCompress(CompressConfig.ofDefaultConfig(), false)
                when (position) {
                    0 -> {
                        RxPermissions(activity!!).request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA).subscribe(Consumer<Boolean>{
                            if (it){
                                createTempFile()
                                if (isCrop){
                                    mTakePhoto.onPickFromCapture(Uri.fromFile(mTempFile))
                                }else{
                                    mTakePhoto.onPickFromCaptureWithCrop(Uri.fromFile(mTempFile),
                                        CropOptions.Builder()
                                            .setAspectX(1)
                                            .setAspectY(1)
                                            .setWithOwnCrop(true)
                                            .create()
                                    )
                                }

                            }
                        })

                    }
                    1 -> {

                        if(isCrop) {
                            RxPermissions(activity!!).request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA).subscribe(Consumer<Boolean>{
                                if (it){
                                    createTempFile()
                                    mTakePhoto.onPickFromGalleryWithCrop(Uri.fromFile(mTempFile),
                                        CropOptions.Builder()
                                            .setAspectX(1)
                                            .setAspectY(1)
                                            .setWithOwnCrop(true)
                                            .create())

                                }
                            })
                        }else{
                            RxPermissions(activity!!).request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA).subscribe(Consumer<Boolean>{
                                if (it){
                                    mTakePhoto.onPickFromGallery()
                                }
                            })

                        }

                    }
                }

            }

        ).show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mTakePhoto.onActivityResult(requestCode, resultCode, data)
    }

    /*
       新建临时文件
    */
    fun createTempFile() {
        val tempFileName = "${DateUtils.curTime}.png"
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            this.mTempFile = File(Environment.getExternalStorageDirectory(), tempFileName)
            return
        }

        this.mTempFile = File(activity?.filesDir, tempFileName)
    }
}