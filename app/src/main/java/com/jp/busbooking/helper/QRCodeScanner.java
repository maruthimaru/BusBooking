package com.jp.busbooking.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QRCodeScanner extends AppCompatActivity {
    public String TAG=QRCodeScanner.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setPrompt("Scan a barcode or QRcode");
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG, "intent: " + data );
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "result "+ result );
        if (result != null) {
                Bundle mBundle=new Bundle();
                mBundle.putString("params",result.getContents());
                  Intent mBackIntent = new Intent();
                mBackIntent.putExtras(mBundle);
                setResult(Activity.RESULT_OK, mBackIntent);
                finish();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
   }
