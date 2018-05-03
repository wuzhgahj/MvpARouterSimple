package com.wzg.core.base

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.wzg.core.App
import com.wzg.core.model.ErrorModel

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2018/1/31
 * 修改时间:  2018/1/31
 * 修改备注:  说明本次修改内容
 */
abstract class BaseActivity<P : BasePresenter<*>> : AppCompatActivity(), BaseView {

    protected var mPresenter: P? = null

    protected var mMaterialDialog: MaterialDialog? = null

    companion object {
        protected var mToast: Toast? = null

        protected fun showToast(msg: String) {
            if (mToast != null) {
                mToast?.setText(msg)
                mToast?.duration = Toast.LENGTH_SHORT
            } else {
                mToast = Toast.makeText(App.getInstance(), msg, Toast.LENGTH_SHORT)
            }
            mToast?.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId(savedInstanceState))
        mPresenter = createPresenter()
        initView()
    }

    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract fun createPresenter(): P

    abstract fun getLayoutId(savedInstanceState: Bundle?): Int

    abstract fun initView()

    override fun showLoading() {
        if (mMaterialDialog == null) {
            mMaterialDialog = MaterialDialog.Builder(this)
                    .content("啦啦啦啦")
                    .progress(true, 0)
                    .cancelable(false)
                    .build()
        }
        mMaterialDialog?.show()
    }

    override fun hideLoading() {
        mMaterialDialog?.hide()
    }

    override fun showError(msg: String) {
        showToast(msg)
    }

    override fun onCodeError(errorModel: ErrorModel) {

    }

    protected fun getResString(id: Int): String {
        return resources.getString(id)
    }

    protected fun getResColor(id: Int): Int {
        return ContextCompat.getColor(this, id)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
    }
}