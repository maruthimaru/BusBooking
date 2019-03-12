package com.jp.busbooking.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jp.busbooking.R;
import com.jp.busbooking.activity.SeatSelection;
import com.jp.busbooking.adapter.BusListAdapter;
import com.jp.busbooking.pojo.BusListModel;

import java.util.ArrayList;
import java.util.List;

public class BusListFragment extends Fragment implements BusListAdapter.ItemListener {
View view;
RecyclerView recyclerView;
BusListAdapter busListAdapter;
DatabaseReference databaseReference;
List<BusListModel> busListModelList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_bus_list,container,false);
        recyclerView=view.findViewById(R.id.buslistRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        //related to select * from booklist
        databaseReference= FirebaseDatabase.getInstance().getReference("busList");
        databaseReference.addValueEventListener(valueEventListener);

        return view;
    }
    public void setAdapter(List<BusListModel> busListModelList){
        busListAdapter=new BusListAdapter(getActivity(),busListModelList,this);
        recyclerView.setAdapter(busListAdapter);
    }

//    public void getListToFirebase(){
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                busListModelList.clear();
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
//                    StudentListModel studentListModel =postSnapshot.getValue(StudentListModel.class);
//                    busListModelList.add(studentListModel);
//                    staticData.getStudentList();
                    String acNon = postSnapshot.child("acNon").getValue(String.class);
                    String amount = postSnapshot.child("amount").getValue(String.class);
                    String id = postSnapshot.child("id").getValue(String.class);
                    String busName= postSnapshot.child("busName").getValue(String.class);
                    String endTiming= postSnapshot.child("endTiming").getValue(String.class);
                    String from= postSnapshot.child("from").getValue(String.class);
                    String ratingMember= postSnapshot.child("ratingMember").getValue(String.class);
                    String ratings= postSnapshot.child("ratings").getValue(String.class);
                    String seats= postSnapshot.child("seats").getValue(String.class);
                    String startTimings= postSnapshot.child("startTimings").getValue(String.class);
                    String to= postSnapshot.child("to").getValue(String.class);
                    busListModelList.add(new BusListModel(id,busName,seats,ratings,amount,startTimings,endTiming,ratingMember,acNon,from,to));
                }
                setAdapter(busListModelList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
//    }

    @Override
    public void onclickUpdate(BusListModel BusListModelList) {
        startActivity(new Intent(getActivity(), SeatSelection.class));
    }
}
