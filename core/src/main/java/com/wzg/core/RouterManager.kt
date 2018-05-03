package com.wzg.core

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter
import com.socks.library.KLog


/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2018/4/23
 * 修改时间:  2018/4/23
 * 修改备注:  说明本次修改内容
 */
class RouterManager {

    object RouterURLCentre {
        const val LOGIN_PATH: String = "/userModel/login"
        const val HOME_PATH: String = "/homeModel/main"
    }

    companion object {
        private var mInstance: RouterManager? = null//上下文

        fun getInstance(): RouterManager? {
            if (mInstance == null) {
                mInstance = RouterManager()
            }
            return mInstance
        }
    }

    fun initRouter(application: Application) {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application)
    }

    private fun gotoNewPage(context: Context, pageUrl: String) {
        ARouter.getInstance().build(pageUrl).navigation(context, object :NavigationCallback{
            override fun onInterrupt(postcard: Postcard?) {
                KLog.e("onInterrupt",postcard)
            }

            override fun onArrival(postcard: Postcard?) {
                KLog.e("onArrival",postcard)
            }

            override fun onLost(postcard: Postcard?) {
                KLog.e("onLost",postcard)
            }

            override fun onFound(postcard: Postcard?) {
                KLog.e("onFound",postcard)
            }
        })

    }

    fun gotoLogin(context: Context) {
        gotoNewPage(context,RouterURLCentre.LOGIN_PATH)
    }

    fun gotoMainPage(context: Context){
        gotoNewPage(context,RouterURLCentre.HOME_PATH)
    }
}