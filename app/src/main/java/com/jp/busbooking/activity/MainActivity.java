package com.jp.busbooking.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jp.busbooking.R;
import com.jp.busbooking.fragment.ChoosePlaceFragment;
import com.jp.busbooking.helper.StaticData;
import com.jp.busbooking.pojo.BusListModel;
import com.jp.busbooking.pojo.DriverModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    FirebaseFirestore firebaseFirestore;
    FirebaseDatabase database;
    DatabaseReference myRef;
    StaticData staticData;
    List<BusListModel> busListModelArrayList =new ArrayList<>();
    List<DriverModel> driverListModelArrayList =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        staticData=new StaticData(this);
        initFirebase();
        setFragment(new ChoosePlaceFragment());
    }
    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        //firebase fire storage this data stored in firestorage.
        //getList();
        addListtoFirebaseBusList();
        addListtoFirebaseDriverList();
//        getListToFirebase();
    }

    private void addListtoFirebaseDriverList() {
        myRef.child("driverList").removeValue();
//        myRef.child("SeatsSelectedList").removeValue();
        driverListModelArrayList=staticData.getDriverList();
        for (DriverModel studentListModel:driverListModelArrayList){
            myRef.child("driverList").child(studentListModel.getId()).setValue(studentListModel);
        }
    }

    private void addListtoFirebaseBusList(){
        myRef.child("busList").removeValue();
//        myRef.child("SeatsSelectedList").removeValue();
        busListModelArrayList=staticData.getStudentList();
        for (BusListModel studentListModel:busListModelArrayList){
            myRef.child("busList").child(studentListModel.getId()).setValue(studentListModel);
        }
    }

    private void setFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.framLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit(); // save the changes
    }
}
