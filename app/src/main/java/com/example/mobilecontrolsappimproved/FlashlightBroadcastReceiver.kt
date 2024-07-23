    package com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved

    import android.content.BroadcastReceiver
    import android.content.Context
    import android.content.Intent

    class FlashlightBroadcastReceiver(private val listenerFlashlight: ListenerAll) : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent == null || context == null) return
            if (intent.action == "com.example.ACTION_FLASHLIGHT_STATE_CHANGED") {
                val state = intent.getBooleanExtra("flashlight_state", false)
                listenerFlashlight.flashStateChange(state)
            }
        }
    }