package com.jp.busbooking.fragment;

import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jp.busbooking.R;
import com.jp.busbooking.helper.Constance;
import com.jp.busbooking.pojo.DriverModel;

public class DriverHomeFragment extends Fragment {

    TextView start,stop;
    private FusedLocationProviderClient fusedLocationClient;
    private int mInterval = 2000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    DatabaseReference databaseReference;
    private String TAG=DriverHomeFragment.class.getSimpleName();
    private DriverModel driverModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        start=view.findViewById(R.id.start);
        stop=view.findViewById(R.id.stop);

        mHandler = new Handler();
        Bundle bundle=getArguments();
        if (bundle!=null){
            driverModel= (DriverModel) bundle.getSerializable(Constance.list);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        databaseReference= FirebaseDatabase.getInstance().getReference("driverList");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop.setVisibility(View.VISIBLE);
                start.setVisibility(View.GONE);

                startRepeatingTask();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop.setVisibility(View.GONE);
                start.setVisibility(View.VISIBLE);
                stopRepeatingTask();
            }
        });
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                getLastLocation(); //this function can change value of mInterval.
            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    private void getLastLocation(){
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Log.e(TAG,"get last location : " + location.getLatitude() +" and "+ location.getLongitude());
//                            driverModel.setLat(String.valueOf(location.getLatitude()));
//                            driverModel.setLng(String.valueOf(location.getLongitude()));
//                            databaseReference.child(driverModel.getId()).setValue(driverModel);
                        }
                    }
                });
    }
}
