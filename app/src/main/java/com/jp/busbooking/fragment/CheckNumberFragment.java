package com.jp.busbooking.fragment;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.firebase.database.ValueEventListener;
import com.jp.busbooking.R;
import com.jp.busbooking.helper.AESUtils;
import com.jp.busbooking.helper.CommonClass;
import com.jp.busbooking.helper.Constance;
import com.jp.busbooking.helper.QRCodeScanner;
import com.jp.busbooking.pojo.UserModel;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.app.Activity.RESULT_OK;

public class CheckNumberFragment extends Fragment {
    ImageView imageView2;
    FirebaseDatabase database;
    DatabaseReference myRef;
    CommonClass commonClass;
    ArrayList<String> stringArrayList;
    View view;
    List<UserModel> userModelArrayList =new ArrayList<>();
    UserModel userModel;
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
                for (UserModel number : userModelArrayList) {
                    userModel=number;
                    if (number.getMobile().equals(decrypted)) {
                        myRef.child(decrypted).addListenerForSingleValueEvent(valueEventListenerSeat);
                        if (number.getCheckenStatus().equals("checkout")){
                            Constance.count = 2;
                            number.setCheckenStatus("reg");
                        }else if (number.getCheckenStatus().equals("reg")){
                            Constance.count = 0;
                        }
                        else {
                            Constance.count = 1;
                            number.setCheckenStatus("checkout");
                        }
                        break;
                    } else {
                        Constance.count = 0;
                    }
                }
                if (Constance.count == 1) {
                    commonClass.sweetAlertDialog(decrypted, " Has Checked Into Bus ", SweetAlertDialog.SUCCESS_TYPE);
                } else   if (Constance.count == 2) {
                    commonClass.sweetAlertDialog(decrypted, " Has Checked Out Bus ", SweetAlertDialog.SUCCESS_TYPE);
                }else {
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

    ValueEventListener valueEventListenerSeat = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            userModelArrayList.clear();
            if (userModel != null) {
                if (userModel.getCheckenStatus().equals("yes")){
                    userModel.setCheckenStatus("checkout");
                }else if (userModel.getCheckenStatus().equals("no")){
                    userModel.setCheckenStatus("yes");
                }/*else if (userModel.getCheckenStatus().equals("checkout")){
                    userModel.setCheckenStatus("reg");
                }*/

                myRef.child(userModel.getMobile()).setValue(userModel);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            busListModelList.clear();
            userModelArrayList.clear();
            Log.e("TAG", "onDataChange: " + dataSnapshot.getValue());
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                stringArrayList.add(postSnapshot.child("mobile").getValue().toString());

                String name = postSnapshot.child("name").getValue(String.class);
                String mobile = postSnapshot.child("mobile").getValue(String.class);
                String age = postSnapshot.child("age").getValue(String.class);
                String base64 = postSnapshot.child("base64").getValue(String.class);
                String busid = postSnapshot.child("busid").getValue(String.class);
                String checkenStatus = postSnapshot.child("checkenStatus").getValue(String.class);
                ArrayList<String> tempArrayList = (ArrayList) postSnapshot.child("arrayList").getValue();
                userModelArrayList.add(new UserModel(name, mobile, age, base64, busid, checkenStatus, tempArrayList));
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
