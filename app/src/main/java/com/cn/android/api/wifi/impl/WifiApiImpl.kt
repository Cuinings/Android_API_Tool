package com.cn.android.api.wifi.impl

import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo
import android.os.Build
import com.cn.android.api.wifi.WifiApi
import com.cn.android.api.wifi.manager.IWifiManager

class WifiApiImpl: WifiApi {

    override fun openWifi(context: Context): Boolean {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            return false;
        return IWifiManager.getInstance(context).openWifi()
    }

    override fun closeWifi(context: Context): Boolean {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            return false;
        return IWifiManager.getInstance(context).closeWifi()
    }

    override fun switchWifiEnabled(context: Context): Boolean {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            return false;
        return IWifiManager.getInstance(context).switchWifiEnabled()
    }

    override fun isWifiEnabled(context: Context): Boolean {
        return IWifiManager.getInstance(context).isWifiEnabled()
    }

    override fun startScan(context: Context): Boolean {
        return IWifiManager.getInstance(context).startScan()
    }

    override fun getScanResults(context: Context): List<ScanResult>? {
        return IWifiManager.getInstance(context).getScanResults();
    }

    override fun connectWifi(context: Context, result: ScanResult, password: String): Boolean {
        return IWifiManager.getInstance(context).connectWifi(result, password)
    }

    override fun getCurrentWifiInfo(context: Context): WifiInfo? {
        return IWifiManager.getInstance(context).getCurrentWifiInfo()
    }
}