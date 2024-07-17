package com.example.mobilecontrolsappimproved

//add icon
//stage 0 Off 1 0n 2 Low 3 Mid 4 High

enum class ButtonState(
    val enabledIcon: Int,
    val disabledIcon: Int,
    val enabledText: String,
    val disabledText: String,
    val enabledBackground : Int,
    val disabledBackground : Int
) {
    Bluetooth(R.drawable.ic_bluetooth_enabled, R.drawable.ic_bluetooth_disabled, "Bluetooth: ON", "Bluetooth: OFF",R.drawable.button_design,R.drawable.button_off_design),
    Flashlight(R.drawable.ic_flashlight_enabled, R.drawable.ic_flashlight_disabled, "Flashlight: ON", "Flashlight: OFF",R.drawable.button_design,R.drawable.button_off_design),
    Wifi(R.drawable.ic_wifi_enabled, R.drawable.ic_wifi_disabled, "Wifi: ON", "Wifi: OFF",R.drawable.button_design,R.drawable.button_off_design),
    MobileData(R.drawable.ic_mobiledata_enabled, R.drawable.ic_mobiledata_disabled, "Mobile Data: ON", "Mobile Data: OFF",R.drawable.button_design,R.drawable.button_off_design),
    AirplaneMode(R.drawable.ic_airplane_enabled, R.drawable.ic_airplane_disabled, "Mobile Data: ON", "Mobile Data: OFF",R.drawable.button_design,R.drawable.button_off_design),
    AutoBrightness(R.drawable.ic_abrightness_enabled, R.drawable.ic_abrightness_disabled, "Mobile Data: ON", "Mobile Data: OFF",R.drawable.button_design,R.drawable.button_off_design),
    Screenshot(R.drawable.ic_screenshot, R.drawable.ic_screenshot, "Mobile Data: ON", "Mobile Data: OFF",R.drawable.button_design,R.drawable.button_off_design),
    LockOrientation(R.drawable.ic_screenlock_enabled, R.drawable.ic_screenlock_disabled, "Mobile Data: ON", "Mobile Data: OFF",R.drawable.button_design,R.drawable.button_off_design),
    LockScreen(R.drawable.ic_screenlock, R.drawable.ic_screenlock, "Mobile Data: ON", "Mobile Data: OFF",R.drawable.button_design,R.drawable.button_off_design),
    Mute(R.drawable.ic_mute_enabled, R.drawable.ic_mute_disabled, "Mobile Data: ON", "Mobile Data: OFF",R.drawable.button_design,R.drawable.button_off_design),
    Hotspot(R.drawable.ic_hotspot_enabled, R.drawable.ic_hotspot_disabled, "Mobile Data: ON", "Mobile Data: OFF",R.drawable.button_design,R.drawable.button_off_design),
    Location(R.drawable.ic_location_enabled, R.drawable.ic_location_disabled, "Mobile Data: ON", "Mobile Data: OFF",R.drawable.button_design,R.drawable.button_off_design),
}
