
package com.zhjh.library.manager;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;


public class AppManager {

	private static Stack<Activity> activityStack;
	private static AppManager instance;

	private AppManager() {
	}

	public static AppManager getAppManager() {
		if (instance == null) {
			instance = new AppManager();
		}
		return instance;
	}

	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}
	
	public void removeActivity(Activity activity) {

        if (activity != null) {

            activityStack.remove(activity);

            activity = null;

        }
       }

	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	public void finishActivity(Activity activity) {
		if (activity != null) {
			if(activity.isFinishing()) {
				activityStack.remove(activity);

//				activity.finish();
				activity = null;
			}else {
				activityStack.remove(activity);

				activity.finish();
				activity = null;
			}
		}
	}


	public void finishActivity(Class cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(activity)) {
				finishActivity(activity);
				break;
			}
		}
	}

	//退出栈中除指定的Activity外的所有
	public void popAllActivityExceptOne(Class cls) {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			if (activity.getClass().equals(cls)) {
				break;
			}
			finishActivity(activity);
		}
	}

	public void finishAllActivityAndExit(Context context) {
		if (null != activityStack) {
			for (int i = 0, size = activityStack.size(); i < size; i++) {
				if (null != activityStack.get(i)) {
					activityStack.get(i).finish();
				}
			}
			activityStack.clear();
		}
	}


}
