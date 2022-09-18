package com.rzandroid.propermission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProPermission {
    private final Activity activity;
    private final OnPermissionListener onPermissionListener;
    private int requestCode = -1;
    private final boolean isDebug;

    private ProPermission(Builder argBuilder) {
        this.activity = argBuilder.activity;
        this.onPermissionListener = argBuilder.listener;
        isDebug = argBuilder.isDebug;
    }

    public void onRequestPermissionsResult(String[] argPermissions, int[] argGrantResults) {
        List<String> grantedPermissions = new ArrayList<>();
        List<String> deniedPermissions = new ArrayList<>();
        for (int i = 0; i < argGrantResults.length; i++) {
            if (argGrantResults[i] == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(argPermissions[i]);
            } else {
                grantedPermissions.add(argPermissions[i]);
            }
        }
        onLogPrint("Granted: " + grantedPermissions.size()
                + " Denied: " + deniedPermissions.size()
                + " Total: " + argPermissions.length);
        if (grantedPermissions.size() == argPermissions.length) {
            onPermissionListener.onAllPermissionsGranted(Arrays.asList(argPermissions));
        } else {
            if (deniedPermissions.size() < argPermissions.length) {
                onPermissionListener.onPermissionsGranted(grantedPermissions);
            }
            onPermissionListener.onPermissionsDenied(deniedPermissions);
        }
    }

    public void request(int argRequestCode, String... argPermissions) {
        requestCode = argRequestCode;
        List<String> permissionNeeded = new ArrayList<>();
        for (String permission : argPermissions) {
            if (!hasPermission(permission)) {
                /*if(ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    permissionNeeded.add(permission);
                }*/
                permissionNeeded.add(permission);
            }
        }
        if (permissionNeeded.size() > 0) {
            onLogPrint("Size of permission needed: " + permissionNeeded.size());
            //ActivityCompat.requestPermissions(activity, permissionNeeded.toArray(new String[0]), requestCode);
            ActivityCompat.requestPermissions(activity, permissionNeeded.toArray(new String[permissionNeeded.size()]), requestCode);
        }
        /*else {
            if (permissionNeeded.size() > 0) {
                //showToast("permissionNeeded: " + permissionNeeded.size());
                ActivityCompat.requestPermissions(activity, new String[]{permissionNeeded.get(0)}, requestCode);
            }
        }*/
    }

    public boolean hasPermission(String... argPermissions) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onLogPrint("Less than marshmello");
            return true;
        }
        for (String permission : argPermissions) {
            /*if(ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showToast("hasPermission: " + permission);
                if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }*/
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                onLogPrint("Permission needed: " + permission);
                return false;
            }
        }
        return true;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public static class Builder {
        private Activity activity;
        private OnPermissionListener listener;
        private boolean isDebug = true;

        public Builder with(Activity argActivity) {
            this.activity = argActivity;
            return this;
        }

        public Builder listener(OnPermissionListener argListener) {
            this.listener = argListener;
            return this;
        }

        public Builder isDebug(boolean argIsDebug) {
            isDebug = argIsDebug;
            return this;
        }

        public ProPermission build() {
            return new ProPermission(this);
        }
    }

    private void showToast(String argMessage) {
        if (isDebug) {
            Toast.makeText(activity, argMessage, Toast.LENGTH_LONG).show();
        }
    }

    private void onLogPrint(String argMessage) {
        int stackIndex = 3;
        if (isDebug) {
            System.out.println("DEBUG_LOG_PRINT: " + argMessage
                    + " Class: " + Thread.currentThread().getStackTrace()[stackIndex].getClassName()
                    + " Method: " + Thread.currentThread().getStackTrace()[stackIndex].getMethodName()
                    + " Line: " + Thread.currentThread().getStackTrace()[stackIndex].getLineNumber());
        }
    }

    public interface OnPermissionListener {
        void onAllPermissionsGranted(List<String> argPermissions);

        void onPermissionsGranted(List<String> argPermissions);

        void onPermissionsDenied(List<String> argPermissions);
    }
}
/**
 * ProPermission class usages
 * <p>
 * Step 1:
 * private ProPermission proPermission;
 * private static final String[] ALL_PERMISSIONS = {
 * Manifest.permission.ACCESS_NETWORK_STATE,
 * Manifest.permission.READ_PHONE_STATE,
 * Manifest.permission.READ_EXTERNAL_STORAGE,
 * Manifest.permission.WRITE_EXTERNAL_STORAGE,
 * };
 * private final int PERMISSION_REQUEST_CODE = 1001;
 * <p>
 * <p>
 * Step 2:
 * proPermission = new ProPermission.Builder()
 * .with(activity)
 * .isDebug(true)
 * .listener(new OnPermissionListener() {
 *
 * @Override public void onAllPermissionsGranted(List<String> argPermissions) {
 * //TODO: listener callback
 * }
 * @Override public void onPermissionsGranted(List<String> argPermissions) {
 * //TODO: listener callback
 * }
 * @Override public void onPermissionsDenied(List<String> argPermissions) {
 * //TODO: listener callback
 * }
 * })
 * .build();
 * <p>
 * Step 3:
 * if (!proPermission.hasPermission(ALL_PERMISSIONS)) {
 * proPermission.request(PERMISSION_REQUEST_CODE, ALL_PERMISSIONS);
 * }
 * <p>
 * <p>
 * Step 4:
 * @Override public void onRequestPermissionsResult(int argRequestCode, @NonNull String[] argPermissions, @NonNull int[] argGrantResults) {
 * super.onRequestPermissionsResult(argRequestCode, argPermissions, argGrantResults);
 * proPermission.onRequestPermissionsResult(argPermissions, argGrantResults);
 * }
 * <p>
 * Check Permissions
 * To check if the app already has permissions use
 * <p>
 * proPermission.hasPermission(String permission) - To check one permission at a time
 * proPermission.hasPermission(String[] permissions) - To check multiple permissions at the same time
 * <p>
 * Request Permissions
 * proPermission.request(String permission) - To request one permission at a time
 * proPermission.request(String[] permissions) - To request multiple permissions at the same time.
 * That's pretty much it and your all wrapped up.
 */
