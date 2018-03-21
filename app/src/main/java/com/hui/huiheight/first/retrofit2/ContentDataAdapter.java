package com.hui.huiheight.first.retrofit2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hui.huiheight.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2016/7/12 0012.
 */
public class ContentDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ContentData.DataBean.LinklistBean> list;
    private LayoutInflater inflater;
    private String mTime;

    public ContentDataAdapter(Context context, List<ContentData.DataBean.LinklistBean> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = inflater.inflate(R.layout.mine_content_details, null);
        MineContentHolder mineContentHolder = new MineContentHolder(inflate);
        return mineContentHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MineContentHolder contentHolder = (MineContentHolder) holder;
        ContentData.DataBean.LinklistBean bean = list.get(position);
        Picasso.with(context).load(bean.getImage()).into(contentHolder.img);
        contentHolder.title.setText(bean.getTitle());
        contentHolder.price.setText("￥" + bean.getPrice());
        /**********字符串时间转成integer*************/
        String createtime = bean.getCreatetime();
        mTime = getTime(createtime);

        contentHolder.source.setText(bean.getSitename() + "|" + mTime);
        contentHolder.votesp.setText(bean.getVotesp() + "");
        contentHolder.commentcount.setText(bean.getCommentcount() + "");

    }

    public void addAll(List<ContentData.DataBean.LinklistBean> linklist, boolean isclear) {
        if (list != null) {
            if (isclear) {
                list.clear(); //添加加页数据时先清除
            }
            list.addAll(linklist);
            notifyDataSetChanged();
        }
    }

    /**************接口回调********************/
    //定义
    public interface RecyclerViewItemClickListener {
        void itemClick(View itemView, int position);
    }
    //2.声明
    public RecyclerViewItemClickListener listener;

    //3.外界设置（创建）

    public void setRecyclerViewItemClickListener(RecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    private class MineContentHolder extends RecyclerView.ViewHolder {
        ImageView img;
        //votesp--赞,commentcount--评论；sitename--来源；price--价格;source--sitename(来源)+time
        TextView title, price, source, votesp, commentcount;

        public MineContentHolder(final View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.first_details_img);
            title = (TextView) itemView.findViewById(R.id.first_details_title);
            price = (TextView) itemView.findViewById(R.id.first_details_price);
            source = (TextView) itemView.findViewById(R.id.first_details_source);
            votesp = (TextView) itemView.findViewById(R.id.first_details_zan);
            commentcount = (TextView) itemView.findViewById(R.id.first_details_comment);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null)
                        listener.itemClick(itemView,getLayoutPosition());
                }
            });

        }
    }

    /**********
     * 字符串时间转成integer/并计算时间
     *************/
    public static String getTime(String createtime) {
        long integer = ((long) Integer.parseInt(createtime));
        Long culData = new Long(System.currentTimeMillis());
        long difftime = (culData / 1000) - integer;
        Long years = (Long) (difftime / (60 * 60 * 24 * 365));
        Long months = (Long) (difftime / (60 * 60 * 24 * 30));
        Long days = (Long) (difftime / (60 * 60 * 24));
        Long hours = (Long) (difftime / (60 * 60));
        Long minutes = (Long) (difftime / 60);
        if (years > 0) {
            return years + "年前";
        } else if (years == 0 && months > 0) {
            return months + "月前";
        } else if (years == 0 && months == 0 && days > 0) {
            return days + "天前";
        } else if (years == 0 && months == 0 && days == 0 && hours > 0) {
            return hours + "小时前";
        } else if (years == 0 && months == 0 && days == 0 && hours == 0 && minutes > 0) {
            return minutes + "分前";
        } else {
            return difftime + "秒前";
        }
    }


}

