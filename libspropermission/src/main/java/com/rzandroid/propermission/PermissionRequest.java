package com.rzandroid.propermission;

import android.Manifest;

public class PermissionRequest {
    public static class Code {
        public static final int INTERNET = 10001;
        public static final int LOCATION = 10010;
        public static final int READ_EXTERNAL = 10020;
        public static final int WRITE_EXTERNAL = 10021;
        public static final int READ_PHONE_STATE = 10030;
        public static final int READ_LOGS = 10050;
    }
    public static class Permission {
        public static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
        public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    }
}
//https://github.com/mukeshsolanki/easypermissions-android
//https://alvinalexander.com/source-code/android-how-send-message-from-thread-to-handler/
//https://blog.mindorks.com/android-core-looper-handler-and-handlerthread-bd54d69fe91a
//https://androidexample.com/Thread_With_Handlers_-_Android_Example/index.php?view=article_discription&aid=58
//https://stackoverflow.com/questions/32714787/android-m-permissions-onrequestpermissionsresult-not-being-called
