package com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class MobileDataManager(val context: Context) {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var isMobileDataOn: Boolean = false

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                isMobileDataOn = true
                broadcastMobileDataState()
            } else {
                isMobileDataOn = false
                broadcastMobileDataState()
            }
        }
    }


    init {
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun broadcastMobileDataState() {
        val intent = Intent("com.example.ACTION_MOBILE_DATA_STATE_CHANGED")
        intent.putExtra("mobile_data_state", isMobileDataOn)
        context.sendBroadcast(intent)
    }

    fun isMobileDataEnabled(): Boolean {
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false
    }

}
