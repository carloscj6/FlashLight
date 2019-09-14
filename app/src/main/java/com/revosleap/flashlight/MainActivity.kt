package com.revosleap.flashlight

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Vibrator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.widget.ToggleButton

class MainActivity : AppCompatActivity() {
    private var flash: ToggleButton? = null
    private var camera: Camera? = null
    private var isFlashOn: Boolean = false
    private var hasFlash: Boolean = false
    private var params: Camera.Parameters? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        flash = findViewById(R.id.toggleButton)

        hasFlash = applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
        getCamera()
        flash!!.setOnClickListener { view ->
            val on = (view as ToggleButton).isChecked
            if (on) {
                if (hasFlash) {
                    vibrate()
                    turnOnFlash()
                    Toast.makeText(this@MainActivity, "Flash On ", Toast.LENGTH_SHORT).show()

                } else
                    Toast.makeText(this@MainActivity, "Flash Not Supported", Toast.LENGTH_SHORT).show()


            } else {
                vibrate()
                turnOffFlash()
                Toast.makeText(this@MainActivity, "Flash Off", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun vibrate() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(70)
    }

    private fun turnOnFlash() {

        if (!isFlashOn) {
            if (camera == null || params == null) {
                return
            }

            params = camera!!.parameters
            //noinspection deprecation,deprecation
            params!!.flashMode = Camera.Parameters.FLASH_MODE_TORCH
            camera!!.parameters = params
            camera!!.startPreview()
            isFlashOn = true
        }

    }

    private fun turnOffFlash() {

        if (isFlashOn) {
            if (camera == null || params == null) {
                return
            }

            params = camera!!.parameters
            //noinspection deprecation,deprecation
            params!!.flashMode = Camera.Parameters.FLASH_MODE_OFF
            camera!!.parameters = params
            camera!!.stopPreview()
            isFlashOn = false
        }
    }

    private fun getCamera() {

        if (camera == null) {
            try {

                camera = Camera.open()
                params = camera!!.parameters
            } catch (e: Exception) {

            }

        }

    }

}
