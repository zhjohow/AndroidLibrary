package com.zhjh.library.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by wangs on 2017/5/5.
 * 隐藏软键盘
 */

public class HindSoftKeyboardUtil {
	public static void hindSoftKeybroad(Activity mContext){
		((InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(mContext.getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
