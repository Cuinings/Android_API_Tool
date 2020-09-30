package com.cn.android

import com.cn.android.wifi.WifiApi
import com.cn.android.wifi.WifiApiImpl

class API {

    companion object {

        private val wifiApi = WifiApiImpl()

        fun getWifiApi(): WifiApi {
            return wifiApi
        }
    }
}