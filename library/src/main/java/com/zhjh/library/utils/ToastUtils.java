package com.zhjh.library.utils;

import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by zhjh.
 */
public class ToastUtils {
    public static Toast toast;
    public static void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.context(), "", Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.show();
    }

    public static void showDataToast() {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.context(), "", Toast.LENGTH_SHORT);
        }
        toast.setText("数据异常，请稍后重试！");
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.show();
    }

    public static void showNetworkToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.context(), "", Toast.LENGTH_SHORT);
        }
        toast.setText("网络异常，请稍后重试！");
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.show();
    }

    /**
     * 居中显示
     * @param context
     * @param text
     */
    public static void showToastCenter(String text) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.context(), "", Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void cancleToast(){
        toast.cancel();
    }
}
