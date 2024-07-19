package com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved

interface ListenerAll {

    fun bluetoothStateChange(isBtState:Boolean)
    fun wifiStateChange(isWifiState:Boolean)
    fun flashStateChange(isFlashlightState : Boolean)
    fun dataStateChange(isDataState : Boolean)
    fun brightnessStateChange(isAutoBrightState: Boolean)
    fun orientationStateChange(isOrientationLockedState: Boolean)
    fun muteStateChange(isMuteState: Boolean)
    fun onHotspotChanged(isHotspotActive : Boolean)
    fun onLocationChanged(isLocationActive : Boolean)
    fun onAirplaneModeChanged(isAirplaneMode : Boolean)

}