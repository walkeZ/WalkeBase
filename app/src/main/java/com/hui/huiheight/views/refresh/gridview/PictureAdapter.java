package com.hui.huiheight.views.refresh.gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hui.huiheight.R;

import java.util.List;

/**
 * Created by walke.Z on 2017/8/22.
 */

public class PictureAdapter extends BaseAdapter {
    private Context mContext;
    private List<Picture> mDatas;
    private LayoutInflater mInflater;

    public PictureAdapter(Context context, List<Picture> datas) {
        mContext = context;
        mDatas = datas;
        mInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PictureViewHolder holder=null;
        if (convertView==null){
            convertView = mInflater.inflate(R.layout.item_picture_gridview, parent, false);
            holder=new PictureViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder=((PictureViewHolder) convertView.getTag());
        }
        Picture picture = mDatas.get(position);
        Glide.with(mContext).load(picture.getImgUrl()).into(holder.img);
        holder.title.setText(picture.getTitle()+"");
        return convertView;
    }

    class PictureViewHolder{

        private ImageView img;
        private TextView title;

        public PictureViewHolder(View view) {
            img = ((ImageView) view.findViewById(R.id.ipg_img));
            title = (TextView) view.findViewById(R.id.pgi_title);
        }
    }


}
