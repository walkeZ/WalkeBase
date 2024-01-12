package walke.widget.img;

import android.view.View;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.widget.R;

/**
 * Created by walke.Z on 2018/3/26.
 */

public class ImgActivity extends TitleActivity {

    private View ivNormal1, ivNormal2;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_img;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        ivNormal1 = findViewById(R.id.img_normal1);
        ivNormal2 = findViewById(R.id.img_normal2);
        CornerUtils.clipViewCircle(ivNormal1);
        CornerUtils.clipViewCornerByDp(ivNormal2, 30);
    }

    @Override
    protected void initData() {

    }
}
