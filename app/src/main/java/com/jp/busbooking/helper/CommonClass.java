package com.jp.busbooking.helper;

import android.content.Context;
import android.os.Handler;

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
}
