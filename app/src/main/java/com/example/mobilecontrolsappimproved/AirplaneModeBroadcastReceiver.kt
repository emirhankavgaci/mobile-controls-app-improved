    package com.example.mobilecontrolsappimproved

    import android.content.BroadcastReceiver
            import android.content.Context
            import android.content.Intent
            import android.provider.Settings
            import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.ListenerAll

    class AirplaneModeBroadcastReceiver(private val listener: ListenerAll) : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent == null || context == null) return

            if (intent.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
                val isAirplaneModeOn = Settings.Global.getInt(
                    context.contentResolver,
                    Settings.Global.AIRPLANE_MODE_ON, 0
                ) != 0
                listener.onAirplaneModeChanged(isAirplaneModeOn)
            }
        }
    }
