package com.jp.busbooking.fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jp.busbooking.R;
import com.jp.busbooking.adapter.UserListAdapter;
import com.jp.busbooking.pojo.BusListModel;
import com.jp.busbooking.pojo.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment implements UserListAdapter.ItemListener {
View view;
RecyclerView recyclerView;
    UserListAdapter busListAdapter;
DatabaseReference databaseReference,reference;
    BusListModel busid;
List<UserModel> userModelArrayList =new ArrayList<>();
    private String TAG=UserListFragment.class.getSimpleName();
    private ArrayList<String> arrayList=new ArrayList<>();
    private ArrayList<String> tempArrayList=new ArrayList<>();
    private Query query;
    private UserModel busListModelList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_bus_list,container,false);
        recyclerView=view.findViewById(R.id.buslistRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        //related to select * busid booklist
        Bundle bundle=getArguments();
        if (bundle!=null){
            busid = (BusListModel) bundle.getSerializable("list");
        }
        databaseReference= FirebaseDatabase.getInstance().getReference("userList");
        reference= FirebaseDatabase.getInstance().getReference("SeatsSelectedList");

        databaseReference.addValueEventListener(valueEventListener);

//        query.addValueEventListener(valueEventListener);
        return view;
    }
    public void setAdapter(List<UserModel> busListModelList){
        busListAdapter=new UserListAdapter(getActivity(),busListModelList,this);
        recyclerView.setAdapter(busListAdapter);
    }

//    public void getListToFirebase(){
        ValueEventListener valueEventListener=new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        userModelArrayList.clear();
        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    StudentListModel studentListModel =postSnapshot.getValue(StudentListModel.class);
//                    userModelArrayList.add(studentListModel);
//                    staticData.getStudentList();
            if (busid != null && postSnapshot.child("busid").getValue(String.class).toLowerCase().trim().equals(busid.getId()) &&
                    postSnapshot.child("checkenStatus").getValue(String.class).toLowerCase().trim().equals("no")) {

                String name = postSnapshot.child("name").getValue(String.class);
                String mobile = postSnapshot.child("mobile").getValue(String.class);
                String age = postSnapshot.child("age").getValue(String.class);
                String base64 = postSnapshot.child("base64").getValue(String.class);
                String busid = postSnapshot.child("busid").getValue(String.class);
                String checkenStatus = postSnapshot.child("checkenStatus").getValue(String.class);
                ArrayList<String> tempArrayList = (ArrayList) postSnapshot.child("arrayList").getValue();
                userModelArrayList.add(new UserModel(name, mobile, age, base64, busid, checkenStatus, tempArrayList));
            }
        }
        setAdapter(userModelArrayList);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
};
//    }

    @Override
    public void onclickUpdate(final UserModel busListModelList) {
       /* Intent intent=new Intent(getActivity(), SeatSelection.class);
        intent.putExtra("list",userModelArrayList);
        startActivity(intent);*/
       this.busListModelList=busListModelList;

        reference.child(busListModelList.getBusid()).addListenerForSingleValueEvent(valueEventListenerSeat);

//        setFragment(new userInfoFragment(),userModelArrayList);
    }

    ValueEventListener valueEventListenerSeat = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            userModelArrayList.clear();
            if (busListModelList.getArrayList() != null) {
                Log.e(TAG, "onDataChange: tempArrayList  : " + busListModelList.getArrayList().size());
//                for (String seat:busListModelList.getArrayList()) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.e(TAG, "onDataChange: init  " + postSnapshot.getValue());
                    if (!busListModelList.getArrayList().contains(postSnapshot.getValue().toString())) {
                        Log.e(TAG, "onDataChange: array list : " + postSnapshot.getValue());
                        arrayList.add(postSnapshot.getValue().toString());
                    }
                }
//                }
                busListModelList.setCheckenStatus("cancel");
                databaseReference.child(busListModelList.getMobile()).setValue(busListModelList);
                Log.e(TAG, "onclickUpdate: arrayList : " + arrayList + " size : " + arrayList.size());
                reference.child(busid.getId()).setValue(arrayList);
            }else {
                busListModelList.setCheckenStatus("cancel");
                databaseReference.child(busListModelList.getMobile()).setValue(busListModelList);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void setFragment(Fragment fragment,BusListModel busListModelList) {
        // create a FragmentManager
        Bundle bundle=new Bundle();
        bundle.putSerializable("list",busListModelList);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.framLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit(); // save the changes
    }

}
