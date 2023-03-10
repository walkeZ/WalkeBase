package walke.demolibrary.layoutmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import walke.demolibrary.R;

/**
 * @author Walker - 2023/3/10 11:23 AM
 * @email 1126648815@qq.ocm
 * @desc : 探探切片适配器
 */
public class TTCAdapter extends RecyclerView.Adapter<TTCAdapter.Holder> {

    private final int[] mImgs;

    public TTCAdapter(int[] imgs) {
        mImgs = imgs;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ttc, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.iv.setImageResource(mImgs[position]);
    }

    @Override
    public int getItemCount() {
        return mImgs.length;
    }

    public static class Holder extends RecyclerView.ViewHolder {

        private final ImageView iv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.i_ttc_iv);
        }
    }
}