package com.example.administrator.picdemo01.utils;

/**
 * Created by Administrator on 2018/5/8.
 */

public interface PermissionsResultListener {
    /**
     * 权限
     */
    void onPermissionGranted();

    void onPermissionDenied();

}
