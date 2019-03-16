package com.jp.busbooking.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jp.busbooking.R;
import com.jp.busbooking.helper.Constance;
import com.jp.busbooking.helper.QRCodeScanner;
import com.jp.busbooking.helper.QRCodeScannerPortait;

import java.util.ArrayList;

public class CheckNumberActivity extends AppCompatActivity {
    ImageView imageView2;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ArrayList<String> stringArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_number);
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        imageView2 = findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringArrayList=new ArrayList<>();
                myRef=FirebaseDatabase.getInstance().getReference("userList");
//                Query query = FirebaseDatabase.getInstance().getReference("userList").orderByChild("mobile").equalTo(id);
                myRef.addValueEventListener(valueEventListener);
                Intent connectIntent = new Intent(CheckNumberActivity.this,
                        QRCodeScanner.class);
                startActivityForResult(connectIntent, 20);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String id = bundle.getString("params");
            Log.e("TAG", "onActivityResult: " + id);
            if (id == null) {
                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
            } else {
                for (String number:stringArrayList){
                    if (number.equals(id)){
                        Constance.count=1;
                        break;
                    }else {
                        Constance.count=0;
                    }
                }
                if (Constance.count==1){
                    Toast.makeText(getApplicationContext(), id + " Has Checked Into Bus ", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),  " Not Yet register ", Toast.LENGTH_SHORT).show();
                }
//                Toast.makeText(getApplicationContext(), id + " Has Checked Into Bus ", Toast.LENGTH_SHORT).show();

//                visitorEntryImprogressDetailListModelArray = dbHelper.getVisitorInprogress(dbHelper.dbVisitorTable, dbHelper.contact_No, id);
//                if (visitorEntryImprogressDetailListModelArray.isEmpty()) {
//                    notFound.setVisibility(View.VISIBLE);
//                    recyclerView.setVisibility(View.GONE);
//                }
//                visitorEntryImprogressDetailAdapter = new VisitorEntryInProgressDetailAdapter(getActivity(), visitorEntryImprogressDetailListModelArray);
//                recyclerView.setAdapter(visitorEntryImprogressDetailAdapter);
            }
        }
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            busListModelList.clear();
            Log.e("TAG", "onDataChange: "+dataSnapshot.getValue() );
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
               stringArrayList.add(postSnapshot.child("mobile").getValue().toString());
            /*   if (postSnapshot.exists()){
                   Toast.makeText(getApplicationContext(),"You Can Check In" ,Toast.LENGTH_SHORT ).show();
               }*/
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
