package com.cn.android.wifi

import android.content.Context
import android.content.Intent
import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo
import android.os.Build
import android.provider.Settings
import com.cn.android.wifi.WifiApi
import com.cn.android.wifi.IWifiManager

class WifiApiImpl: WifiApi {

    override fun enabledWifi(context: Context, boolean: Boolean): Boolean {
        return IWifiManager.getInstance(context).enabledWifi(boolean)
    }

    override fun enabledWifi(context: Context): Boolean {
        return IWifiManager.getInstance(context).enabledWifi()
    }

    override fun isWifiEnabled(context: Context): Boolean {
        return IWifiManager.getInstance(context).isWifiEnabled()
    }

    override fun startScan(context: Context): Boolean {
        return IWifiManager.getInstance(context).startScan()
    }

    override fun getScanResults(context: Context): List<ScanResult>? {
        return IWifiManager.getInstance(context).getScanResults()
    }

    override fun connectWifi(context: Context, result: ScanResult, password: String): Boolean {
        return IWifiManager.getInstance(context).connectWifi(result, password)
    }

    override fun getCurrentWifiInfo(context: Context): WifiInfo? {
        return IWifiManager.getInstance(context).getCurrentWifiInfo()
    }
}