package com.wzg.homemodel.provider

import android.content.Context
import com.wzg.core.base.IBaseProvider

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2018/4/24
 * 修改时间:  2018/4/24
 * 修改备注:  说明本次修改内容
 */
interface IHomeProvider : IBaseProvider {
    object HOST {
        const val HOME_MODEL_PATH: String = "/homeModel/home"
        const val HOME_SERVICE_PATH: String = "/homeModel/service"
    }
}

class HomeProvider : IHomeProvider {

    override fun init(context: Context?) {

    }
}