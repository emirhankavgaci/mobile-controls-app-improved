package com.example.mobilecontrolsappimproved

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.ListenerAll

class MobileDataBroadcastReceiver(private val listenerMobileData: ListenerAll) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || context == null) return
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        val isMobileDataOn = networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false
        listenerMobileData.dataStateChange(isMobileDataOn)
    }
}
