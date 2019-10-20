package com.jp.busbooking.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.jp.busbooking.R;
import com.jp.busbooking.pojo.BusListModel;
import com.jp.busbooking.pojo.DriverModel;
import com.jp.busbooking.pojo.ImagesModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class StaticData1 {
    Context context;
    private boolean isBackPressed = false;
    public StaticData1(Context context) {
        this.context = context;
    }

  /*  public List<BusListModel> getStudentList(){
        List<BusListModel> studentListModelArrayList=new ArrayList<>();
        studentListModelArrayList.add(new BusListModel("1","No 1 AirTravels","30","4.0","600","23:00","05:30","651","A/C"
                ,"Bangalore","coimbatore",bus1()));
        studentListModelArrayList.add(new BusListModel("2","A1 Travels","52","4.2","800","22:00","04:30","1251","A/C"
                ,"Bangalore","coimbatore",bus2()));
        studentListModelArrayList.add(new BusListModel("3","National Travels","45","2.5","400","23:30","06:30","241","Non A/C"
                ,"Madurai","coimbatore",bus3()));
        studentListModelArrayList.add(new BusListModel("4","A1 Travels","52","4.2","1200","22:00","06:30","1251","A/C"
                ,"Madurai","coimbatore",bus4()));
        return studentListModelArrayList;
    }
*/
    public List<ImagesModel> bus1(){
        List<ImagesModel> imageList=new ArrayList<>();
        //encode image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.universal_bus);
//        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes1 = baos.toByteArray();
//        String imageString1 = Base64.encodeToString(imageBytes1, Base64.DEFAULT);

        Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.universal_bus);
//        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes2 = baos.toByteArray();
//        String imageString2 = Base64.encodeToString(imageBytes2, Base64.DEFAULT);

        Bitmap bitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.universal_bus);
//        bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes3 = baos.toByteArray();
//        String imageString3 = Base64.encodeToString(imageBytes3, Base64.DEFAULT);

        Bitmap bitmap4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.universal_bus);
//        bitmap4.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes4 = baos.toByteArray();
//        String imageString4 = Base64.encodeToString(imageBytes4, Base64.DEFAULT);

        imageList.add(new ImagesModel(bitmap1));
        imageList.add(new ImagesModel(bitmap2));
        imageList.add(new ImagesModel(bitmap3));
        imageList.add(new ImagesModel(bitmap4));
        return imageList;
    }

    public List<ImagesModel> bus2(){
        List<ImagesModel> imageList=new ArrayList<>();
        //encode image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.universal_bus);
//        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes1 = baos.toByteArray();
//        String imageString1 = Base64.encodeToString(imageBytes1, Base64.DEFAULT);

        Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.universal_bus);
//        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes2 = baos.toByteArray();
//        String imageString2 = Base64.encodeToString(imageBytes2, Base64.DEFAULT);

        Bitmap bitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.universal_bus);
//        bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes3 = baos.toByteArray();
//        String imageString3 = Base64.encodeToString(imageBytes3, Base64.DEFAULT);

        Bitmap bitmap4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.universal_bus);
//        bitmap4.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes4 = baos.toByteArray();
//        String imageString4 = Base64.encodeToString(imageBytes4, Base64.DEFAULT);

        imageList.add(new ImagesModel(bitmap1));
        imageList.add(new ImagesModel(bitmap2));
        imageList.add(new ImagesModel(bitmap3));
        imageList.add(new ImagesModel(bitmap4));
        return imageList;
    }
    public List<ImagesModel> bus3(){
        List<ImagesModel> imageList=new ArrayList<>();
        //encode image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.universal_bus);
//        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes1 = baos.toByteArray();
//        String imageString1 = Base64.encodeToString(imageBytes1, Base64.DEFAULT);

        Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.universal_bus);
//        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes2 = baos.toByteArray();
//        String imageString2 = Base64.encodeToString(imageBytes2, Base64.DEFAULT);

        Bitmap bitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.universal_bus);
//        bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes3 = baos.toByteArray();
//        String imageString3 = Base64.encodeToString(imageBytes3, Base64.DEFAULT);

        Bitmap bitmap4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.universal_bus);
//        bitmap4.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes4 = baos.toByteArray();
//        String imageString4 = Base64.encodeToString(imageBytes4, Base64.DEFAULT);

        imageList.add(new ImagesModel(bitmap1));
        imageList.add(new ImagesModel(bitmap2));
        imageList.add(new ImagesModel(bitmap3));
        imageList.add(new ImagesModel(bitmap4));
        return imageList;
    }
    public List<ImagesModel> bus4(){
        List<ImagesModel> imageList=new ArrayList<>();
        //encode image to base64 string
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.universal_bus);
//        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes1 = baos.toByteArray();
//        String imageString1 = Base64.encodeToString(imageBytes1, Base64.DEFAULT);

        Bitmap bitmap2 = BitmapFactory.decodeResource(context.getResources(), R.drawable.universal_bus);
//        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes2 = baos.toByteArray();
//        String imageString2 = Base64.encodeToString(imageBytes2, Base64.DEFAULT);

        Bitmap bitmap3 = BitmapFactory.decodeResource(context.getResources(), R.drawable.universal_bus);
//        bitmap3.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes3 = baos.toByteArray();
//        String imageString3 = Base64.encodeToString(imageBytes3, Base64.DEFAULT);

        Bitmap bitmap4 = BitmapFactory.decodeResource(context.getResources(), R.drawable.universal_bus);
//        bitmap4.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes4 = baos.toByteArray();
//        String imageString4 = Base64.encodeToString(imageBytes4, Base64.DEFAULT);

        imageList.add(new ImagesModel(bitmap1));
        imageList.add(new ImagesModel(bitmap2));
        imageList.add(new ImagesModel(bitmap3));
        imageList.add(new ImagesModel(bitmap4));
        return imageList;
    }

    public List<DriverModel> getDriverList(){
        List<DriverModel> studentListModelArrayList=new ArrayList<>();
        studentListModelArrayList.add(new DriverModel("raja","001","1","11.0210","76.9663"));
        studentListModelArrayList.add(new DriverModel("sri","002","2","11.0210","76.9663"));
        studentListModelArrayList.add(new DriverModel("arun","003","3","11.0210","76.9663"));
        studentListModelArrayList.add(new DriverModel("ram","004","4","11.0210","76.9663"));
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
