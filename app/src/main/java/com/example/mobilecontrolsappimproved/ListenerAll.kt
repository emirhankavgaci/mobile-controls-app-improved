package com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved

interface ListenerAll {

    fun bluetoothStateChange(isBtState:Boolean)
    fun wifiStateChange(isWifiState:Boolean)
    fun flashStateChange(isFlashlightState : Boolean)
    fun dataStateChange(isDataState : Boolean)
    fun brightnessStateChange(isAutoBright: Boolean)
    fun orientationStateChange(isOrientationLocked: Boolean)

}