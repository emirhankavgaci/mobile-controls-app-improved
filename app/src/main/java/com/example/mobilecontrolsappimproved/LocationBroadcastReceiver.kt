package com.example.mobilecontrolsappimproved

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.ListenerAll

class LocationBroadcastReceiver(private val listener: ListenerAll) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        listener.onLocationChanged(isLocationEnabled)
    }
}