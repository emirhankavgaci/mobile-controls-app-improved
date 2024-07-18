package com.example.mobilecontrolsappimproved

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.ListenerAll

class AutoBrightnessBroadcastReceiver(private val listener: ListenerAll) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null || context == null) return
        val isAutoBrightnessEnabled = Settings.System.getInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        ) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
        listener.brightnessStateChange(isAutoBrightnessEnabled)
        Toast.makeText(context, "Auto Brightness State Changed: $isAutoBrightnessEnabled", Toast.LENGTH_SHORT).show() // Debugging toast
    }
}
