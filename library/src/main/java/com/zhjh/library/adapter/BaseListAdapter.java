package com.zhjh.library.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liming on 15/9/12.
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

    public LayoutInflater mInflater;

    public Context mContext;

    public BaseListAdapter(Context c) {
        this.mContext = c;
        mInflater = LayoutInflater.from(c);
    }

    public List<T> mDatas;

    public void setDatas(List datas) {
        this.mDatas = datas;
    }

    public  List<T> getDatas(){
        return mDatas;
    }

    public void addData(T itme) {
        if (mDatas == null)
            mDatas = new ArrayList<>();
        mDatas.add(itme);
    }



    public void addDataToFirst(T itme){
        if (mDatas == null)
            mDatas = new ArrayList<>();
        mDatas.add(0,itme);
    }

    @Override
    public int getCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return mDatas != null ? mDatas.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
