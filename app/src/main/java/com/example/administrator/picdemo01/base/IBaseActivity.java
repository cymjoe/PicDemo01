package com.example.administrator.picdemo01.base;

import android.app.Activity;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/5/8.
 */

public interface IBaseActivity {
    /**
     * 显示dialog
     */
    void showDialog();

    /**
     * 隐藏dialog
     */
    void hintDialog();

    /**
     * 关闭输入法
     * @param activity
     */
    void 关闭输入法(Activity activity);

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    boolean hasSdcard();

    /**
     * 显示日期选择器
     *
     * @param textView
     */
    void showDatePicker(TextView textView);


}
