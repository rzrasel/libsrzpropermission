package com.rzandroid.propermission;

import java.util.List;

interface OnPermissionListener {
    void onAllPermissionsGranted(List<String> argPermissions);

    void onPermissionsGranted(List<String> argPermissions);

    void onPermissionsDenied(List<String> argPermissions);
}
