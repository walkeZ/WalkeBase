package com.hui.huiheight.encapsulation.recyclerview;

import com.hui.huiheight.R;

import java.util.List;

/**
 * Created by walke.Z on 2017/8/22.
 */

public class MineRecyclerViewAdapter extends RecyclerViewBaseAdapter<MinePicture> {


    public MineRecyclerViewAdapter(List<MinePicture> datas) {
        this(datas, R.layout.item_picture1_recyclerview);
    }

    public MineRecyclerViewAdapter(List<MinePicture> datas, int layoutId) {
        super(datas, layoutId);
    }

    @Override
    protected void convertView(RecyclerViewHolder holder, MinePicture minePicture) {
        holder.setText(R.id.ipr_title, minePicture.getTitle());
        holder.setText(R.id.ipr_text, minePicture.getText());
        holder.setImageByUrl(R.id.ipr_img, minePicture.getImgUrl());
    }
}
