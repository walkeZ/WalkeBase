package com.hui.huiheight.encapsulation.recyclerview;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

/**   使用RecycleView时候的adapter的简单封装
 * Created by walke.Z on 2017/8/22.
 *
 * 参考 http://blog.csdn.net/wuyinlei/article/details/50658934
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    /**
     * 如果用到了，比如我们用glide加载图片的时候，还有其他的需要用到上下文的时候
     */
    private Context mContext;
    /**
     * 这个稀疏数组，网上说的是提高效率的
     */
    private SparseArray<View> mViews;
    private View convertView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mViews=new SparseArray<>();
        convertView=itemView;
        mContext=itemView.getContext();
    }

    /**
     * 返回一个具体的view对象
     * 这个就是借鉴的base-adapter-helper中的方法
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T getView(int viewId){
        View view=mViews.get(viewId);
        if (view==null){
            view=convertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    /** 根据资源id(TextView)设置文本
     * @param resId
     * @param text
     */
    public void setText(int resId,String text){
        TextView tv = getView(resId);
        tv.setText(text+"");
    }

    /** 根据资源id(ImageView)和图片连接设置图片
     * @param resId
     * @param url
     */
    public void setImageByUrl(int resId,String url){
        ImageView img= getView(resId);
        Glide.with(mContext).load(url).into(img);
    }

    // TODO: 2017/8/22  在这里我们可以定义其他的方法，


}
