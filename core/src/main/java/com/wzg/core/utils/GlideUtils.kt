package com.wzg.core.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import java.io.File


/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2018/3/16
 * 修改时间:  2018/3/16
 * 修改备注:  说明本次修改内容
 */
class GlideUtils {
    companion object {
        val instance = GlideUtils()
    }

    /**
     * 默认glide，不做任何处理，glide 从字符串中加载图片（网络地址或者本地地址）
     */
    fun disPlayDefault(context: Context, url: String, view: ImageView) {
        Glide.with(context).load(url).into(view)
    }

    /**
     * 默认glide，不做任何处理，加载资源图片
     */
    fun disPlayDefault(context: Context, id: Int, view: ImageView) {
        Glide.with(context).load(id).into(view)
    }

    /**
     * glide 从文件中加载图片
     */
    fun disPlayFile(context: Context, file: File, view: ImageView, defaultId: Int) {
        var requestOptions = getOptions().placeholder(defaultId)
        Glide.with(context).load(file)
                .apply(requestOptions)
                .into(view)
    }

    /**
     * glide 从URI中加载图片
     */
    fun disPlayUri(context: Context, uri: Uri, view: ImageView, defaultId: Int) {
        var requestOptions = getOptions().placeholder(defaultId)
        Glide.with(context).load(uri)
                .apply(requestOptions)
                .into(view)
    }

    /**
     * glide 通过指定的大小从字符串中加载图片（网络地址或者本地地址）
     */
    fun disPlayImageSize(context: Context, url: String, view: ImageView, width: Int, height: Int) {
        var requestOptions = getOptions().override(width, height)
        Glide.with(context).load(url)
                .apply(requestOptions)
                .into(view)
    }

    private fun getOptions(): RequestOptions {
        return RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .centerCrop()
                .priority(Priority.NORMAL)
    }

}