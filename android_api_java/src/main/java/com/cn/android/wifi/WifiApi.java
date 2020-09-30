package com.cn.android.wifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;

import java.util.List;

public interface WifiApi {

    /**
     * 开启/关闭wifi
     * @param context
     * @return
     */
    boolean enabledWifi(Context context);

    /**
     * 开启/关闭wifi
     * @param enable boolean
     * @return boolean
     */
    boolean enabledWifi(Context context, boolean enable);

    boolean isWifiEnabled(Context context);

    boolean startScan(Context context);

    List<ScanResult> getScanResults(Context context);

    boolean connectWifi(Context context, ScanResult result, String password);

    WifiInfo getConnectionWifiInfo(Context context);
}
