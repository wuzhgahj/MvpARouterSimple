package com.wzg.homemodel

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.wzg.core.RouterManager
import com.wzg.core.base.BaseActivity
import com.wzg.core.base.BaseView
import kotlinx.android.synthetic.main.ac_main.*

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2018/4/24
 * 修改时间:  2018/4/24
 * 修改备注:  说明本次修改内容
 */
@Route(path = RouterManager.RouterURLCentre.HOME_PATH)
class AcMain : BaseActivity<MainPresenter>(),BaseView{
    override fun createPresenter(): MainPresenter {
        return MainPresenter(this)
    }

    override fun getLayoutId(savedInstanceState: Bundle?): Int {
       return R.layout.ac_main
    }

    override fun initView() {

    }
}