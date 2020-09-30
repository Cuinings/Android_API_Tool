package com.cn.android;

import com.cn.android.wifi.WifiApi;
import com.cn.android.wifi.WifiApiImpl;

public class API {

    private static WifiApi wifiApi = new WifiApiImpl();

    public static WifiApi getWifiApi() {
        return wifiApi;
    }
}
