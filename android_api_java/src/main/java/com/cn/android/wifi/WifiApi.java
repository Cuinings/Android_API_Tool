package com.cn.android.wifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import kotlin.reflect.KProperty1;

public interface WifiApi {

    boolean enabledWifi(Context context);

    boolean enabledWifi(Context context, boolean enable);

    boolean isWifiEnabled(Context context);

    boolean startScan(Context context);

    List<ScanResult> getScanResults(Context context);

    boolean connectWifi(Context context, ScanResult result, String password);

    WifiInfo getConnectionWifiInfo(Context context);
}
