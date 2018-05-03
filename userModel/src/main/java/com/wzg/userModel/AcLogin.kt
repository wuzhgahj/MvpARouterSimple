package com.wzg.userModel

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.wzg.core.RouterManager
import com.wzg.core.base.BaseActivity

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2018/4/23
 * 修改时间:  2018/4/23
 * 修改备注:  说明本次修改内容
 */
@Route(path = RouterManager.RouterURLCentre.LOGIN_PATH)
class AcLogin : BaseActivity<LoginPresenter>(), LoginView {

    override fun createPresenter(): LoginPresenter {
        return LoginPresenter(this);
    }

    override fun getLayoutId(savedInstanceState: Bundle?): Int {
        return R.layout.ac_login
    }

    override fun initView() {
        RouterManager.getInstance()?.gotoMainPage(this)
    }

}