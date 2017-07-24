package com.zhjh.library.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.zhjh.library.interfaces.OnRecycleriewItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhjh on 15/9/12.
 */
public abstract class BaseRecylerViewAdapter<T>  extends RecyclerView.Adapter {

    public LayoutInflater mInflater;

    public Context mContext;

    public OnRecycleriewItemClickListener mlistener;

    public BaseRecylerViewAdapter(Context c) {
        this.mContext = c;
        mInflater = LayoutInflater.from(c);
    }

    public List<T> mDatas;

    public void setDatas(List datas) {
        this.mDatas = datas;
    }

    public  List<T> getDatas(){
        if (mDatas == null)
            mDatas = new ArrayList<>();
        return mDatas;
    }

    public void addData(T itme) {
        if (mDatas == null)
            mDatas = new ArrayList<>();
        mDatas.add(itme);
        this.notifyDataSetChanged();
    }

    public void addAll(List<T> datas) {
        if (mDatas == null)
            mDatas = new ArrayList<>();
        mDatas.addAll(datas);
        this.notifyDataSetChanged();


    }


    public void addDataToFirst(T itme){
        if (mDatas == null)
            mDatas = new ArrayList<>();
        mDatas.add(0,itme);
        this.notifyDataSetChanged();
    }

    public void replaceAll(List<T> datas) {
        if (mDatas == null)
            mDatas = new ArrayList<>();
        mDatas.clear();
        mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }


    public void remove(int position) {
        if (mDatas == null)
            mDatas = new ArrayList<>();

        mDatas.remove(position);
        this.notifyItemRemoved(position);

//        if(position != data.size() - 1){ // 如果移除的是最后一个，忽略
        this.notifyItemRangeChanged(position, getItemCount() - position);
//        }

//        this.notifyItemRangeChanged();
    }

    public void setOnRecyclerViewItemClickListener(OnRecycleriewItemClickListener listener) {
        this.mlistener = listener;
    }


    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;

    }

    public T getItem(int position) {
        return mDatas != null ? mDatas.get(position) : null;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }
}
