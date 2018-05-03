package com.wzg.core.http


import com.wzg.core.base.BaseView
import io.reactivex.observers.DisposableObserver

/**
 * 类描述:    描述该类的功能
 * 创建人:    wzg
 * 创建时间:  2017/11/28
 * 修改备注:  说明本次修改内容
 */
abstract class SubscriberCallBack<T> : DisposableObserver<T> {

    private val SUCCESS = 0
    private var baseView: BaseView? = null

    constructor(baseView: BaseView) {
        this.baseView = baseView
    }

    constructor() {}


    override fun onNext(response: T) {
        onSuccess(response)
    }

    override fun onComplete() {

    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
    }

    protected abstract fun onSuccess(response: T?)

    companion object {
        var NET_ERROR_MSG = "网络异常,请稍后再试..."
    }


}
