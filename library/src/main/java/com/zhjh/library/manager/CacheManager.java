package com.zhjh.library.manager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.zhjh.library.utils.FileUtil;
import com.zhjh.library.utils.ToastUtils;

import java.io.File;

/**
 * Created by zhjh on 2016/3/14.
 */
public class CacheManager {

    /**
     * 计算缓存的大小
     */
    public static String caculateCacheSize(Context context) {
        long fileSize = 0;
        String cacheSize = "";
        File filesDir = context.getFilesDir();
        File cacheDir = context.getCacheDir();

        fileSize += FileUtil.getDirSize(filesDir);
        fileSize += FileUtil.getDirSize(cacheDir);

        if (fileSize > 0)
            cacheSize = FileUtil.formatFileSize(fileSize);
        return cacheSize;
    }

    public static void clearAppCache(final Context context) {
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    ToastUtils.showToast(context,"缓存清除成功");
                } else {
                    ToastUtils.showToast(context,"缓存清除失败");

                }
            }
        };
        new Thread() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    // 清除数据缓存
                    DataCleanManager.cleanInternalCache(context);
                    msg.what = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = -1;
                }
                handler.sendMessage(msg);
            }
        }.start();
    }



}

