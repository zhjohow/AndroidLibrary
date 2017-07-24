package com.zhjh.library.manager;

import android.util.Log;

import com.zhjh.library.base.BaseApplication;
import com.zhjh.library.utils.HttpUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by zhang on 2016/10/17.
 */

public class HttpManager {
    //设缓存有效期为5天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 5;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
    private static final String CACHE_CONTROL_NETWORK = "max-age=1";

    private static volatile OkHttpClient sOkHttpClient;

    // 云端响应头拦截器，用来配置缓存策略
    private static Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!HttpUtils.isNetworkConnected(BaseApplication.context())) {

                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
//                DialogUtils.hintLoadingProgress();
            }
            Response originalResponse = chain.proceed(request);

            if (HttpUtils.isNetworkConnected(BaseApplication.context())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder().header("Cache-Control", cacheControl)
                        .removeHeader("Pragma").build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached," + CACHE_STALE_SEC)
                        .removeHeader("Pragma").build();
            }



//            Response responseLatest;
//            if (HttpUtils.isNetworkConnected(BaseApplication.context())) {
//                int maxAge = 60; //有网失效一分钟
//                responseLatest = originalResponse.newBuilder()
//                        .removeHeader("Pragma")
//                        .removeHeader("Cache-Control")
//                        .header("Cache-Control", "public, max-age=" + maxAge)
//                        .build();
//            } else {
//                int maxStale = 60 * 60 * 6; // 没网失效6小时
//                responseLatest= originalResponse.newBuilder()
//                        .removeHeader("Pragma")
//                        .removeHeader("Cache-Control")
//                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                        .build();
//            }
//            return  responseLatest;
        }
    };

//    // 打印返回的json数据拦截器
//    private static Interceptor mLoggingInterceptor = new Interceptor() {
//        @Override
//        public okhttp3.Response intercept(Chain chain) throws IOException {
//
//            Request request = chain.request();
//            Request.Builder requestBuilder = request.newBuilder();
//            Request signedRequest = requestBuilder
//                    .build();
//
//            okhttp3.Response response = chain.proceed(signedRequest);
//
//            MediaType contentType = null;
//            String bodyString = null;
//            if (response.body() != null) {
//                contentType = response.body().contentType();
//                bodyString = response.body().string();
//            }
//
//            switch (request.method()) {
//                case "GET":
//                    Log.e("retrofit-->",
//                            "GET "+ signedRequest.url()+" -->"+
//                                    bodyString);
//                    break;
//                case "POST":
//                    Log.e("retrofit-->",
//                            "POST "+ signedRequest.url()+" -->"+
//                                    bodyToString(signedRequest)+" -->"+
//                                    bodyString);
//                    break;
//            }
//            if (response.body() != null) {
//                // 深坑！
//                // 打印body后原ResponseBody会被清空，需要重新设置body
//                ResponseBody body = ResponseBody.create(contentType, bodyString);
//                return response.newBuilder().body(body).build();
//            } else {
//                return response;
//            }
//        }
//    };
//
//
//    private static String bodyToString(final Request request){
//
//        try {
//            final Buffer buffer = new Buffer();
//            request.body().writeTo(buffer);
//            return buffer.readUtf8();
//        } catch (final IOException e) {
//            return "did not work";
//        }
//    }



    //增加头部信息
    private static Interceptor headerInterceptor =new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request build = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build();
            return chain.proceed(build);
        }
    };

    // 配置OkHttpClient
    public static OkHttpClient getOkHttpClient() {
        if (sOkHttpClient == null) {
            synchronized (HttpManager.class) {
                if (sOkHttpClient == null) {
                    // OkHttpClient配置是一样的,静态创建一次即可
                    // 指定缓存路径,缓存大小100Mb
                    Cache cache = new Cache(new File(BaseApplication.context().getCacheDir(), "HttpCache"),
                            1024 * 1024 * 50);


                    HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            Log.e("okhttp", message);
                        }
                    });
                    logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                    sOkHttpClient = new OkHttpClient.Builder().cache(cache)
                            .addNetworkInterceptor(mRewriteCacheControlInterceptor)
                            .addInterceptor(mRewriteCacheControlInterceptor)
//                            .addInterceptor(mLoggingInterceptor)
                            .addInterceptor(logInterceptor)
                            .addInterceptor(headerInterceptor)
                            .retryOnConnectionFailure(true)
                            .connectTimeout(20, TimeUnit.SECONDS).build();

                }
            }
        }
        return sOkHttpClient;
    }

//    /**
//     * 根据网络状况获取缓存的策略
//     *
//     * @return
//     */
//    @NonNull
//    private String getCacheControl() {
//        return HttpUtils.isNetworkConnected(BaseApplication.context()) ? CACHE_CONTROL_NETWORK : CACHE_CONTROL_CACHE;
//    }
}
