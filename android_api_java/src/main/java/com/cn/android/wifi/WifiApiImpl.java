package com.cn.android.wifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;

import java.util.List;

public class WifiApiImpl implements WifiApi {

    @Override
    public boolean enabledWifi(Context context) {
        return IWifiManager.getManager(context).enableWifi();
    }

    @Override
    public boolean enabledWifi(Context context, boolean enable) {
        return IWifiManager.getManager(context).enableWifi(enable);
    }

    @Override
    public boolean isWifiEnabled(Context context) {
        return IWifiManager.getManager(context).isWifiEnabled();
    }

    @Override
    public boolean startScan(Context context) {
        return IWifiManager.getManager(context).startScan();
    }

    @Override
    public List<ScanResult> getScanResults(Context context) {
        return IWifiManager.getManager(context).getScanResults();
    }

    @Override
    public boolean connectWifi(Context context, ScanResult result, String password) {
        return IWifiManager.getManager(context).connectWifi(result, password);
    }

    @Override
    public WifiInfo getConnectionWifiInfo(Context context) {
        return IWifiManager.getManager(context).getConnectionWifiInfo();
    }
}
