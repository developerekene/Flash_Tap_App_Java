package com.example.flashtapjava;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton powerButton;

    boolean hasCameraFlash = false;
    boolean isFlash = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        powerButton = findViewById(R.id.toggle_button);
        hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        powerButton.setOnClickListener(new View.OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if(hasCameraFlash){
                    if(isFlash){
                        isFlash = false;
                        powerButton.setImageResource(R.drawable.ic_power_off);
                        try {
                            flashLighOn();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }else{
                        isFlash = true;
                        powerButton.setImageResource(R.drawable.ic_power_on);
                        try {
                            flashLighOff();
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    Toast.makeText(MainActivity.this, "No Flash available on your device", Toast.LENGTH_SHORT);
                }
            }

        });
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void flashLighOn() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId, true);
        Toast.makeText(this, "FlashLight is ON", Toast.LENGTH_SHORT).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void flashLighOff() throws CameraAccessException {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = cameraManager.getCameraIdList()[0];
        cameraManager.setTorchMode(cameraId, false);
        Toast.makeText(this, "FlashLight is OFF", Toast.LENGTH_SHORT).show();
    }
}