package com.example.mobilecontrolsappimproved

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
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
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.BluetoothBroadcastReceiver
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.ListenerBluetooth
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.WifiBroadcastReceiver

class MainActivity : AppCompatActivity(),ListenerBluetooth {

    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonAdapter: ButtonAdapter
    private lateinit var bluetoothReceiver: BluetoothBroadcastReceiver
    private  lateinit var  wifiReceiver : WifiBroadcastReceiver
    private var buttonItems2 :List<ButtonItem> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        InitializeRecycler()
        wifiReceiver = WifiBroadcastReceiver(this) // receivers
        bluetoothReceiver = BluetoothBroadcastReceiver(this)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // 4 columns
        buttonAdapter = ButtonAdapter(this,buttonItems2)
        recyclerView.adapter = buttonAdapter


        val btFilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)   //filters
        registerReceiver(bluetoothReceiver, btFilter)
        val wifiFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(wifiReceiver, wifiFilter)

        setState()
    }

    private fun InitializeRecycler(){

        buttonItems2 = listOf(
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

    }

    private fun setState() {
        for (item in buttonItems2){
            when(item.state){
                ButtonState.Bluetooth ->{
                    val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
                    val bluetoothAdapter = bluetoothManager.adapter
                    item.isEnabled = bluetoothAdapter.isEnabled

                    println("-     ------------------------------ >>> ${bluetoothAdapter.isEnabled}")
                }
                ButtonState.Wifi ->{
                    val wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager
                    item.isEnabled = wifiManager.isWifiEnabled
                    println("-     ------------------------------ >>> ${wifiManager.isWifiEnabled}")
                }
                else-> {}
            }

            buttonAdapter.updateStates(buttonItems2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(bluetoothReceiver)
        unregisterReceiver(wifiReceiver)

    }

    override fun bluetoothStateChange(isBtState: Boolean) : Boolean {
        if (isBtState)
            Toast.makeText(this, "Bluetooth turned on", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Bluetooth turned off", Toast.LENGTH_SHORT).show()

        setState()
        return isBtState
    }

    override fun wifiStateChange(isWifiState: Boolean) : Boolean {
        if (isWifiState)
            Toast.makeText(this, "Wifi turned on", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Wifi turned off", Toast.LENGTH_SHORT).show()

        setState()
        return isWifiState
    }

}




