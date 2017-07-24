package com.zhjh.library.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.zhjh.library.utils.ToastUtils;


public class NetWorkReceiver extends BroadcastReceiver {
    private ConnectivityManager connectivityManager;
    private NetworkInfo info;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

            connectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            info = connectivityManager.getActiveNetworkInfo();
            if(info != null && info.isAvailable()) {
                ToastUtils.showToast(context,"网络已可用！");
            } else {
                ToastUtils.showToast(context,"网络不可用，请检查设置！");
            }
        }
    }
}
