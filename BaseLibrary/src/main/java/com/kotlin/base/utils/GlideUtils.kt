package com.kotlin.base.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.kotlin.base.R

/*
    Glide工具类
 */
object GlideUtils {
  fun loadImage(context: Context, url: Any, imageView: ImageView) {
        Glide.with(context).load(url).into(imageView)
    }

    fun loadImageFitCenter(context: Context, url: Any, imageView: ImageView) {
        Glide.with(context).load(url).fitCenter().into(imageView)
    }

    fun loadImageDefault(context: Context, url: Any, imageView: ImageView) {
        Glide.with(context).load(url).placeholder(R.drawable.is_empty).error(R.drawable.is_empty).into(imageView)
    }

    /*
        当fragment或者activity失去焦点或者destroyed的时候，Glide会自动停止加载相关资源，确保资源不会被浪费
     */
    fun loadUrlImage(context: Context, url: String?,placeholder:Int, imageView: ImageView){
        Glide.with(context).load(url).placeholder(placeholder).error(placeholder).centerCrop().into(
            imageView)
    }

    fun trimMemory(context:Context,level: Int){
        Glide.get(context).trimMemory(level)
    }

    fun clearMemory(context:Context){
        Glide.get(context).clearMemory()
    }

    fun loadRoundImage(context:Context,url:Any,imageView: ImageView,radius:Int){
        Glide.with(context)
            .load(url)
            .override(imageView.getWidth(), imageView.getHeight())
            .apply(
                RequestOptions.centerCropTransform()
                    .placeholder(R.drawable.is_empty)
                    .error(R.drawable.is_empty)
                    .transform(RoundedCorners(radius))
            )
            .into(imageView)
    }
}
