package com.example.mobilecontrolsappimproved

import android.app.NotificationManager
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraManager
import android.media.AudioManager
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity.BLUETOOTH_SERVICE
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.FlashlightManager
import com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved.ListenerAll

class ButtonAdapter(
    private val context: Context,
    private var buttonItems: List<ButtonItem>,
    private val listenerAll: ListenerAll
) : RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder>() {

    class ButtonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.button)
        val imageV: ImageView = view.findViewById(R.id.imageView)
        val textV : TextView = view.findViewById(R.id.textView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.button_layouts, parent, false)
        return ButtonViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val buttonItem = buttonItems[position]
        val state = buttonItem.state
        holder.button.background = ContextCompat.getDrawable(context, if (buttonItem.isEnabled) state.enabledBackground else state.disabledBackground)
        holder.imageV.setImageDrawable(ContextCompat.getDrawable(context, if (buttonItem.isEnabled) state.enabledIcon else state.disabledIcon))
        holder.textV.text = if(buttonItem.isEnabled) state.enabledText else state.disabledText

        holder.button.setOnClickListener {
            when (buttonItem.state) {
                ButtonState.Bluetooth -> openBluetooth()
                ButtonState.Flashlight -> {
                    val activity = context as MainActivity
                    activity.flashlightManager.toggleFlashlight()
                }
                ButtonState.Mute -> toggleMute()
                ButtonState.Location -> openLocation()
                ButtonState.Wifi -> openWifi()
                ButtonState.MobileData -> openMobileData()
                ButtonState.AirplaneMode -> openAirplaneModeSettings()
                ButtonState.LockOrientation -> toggleOrientationLock()
                ButtonState.AutoBrightness -> toggleAutoBrightness()
                ButtonState.Hotspot -> toggleHotspot()
                else -> {}
            }
            notifyItemChanged(position)
        }
    }

    fun updateStates(newButtonItems: List<ButtonItem>) {
        buttonItems = newButtonItems
        notifyDataSetChanged()
    }
    @RequiresApi(Build.VERSION_CODES.S)
    private fun openMobileData() {
        val intent = Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY)
        context.startActivity(intent)
    }

    private fun openBluetooth() {
        val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
        context.startActivity(intent)
    }

    fun updateAirplaneModeState(isAirplaneModeOn: Boolean) {
        buttonItems.find { it.state == ButtonState.AirplaneMode }?.isEnabled = isAirplaneModeOn
        notifyDataSetChanged()
    }
    private fun toggleMute() {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (notificationManager.isNotificationPolicyAccessGranted) {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val currentMode = audioManager.ringerMode
            if (currentMode == AudioManager.RINGER_MODE_SILENT) {
                audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
            } else {
                audioManager.ringerMode = AudioManager.RINGER_MODE_SILENT
            }
            listenerAll.muteStateChange(audioManager.ringerMode == AudioManager.RINGER_MODE_SILENT)
        } else {
            Toast.makeText(context, "Do Not Disturb access not granted", Toast.LENGTH_SHORT).show()
        }
    }


    private fun openLocation() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        context.startActivity(intent)

    }
    private fun openWifi() {
        val intent = Intent(Settings.Panel.ACTION_WIFI)
        context.startActivity(intent) }

    private fun openAirplaneModeSettings() {
        val intent = Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS)
        context.startActivity(intent)
    }

    private fun toggleAutoBrightness() {
        val isEnabled = Settings.System.getInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        ) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC

        Settings.System.putInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            if (isEnabled) Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL else Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC
        )

        Toast.makeText(context, if (isEnabled) "Auto Brightness OFF" else "Auto Brightness ON", Toast.LENGTH_SHORT).show()
    }
    private fun toggleOrientationLock() {
        val isLocked = Settings.System.getInt(
            context.contentResolver,
            Settings.System.ACCELEROMETER_ROTATION,
            1
        ) == 0

        Settings.System.putInt(
            context.contentResolver,
            Settings.System.ACCELEROMETER_ROTATION,
            if (isLocked) 1 else 0
        )

        Toast.makeText(
            context,
            if (isLocked) "Orientation Lock OFF" else "Orientation Lock ON",
            Toast.LENGTH_SHORT
        ).show()

    }
    private fun toggleHotspot() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.setClassName(
            "com.android.settings",
            "com.android.settings.TetherSettings"
        )
        context.startActivity(intent)

    }
    override fun getItemCount(): Int = buttonItems.size
}
