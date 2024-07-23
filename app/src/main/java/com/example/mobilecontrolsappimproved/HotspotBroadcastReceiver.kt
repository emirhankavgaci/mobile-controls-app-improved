package com.example.mobilecontrolsappimproved

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.ListenerAll

class HotspotBroadcastReceiver(private val listener: ListenerAll) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || context == null) return

        if (intent.action == "android.net.wifi.WIFI_AP_STATE_CHANGED") {
            val state = intent.getIntExtra("wifi_state", 0)
            val isHotspotActive = state == 13 // WIFI_AP_STATE_ENABLED
            listener.onHotspotChanged(isHotspotActive)
        }
    }
}