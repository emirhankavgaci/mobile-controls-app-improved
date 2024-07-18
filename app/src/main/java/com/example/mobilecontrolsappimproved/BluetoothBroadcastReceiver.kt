package com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BluetoothBroadcastReceiver(val listenerBluetooth: ListenerAll) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent == null || context == null) return

        when (intent.action) {
            BluetoothAdapter.ACTION_STATE_CHANGED -> {
                val state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR)
                when (state) {
                    BluetoothAdapter.STATE_OFF -> {
                        listenerBluetooth.bluetoothStateChange(false)
                    }
                    BluetoothAdapter.STATE_ON -> {
                        listenerBluetooth.bluetoothStateChange(true)
                    }
                }
            }
        }
    }
}
