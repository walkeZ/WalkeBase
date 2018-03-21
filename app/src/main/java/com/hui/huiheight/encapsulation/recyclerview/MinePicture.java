package com.hui.huiheight.encapsulation.recyclerview;

import com.hui.huiheight.views.refresh.gridview.Picture;

/**
 * Created by walke.Z on 2017/8/22.
 */

public class MinePicture extends Picture {
    private String text;

    public MinePicture(String text) {
        this.text = text;
    }

    public MinePicture(String title, String imgUrl, String text) {
        super(title, imgUrl);
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
