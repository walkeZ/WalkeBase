package walke.viewlibrary;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * Created by walke.Z on 2017/8/2.
 */

public class ViewAdapter extends RecyclerViewBaseAdapter<AppCompatActivity> {

    private Context mContext;
    private List<AppCompatActivity> datas;

    public ViewAdapter(Context context,List<AppCompatActivity> datas) {
        super(datas, R.layout.view_recyclerview_item);
        mContext = context;
        this.datas=datas;
    }

    public ViewAdapter(List<AppCompatActivity> datas, int layoutId) {
        super(datas, layoutId);
    }

    @Override
    protected void convertView(RecyclerViewHolder holder, int position) {
        holder.setText(R.id.recyclerViewItem,datas.get(position).getClass().getSimpleName());
        holder.setOnItemClickListener(new RecyclerViewHolder.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ((AppCompatActivity) mContext).startActivity(new Intent(mContext,datas.get(position).getClass()));
            }
        });
    }
}
