package cn.shineiot.work.http;

import android.content.Context;

import com.zhjh.library.base.BaseApplication;
import com.zhjh.library.base.BaseBus;
import com.zhjh.library.utils.LogUtil;
import com.zhjh.library.utils.ToastUtils;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by zhang on 2016/3/2.
 */
public class HttpClient {

	private static HttpClient sInstance;

	private HttpService httpservice;
	public static String token;

	private Context mContext;



	/**
	 * 获取单例
	 *
	 * @param
	 * @return 实例
	 */
	public static HttpClient getInstance() {

		token = UserManager.getToken(BaseApplication.context());

		if (!HttpUtils.isNetworkConnected(BaseApplication.context())) {
			DialogUtils.hintLoadingProgress();

			ToastUtils.showToast("没有网络，请检查网络设置！");

		}

		if (sInstance == null) {
			sInstance = new HttpClient();

		}
		return sInstance;
	}

	private HttpClient() {
	
		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BaseApplication.context().getResources()
						.getString(R.string.strKey))
				.client(HttpManager.getOkHttpClient())
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();

		httpservice = retrofit.create(HttpService.class);
	}




	private static <T> Observable toSubscribe(Observable<T> o) {
		return o.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}


	public Observable<BaseListResult<RepairEvent>> getSweeperRepair(String category_uuid) {

		return toSubscribe(httpservice.getSweepCheck(token, category_uuid));
	}

	
}
