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
import com.google.firebase.database.ValueEventListener;
import com.jp.busbooking.R;
import com.jp.busbooking.adapter.BusListAdapter;
import com.jp.busbooking.pojo.BusListModel;
import com.jp.busbooking.pojo.ImagesModel;

import java.util.ArrayList;
import java.util.List;

public class AllBusListFragment extends Fragment implements BusListAdapter.ItemListener {
    View view;
    RecyclerView recyclerView;
    BusListAdapter busListAdapter;
    DatabaseReference databaseReference;
    String from, to;
    List<BusListModel> busListModelList = new ArrayList<>();
    private int i=0;
    private String TAG=AllBusListFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bus_list, container, false);
        recyclerView = view.findViewById(R.id.buslistRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        //related to select * busid booklist
        databaseReference = FirebaseDatabase.getInstance().getReference("busList");

        databaseReference.addValueEventListener(valueEventListener);
//        query.addValueEventListener(valueEventListener);
        return view;
    }

    public void setAdapter(List<BusListModel> busListModelList) {
        busListAdapter = new BusListAdapter(getActivity(), busListModelList, this);
        recyclerView.setAdapter(busListAdapter);
    }

    //    public void getListToFirebase(){
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            busListModelList.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    StudentListModel studentListModel =postSnapshot.getValue(StudentListModel.class);
//                    userModelArrayList.add(studentListModel);
//                    staticData.getStudentList();
                String acNon = postSnapshot.child("acNon").getValue(String.class);
                String amount = postSnapshot.child("amount").getValue(String.class);
                String id = postSnapshot.child("id").getValue(String.class);
                String busName = postSnapshot.child("busName").getValue(String.class);
                String endTiming = postSnapshot.child("endTiming").getValue(String.class);
                String from = postSnapshot.child("from").getValue(String.class);
                String ratingMember = postSnapshot.child("ratingMember").getValue(String.class);
                String ratings = postSnapshot.child("ratings").getValue(String.class);
                String seats = postSnapshot.child("seats").getValue(String.class);
                String startTimings = postSnapshot.child("startTimings").getValue(String.class);
                String to = postSnapshot.child("to").getValue(String.class);
                List<Integer> imageList=new ArrayList<>();
                imageList= (List<Integer>) postSnapshot.child("bus_images_list").getValue();
                Log.e(TAG, "onDataChange: i = "+ i + " value : " + imageList.size());
                busListModelList.add(new BusListModel(id, busName, seats, ratings, amount, startTimings, endTiming, ratingMember, acNon, from, to,imageList));
            }
            setAdapter(busListModelList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
//    }

    @Override
    public void onclickUpdate(BusListModel busListModelList) {
       /* Intent intent=new Intent(getActivity(), SeatSelection.class);
        intent.putExtra("list",userModelArrayList);
        startActivity(intent);*/
        setFragment(new UserListFragment(), busListModelList);
    }

    private void setFragment(Fragment fragment, BusListModel busListModelList) {
        // create a FragmentManager
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", busListModelList);
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
