package com.cn.android.api.wifi

import android.content.Context
import android.net.wifi.ScanResult

interface WifiApi {

    fun openWifi(context: Context): Boolean

    fun closeWifi(context: Context): Boolean

    fun switchWifiEnabled(context: Context): Boolean

    fun isWifiEnabled(context: Context): Boolean

    fun startScan(context: Context): Boolean

    fun getScanResults(context: Context): List<ScanResult>

    fun connectWifi(context: Context, result: ScanResult, password: String)

    fun getCurrentWifiInfo(context: Context)
}