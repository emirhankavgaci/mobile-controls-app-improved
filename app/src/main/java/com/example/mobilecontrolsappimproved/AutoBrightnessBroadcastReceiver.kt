package com.example.mobilecontrolsappimproved

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Settings
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.ListenerAll

class AutoBrightnessBroadcastReceiver(private val onAutoBrightnessChanged: ListenerAll) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && intent.action == "android.settings.SCREEN_BRIGHTNESS_MODE_CHANGED") {
            val isAutoBrightnessEnabled = Settings.System.getInt(
                context?.contentResolver,
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
            ) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
            onAutoBrightnessChanged.brightnessStateChange(isAutoBrightnessEnabled)
        }
    }
}