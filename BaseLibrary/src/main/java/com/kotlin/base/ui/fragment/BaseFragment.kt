package com.kotlin.base.ui.fragment

import android.Manifest
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.jph.takephoto.model.CropOptions
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle3.components.support.RxFragment
import io.reactivex.functions.Consumer

/*
    Fragment基类，业务无关
 */
open class BaseFragment : RxFragment(){

    fun getFragmentWithArg(fragment: Fragment, vararg params: Pair<String, Any?>): Fragment {
        val arg = Bundle()
        params?.apply {
            forEach {item->
                if (item.second!=null){
                    when (item.second) {
                        is String -> {
                            arg.putString(item.first,item.second as String)
                        }
                        is Int -> {
                            arg.putInt(item.first,item.second as Int)
                        }
                        is Double -> {
                            arg.putDouble(item.first,item.second as Double)
                        }
                        is Float -> {
                            arg.putFloat(item.first,item.second as Float)
                        }
                        is Byte -> {
                            arg.putByte(item.first,item.second as Byte)
                        }
                        is Bundle -> {
                            arg.putBundle(item.first,item.second as Bundle)
                        }
                    }
                }
            }
        }
        fragment.arguments = arg
        return fragment
    }

    fun requestScan(method:()->Unit){
        RxPermissions(activity!!).request(Manifest.permission.CAMERA).subscribe(
            Consumer<Boolean>{
            if (it){
                method()
            }
        })
    }
}

