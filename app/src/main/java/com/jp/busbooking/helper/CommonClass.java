package com.jp.busbooking.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CommonClass {
    Context context;

    public CommonClass(Context context) {
        this.context = context;
    }
    public void sweetAlertDialog(String title, String content, int alertDialog) {
        SweetAlertDialog sw = new SweetAlertDialog( context, alertDialog );
        sw.setTitleText( title );
        sw.setContentText( content );
        sw.show();
        final SweetAlertDialog sweetAlertDialog = sw;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                sweetAlertDialog.dismissWithAnimation();
            }
        }, 5000 );
    }

    public void imageLoad(ImageView imageView, Bitmap bitmap){
        Glide.with(context).asBitmap().
                load(bitmap).
                apply(RequestOptions.circleCropTransform()).
                into(imageView);
    }
}
