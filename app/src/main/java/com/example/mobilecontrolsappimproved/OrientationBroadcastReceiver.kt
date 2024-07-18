package com.example.mobilecontrolsappimproved

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.ListenerAll

class OrientationBroadcastReceiver(private val listener: ListenerAll) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || context == null) return
        val isOrientationLocked = Settings.System.getInt(
            context.contentResolver,
            Settings.System.ACCELEROMETER_ROTATION,
            1
        ) == 0
        listener.orientationStateChange(isOrientationLocked)
        Toast.makeText(context, "Orientation Lock State Changed: $isOrientationLocked", Toast.LENGTH_SHORT).show() // Debugging toast
    }
}


