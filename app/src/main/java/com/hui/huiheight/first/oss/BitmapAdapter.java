package com.hui.huiheight.first.oss;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hui.huiheight.R;

import java.util.List;

/**
 * Created by walke.Z on 2018/2/27.
 */

public class BitmapAdapter extends BaseAdapter {
    private List<Bitmap> mBitmapList;

    public BitmapAdapter(List<Bitmap> bitmapList) {
        mBitmapList = bitmapList;
    }

    @Override
    public int getCount() {
        return mBitmapList.size();
    }

    @Override
    public Object getItem(int position) {
        return mBitmapList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BitmapViewHolder holder=null;
        if (convertView==null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.item_gridview_bitmap, parent, false);
            holder=new BitmapViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder= (BitmapViewHolder) convertView.getTag();
        }
        holder.iv.setImageBitmap(mBitmapList.get(position));
        if (position==0){
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mBitmapAddClickListener!=null){
                        mBitmapAddClickListener.onBitmapAddClick();
                    }
                }
            });
            holder.iv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mBitmapAddClickListener!=null){
                        mBitmapAddClickListener.onBitmapDeleteClick();
                    }
                    return false;
                }
            });
        }
        return convertView;
    }

    class BitmapViewHolder{
        private ImageView iv;

        public BitmapViewHolder(View view) {
            iv = ((ImageView) view.findViewById(R.id.igb_img));
        }

    }

    public interface BitmapAddClickListener{
        void onBitmapAddClick();
        void onBitmapDeleteClick();
    }
    public BitmapAddClickListener mBitmapAddClickListener;

    public void setBitmapAddClickListener(BitmapAddClickListener bitmapAddClickListener) {
        mBitmapAddClickListener = bitmapAddClickListener;
    }
}
