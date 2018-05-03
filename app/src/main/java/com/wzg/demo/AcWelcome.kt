package com.wzg.demo

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.socks.library.KLog
import com.wzg.core.RouterManager
import com.wzg.core.base.BaseActivity

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2018/3/28
 * 修改时间:  2018/3/28
 * 修改备注:  说明本次修改内容
 */
class AcWelcome : BaseActivity<WelcomePresenter>(), WelcomeView {

    override fun createPresenter(): WelcomePresenter {
        return WelcomePresenter(this)
    }

    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        return R.layout.ac_login
    }

    override fun initView() {
        ARouter.enableAutoInject()
        RouterManager.getInstance()?.gotoLogin(this)
    }
}