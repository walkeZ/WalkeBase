package walke.widget.gallery;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.widget.R;

/**
 * Created by walke.Z on 2018/4/17.
 *  Android:实现图片浏览的倒影效果  https://blog.csdn.net/zd_1471278687/article/details/13998413
 */

public class GalleryActivity extends TitleActivity {

    private TextView tvTitle;
    private MyGallery gallery;
    private ImageAdapter adapter;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_gallery;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("GalleryActivity 图片浏览的倒影效果");
    }

    @Override
    protected void initData() {
        tvTitle = (TextView) findViewById(R.id.ag_tvTitle);
        gallery = (MyGallery) findViewById(R.id.ag_myGallery);     // 获取自定义的myGallery控件

        adapter = new ImageAdapter(this);
        adapter.createReflectedImages();    // 创建倒影效果
        gallery.setAdapter(adapter);

        gallery.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {    // 设置选择事件监听
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvTitle.setText(adapter.titles[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {          // 设置点击事件监听
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toast("img " + (position+1) + " selected");
            }
        });
    }


}
