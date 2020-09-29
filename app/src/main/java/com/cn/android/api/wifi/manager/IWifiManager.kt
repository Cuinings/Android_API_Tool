package com.cn.android.api.wifi.manager

import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager

class IWifiManager private constructor(context: Context) {

    companion object {
        private var instance: IWifiManager? = null//?当前对象可为空 如果为空系统不会报空指针

        fun getInstance(context: Context): IWifiManager? {
            if (null == instance) {
                synchronized(IWifiManager::class) {
                    if (null == instance) {
                        instance = IWifiManager(context)
                    }
                }
            }
            return instance
        }
    }

    private var mWifiManager: WifiManager? = context.getSystemService(Context.WIFI_SERVICE) as WifiManager?

    fun openWifi(): Boolean {
        return mWifiManager?.setWifiEnabled(true)?: false
    }

    fun closeWifi(): Boolean {
        return mWifiManager?.setWifiEnabled(false)?: false
    }

    fun switchWifiEnabled(): Boolean {
        return mWifiManager?.setWifiEnabled(!isWifiEnabled())?: false
    }

    fun isWifiEnabled(): Boolean {
        return mWifiManager?.isWifiEnabled?: false
    }

    fun startScan(): Boolean {
        return mWifiManager?.startScan()?: false
    }

    fun getScanResults(): List<ScanResult> {
        return mWifiManager?.scanResults?: ArrayList()
    }

    fun getCurrentWifiInfo(): WifiInfo? {
        return mWifiManager?.connectionInfo
    }

    fun connectWifi(result: ScanResult, password: String): Boolean {
        if (null == result || password?.isEmpty()?:true)
            return false
        var configuration = hasWifiConfiguration(result.SSID)
        var netWorkId = configuration?.networkId?: mWifiManager?.addNetwork(createWifiConfig(result?.SSID, password, getCapabilities(result?.capabilities)))?: -1
        if (netWorkId == -1) return false
        return mWifiManager?.enableNetwork(netWorkId, true)?: false
    }

    private fun createWifiConfig(
        ssid: String,
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
        when (dataType) {
            Data.WIFI_CIPHER_NOPASS -> {
                config.wepKeys[0] = ""
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
                config.wepTxKeyIndex = 0
            }
            Data.WIFI_CIPHER_WEP -> {
                config.hiddenSSID = true
                config.wepKeys[0] = "\"" + password + "\""
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED)
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40)
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104)
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
                config.wepTxKeyIndex = 0
            }
            Data.WIFI_CIPHER_WPA -> {
                config.preSharedKey = "\"" + password + "\""
                config.hiddenSSID = true
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
                config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
                config.allowedProtocols.set(WifiConfiguration.Protocol.WPA)
                config.status = WifiConfiguration.Status.ENABLED
            }
            Data.WIFI_CIPHER_WPA2 -> {
                config.preSharedKey = "\"" + password + "\""
                config.hiddenSSID = true
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP)
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
                config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP)
                config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
                config.status = WifiConfiguration.Status.ENABLED
            }
        }
        return config
    }

    private fun getCapabilities(capabilities: String): Data {
        return when {
            capabilities.contains("WPA2") or capabilities.contains("WPA-PSK") -> Data.WIFI_CIPHER_WPA2
            capabilities.contains("capabilities.contains(\"WPA2\")") -> Data.WIFI_CIPHER_WPA
            capabilities.contains("WEP") -> Data.WIFI_CIPHER_WEP
            else -> Data.WIFI_CIPHER_NOPASS
        }
    }

    private fun hasWifiConfiguration(ssid: String): WifiConfiguration? {
        var wifiConfigurations = mWifiManager?.configuredNetworks?: null
        wifiConfigurations?.forEach {
            if (it.equals(ssid)) {
                return it
            }
        }?:return null
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