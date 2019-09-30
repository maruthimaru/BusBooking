package com.jp.busbooking.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;

import com.jp.busbooking.pojo.BusListModel;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class StaticData {
    Context context;
    private boolean isBackPressed = false;
    public StaticData(Context context) {
        this.context = context;
    }

    public List<BusListModel> getStudentList(){
        List<BusListModel> studentListModelArrayList=new ArrayList<>();
        studentListModelArrayList.add(new BusListModel("1","No 1 AirTravels","30","4.0","600","23:00","05:30","651","A/C"
                ,"Bangalore","coimbatore"));
        studentListModelArrayList.add(new BusListModel("2","A1 Travels","52","4.2","800","22:00","04:30","1251","A/C"
                ,"Bangalore","coimbatore"));
        studentListModelArrayList.add(new BusListModel("3","National Travels","45","2.5","400","23:30","06:30","241","Non A/C"
                ,"Madurai","coimbatore"));
        studentListModelArrayList.add(new BusListModel("4","A1 Travels","52","4.2","1200","22:00","06:30","1251","A/C"
                ,"Madurai","coimbatore"));
        return studentListModelArrayList;
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
        }, 2000 );
    }

    public void handleBackPress(View view) {

        view.setFocusableInTouchMode( true );
        view.requestFocus();
        view.setOnKeyListener( new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (!isBackPressed) {
                        Activity activity= (Activity) context;
                        activity.finishAffinity();
//                        intentFinish( context, _targetClass );
                        isBackPressed = true;
                    } else if (isBackPressed) {
                        isBackPressed = false;
                    }
                    return true;
                }
                return false;

            }
        } );
    }
    public void intentFinish(Context _context, Class _targetClass) {
        Activity activity = (Activity) _context;
        Intent intent = new Intent( _context, _targetClass );
        activity.startActivity( intent );
        activity.finish();
    }
}
