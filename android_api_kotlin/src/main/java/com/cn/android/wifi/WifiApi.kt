package com.cn.android.wifi

import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo

interface WifiApi {

    fun openWifi(context: Context): Boolean

    fun closeWifi(context: Context): Boolean

    fun switchWifiEnabled(context: Context): Boolean

    fun isWifiEnabled(context: Context): Boolean

    fun startScan(context: Context): Boolean

    fun getScanResults(context: Context): List<ScanResult>?

    fun connectWifi(context: Context, result: ScanResult, password: String): Boolean

    fun getCurrentWifiInfo(context: Context): WifiInfo?
}