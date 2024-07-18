package com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager

class WifiBroadcastReceiver (val listenerWifi: ListenerAll): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || context == null) return

        when (intent.action) {
            WifiManager.WIFI_STATE_CHANGED_ACTION-> {
                val state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)
                when (state) {
                    WifiManager.WIFI_STATE_DISABLED -> {
                        listenerWifi.wifiStateChange(false)
                    }
                    WifiManager.WIFI_STATE_ENABLED-> {
                        listenerWifi.wifiStateChange(true)
                    }

                }
            }
        }
    }
}