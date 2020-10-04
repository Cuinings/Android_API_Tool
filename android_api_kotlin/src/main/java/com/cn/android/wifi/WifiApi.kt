package com.cn.android.wifi

import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo

interface WifiApi {

    fun enabledWifi(context: Context): Boolean

    fun enabledWifi(context: Context, boolean: Boolean): Boolean

    fun isWifiEnabled(context: Context): Boolean

    fun startScan(context: Context): Boolean

    fun getScanResults(context: Context): List<ScanResult>?

    fun connectWifi(context: Context, result: ScanResult, password: String): Boolean

    fun getCurrentWifiInfo(context: Context): WifiInfo?
}