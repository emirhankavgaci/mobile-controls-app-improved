package com.example.mobilecontrolsappimproved

import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.ListenerBluetooth

class MainActivity : AppCompatActivity(),ListenerBluetooth {

    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonAdapter: ButtonAdapter
    private lateinit var bluetoothReceiver: BluetoothBroadcastReceiver
    private  lateinit var  wifiReceiver : WifiBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bluetoothReceiver = BluetoothBroadcastReceiver(this)
        val filterBt = IntentFilter().apply {
            addAction(BluetoothAdapter.ACTION_STATE_CHANGED)
        }
       // val filterWifi = IntentFilter().apply
       // registerReceiver(bluetoothReceiver, filterBt)


        val buttonItems = listOf(
            ButtonItem(ButtonState.Wifi, false),
            ButtonItem(ButtonState.MobileData, false),
            ButtonItem(ButtonState.Bluetooth, false),
            ButtonItem(ButtonState.Flashlight, false),
            ButtonItem(ButtonState.AirplaneMode,false),
            ButtonItem(ButtonState.AutoBrightness,false),
            ButtonItem(ButtonState.Screenshot,false),
            ButtonItem(ButtonState.LockOrientation,false),
            ButtonItem(ButtonState.LockScreen,false),
            ButtonItem(ButtonState.Mute,false),
            ButtonItem(ButtonState.Hotspot,false),
            ButtonItem(ButtonState.Location,false)
        )

        buttonAdapter = ButtonAdapter(this,buttonItems)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // 4 columns
        recyclerView.adapter = buttonAdapter
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(bluetoothReceiver)
    }

    private fun stateChange(isBtState: Boolean, isWifiState: Boolean) : List<ButtonItem>
    {
            val buttonItems = listOf(
            ButtonItem(ButtonState.Wifi, isWifiState),
            ButtonItem(ButtonState.MobileData, false),
            ButtonItem(ButtonState.Bluetooth, isBtState),
            ButtonItem(ButtonState.Flashlight, false),
            ButtonItem(ButtonState.AirplaneMode,false),
            ButtonItem(ButtonState.AutoBrightness,false),
            ButtonItem(ButtonState.Screenshot,false),
            ButtonItem(ButtonState.LockOrientation,false),
            ButtonItem(ButtonState.LockScreen,false),
            ButtonItem(ButtonState.Mute,false),
            ButtonItem(ButtonState.Hotspot,false),
            ButtonItem(ButtonState.Location,false))

        return buttonItems
    }



    override fun bluetoothStateChange(isBtState: Boolean) : Boolean {
        if (isBtState)
            Toast.makeText(this, "Bluetooth turned on", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Bluetooth turned off", Toast.LENGTH_SHORT).show()
            // Add more items as needed
        val buttonItems = stateChange(isBtState,isWifiState = true)
        buttonAdapter = ButtonAdapter(this,buttonItems)
        recyclerView.adapter = buttonAdapter
        return isBtState
    }

    override fun wifiStateChange(isWifiState: Boolean) : Boolean {
        return true
    }


    class BluetoothBroadcastReceiver (val listenerBluetooth: ListenerBluetooth): BroadcastReceiver() {

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
                            // Bluetooth turned on
                            listenerBluetooth.bluetoothStateChange(true)

                            // Update UI or perform actions here
                        }
                        // Handle other states if needed
                    }
                }
            }
        }
    }

    class WifiBroadcastReceiver (val listenerWifi: ListenerBluetooth): BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent == null || context == null) return

            when (intent.action) {
                WifiManager.WIFI_STATE_CHANGED_ACTION-> {
                    val state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,WifiManager.WIFI_STATE_UNKNOWN)
                    when (state) {
                        BluetoothAdapter.STATE_OFF -> {
                            listenerWifi.wifiStateChange(false)
                        }
                        BluetoothAdapter.STATE_ON -> {
                            // Bluetooth turned on
                            listenerWifi.wifiStateChange(true)

                            // Update UI or perform actions here
                        }
                        // Handle other states if needed
                    }
                }
            }
        }
    }



}




