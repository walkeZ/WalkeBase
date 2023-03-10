package com.hui.huiheight.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hui.huiheight.R;

import java.util.List;

/**
 * Created by walke.Z on 2017/8/2.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<String> datas;
    private LayoutInflater mInflater;

    public RecyclerViewAdapter(Context context, List<String> datas) {
        mContext = context;
        this.datas = datas;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mInflater.inflate(R.layout.recyclerview_child, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(inflate);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((RecyclerViewHolder) holder).mTextView.setText(datas.get(position));
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextView;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.rc_tvText);
            mTextView.setOnClickListener(v -> {
                if (mClickListener!=null)
                    mClickListener.onClick(getLayoutPosition());
            });

        }

    }

    public interface onItemClickListener{
        void onClick(int position);
    }

    private onItemClickListener mClickListener;

    public void setItemClickListener(onItemClickListener clickListener) {
        mClickListener = clickListener;
    }
}
