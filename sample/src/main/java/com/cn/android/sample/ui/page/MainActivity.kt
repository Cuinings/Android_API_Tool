package com.cn.android.sample.ui.page

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.cn.android.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var mainBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding?.root)
    }

    fun toWifiPage(view: View) {
        startActivity(Intent(this, WifiActivity::class.java))
    }
}
