package com.zhjh.library.base;

import rx.Subscriber;

/**
 * 所有javabean的基类，包含返回的公共属性
 * <p/>
 * BaseEvent
 * <p/>
 * 2014-12-9 下午2:40:40
 *
 * @version 1.0.0
 */

public class BaseSubscriber<T>  extends Subscriber<T> {

    //出错提示
    public final String networkMsg = "服务器开小差";
    public final String parseMsg = "数据解析出错";
    public final String net_connection = "网络连接错误";
    public final String unknownMsg = "未知错误";

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T entity) {

    }
}

