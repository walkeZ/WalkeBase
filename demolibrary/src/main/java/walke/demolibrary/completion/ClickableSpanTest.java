package walke.demolibrary.completion;

import android.text.style.ClickableSpan;
import android.view.View;

import walke.base.activity.BaseActivity;

/**
 * Created by walke.Z on 2017/10/16.
 */

public class ClickableSpanTest extends ClickableSpan {
    @Override
    public void onClick(View widget) {
        ((BaseActivity) widget.getContext()).toast("ClickableSpanTest");
    }
}
