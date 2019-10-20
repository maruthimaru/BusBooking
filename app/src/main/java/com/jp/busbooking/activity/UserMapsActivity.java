package com.jp.busbooking.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jp.busbooking.R;
import com.jp.busbooking.fragment.DriverHomeFragment;
import com.jp.busbooking.helper.Constance;
import com.jp.busbooking.pojo.DriverModel;

import java.util.Timer;
import java.util.TimerTask;

public class UserMapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    private String TAG = UserMapsActivity.class.getSimpleName();
    private DriverModel driverModel;
    String busid="";
    Timer timer = new Timer();
    TimerTask timerTask;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            busid=bundle.getString(Constance.busId);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(11.0210, 76.9663);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Bus location")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.isMyLocationEnabled();


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
        startTimer();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        super.onStop();
        startTimer();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

      /*  //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }*/
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

    private void getLastLocationUpdate() {

        Query query = FirebaseDatabase.getInstance().getReference("driverList/")
                .orderByChild("busid")
                .equalTo(busid);

        query.addListenerForSingleValueEvent(valueEventListener);
    }

    private void getMarkerUpdate(Double lat,Double lng){
       if (mCurrLocationMarker != null) {
           mCurrLocationMarker.remove();
       }
       //Place current location marker
       LatLng latLng = new LatLng(lat, lng);
       MarkerOptions markerOptions = new MarkerOptions();
       markerOptions.position(latLng);
       markerOptions.title("Bus location");
       markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
       mCurrLocationMarker = mMap.addMarker(markerOptions);

       //move map camera
       mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
       mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
   }


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            userModelArrayList.clear();
            Log.e(TAG, "valueEventListener :" + dataSnapshot.getValue());
            if (dataSnapshot.getValue() != null) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    if (postSnapshot.child("busid").getValue(String.class).toLowerCase().trim().equals(busid)) {
                        String busid = postSnapshot.child("busid").getValue(String.class);
                        String id = postSnapshot.child("id").getValue(String.class);
                        String lat = postSnapshot.child("lat").getValue(String.class);
                        String lng = postSnapshot.child("lng").getValue(String.class);
                        String name = postSnapshot.child("name").getValue(String.class);
                        driverModel = new DriverModel(name, id, busid, lat, lng);
                        if (lat!="11.0210" && lng !="76.9663" ) {
                            getMarkerUpdate(Double.parseDouble(lat), Double.parseDouble(lng));
                        }else {
                            Toast.makeText(getApplicationContext(),"Bus Not yet Start ",Toast.LENGTH_SHORT).show();
                        }
                    }
//                }
            } else {
                Toast.makeText(getApplicationContext(),"No data found! ",Toast.LENGTH_SHORT).show();
            }
//
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
