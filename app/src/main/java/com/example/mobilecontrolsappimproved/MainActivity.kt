package com.example.mobilecontrolsappimproved

import android.app.NotificationManager
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.BluetoothBroadcastReceiver
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.FlashlightBroadcastReceiver
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.FlashlightManager
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.ListenerAll
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.MobileDataManager
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.WifiBroadcastReceiver

class MainActivity : AppCompatActivity(), ListenerAll {

    private val WRITE_SETTINGS_REQUEST_CODE = 200
    private val REQUEST_NOTIFICATION_POLICY_ACCESS = 1

    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonAdapter: ButtonAdapter
    private lateinit var bluetoothReceiver: BluetoothBroadcastReceiver
    private lateinit var wifiReceiver: WifiBroadcastReceiver
    private lateinit var flashlightReceiver: FlashlightBroadcastReceiver
    private lateinit var dataReceiver: MobileDataBroadcastReceiver
    private lateinit var autoBrightnessReceiver: AutoBrightnessBroadcastReceiver
    private lateinit var orientationReceiver: OrientationBroadcastReceiver
    private lateinit var muteReceiver: MuteBroadcastReceiver
    private lateinit var hotspotReceiver: HotspotBroadcastReceiver
    private lateinit var airplaneModeReceiver: AirplaneModeBroadcastReceiver


    lateinit var flashlightManager: FlashlightManager
    private lateinit var dataManager: MobileDataManager
    private var buttonItems2: List<ButtonItem> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeRecycler()

        if (!Settings.System.canWrite(this)) {
            requestWriteSettingsPermission()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                setupReceivers()
            }
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (!notificationManager.isNotificationPolicyAccessGranted) {
                val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
                startActivityForResult(intent, REQUEST_NOTIFICATION_POLICY_ACCESS)
            }
        }


        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // 2 columns
        buttonAdapter = ButtonAdapter(this, buttonItems2, this)
        recyclerView.adapter = buttonAdapter

        val isAirplaneModeOn = Settings.Global.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON, 0) != 0
        onAirplaneModeChanged(isAirplaneModeOn)
        setState()
    }

    private fun initializeRecycler() {
        buttonItems2 = listOf(
            ButtonItem(ButtonState.Wifi, false),
            ButtonItem(ButtonState.MobileData, false),
            ButtonItem(ButtonState.Bluetooth, false),
            ButtonItem(ButtonState.Flashlight, false),
            ButtonItem(ButtonState.AirplaneMode, false),
            ButtonItem(ButtonState.AutoBrightness, false),
            ButtonItem(ButtonState.Screenshot, false),
            ButtonItem(ButtonState.LockOrientation, false),
            ButtonItem(ButtonState.LockScreen, false),
            ButtonItem(ButtonState.Mute, false),
            ButtonItem(ButtonState.Hotspot, false),
            ButtonItem(ButtonState.Location, false)
        )
    }

    override fun onResume() {
        super.onResume()
        setState()
    }

    private fun requestWriteSettingsPermission() {
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        intent.data = Uri.parse("package:$packageName")
        startActivityForResult(intent, WRITE_SETTINGS_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_NOTIFICATION_POLICY_ACCESS) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (notificationManager.isNotificationPolicyAccessGranted) {
                Toast.makeText(this, "Do Not Disturb access granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Do Not Disturb access denied", Toast.LENGTH_SHORT).show()
            }
        }
        if (requestCode == WRITE_SETTINGS_REQUEST_CODE) {
            if (Settings.System.canWrite(this)) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    setupReceivers()
                }
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun setupReceivers() {
        wifiReceiver = WifiBroadcastReceiver(this) // receivers
        bluetoothReceiver = BluetoothBroadcastReceiver(this)
        flashlightManager = FlashlightManager(this)
        flashlightReceiver = FlashlightBroadcastReceiver(this)
        autoBrightnessReceiver = AutoBrightnessBroadcastReceiver(this)
        orientationReceiver = OrientationBroadcastReceiver(this)
        dataManager = MobileDataManager(this)
        dataReceiver = MobileDataBroadcastReceiver(this)
        muteReceiver = MuteBroadcastReceiver(this)
        hotspotReceiver = HotspotBroadcastReceiver(this)
        airplaneModeReceiver = AirplaneModeBroadcastReceiver(this)

        val flashFilter = IntentFilter("com.example.ACTION_FLASHLIGHT_STATE_CHANGED")
        registerReceiver(flashlightReceiver, flashFilter, RECEIVER_NOT_EXPORTED)

        val mobileDataFilter = IntentFilter("com.example.ACTION_MOBILE_DATA_STATE_CHANGED")
        registerReceiver(dataReceiver, mobileDataFilter, RECEIVER_NOT_EXPORTED)

        val brightnessFilter = IntentFilter("com.example.ACTION_BRIGHTNESS_STATE_CHANGED")
        registerReceiver(autoBrightnessReceiver, brightnessFilter, RECEIVER_NOT_EXPORTED)

        val orientationFilter = IntentFilter("com.example.ACTION_ORIENTATION_STATE_CHANGED")
        registerReceiver(orientationReceiver, orientationFilter, RECEIVER_NOT_EXPORTED)

        val btFilter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        registerReceiver(bluetoothReceiver, btFilter)

        val wifiFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)
        registerReceiver(wifiReceiver, wifiFilter)

        val muteFilter = IntentFilter(AudioManager.RINGER_MODE_CHANGED_ACTION)
        registerReceiver(muteReceiver, muteFilter)

        val hotspotFilter = IntentFilter("android.net.wifi.WIFI_AP_STATE_CHANGED")
        registerReceiver(hotspotReceiver, hotspotFilter)

        val airplaneFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(airplaneModeReceiver, airplaneFilter)
    }

    private fun setState() {
        for (item in buttonItems2) {
            when (item.state) {
                ButtonState.Bluetooth -> {
                    val bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
                    val bluetoothAdapter = bluetoothManager.adapter
                    item.isEnabled = bluetoothAdapter.isEnabled
                }

                ButtonState.Wifi -> {
                    val wifiManager = getSystemService(WIFI_SERVICE) as WifiManager
                    item.isEnabled = wifiManager.isWifiEnabled
                }

                ButtonState.Flashlight -> {
                    item.isEnabled = flashlightManager.getFlashlightState()
                }

                ButtonState.MobileData -> {
                    item.isEnabled = dataManager.isMobileDataEnabled()
                }

                ButtonState.AutoBrightness -> {
                    val isAutoBrightnessEnabled = Settings.System.getInt(
                        contentResolver,
                        Settings.System.SCREEN_BRIGHTNESS_MODE,
                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
                    ) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
                    item.isEnabled = isAutoBrightnessEnabled
                }

                ButtonState.LockOrientation -> {
                    val isOrientationLocked = Settings.System.getInt(
                        contentResolver,
                        Settings.System.ACCELEROMETER_ROTATION,
                        1
                    ) == 0
                    item.isEnabled = isOrientationLocked
                }

                ButtonState.Mute -> {
                    item.isEnabled = isMuteEnabled()
                }

                ButtonState.Hotspot -> {
                    item.isEnabled = isHotspotEnabled()
                }

                else -> {}
            }
        }
        buttonAdapter.updateStates(buttonItems2)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(bluetoothReceiver)
        unregisterReceiver(wifiReceiver)
        unregisterReceiver(flashlightReceiver)
        unregisterReceiver(dataReceiver)
        unregisterReceiver(autoBrightnessReceiver)
        unregisterReceiver(orientationReceiver)
        unregisterReceiver(muteReceiver)
        unregisterReceiver(hotspotReceiver)
        unregisterReceiver(airplaneModeReceiver)
    }

    private fun isHotspotEnabled(): Boolean {
        try {
            val wifiManager =
                applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val method = wifiManager.javaClass.getDeclaredMethod("getWifiApState")
            method.isAccessible = true
            val state = method.invoke(wifiManager) as Int
            return state == 13 // 13 is WifiManager.WIFI_AP_STATE_ENABLED
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    private fun isMuteEnabled(): Boolean {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        return audioManager.ringerMode == AudioManager.RINGER_MODE_SILENT
    }


    private fun isMobileDataEnabled(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as android.net.ConnectivityManager
        val networkInfo =
            connectivityManager.getNetworkInfo(android.net.ConnectivityManager.TYPE_MOBILE)
        return networkInfo?.isConnected ?: false
    }

    override fun bluetoothStateChange(isBtState: Boolean) {
        if (isBtState)
            Toast.makeText(this, "Bluetooth turned on", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Bluetooth turned off", Toast.LENGTH_SHORT).show()

        setState()
    }

    override fun wifiStateChange(isWifiState: Boolean) {
        if (isWifiState)
            Toast.makeText(this, "WiFi turned on", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "WiFi turned off", Toast.LENGTH_SHORT).show()

        setState()
    }

    override fun flashStateChange(isFlashlightState: Boolean) {
        if (isFlashlightState)
            Toast.makeText(this, "Flashlight turned on", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Flashlight turned off", Toast.LENGTH_SHORT).show()

        setState()
    }

    override fun dataStateChange(isDataState: Boolean) {
        if (isDataState)
            Toast.makeText(this, "Mobile Data turned on", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Mobile Data turned off", Toast.LENGTH_SHORT).show()

        setState()
    }

    override fun brightnessStateChange(isAutoBrightState: Boolean) {
        buttonItems2.find { it.state == ButtonState.AutoBrightness }?.isEnabled = isAutoBrightState
        buttonAdapter.updateStates(buttonItems2)
    }

    override fun orientationStateChange(isOrientationLockedState: Boolean) {
        buttonItems2.find { it.state == ButtonState.LockOrientation }?.isEnabled =
            isOrientationLockedState
        buttonAdapter.updateStates(buttonItems2)
        setState()
    }

    override fun muteStateChange(isMuteState: Boolean) {
        val muteButton = buttonItems2.find { it.state == ButtonState.Mute }
        muteButton?.isEnabled = isMuteState
        buttonAdapter.updateStates(buttonItems2)
    }

    override fun onHotspotChanged(isHotspotActive: Boolean) {
        val hotspotButton = buttonItems2.find { it.state == ButtonState.Hotspot }
        hotspotButton?.isEnabled = isHotspotActive
        buttonAdapter.updateStates(buttonItems2)
    }

    override fun onLocationChanged(isLocationActive: Boolean) {
        if (isLocationActive)
            Toast.makeText(this, "Mobile Data turned on", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Mobile Data turned off", Toast.LENGTH_SHORT).show()
        setState()
    }

    override fun onAirplaneModeChanged(isAirplaneMode: Boolean) {
        buttonAdapter.updateAirplaneModeState(isAirplaneMode)
    }

}