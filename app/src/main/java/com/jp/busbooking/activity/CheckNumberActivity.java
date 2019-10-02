package com.jp.busbooking.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.jp.busbooking.fragment.AllBusListFragment;
import com.jp.busbooking.helper.AESUtils;
import com.jp.busbooking.helper.CommonClass;
import com.jp.busbooking.helper.Constance;
import com.jp.busbooking.helper.QRCodeScanner;
import com.jp.busbooking.helper.QRCodeScannerPortait;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.app.Activity.RESULT_OK;

public class CheckNumberActivity extends Fragment {
    ImageView imageView2;
    FirebaseDatabase database;
    DatabaseReference myRef;
    CommonClass commonClass;
    ArrayList<String> stringArrayList;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_check_number, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        FirebaseApp.initializeApp(getActivity());
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        commonClass = new CommonClass(getActivity());
        imageView2 = view.findViewById(R.id.imageView2);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringArrayList = new ArrayList<>();
                myRef = FirebaseDatabase.getInstance().getReference("userList");
//                Query query = FirebaseDatabase.getInstance().getReference("userList").orderByChild("mobile").equalTo(id);
                myRef.addValueEventListener(valueEventListener);
                Intent connectIntent = new Intent(getActivity(),
                        QRCodeScanner.class);
                startActivityForResult(connectIntent, 20);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.cancel_user, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cancel_action:
                setFragment(new AllBusListFragment());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setFragment(Fragment fragment) {
        // create a FragmentManager
        FragmentManager fm = getActivity().getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.framLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit(); // save the changes
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String id = bundle.getString("params");
            Log.e("TAG", "onActivityResult: " + id);
            if (id == null) {
                Toast.makeText(getActivity(), "Cancel", Toast.LENGTH_SHORT).show();
            } else {
                String encrypted = id;
                String decrypted = "";
                try {
                    decrypted = AESUtils.decrypt(encrypted);
                    Log.d("TEST", "decrypted:" + decrypted);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (String number : stringArrayList) {
                    if (number.equals(decrypted)) {
                        Constance.count = 1;
                        break;
                    } else {
                        Constance.count = 0;
                    }
                }
                if (Constance.count == 1) {
                    commonClass.sweetAlertDialog(decrypted, " Has Checked Into Bus ", SweetAlertDialog.SUCCESS_TYPE);
                } else {
                    commonClass.sweetAlertDialog(decrypted, "  Not Yet register ", SweetAlertDialog.ERROR_TYPE);
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
            Log.e("TAG", "onDataChange: " + dataSnapshot.getValue());
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
