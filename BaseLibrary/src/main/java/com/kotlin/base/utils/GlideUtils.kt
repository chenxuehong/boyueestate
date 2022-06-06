package com.kotlin.base.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.kotlin.base.R

/*
    Glide工具类
 */
object GlideUtils {
    fun loadImage2(context: Context, url: Any, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .into(imageView)
    }

    fun loadImage(context: Context, url: Any?, imageView: ImageView) {
        loadImage(context,url,R.drawable.is_empty,imageView);
    }

    fun loadRoundedImage(context: Context, url: Any?,radius:Int, imageView: ImageView) {
        loadRoundedImage(context,url,R.drawable.is_empty,radius,imageView);
    }

    fun loadHeadUrl(context: Context, url: Any?, imageView: ImageView) {
        loadImage(context,url,R.drawable.head_icon,imageView);
    }

    /*
        当fragment或者activity失去焦点或者destroyed的时候，Glide会自动停止加载相关资源，确保资源不会被浪费
     */
    fun loadImage(context: Context, url: Any?,placeholder:Int, imageView: ImageView){
        Glide.with(context)
            .load(url)
            .placeholder(placeholder)
            .error(placeholder)
            .centerCrop()
            .into(imageView)
    }

    /*
        当fragment或者activity失去焦点或者destroyed的时候，Glide会自动停止加载相关资源，确保资源不会被浪费
     */
    fun loadRoundedImage(context: Context, url: Any?,placeholder:Int,radius:Int, imageView: ImageView){
        Glide.with(context)
            .load(url)
            .transform(RoundedCorners(radius))
            .placeholder(placeholder)
            .error(placeholder)
            .into(imageView)
    }

    fun trimMemory(context:Context,level: Int){
        Glide.get(context).trimMemory(level)
    }

    fun clearMemory(context:Context){
        Glide.get(context).clearMemory()
    }
}
