package com.example.administrator.picdemo01.utils;

import android.content.Context;

import java.io.File;

/**
 * Created by Administrator on 2018/3/28.
 */

public class FileUtil {
    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }
}