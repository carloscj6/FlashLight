package com.revosleap.flashlight;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private ToggleButton flash;
    @SuppressWarnings("deprecation")
    private Camera camera;
    private boolean isFlashOn;
    private boolean hasFlash;
    @SuppressWarnings("deprecation")
    private
    Camera.Parameters params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flash=findViewById(R.id.toggleButton);

        hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        getCamera();
        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean on=((ToggleButton)view).isChecked();
                if (on){
                    if (hasFlash){
                        vibrate();
                        turnOnFlash();
                        Toast.makeText(MainActivity.this, "Flash On ", Toast.LENGTH_SHORT).show();

                    }
                    else Toast.makeText(MainActivity.this, "Flash Not Supported", Toast.LENGTH_SHORT).show();


                }
                else {
                    vibrate();
                    turnOffFlash();
                    Toast.makeText(MainActivity.this, "Flash Off", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
    private void vibrate(){
        Vibrator vibrator= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(70);
    }
    @SuppressWarnings("deprecation")
    private void turnOnFlash() {

        if(!isFlashOn) {
            if(camera == null || params == null) {
                return;
            }

            params = camera.getParameters();
            //noinspection deprecation,deprecation
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }

    }
    @SuppressWarnings("deprecation")
    private void turnOffFlash() {

        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }

            params = camera.getParameters();
            //noinspection deprecation,deprecation
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
        }
    }
    @SuppressWarnings("deprecation")
    private void getCamera() {

        if (camera == null) {
            try {
                //noinspection deprecation
                camera = Camera.open();
                params = camera.getParameters();
            }catch (Exception e) {

            }
        }

    }

}
