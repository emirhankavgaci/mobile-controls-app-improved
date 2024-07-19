package com.example.mobilecontrolsappimproved
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.ListenerAll

class LocationBroadcastReceiver(private val listenerLocation: ListenerAll) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
       /* if (intent != null && Intent.ACTION_LOCATION_SOURCE_SETTINGS == intent.action) {
            val isLocationEnabled = Settings.Secure.getInt(
                context?.contentResolver,
                Settings.Secure.LOCATION_MODE,
                Settings.Secure.LOCATION_MODE_OFF
            ) != Settings.Secure.LOCATION_MODE_OFF
            listenerLocation.onLocationChanged(isLocationEnabled)
        }*/
    }
}
