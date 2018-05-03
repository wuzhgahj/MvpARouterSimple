package com.wzg.core.base

import com.wzg.core.model.ErrorModel


/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2018/2/1
 * 修改时间:  2018/2/1
 * 修改备注:  说明本次修改内容
 */
interface BaseView {
    /**
     * 显示dialog
     */
    fun showLoading()

    /**
     * 隐藏 dialog
     */

    fun hideLoading()

    /**
     * 显示错误信息
     *
     * @param msg
     */
    fun showError(msg: String)

    /**
     *
     */
    fun onCodeError(errorModel: ErrorModel)
}