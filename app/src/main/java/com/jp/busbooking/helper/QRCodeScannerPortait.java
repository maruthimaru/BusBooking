package com.jp.busbooking.helper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class QRCodeScannerPortait extends AppCompatActivity {
    public String TAG=QRCodeScannerPortait.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ZxingOrient integrator = new ZxingOrient(this);
//        integrator.setInfo("Scan a barcode or QRcode");
//        integrator.initiateScan(Barcode.DEFAULT_CODE_TYPES);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "intent: " + data );
//        ZxingOrientResult result = ZxingOrient.parseActivityResult(requestCode, resultCode, data);
//        Log.e(TAG, "result "+ result );
//        if (result != null) {
//                Bundle mBundle=new Bundle();
//                mBundle.putString("params",result.getContents());
//                  Intent mBackIntent = new Intent();
//                mBackIntent.putExtras(mBundle);
//                setResult(Activity.RESULT_OK, mBackIntent);
//                finish();
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
    }
   }
