package com.example.mobilecontrolsappimproved.com.example.mobilecontrolsappimproved

import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraManager

class FlashlightManager(val context: Context) {

    private val cameraManager: CameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var isFlashlightOn: Boolean = false
    private val cameraId: String = cameraManager.cameraIdList[0] // Assuming the device has at least one camera

    private val torchCallback = object : CameraManager.TorchCallback() {
        override fun onTorchModeChanged(cameraId: String, enabled: Boolean) {
            isFlashlightOn = enabled
            broadcastFlashlightState()
        }
    }

    init {
        cameraManager.registerTorchCallback(torchCallback, null)
    }

    fun toggleFlashlight() {
        isFlashlightOn = !isFlashlightOn
        cameraManager.setTorchMode(cameraId, isFlashlightOn)
    }

    private fun broadcastFlashlightState() {
        val intent = Intent("com.example.ACTION_FLASHLIGHT_STATE_CHANGED")
        intent.putExtra("flashlight_state", isFlashlightOn)
        context.sendBroadcast(intent) // Use system-wide broadcast
    }

    fun getFlashlightState(): Boolean {
        return isFlashlightOn
    }
}
