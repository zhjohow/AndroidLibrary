package com.zhjh.library.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BasePresenter<T> {
    public T mView;//View
    protected Subscription mSubscription;
    protected CompositeSubscription mCompositeSubscription;//使用compositesubcription 管理Subcription
    protected Reference<T>  viewRef; 

    public void attachView(T view) {

        this.mView = view;
        viewRef= new WeakReference<T>(view); 
   }

    public void detachView() {
        if(viewRef !=){  
            viewRef.clear();  
            viewRef=;  
        } 
        this.mView = null;
        onUnsubscribe();
    }



    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
            mCompositeSubscription = null;
        }
    }


    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }


    public boolean isViewAttached(){
        return mView!= null;
    }

}

