package com.example.mobilecontrolsappimproved

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.ListenerAll

class MuteBroadcastReceiver(private val listenerMute: ListenerAll) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent == null || context == null) return

        // Check if the action is audio state change
        if (intent.action == AudioManager.RINGER_MODE_CHANGED_ACTION) {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            when (audioManager.ringerMode) {
                AudioManager.RINGER_MODE_SILENT -> {
                    listenerMute.muteStateChange(true)
                }
                AudioManager.RINGER_MODE_NORMAL -> {
                    listenerMute.muteStateChange(false)
                }
                AudioManager.RINGER_MODE_VIBRATE -> {
                    // Vibrate mode can be considered as not completely muted, handle as per your need
                    listenerMute.muteStateChange(false)
                }
            }
        }
    }
}