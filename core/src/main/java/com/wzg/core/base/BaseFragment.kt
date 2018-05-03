package com.wzg.core.base

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wzg.core.model.ErrorModel

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2018/2/1
 * 修改时间:  2018/2/1
 * 修改备注:  说明本次修改内容
 */
abstract class BaseFragment<P : BasePresenter<*>> : BaseLazyFragment(), BaseView {
    private var rootView: View? = null

    protected var mPresenter: P? = null

    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract fun createPresenter(): P

    protected var mContext: Context? = null

    abstract fun getLayoutId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            rootView = layoutInflater.inflate(getLayoutId(), container, false)
            isViewCreated = true
            initListener()
        } else {
            var viewGroup: ViewGroup? = rootView?.parent as ViewGroup?
            viewGroup?.removeView(rootView)
        }
        return rootView
    }

    protected fun initListener() {}

    protected fun getResString(id: Int): String {
        return resources.getString(id)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context
    }

    protected fun getResColor(id: Int): Int {
        return ContextCompat.getColor(mContext!!, id)
    }

    override fun showLoading() {
        (activity as BaseActivity<*>).showLoading()
    }

    override fun hideLoading() {
        (activity as BaseActivity<*>).hideLoading()
    }

    override fun showError(msg: String) {
        (activity as BaseActivity<*>).showError(msg)
    }

    override fun onCodeError(errorModel: ErrorModel) {
        (activity as BaseActivity<*>).onCodeError(errorModel)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter?.detachView()
    }
}