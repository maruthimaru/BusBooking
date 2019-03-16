package com.jp.busbooking.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jp.busbooking.R;
import com.jp.busbooking.helper.Constance;
import com.jp.busbooking.pojo.BusListModel;
import com.jp.busbooking.pojo.userModel;

public class userInfoFragment extends Fragment {
View view;
userModel userModel;
EditText userName,mobileNo,age;
FloatingActionButton floatingActionButton;
    String busId;
    BusListModel busListModelList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_user_info,container,false );

        userName=view.findViewById(R.id.UserName);
        mobileNo=view.findViewById(R.id.mobileNo);
        age=view.findViewById(R.id.age);
        floatingActionButton=view.findViewById(R.id.floatingActionButton);
        Bundle b = getArguments();
        if (!b.equals(null)) {
            busListModelList = (BusListModel) b.getSerializable("list");
            busId = busListModelList.getId();
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=userName.getText().toString();
                String mobile=mobileNo.getText().toString();
                String aget=age.getText().toString();
                if (name.length()>0){
                    userName.setError(null);
                    if (mobile.length()>0){
                        mobileNo.setError(null);
                        if (aget.length()>0){
                            age.setError(null);
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("userList").child(mobile);
                            userModel=new userModel(name,mobile , aget);
                            reference.setValue(userModel);
                            setFragment(new SeatSelection(),busListModelList);
                            Constance.mobile=mobile;
                        }else {
                            age.requestFocus();
                            age.setError("age required");
                        }
                    }else {
                        mobileNo.requestFocus();
                        mobileNo.setError("mobile Number required");
                    }
                }else {
                    userName.requestFocus();
                    userName.setError("name required");
                }
            }
        });
        return view;
    }

    private void setFragment(Fragment fragment,BusListModel busListModelList) {
        // create a FragmentManager
        Bundle bundle=new Bundle();
        bundle.putSerializable("list",busListModelList);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragment.setArguments(bundle);
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.framLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit(); // save the changes
    }
}
