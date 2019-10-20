package com.jp.busbooking.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jp.busbooking.R;
import com.jp.busbooking.helper.Constance;
import com.jp.busbooking.pojo.DriverModel;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class DriverHomeFragment extends Fragment implements LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    Button start, stop;
    private FusedLocationProviderClient fusedLocationClient;
    private int mInterval = 2000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    DatabaseReference databaseReference;
    private String TAG = DriverHomeFragment.class.getSimpleName();
    private DriverModel driverModel;
    Timer timer = new Timer();
    TextView latlng;
    TimerTask timerTask;

    private Location location;
    private TextView locationTv;
    private GoogleApiClient googleApiClient;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private LocationRequest locationRequest;
    private static final long UPDATE_INTERVAL = 5000, FASTEST_INTERVAL = 5000; // = 5 seconds
    // lists for permissions
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    // integer for permissions results request
    private static final int ALL_PERMISSIONS_RESULT = 1011;

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_home, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        start = view.findViewById(R.id.start);
        stop = view.findViewById(R.id.stop);
        latlng=view.findViewById(R.id.latlng);

        mHandler = new Handler();
        Bundle bundle = getArguments();
        if (bundle != null) {
            driverModel = (DriverModel) bundle.getSerializable(Constance.list);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(Objects.requireNonNull(getActivity()));
        databaseReference = FirebaseDatabase.getInstance().getReference("driverList");
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop.setBackgroundColor(getResources().getColor(R.color.white));
                stop.setTextColor(getResources().getColor(R.color.black));
                start.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                start.setTextColor(getResources().getColor(R.color.white));
                start.setClickable(false);
                stop.setClickable(true);

//                startRepeatingTask();
                startTimer();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.setBackgroundColor(getResources().getColor(R.color.white));
                start.setTextColor(getResources().getColor(R.color.black));
                stop.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                stop.setTextColor(getResources().getColor(R.color.white));
//                stopRepeatingTask();
                stop.setClickable(false);
                start.setClickable(true);
                stoptimertask();
            }
        });

        //location update

        // we add permissions we need to request location of the users
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        permissionsToRequest = permissionsToRequest(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0) {
                requestPermissions(permissionsToRequest.toArray(
                        new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }

        // we build google api client
        googleApiClient = new GoogleApiClient.Builder(getActivity()).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void getLastLocationUpdate() {
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(Objects.requireNonNull(getActivity()), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Log.e(TAG, "get last location : " + location.getLatitude() + " and " + location.getLongitude());
                            driverModel.setLat(String.valueOf(location.getLatitude()));
                            driverModel.setLng(String.valueOf(location.getLongitude()));
                            databaseReference.child(driverModel.getId()).setValue(driverModel);
                            Toast.makeText(getActivity(), "Location Updating to server! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 5000, 10000); //
    }

    private void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        getLastLocationUpdate();
                    }
                });
            }
        };
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getActivity(), "Location Updating to server! ", Toast.LENGTH_SHORT).show();
        latlng.setText("Latitude - longitude = "+ location.getLatitude() + " -- " +location.getLongitude());
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&  ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Permissions ok, we get last location
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (location != null) {
            latlng.setText("Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());
        }

        startLocationUpdates();
    }

    private void startLocationUpdates() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);

        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&  ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getActivity(), "You need to enable permissions to display location !", Toast.LENGTH_SHORT).show();
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getActivity(), "onConnectionFailed !", Toast.LENGTH_SHORT).show();
    }

    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {
        ArrayList<String> result = new ArrayList<>();

        for (String perm : wantedPermissions) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }
    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }

}
