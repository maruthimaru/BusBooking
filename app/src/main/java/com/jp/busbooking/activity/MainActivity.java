package com.jp.busbooking.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jp.busbooking.R;
import com.jp.busbooking.fragment.ChoosePlaceFragment;
import com.jp.busbooking.helper.StaticData;
import com.jp.busbooking.pojo.BusListModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
//    FirebaseFirestore firebaseFirestore;
    FirebaseDatabase database;
    DatabaseReference myRef;
    StaticData staticData;
    List<BusListModel> busListModelArrayList =new ArrayList<>();
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
        addListtoFirebase();
//        getListToFirebase();
    }
    private void addListtoFirebase(){
        myRef.child("busList").removeValue();
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
