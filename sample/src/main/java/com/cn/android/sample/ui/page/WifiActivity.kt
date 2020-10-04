package com.cn.android.sample.ui.page

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cn.android.API.*
//import com.cn.android.API.Companion.getWifiApi
import com.cn.android.sample.databinding.ActivityWifiBinding
import com.cn.android.sample.ui.adapter.WifiAdapter
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class WifiActivity: AppCompatActivity() {

    val instance by lazy { applicationContext } //这里使用了委托，表示只有使用到instance才会执行该段代码

    companion object {
        val TAG: String = WifiActivity.javaClass.simpleName
    }

    var receiver: IWifiReceiver =
        IWifiReceiver()

    var wifiBinding: ActivityWifiBinding? = null

    var executorService: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

    var runnable: Runnable = Runnable {
        if (getWifiApi().isWifiEnabled(applicationContext)) {
            getWifiApi().startScan(applicationContext)
            Log.e(TAG, "startScan")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wifiBinding = ActivityWifiBinding.inflate(layoutInflater)
        setContentView(wifiBinding?.root)
        executorService.scheduleWithFixedDelay(runnable, 5, 3, TimeUnit.SECONDS)

        var wifiAdapter = WifiAdapter()
    }

    override fun onStart() {
        super.onStart()
        var filter = IntentFilter()
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        registerReceiver(receiver, filter)
    }

    override fun onResume() {
        super.onResume()
        updateWifiEnabled()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }

    fun enabledWifi(view: View) {
        getWifiApi().enabledWifi(instance)
        updateWifiEnabled()
    }

    private fun updateWifiEnabled() {
        wifiBinding?.btnWifiEnabled?.text =
            StringBuilder("WIFI：").append(if (getWifiApi().isWifiEnabled(instance)) "开启" else "关闭")
                .toString()
    }

    override fun onDestroy() {
        executorService.shutdownNow()
        super.onDestroy()
    }

    class IWifiReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
//            val scanResults = getWifiApi().getScanResults(context)

        }
    }
}