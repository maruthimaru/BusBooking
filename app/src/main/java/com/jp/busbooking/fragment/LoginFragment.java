package com.jp.busbooking.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jp.busbooking.R;
import com.jp.busbooking.activity.UserMapsActivity;
import com.jp.busbooking.helper.Constance;
import com.jp.busbooking.pojo.DriverModel;
import com.jp.busbooking.pojo.UserModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoginFragment extends Fragment implements View.OnClickListener {

    RadioGroup radioGroup;
    RadioButton admin, staff, student;
    TextView login;
    private String type = "admin";
    EditText userId, password;
    DriverModel driverModel;
    View view;
    DatabaseReference databaseReference;
    List<UserModel> userModelArrayList =new ArrayList<>();


    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange (@NonNull DataSnapshot dataSnapshot){
//            userModelArrayList.clear();
            Log.e(TAG,"valueEventListener :" +dataSnapshot.getValue() );
            if (dataSnapshot.getValue()!=null){
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    String busid = postSnapshot.child("busid").getValue(String.class);
                    String id = postSnapshot.child("id").getValue(String.class);
                    String lat = postSnapshot.child("lat").getValue(String.class);
                    String lng= postSnapshot.child("lng").getValue(String.class);
                    String name= postSnapshot.child("name").getValue(String.class);
                     driverModel= new DriverModel(name,id,busid,lat,lng);
                }
                setFragment(new DriverHomeFragment(),driverModel);
            }else {
                databaseReference= FirebaseDatabase.getInstance().getReference("userList");
                databaseReference.addListenerForSingleValueEvent(valueEventListenerUser);
            }
//
        }

        //userlist
        ValueEventListener valueEventListenerUser=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userModelArrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    StudentListModel studentListModel =postSnapshot.getValue(StudentListModel.class);
//                    userModelArrayList.add(studentListModel);
//                    staticData.getStudentList();
                    Log.e(TAG,"valueEventListenerUser userId  : " +userId.getText().toString() +" mobile : "+postSnapshot.child("mobile").getValue(String.class) );
                    if (postSnapshot.child("mobile").getValue(String.class).toLowerCase().trim().equals(userId.getText().toString()) &&
                            !postSnapshot.child("checkenStatus").getValue(String.class).toLowerCase().trim().equals("cancel")) {

                        String name = postSnapshot.child("name").getValue(String.class);
                        String mobile = postSnapshot.child("mobile").getValue(String.class);
                        String gmobile = postSnapshot.child("gmobile").getValue(String.class);
                        String age = postSnapshot.child("age").getValue(String.class);
                        String base64 = postSnapshot.child("base64").getValue(String.class);
                        String busid = postSnapshot.child("busid").getValue(String.class);
                        String checkenStatus = postSnapshot.child("checkenStatus").getValue(String.class);
                        ArrayList<String> tempArrayList = (ArrayList) postSnapshot.child("arrayList").getValue();
                        userModelArrayList.add(new UserModel(name, mobile, age, base64, busid, checkenStatus, tempArrayList,gmobile));

                        setFragment(new userSingleFragment(),userModelArrayList);

                    }else {
                        Toast.makeText(getActivity(), "Invaild Login", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Toast.makeText(getActivity(), "Invaild Login", Toast.LENGTH_SHORT).show();
        }
    };
    private String TAG=LoginFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_login,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userId = view.findViewById(R.id.userId);
        password = view.findViewById(R.id.password);
        radioGroup = view.findViewById(R.id.radioGroupChoose);
        admin = view.findViewById(R.id.admin);
        staff = view.findViewById(R.id.staff);
        student = view.findViewById(R.id.student);
        login = view.findViewById(R.id.login);
        admin.setChecked(true);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.admin:
                        type = "admin";
                        break;
                    case R.id.staff:
                        type = "staff";
                        break;
                    case R.id.student:
                        type = "student";
                        break;
                }
            }
        });
       /* studentModelArrayList=dbHelper.getStudent();
        if (studentModelArrayList.size()>0){
            for (StudentModel studentModel:studentModelArrayList){
                if (!dbHelper.checkData(DBHelper.dbStudentDaily,DBHelper.userId,studentModel.getUserId(),DBHelper.date,commonMethod.date())){
                    dbHelper.insertstudentDaily(studentModel.getUserId(),studentModel.getStaffId(),studentModel.getStudentName(),studentModel.getEmail(),
                            studentModel.getStudentPassword(),studentModel.getImage(),studentModel.getChooseClass(),studentModel.getAttendance(),
                            commonMethod.date());
                }
            }
        }*/

        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                validation();
                break;
        }
    }

    private void validation() {

        String userID = userId.getText().toString();
        String pass = password.getText().toString();
        if (userID.length() > 0) {
            userId.setError(null);
            if (pass.length() > 0) {
                password.setError(null);
//                switch (type) {
//                    case "admin":
                if (userID.equals("admin") && pass.equals("admin")) {

                    setFragment(new CheckNumberFragment());
                    Toast.makeText(getActivity(), "Admin Login", Toast.LENGTH_SHORT).show();
                } else {
                    Query query = FirebaseDatabase.getInstance().getReference("driverList/")
                            .orderByChild("id")
                            .equalTo(userID);

                    query.addListenerForSingleValueEvent(valueEventListener);
                }
            } else {
                password.requestFocus();
                password.setError("required password");
            }
        } else {
            userId.requestFocus();
            userId.setError("required userId");
        }

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

    private void setFragment(Fragment fragment,DriverModel driverModel) {
        // create a FragmentManager
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constance.list,driverModel);
        fragment.setArguments(bundle);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.framLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit(); // save the changes
    }

    private void setFragment(Fragment fragment, List<UserModel> userLlist) {
        // create a FragmentManager
        Bundle bundle=new Bundle();
        bundle.putSerializable(Constance.list, (Serializable) userLlist);
        fragment.setArguments(bundle);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.framLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit(); // save the changes
    }
}
