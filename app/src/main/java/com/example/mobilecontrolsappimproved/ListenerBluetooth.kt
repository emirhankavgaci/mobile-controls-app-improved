package com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved

interface ListenerBluetooth {

    fun bluetoothStateChange(isBtState:Boolean) : Boolean
    fun wifiStateChange(isWifiState:Boolean) : Boolean


}