package com.example.mobilecontrolsappimproved

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraManager
import android.media.AudioManager
import android.net.wifi.WifiManager
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.BLUETOOTH_SERVICE
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ButtonAdapter(
    private val context: Context,
    private val buttonItems: List<ButtonItem>
) : RecyclerView.Adapter<ButtonAdapter.ButtonViewHolder>() {

    class ButtonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.button)
        val imageV: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ButtonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.button_layouts, parent, false)
        return ButtonViewHolder(view)
    }

    override fun onBindViewHolder(holder: ButtonViewHolder, position: Int) {
        val buttonItem = buttonItems[position]
        val state = buttonItem.state
        holder.button.background = ContextCompat.getDrawable(context,if (buttonItem.isEnabled) state.enabledBackground else state.disabledBackground)
        holder.imageV.setImageDrawable(ContextCompat.getDrawable(context,if (buttonItem.isEnabled) state.enabledIcon else state.disabledIcon)
        )

        holder.button.setOnClickListener {
            buttonItem.isEnabled = !buttonItem.isEnabled
            notifyItemChanged(position)

            when (buttonItem.state) {
                ButtonState.Bluetooth -> openBluetooth(buttonItem.isEnabled)
                ButtonState.Flashlight -> toggleFlashlight(buttonItem.isEnabled)
                ButtonState.Mute -> toggleMute()
                ButtonState.Location -> openLocation()
                ButtonState.Wifi -> openWifi()
                ButtonState.AirplaneMode -> openAirplaneModeSettings()
                else -> {}
            }
        }
    }

    private fun openBluetooth(enabled: Boolean) {
            //val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
            //context.startActivity(intent)
    }

    private fun toggleFlashlight(enabled: Boolean) {
        val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            val cameraId = cameraManager.cameraIdList[0]
            cameraManager.setTorchMode(cameraId, enabled)
        } catch (e: Exception) {
            println("Error toggling flashlight: $e")
        }
    }

    private fun toggleMute() {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if (audioManager.isStreamMute(AudioManager.STREAM_SYSTEM)) {
            // Unmute all streams
            audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_UNMUTE, 0)
            audioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, 0)
            audioManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_UNMUTE, 0)
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE, 0)
           // audioManager.adjustStreamVolume(AudioManager.STREAM_ALARM,AudioManager.ADJUST_UNMUTE, 0)
        } else {
            // Mute all streams
            audioManager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_MUTE, 0)
            audioManager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_MUTE, 0)
            audioManager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_MUTE, 0)
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0)
           // audioManager.adjustStreamVolume(AudioManager.STREAM_ALARM,AudioManager.ADJUST_MUTE, 0)
        }
    }

    private fun openLocation() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        context.startActivity(intent)

    }
    private fun openWifi() {
            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
            context.startActivity(intent) }

    private fun openAirplaneModeSettings() {
        val intent = Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS)
        context.startActivity(intent)
    }
    override fun getItemCount(): Int = buttonItems.size
}
