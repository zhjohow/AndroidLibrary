package com.zhjh.library.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * Created by wangs on 2017/3/7.
 */

public abstract class BaseFragment extends Fragment {

	public abstract void initViews(View view);

	public abstract int getLayoutId();
	private View mFragmentView;
	public Context mContext;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = getActivity();

	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (mFragmentView == null) {
			mFragmentView = inflater.inflate(getLayoutId(), container, false);
			ButterKnife.bind(this, mFragmentView);
			initViews(mFragmentView);
		}
		return mFragmentView;
	}

	@Override
	public void onResume() {
		MobclickAgent.onResume(mContext);
		BaseBus.getInstance().register(this);
		super.onResume();
	}


	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(mContext);
		BaseBus.getInstance().unregister(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
