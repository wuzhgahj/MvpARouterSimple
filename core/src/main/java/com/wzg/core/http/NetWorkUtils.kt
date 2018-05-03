package com.wzg.core.http

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import java.net.Inet6Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2018/3/15
 * 修改时间:  2018/3/15
 * 修改备注:  说明本次修改内容
 */
object NetWorkUtils {

    /**
     * 获取ip地址
     *
     * @return
     */
    // skip ipv6
    val hostIP: String?
        get() {

            var hostIp: String? = null
            try {
                val nis = NetworkInterface.getNetworkInterfaces()
                var ia: InetAddress?
                while (nis.hasMoreElements()) {
                    val ni = nis.nextElement() as NetworkInterface
                    val ias = ni.inetAddresses
                    while (ias.hasMoreElements()) {
                        ia = ias.nextElement()
                        if (ia is Inet6Address) {
                            continue
                        }
                        val ip = ia!!.hostAddress
                        if ("127.0.0.1" != ip) {
                            hostIp = ia.hostAddress
                            break
                        }
                    }
                }
            } catch (e: SocketException) {
                Log.i("yao", "SocketException")
                e.printStackTrace()
            }

            return hostIp

        }

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context != null) {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = cm.activeNetworkInfo
            if (info != null) {
                return info.isAvailable
            }
        }
        return false
    }
}
