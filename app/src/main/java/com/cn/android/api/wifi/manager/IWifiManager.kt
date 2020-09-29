package com.cn.android.api.wifi.manager

import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.text.TextUtils

class IWifiManager private constructor(context: Context) {

    companion object {
        private var instance: IWifiManager? = null//?当前对象可为空 如果为空系统不会报空指针

        fun getInstance(context: Context): IWifiManager {
            if (null == instance) {
                synchronized(IWifiManager::class) {
                    if (null == instance) {
                        instance =
                            IWifiManager(context)
                    }
                }
            }
            return instance!!//!!表示当前对象不为空的情况下执行 如果为空系统一定会报异常
        }
    }

    private var mWifiManager: WifiManager? = null

    init {
        mWifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager?
    }

    fun openWifi(): Boolean {
        if (null != mWifiManager && !mWifiManager!!.isWifiEnabled)
            return mWifiManager!!.setWifiEnabled(true)
        return false
    }

    fun closeWifi(): Boolean {
        if (null != mWifiManager && mWifiManager!!.isWifiEnabled)
            return mWifiManager!!.setWifiEnabled(false)
        return false
    }

    fun switchWifiEnabled(): Boolean {
        if (null != mWifiManager) {
            return mWifiManager!!.setWifiEnabled(!isWifiEnabled())
        }
        return false
    }

    fun isWifiEnabled(): Boolean {
        if (null != mWifiManager)
            return mWifiManager!!.isWifiEnabled
        return false
    }

    fun startScan(): Boolean {
        if (null != mWifiManager)
            return mWifiManager!!.startScan()
        return false
    }

    fun getScanResults(): List<ScanResult>? {
        if (null != mWifiManager)
            return mWifiManager!!.scanResults
        return null
    }

    fun connectWifi(result: ScanResult, password: String): Boolean {
        if (null == result || TextUtils.isEmpty(password))
            return false
        var configuration = hasWifiConfiguration(result.SSID);
        var netWorkId: Int  = -1
        if (null == configuration) {
            configuration = createWifiConfig(result.SSID, password, getCapabilities(result.capabilities))
            netWorkId = mWifiManager!!.addNetwork(configuration)
        } else {
            netWorkId = configuration!!.networkId
        }
        if (netWorkId == -1) return false;
        return mWifiManager!!.enableNetwork(netWorkId, true);
    }

    private fun createWifiConfig(
        ssid: String?,
        password: String,
        dataType: Data
    ): WifiConfiguration? {
        var config = WifiConfiguration()
        config.allowedAuthAlgorithms.clear()
        config.allowedGroupCiphers.clear()
        config.allowedKeyManagement.clear()
        config.allowedPairwiseCiphers.clear()
        config.allowedProtocols.clear()
        config.SSID = StringBuilder("\"").append(ssid).append("\"").toString()
        if (dataType == Data.WIFI_CIPHER_NOPASS) {
            config.wepKeys[0] = ""
            config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
            config.wepTxKeyIndex = 0
        }
        return config
    }

    private fun getCapabilities(capabilities: String): Data {
        if (capabilities.contains("WPA2") or capabilities.contains("WPA-PSK"))
            return Data.WIFI_CIPHER_WPA2
        else if (capabilities.contains("capabilities.contains(\"WPA2\")"))
            return Data.WIFI_CIPHER_WPA
        else if (capabilities.contains("WEP"))
            return Data.WIFI_CIPHER_WEP
        return Data.WIFI_CIPHER_NOPASS
    }

    private fun hasWifiConfiguration(ssid: String): WifiConfiguration? {
        if (null == mWifiManager) return null
        var wifiConfigurations = mWifiManager!!.configuredNetworks
        wifiConfigurations!!.forEach {
            if (it.equals(ssid))
                return it
        }
        return null
    }

    enum class Data private constructor(value: Int) {
        WIFI_CIPHER_NOPASS(0),
        WIFI_CIPHER_WEP(1),
        WIFI_CIPHER_WPA(2),
        WIFI_CIPHER_WPA2(3);
        private var value: Int = 0
        init {
            this.value = value
        }
    }

}