package com.hui.huiheight.encapsulation.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by walke.Z on 2017/8/22.
 */

public abstract class RecyclerViewBaseAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {


    private List<T> mDatas;
    private int layoutId;

    public RecyclerViewBaseAdapter(List<T> datas,int layoutId) {

        mDatas = datas;
        this.layoutId=layoutId;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View inflate = inflater.inflate(layoutId, parent, false);

        return new RecyclerViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        convertView(holder,mDatas.get(position));
    }

    /** 留给调用者去实现(必须重写)
     * @param holder
     * @param t
     */
    protected abstract void convertView(RecyclerViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
