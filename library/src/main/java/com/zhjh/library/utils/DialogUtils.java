package com.zhjh.library.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import java.lang.ref.WeakReference;

/**
 * @ClassName: DialogUtils
 * @Description: 提示框工具类
 */
public class DialogUtils {
    public  static MaterialDialog dialog;

    private static WeakReference<Activity> reference;

    public static void showLoadingProgress(Context context) {

        showLoadingProgress(context, true);
    }

    public static void showLoadingProgress(Context context, boolean cancelable) {
        showLoadingProgress(context, cancelable, "加载中...");
    }

    public  static void showLoadingProgress(Context context, boolean cancelable, String showContent) {
        if (dialog == null || reference == null || reference.get() == null || reference.get().isFinishing()) {
            reference = new WeakReference<>((Activity) context);
            }

            dialog = new MaterialDialog.Builder(context).theme(Theme.LIGHT).content(showContent).progress(true, 0).progressIndeterminateStyle(false).show();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(cancelable);
        
    }

    public  static void showLoadingProgress(Context context,boolean cancelable,String showContent,MaterialDialog.OnDismissListener dismissListener) {

        if (dialog == null || reference == null || reference.get() == null || reference.get().isFinishing()) {
            reference = new WeakReference<>((Activity) context);
              }
            dialog = new MaterialDialog.Builder(context).theme(Theme.LIGHT).dismissListener(dismissListener).content(showContent).progress(true, 0).progressIndeterminateStyle(false).show();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(cancelable);
      
    }

    public static void showDialog(Context context, boolean cancelable, String showtitle,
                                  String positivetext,MaterialDialog.SingleButtonCallback positivecallback) {
        if (dialog == null || reference == null || reference.get() == null || reference.get().isFinishing()) {
            reference = new WeakReference<>((Activity) context);
             }
            dialog = new MaterialDialog.Builder(context).theme(Theme.LIGHT).title(showtitle).positiveText(positivetext).onPositive(positivecallback).show();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(cancelable);
       

    }

    public static void showDialog(Context context, boolean cancelable, String showtitle,
                                  String positivetext,String negative,MaterialDialog.SingleButtonCallback positivecallback) {
        if (dialog == null || reference == null || reference.get() == null || reference.get().isFinishing()) {
            reference = new WeakReference<>((Activity) context);
             }
            dialog = new MaterialDialog.Builder(context).theme(Theme.LIGHT).title(showtitle).positiveText(positivetext).onPositive(positivecallback).show();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(cancelable);

       
    }




    public static void showDialog(Context context, boolean cancelable, String showtitle, String showContent,
                                  String positivetext, String negative, MaterialDialog.SingleButtonCallback positivecallback, MaterialDialog.SingleButtonCallback negativecallback) {
        if (dialog == null || reference == null || reference.get() == null || reference.get().isFinishing()) {
            reference = new WeakReference<>((Activity) context);
             }
            dialog = new MaterialDialog.Builder(context).theme(Theme.LIGHT).title(showtitle).content(showContent).positiveText(positivetext).negativeText(negative).onPositive(positivecallback).onNegative(negativecallback).show();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(cancelable);

       
    }

    /**
     * 隐藏加载进度条
     */
    public static void hintLoadingProgress() {
        if (dialog != null ) {
            dialog.dismiss();
            dialog = null;
        }     
        reference = null;
    }

}
