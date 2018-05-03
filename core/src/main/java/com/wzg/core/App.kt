package com.wzg.core

import android.content.Context
import android.support.multidex.MultiDexApplication

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2018/2/1
 * 修改时间:  2018/2/1
 * 修改备注:  说明本次修改内容
 */
class App : MultiDexApplication() {
    companion object {
        private var mInstance: App? = null//上下文

        fun getInstance(): Context? {
            if (mInstance == null) {
                mInstance = App()
            }
            return mInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        RouterManager.getInstance()?.initRouter(this)
        if(BuildConfig.DEBUG){
            URLCentre.BASE_URL = ""
        }else{
            URLCentre.BASE_URL = ""
        }
    }
}