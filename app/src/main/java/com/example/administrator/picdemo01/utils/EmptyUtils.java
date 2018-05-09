package com.example.administrator.picdemo01.utils;

import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.EditText;

import java.util.List;

/**
 *
 */
public class EmptyUtils {

    public static boolean isEmpty(String string) {
        if (TextUtils.isEmpty(string) || string.equals("null") || string.equals("undefined")) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(List list) {
        if (list == null || list.isEmpty() || list.size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        return false;
    }
    /**
     * 禁止EditText输入特殊字符
     */
    public static void setEditTextInhibitInputSpeChat(EditText editText) {
        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                return getNewStringNoNum(source.toString());
            }
        };
        editText.setFilters(new InputFilter[]{filter});
    }

    //禁止输入数字
    private static String getNewStringNoNum(String str) {
        char[] chars = str.toCharArray();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            if ((chars[i] >= 19968 && chars[i] <= 40869) || (chars[i] >= 97 && chars[i] <= 122) || (chars[i] >= 65 && chars[i] <= 90)) {
                buffer.append(chars[i]);
            }
        }
        return buffer.toString();
    }

}