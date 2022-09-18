package com.rzandroid.usagespropermission;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.rzandroid.propermission.ProPermission;

import java.util.List;

public class ActivitySplash extends AppCompatActivity {
    private Activity activity;
    private Context context;
    private ProPermission proPermission;
    private static final String[] ALL_PERMISSIONS = {
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private final int PERMISSION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        context = this;
        setContentView(R.layout.activity_splash);
        //
        proPermission = new ProPermission.Builder()
                .with(activity)
                .isDebug(true)
                .listener(new ProPermission.OnPermissionListener() {
                    @Override
                    public void onAllPermissionsGranted(List<String> argPermissions) {
                        //TODO: listener callback
                    }

                    @Override
                    public void onPermissionsGranted(List<String> argPermissions) {
                        //TODO: listener callback
                    }

                    @Override
                    public void onPermissionsDenied(List<String> argPermissions) {
                        //TODO: listener callback
                    }
                })
                .build();
        if (!proPermission.hasPermission(ALL_PERMISSIONS)) {
            proPermission.request(PERMISSION_REQUEST_CODE, ALL_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        proPermission.onRequestPermissionsResult(permissions, grantResults);
    }
}