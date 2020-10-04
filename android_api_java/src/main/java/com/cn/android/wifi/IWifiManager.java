package com.cn.android.wifi;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import static com.cn.android.wifi.IWifiManager.ConnectType.*;

public class IWifiManager {

    private static IWifiManager instance;
    private final Context context;

    public static IWifiManager getManager(Context context) {
        if (null == instance)
            synchronized (IWifiManager.class) {
                if (null == instance)
                    instance = new IWifiManager(context);
            }
        return instance;
    }

    private WifiManager mWifiManager;

    private IWifiManager(Context context) {
        this.context = context;
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    public boolean enableWifi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return false;
        }
        if (null == mWifiManager) return false;
        return mWifiManager.setWifiEnabled(!mWifiManager.isWifiEnabled());
    }

    public boolean enableWifi(boolean enable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return false;
        }
        if (null == mWifiManager || mWifiManager.isWifiEnabled() == enable) return false;
        return mWifiManager.setWifiEnabled(enable);
    }

    public boolean isWifiEnabled() {
        if (null == mWifiManager) return false;
        return mWifiManager.isWifiEnabled();
    }

    public boolean startScan() {
        if (null == mWifiManager) return false;
        return mWifiManager.startScan();
    }

    public List<ScanResult> getScanResults() {
        if (null == mWifiManager) return new ArrayList<>();
        return mWifiManager.getScanResults();
    }

    public WifiInfo getConnectionWifiInfo() {
        if (null == mWifiManager)
            return null;
        return mWifiManager.getConnectionInfo();
    }

    public boolean connectWifi(ScanResult result, String password) {
        if (null == result || TextUtils.isEmpty(password)) return false;
        WifiConfiguration configuration = hasWifiConfiguration(result.SSID);
        int netWorkId = -1;
        if (null != configuration) {
            netWorkId = configuration.networkId;
        } else {
            netWorkId = mWifiManager.addNetwork(createWifiConfig(result.SSID, password, getConnectType(result.capabilities)));
        }
        return mWifiManager.enableNetwork(netWorkId, true);
    }

    private WifiConfiguration createWifiConfig(String ssid, String password, ConnectType connectType) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = new StringBuilder("\"").append(ssid).append("\"").toString();
        switch (connectType) {
            case WIFI_CIPHER_NOPASS:
                config.wepKeys[0] = "";
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                config.wepTxKeyIndex = 0;
                break;
            case WIFI_CIPHER_WEP:
                config.hiddenSSID = true;
                config.wepKeys[0] = "\"" + password + "\"";
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                config.wepTxKeyIndex = 0;
                break;
            case WIFI_CIPHER_WPA:
                config.preSharedKey = "\"" + password + "\"";
                config.hiddenSSID = true;
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                config.status = WifiConfiguration.Status.ENABLED;
                break;
            case WIFI_CIPHER_WPA2:
                config.preSharedKey = "\"" + password + "\"";
                config.hiddenSSID = true;
                config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                config.status = WifiConfiguration.Status.ENABLED;
                break;
        }
        return config;
    }

    private ConnectType getConnectType(String capabilities) {
        if (capabilities.contains("WPA2") || capabilities.contains("WPA-PSK"))
            return WIFI_CIPHER_WPA2;
        else if (capabilities.contains("WPA"))
            return WIFI_CIPHER_WPA;
        else if (capabilities.contains("WEP"))
            return WIFI_CIPHER_WEP;
        return WIFI_CIPHER_NOPASS;
    }

    private WifiConfiguration hasWifiConfiguration(String ssid) {
        List<WifiConfiguration> configurations = mWifiManager.getConfiguredNetworks();
        for (WifiConfiguration configuration : configurations) {
            if (configuration.SSID.equals(ssid)) {
                return configuration;
            }
        }
        return null;
    }

    enum ConnectType {
        WIFI_CIPHER_NOPASS(0),
        WIFI_CIPHER_WEP(1),
        WIFI_CIPHER_WPA(2),
        WIFI_CIPHER_WPA2(3);

        private int value = 0;

        ConnectType(int value) {
            this.value = value;
        }
    }
}
