package com.example.mobilecontrolsappimproved

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.ListenerAll

class OrientationBroadcastReceiver(private val onOrientationChanged: ListenerAll) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && Settings.System.ACCELEROMETER_ROTATION == intent.action) {
            val isOrientationLocked = Settings.System.getInt(
                context?.contentResolver,
                Settings.System.ACCELEROMETER_ROTATION,
                1
            ) == 0
            onOrientationChanged.orientationStateChange(isOrientationLocked)
        }
    }
}


