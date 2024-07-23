package com.example.mobilecontrolsappimproved

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.ListenerAll

class OrientationBroadcastReceiver(private val onOrientationChanged: ListenerAll) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_CONFIGURATION_CHANGED) {
            val isOrientationLocked = Settings.System.getInt(
                context?.contentResolver,
                Settings.System.ACCELEROMETER_ROTATION,
                1
            ) == 0
            onOrientationChanged.orientationStateChange(isOrientationLocked)
        }
    }
}
