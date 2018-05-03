package com.wzg.core.base

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.wzg.core.utils.GlideUtils

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2018/2/1
 * 修改时间:  2018/2/1
 * 修改备注:  说明本次修改内容
 */
abstract class BaseRecyclerAdapter<T, K : BaseViewHolder>(data: MutableList<T>?) : BaseQuickAdapter<T, K>(data) {

    fun disPlayImage(imageView: ImageView, url: String) {
        GlideUtils.instance.disPlayDefault(mContext, url, imageView)
    }

    fun disPlayImageSize(imageView: ImageView, url: String, width: Int, height: Int) {
        GlideUtils.instance.disPlayImageSize(mContext, url, imageView, width, height)
    }
}