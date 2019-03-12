package com.jp.busbooking.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.jp.busbooking.R;
import com.jp.busbooking.db.DatabaseHelper;
import com.jp.busbooking.helper.StaticData;
import com.jp.busbooking.pojo.BusListModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class SeatSelection extends Fragment {
    ArrayList<String> arrayList = new ArrayList<String>();
    ArrayList<String> arrayList1 = new ArrayList<String>();
    SQLiteDatabase sql;
    DatabaseHelper db;
    StaticData staticData;
    ArrayList<ArrayList<String>> outer = new ArrayList<ArrayList<String>>();
    int seats_counter = 0;
    Button A1, A2, A3, A4, A5, A6, A7, A8, A9, A10;
    Button B1, B2, B3, B4, B5, B6, B7, B8;
    Button C1, C2, C3, C4, C5, C6, C7, C8;
    Button D1, D2, D3, D4, D5, D6, D7, D8;
    Button proceed;
    String temp, temp1;
    int colorid;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference databaseReference;
    int flag = 0;
    Bundle bundle;
    String busId;
    BusListModel busListModelList;
    ValueEventListener valueEventListener;
    int movie;
    String date;
    String seating = "";
    String theatre = "maruthi";
    String time;
    String seats;
    View view;
    private String TAG = SeatSelection.class.getSimpleName();


    private void initFirebase() {
        FirebaseApp.initializeApp(getActivity());
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        //firebase fire storage this data stored in firestorage.
        //getList();
//        addListtoFirebase();
//        getListToFirebase();
    }

    private void addListtoFirebase(ArrayList<String> arrayList) {
        Log.e(TAG, "addListtoFirebase: before add : " + arrayList.size());
//        myRef.child("SeatsSelectedList").removeValue();
//        arrayList=staticData.getStudentList();
        String key = myRef.child("posts").push().getKey();
//        for (String seats:arrayList){
        myRef.child("SeatsSelectedList").child(busId).setValue(arrayList);
//}

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_seat_selection,container,false);
        db = new DatabaseHelper(getActivity());
        initFirebase();
        sql = db.getReadableDatabase();
        staticData=new StaticData(getActivity());
        proceed=view.findViewById(R.id.proceed);
        A1 = (Button) view.findViewById(R.id.A1);
        A2 = (Button) view.findViewById(R.id.A2);
        A3 = (Button) view.findViewById(R.id.A3);
        A4 = (Button) view.findViewById(R.id.A4);
        A5 = (Button) view.findViewById(R.id.A5);
        A6 = (Button) view.findViewById(R.id.A6);


        B1 = (Button) view.findViewById(R.id.B1);
        B2 = (Button) view.findViewById(R.id.B2);
        B3 = (Button) view.findViewById(R.id.B3);
        B4 = (Button) view.findViewById(R.id.B4);


        C1 = (Button) view.findViewById(R.id.C1);
        C2 = (Button) view.findViewById(R.id.C2);
        C3 = (Button) view.findViewById(R.id.C3);
        C4 = (Button) view.findViewById(R.id.C4);


        D1 = (Button) view.findViewById(R.id.D1);

        D2 = (Button) view.findViewById(R.id.D2);
        D3 = (Button) view.findViewById(R.id.D3);
        D4 = (Button) view.findViewById(R.id.D4);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seat_information(view);
            }
        });
        Bundle b = getArguments();
        if (!b.equals(null)) {
            busListModelList = (BusListModel) b.getSerializable("list");
            busId = busListModelList.getId();
        }

        Query query = FirebaseDatabase.getInstance().getReference("SeatsSelectedList/"+busId)
                /*.orderByChild("id")
                .equalTo(busId)*/;

//        databaseReference.addValueEventListener(valueEventListener);
        movie =/* b.getInt("movie_id")*/1;
        date = /*b.getString("date")*/"";
        theatre = /*b.getString("theatre")*/"";
        time = /*b.getString("time")*/"";
        // while should encompass the below code and forloop
        int j = 0;

        System.out.print(theatre);

        //fire base data:

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            busListModelList.clear();

                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Log.e(TAG, "onDataChange: " + postSnapshot.getValue());
                    if (postSnapshot.getKey().equals("0")){
                        seating=postSnapshot.getValue().toString();
                        arrayList.add(postSnapshot.getValue().toString());
                    }else {
                        seating = seating + ',';
                        seating = seating +postSnapshot.getValue();
                        arrayList.add(postSnapshot.getValue().toString());

                    }
                }
                Log.e(TAG, "fassdfsdsdfssdfssdf: "+ seating);
                System.out.print("fassdfsdsdfssdfssdf " + seating);
                List<String> seats_booked = Arrays.asList(seating.split(","));
                Log.e(TAG, "seatbooked : "+ seats_booked.size());
                for (int i = 0; i < seats_booked.size(); i++) {
                    System.out.println("Split String: " + seats_booked.get(i));
                    if ("A1".equals(seats_booked.get(i))) {
                        A1.setBackgroundColor(Color.parseColor("#A45B5B"));
                    } else if ("A2".equals(seats_booked.get(i))) {
                        A2.setBackgroundColor(Color.parseColor("#A45B5B"));
                    } else if ("A3".equals(seats_booked.get(i))) {
                        A3.setBackgroundColor(Color.parseColor("#A45B5B"));
                    } else if ("A4".equals(seats_booked.get(i))) {
                        A4.setBackgroundColor(Color.parseColor("#A45B5B"));
                    } else if ("A5".equals(seats_booked.get(i))) {
                        A5.setBackgroundColor(Color.parseColor("#A45B5B"));
                    } else if ("A6".equals(seats_booked.get(i))) {
                        A6.setBackgroundColor(Color.parseColor("#A45B5B"));
                    } else if ("B1".equals(seats_booked.get(i))) {
                        B1.setBackgroundColor(Color.parseColor("#A45B5B"));
                    } else if ("B2".equals(seats_booked.get(i))) {
                        B2.setBackgroundColor(Color.parseColor("#A45B5B"));
                    } else if ("B3".equals(seats_booked.get(i))) {
                        B3.setBackgroundColor(Color.parseColor("#A45B5B"));
                    } else if ("B4".equals(seats_booked.get(i))) {
                        B4.setBackgroundColor(Color.parseColor("#A45B5B"));
                    } else if ("C1".equals(seats_booked.get(i))) {
                        C1.setBackgroundColor(Color.parseColor("#A45B5B"));

                    } else if ("C2".equals(seats_booked.get(i))) {
                        C2.setBackgroundColor(Color.parseColor("#A45B5B"));
                    } else if ("C3".equals(seats_booked.get(i))) {
                        C3.setBackgroundColor(Color.parseColor("#A45B5B"));
                    } else if ("C4".equals(seats_booked.get(i))) {
                        C4.setBackgroundColor(Color.parseColor("#A45B5B"));
                    } else if ("D1".equals(seats_booked.get(i))) {
                        D1.setBackgroundColor(Color.parseColor("#A45B5B"));
                    } else if ("D2".equals(seats_booked.get(i))) {
                        D2.setBackgroundColor(Color.parseColor("#A45B5B"));
                    } else if ("D3".equals(seats_booked.get(i))) {
                        D3.setBackgroundColor(Color.parseColor("#A45B5B"));
                    } else if ("D4".equals(seats_booked.get(i))) {
                        D4.setBackgroundColor(Color.parseColor("#A45B5B"));
                    }
                }
                Log.e(TAG, "onDataChange: adasda " + seating);
//            setAdapter(busListModelList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        query.addValueEventListener(valueEventListener);

        Cursor seatnos = db.getseats(sql, theatre, date, time, movie);
//        Cursor seatnos = null;
        System.out.print("   " + seatnos.getCount());

        if (seatnos != null && seatnos.getCount() > 0) {
            System.out.print(seating);
            while (seatnos.moveToNext()) {
                if (j == 0) {
                    seating = seatnos.getString(0);

                } else if (seatnos.getCount() > 1) {
                    seating = seating + ',';
                    if (!seatnos.isLast()) {
                        seating = seating + seatnos.getString(0);
                    } else {
                        seating = seating + seatnos.getString(0);
                    }
                }
                j++;
            }
            Log.e(TAG, "fassdfsdsdfssdfssdf: "+ seating);
            System.out.print("fassdfsdsdfssdfssdf " + seating);
            List<String> seats_booked = Arrays.asList(seating.split(","));
            Log.e(TAG, "seatbooked : "+ seats_booked.size());
            for (int i = 0; i < seats_booked.size(); i++) {
                System.out.println("Split String: " + seats_booked.get(i));
                if ("A1".equals(seats_booked.get(i))) {
                    A1.setBackgroundColor(Color.parseColor("#A45B5B"));
                } else if ("A2".equals(seats_booked.get(i))) {
                    A2.setBackgroundColor(Color.parseColor("#A45B5B"));
                } else if ("A3".equals(seats_booked.get(i))) {
                    A3.setBackgroundColor(Color.parseColor("#A45B5B"));
                } else if ("A4".equals(seats_booked.get(i))) {
                    A4.setBackgroundColor(Color.parseColor("#A45B5B"));
                } else if ("A5".equals(seats_booked.get(i))) {
                    A5.setBackgroundColor(Color.parseColor("#A45B5B"));
                } else if ("A6".equals(seats_booked.get(i))) {
                    A6.setBackgroundColor(Color.parseColor("#A45B5B"));
                } else if ("B1".equals(seats_booked.get(i))) {
                    B1.setBackgroundColor(Color.parseColor("#A45B5B"));
                } else if ("B2".equals(seats_booked.get(i))) {
                    B2.setBackgroundColor(Color.parseColor("#A45B5B"));
                } else if ("B3".equals(seats_booked.get(i))) {
                    B3.setBackgroundColor(Color.parseColor("#A45B5B"));
                } else if ("B4".equals(seats_booked.get(i))) {
                    B4.setBackgroundColor(Color.parseColor("#A45B5B"));
                } else if ("C1".equals(seats_booked.get(i))) {
                    C1.setBackgroundColor(Color.parseColor("#A45B5B"));

                } else if ("C2".equals(seats_booked.get(i))) {
                    C2.setBackgroundColor(Color.parseColor("#A45B5B"));
                } else if ("C3".equals(seats_booked.get(i))) {
                    C3.setBackgroundColor(Color.parseColor("#A45B5B"));
                } else if ("C4".equals(seats_booked.get(i))) {
                    C4.setBackgroundColor(Color.parseColor("#A45B5B"));
                } else if ("D1".equals(seats_booked.get(i))) {
                    D1.setBackgroundColor(Color.parseColor("#A45B5B"));
                } else if ("D2".equals(seats_booked.get(i))) {
                    D2.setBackgroundColor(Color.parseColor("#A45B5B"));
                } else if ("D3".equals(seats_booked.get(i))) {
                    D3.setBackgroundColor(Color.parseColor("#A45B5B"));
                } else if ("D4".equals(seats_booked.get(i))) {
                    D4.setBackgroundColor(Color.parseColor("#A45B5B"));
                }
            }
            // }
        } else {
            System.out.print("no print");
        }


        A1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable buttonColor = (ColorDrawable) A1.getBackground();
                colorid = buttonColor.getColor();

                if (colorid == (-1654272)) {
                    System.out.println("I am in selected seat");
                    A1.setBackgroundColor(Color.parseColor("#87A96B"));
                    seats_counter++;
                    arrayList.add("A1");
                    // Toast toast= Toast.makeText(getActivity(),"You have selected this seat.",Toast.LENGTH_LONG);
                    // toast.show();
                    //    System.out.println("The seats are :"+ arrayList);
                } else if (colorid == (-6005925)) {
                    System.out.println("This seat is already booked");
                    Toast toast = Toast.makeText(getActivity(), "Sorry, this seat is already booked.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (colorid == (-7886485)) {
                    System.out.println("This seat is unselected");
                    //  Toast toast= Toast.makeText(getActivity(),"You have unselected this seat.",Toast.LENGTH_LONG);
                    //  toast.show();
                    A1.setBackgroundColor(Color.parseColor("#E6C200"));
                    seats_counter--;
                    arrayList.remove("A1");
                }
            }
        });

        A2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable buttonColor = (ColorDrawable) A2.getBackground();
                colorid = buttonColor.getColor();

                if (colorid == (-1654272)) {
                    A2.setBackgroundColor(Color.parseColor("#87A96B"));
                    seats_counter++;
                    arrayList.add("A2");
                    //   Toast toast= Toast.makeText(getActivity(),"You have selected this seat.",Toast.LENGTH_LONG);
                    //   toast.show();
                    System.out.println("The seats are :" + arrayList);
                } else if (colorid == (-6005925)) {
                    Toast toast = Toast.makeText(getActivity(), "Sorry, this seat is already booked.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (colorid == (-7886485)) {
                    //    Toast toast= Toast.makeText(getActivity(),"You have unselected this seat.",Toast.LENGTH_LONG);
                    //    toast.show();
                    A2.setBackgroundColor(Color.parseColor("#E6C200"));
                    seats_counter--;
                    arrayList.remove("A2");
                }
            }
        });

        A3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable buttonColor = (ColorDrawable) A3.getBackground();
                colorid = buttonColor.getColor();

                if (colorid == (-1654272)) {
                    A3.setBackgroundColor(Color.parseColor("#87A96B"));
                    seats_counter++;
                    arrayList.add("A3");

                } else if (colorid == (-6005925)) {
                    Toast toast = Toast.makeText(getActivity(), "Sorry, this seat is already booked.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (colorid == (-7886485)) {

                    A3.setBackgroundColor(Color.parseColor("#E6C200"));
                    seats_counter--;
                    arrayList.remove("A3");
                }
            }
        });

        A4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable buttonColor = (ColorDrawable) A4.getBackground();
                colorid = buttonColor.getColor();

                if (colorid == (-1654272)) {
                    A4.setBackgroundColor(Color.parseColor("#87A96B"));
                    seats_counter++;
                    arrayList.add("A4");
//                    Toast toast= Toast.makeText(getActivity(),"You have selected this seat.",Toast.LENGTH_LONG);
//                    toast.show();
                } else if (colorid == (-6005925)) {
                    Toast toast = Toast.makeText(getActivity(), "Sorry, this seat is already booked.", Toast.LENGTH_LONG);
                    toast.show();
                } else if (colorid == (-7886485)) {
                    //   Toast toast= Toast.makeText(getActivity(),"You have unselected this seat.",Toast.LENGTH_LONG);
                    //   toast.show();
                    A4.setBackgroundColor(Color.parseColor("#E6C200"));
                    seats_counter--;
                    arrayList.remove("A4");
                }
            }
        });

        A5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable buttonColor = (ColorDrawable) A5.getBackground();
                colorid = buttonColor.getColor();

                if (colorid == (-1654272)) {
                    A5.setBackgroundColor(Color.parseColor("#87A96B"));
                    seats_counter++;
                    arrayList.add("A5");

                } else if (colorid == (-6005925)) {
                    Toast toast = Toast.makeText(getActivity(), "Sorry, this seat is already booked.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (colorid == (-7886485)) {

                    A5.setBackgroundColor(Color.parseColor("#E6C200"));
                    seats_counter--;
                    arrayList.remove("A5");
                }
            }
        });

        A6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable buttonColor = (ColorDrawable) A6.getBackground();
                colorid = buttonColor.getColor();

                if (colorid == (-1654272)) {
                    A6.setBackgroundColor(Color.parseColor("#87A96B"));
                    seats_counter++;
                    arrayList.add("A6");
                    //Toast toast= Toast.makeText(getActivity(),"You have selected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                } else if (colorid == (-6005925)) {
                    Toast toast = Toast.makeText(getActivity(), "Sorry, this seat is already booked.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if (colorid == (-7886485)) {
                    //Toast toast= Toast.makeText(getActivity(),"You have unselected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                    A6.setBackgroundColor(Color.parseColor("#E6C200"));
                    seats_counter--;
                    arrayList.remove("A6");
                }
            }
        });
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable buttonColor = (ColorDrawable) B1.getBackground();
                colorid = buttonColor.getColor();

                if (colorid == (-1654272)) {
                    B1.setBackgroundColor(Color.parseColor("#87A96B"));
                    seats_counter++;
                    arrayList.add("B1");
                    //Toast toast= Toast.makeText(getActivity(),"You have selected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                } else if (colorid == (-6005925)) {
                    Toast toast = Toast.makeText(getActivity(), "Sorry, this seat is already booked.", Toast.LENGTH_LONG);
                    toast.show();
                } else if (colorid == (-7886485)) {
                    //Toast toast= Toast.makeText(getActivity(),"You have unselected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                    B1.setBackgroundColor(Color.parseColor("#E6C200"));
                    seats_counter--;
                    arrayList.remove("B1");
                }
            }
        });

        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable buttonColor = (ColorDrawable) B2.getBackground();
                colorid = buttonColor.getColor();

                if (colorid == (-1654272)) {
                    B2.setBackgroundColor(Color.parseColor("#87A96B"));
                    seats_counter++;
                    arrayList.add("B2");
                    //Toast toast= Toast.makeText(getActivity(),"You have selected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                } else if (colorid == (-6005925)) {
                    Toast toast = Toast.makeText(getActivity(), "Sorry, this seat is already booked.", Toast.LENGTH_LONG);
                    toast.show();
                } else if (colorid == (-7886485)) {
                    //Toast toast= Toast.makeText(getActivity(),"You have unselected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                    B2.setBackgroundColor(Color.parseColor("#E6C200"));
                    seats_counter--;
                    arrayList.remove("B2");
                }
            }
        });

        B3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable buttonColor = (ColorDrawable) B3.getBackground();
                colorid = buttonColor.getColor();

                if (colorid == (-1654272)) {
                    B3.setBackgroundColor(Color.parseColor("#87A96B"));
                    seats_counter++;
                    arrayList.add("B3");
                    //Toast toast= Toast.makeText(getActivity(),"You have selected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                } else if (colorid == (-6005925)) {
                    Toast toast = Toast.makeText(getActivity(), "Sorry, this seat is already booked.", Toast.LENGTH_LONG);
                    toast.show();
                } else if (colorid == (-7886485)) {
                    //Toast toast= Toast.makeText(getActivity(),"You have unselected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                    B3.setBackgroundColor(Color.parseColor("#E6C200"));
                    seats_counter--;
                    arrayList.remove("B3");
                }
            }
        });

        B4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable buttonColor = (ColorDrawable) B4.getBackground();
                colorid = buttonColor.getColor();

                if (colorid == (-1654272)) {
                    B4.setBackgroundColor(Color.parseColor("#87A96B"));
                    seats_counter++;
                    arrayList.add("B4");
                    //Toast toast= Toast.makeText(getActivity(),"You have selected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                } else if (colorid == (-6005925)) {
                    Toast toast = Toast.makeText(getActivity(), "Sorry, this seat is already booked.", Toast.LENGTH_LONG);
                    toast.show();
                } else if (colorid == (-7886485)) {
                    //Toast toast= Toast.makeText(getActivity(),"You have unselected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                    B4.setBackgroundColor(Color.parseColor("#E6C200"));
                    seats_counter--;
                    arrayList.remove("B4");
                }
            }
        });

        C1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable buttonColor = (ColorDrawable) C1.getBackground();
                colorid = buttonColor.getColor();

                if (colorid == (-1654272)) {
                    C1.setBackgroundColor(Color.parseColor("#87A96B"));
                    seats_counter++;
                    arrayList.add("C1");
                    //Toast toast= Toast.makeText(getActivity(),"You have selected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                } else if (colorid == (-6005925)) {
                    Toast toast = Toast.makeText(getActivity(), "Sorry, this seat is already booked.", Toast.LENGTH_LONG);
                    toast.show();
                } else if (colorid == (-7886485)) {
                    //Toast toast= Toast.makeText(getActivity(),"You have unselected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                    C1.setBackgroundColor(Color.parseColor("#E6C200"));
                    seats_counter--;
                    arrayList.remove("C1");
                }

            }
        });

        C2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable buttonColor = (ColorDrawable) C2.getBackground();
                colorid = buttonColor.getColor();

                if (colorid == (-1654272)) {
                    C2.setBackgroundColor(Color.parseColor("#87A96B"));
                    seats_counter++;
                    arrayList.add("C2");
                    //Toast toast= Toast.makeText(getActivity(),"You have selected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                } else if (colorid == (-6005925)) {
                    Toast toast = Toast.makeText(getActivity(), "Sorry, this seat is already booked.", Toast.LENGTH_LONG);
                    toast.show();
                } else if (colorid == (-7886485)) {
                    //Toast toast= Toast.makeText(getActivity(),"You have unselected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                    C2.setBackgroundColor(Color.parseColor("#E6C200"));
                    seats_counter--;
                    arrayList.remove("C2");
                }
            }
        });

        C3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable buttonColor = (ColorDrawable) C3.getBackground();
                colorid = buttonColor.getColor();

                if (colorid == (-1654272)) {
                    C3.setBackgroundColor(Color.parseColor("#87A96B"));
                    seats_counter++;
                    arrayList.add("C3");
                    // Toast toast= Toast.makeText(getActivity(),"You have selected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                } else if (colorid == (-6005925)) {
                    Toast toast = Toast.makeText(getActivity(), "Sorry, this seat is already booked.", Toast.LENGTH_LONG);
                    toast.show();
                } else if (colorid == (-7886485)) {
                    //Toast toast= Toast.makeText(getActivity(),"You have unselected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                    C3.setBackgroundColor(Color.parseColor("#E6C200"));
                    seats_counter--;
                    arrayList.remove("C3");
                }
            }
        });

        C4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable buttonColor = (ColorDrawable) C4.getBackground();
                colorid = buttonColor.getColor();

                if (colorid == (-1654272)) {
                    C4.setBackgroundColor(Color.parseColor("#87A96B"));
                    seats_counter++;
                    arrayList.add("C4");
                    //Toast toast= Toast.makeText(getActivity(),"You have selected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                } else if (colorid == (-6005925)) {
                    Toast toast = Toast.makeText(getActivity(), "Sorry, this seat is already booked.", Toast.LENGTH_LONG);
                    toast.show();
                } else if (colorid == (-7886485)) {
                    //Toast toast= Toast.makeText(getActivity(),"You have unselected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                    C4.setBackgroundColor(Color.parseColor("#E6C200"));
                    seats_counter--;
                    arrayList.remove("C4");
                }
            }
        });

        D1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable buttonColor = (ColorDrawable) D1.getBackground();
                colorid = buttonColor.getColor();

                if (colorid == (-1654272)) {
                    D1.setBackgroundColor(Color.parseColor("#87A96B"));
                    seats_counter++;
                    arrayList.add("D1");
                    //Toast toast= Toast.makeText(getActivity(),"You have selected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                } else if (colorid == (-6005925)) {
                    Toast toast = Toast.makeText(getActivity(), "Sorry, this seat is already booked.", Toast.LENGTH_LONG);
                    toast.show();
                } else if (colorid == (-7886485)) {
                    //Toast toast= Toast.makeText(getActivity(),"You have unselected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                    D1.setBackgroundColor(Color.parseColor("#E6C200"));
                    seats_counter--;
                    arrayList.remove("D1");
                }
            }
        });

        D2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable buttonColor = (ColorDrawable) D2.getBackground();
                colorid = buttonColor.getColor();

                if (colorid == (-1654272)) {
                    D2.setBackgroundColor(Color.parseColor("#87A96B"));
                    seats_counter++;
                    arrayList.add("D2");
                    //Toast toast= Toast.makeText(getActivity(),"You have selected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                } else if (colorid == (-6005925)) {
                    Toast toast = Toast.makeText(getActivity(), "Sorry, this seat is already booked.", Toast.LENGTH_LONG);
                    toast.show();
                } else if (colorid == (-7886485)) {
                    //Toast toast= Toast.makeText(getActivity(),"You have unselected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                    D2.setBackgroundColor(Color.parseColor("#E6C200"));
                    seats_counter--;
                    arrayList.remove("D2");
                }
            }
        });

        D3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable buttonColor = (ColorDrawable) D3.getBackground();
                colorid = buttonColor.getColor();

                if (colorid == (-1654272)) {
                    D3.setBackgroundColor(Color.parseColor("#87A96B"));
                    seats_counter++;
                    arrayList.add("D3");
                    //Toast toast= Toast.makeText(getActivity(),"You have selected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                } else if (colorid == (-6005925)) {
                    Toast toast = Toast.makeText(getActivity(), "Sorry, this seat is already booked.", Toast.LENGTH_LONG);
                    toast.show();
                } else if (colorid == (-7886485)) {
                    //Toast toast= Toast.makeText(getActivity(),"You have unselected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                    D3.setBackgroundColor(Color.parseColor("#E6C200"));
                    seats_counter--;
                    arrayList.remove("D3");
                }
            }
        });

        D4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable buttonColor = (ColorDrawable) D4.getBackground();
                colorid = buttonColor.getColor();

                if (colorid == (-1654272)) {
                    D4.setBackgroundColor(Color.parseColor("#87A96B"));
                    seats_counter++;
                    arrayList.add("D4");
                    //Toast toast= Toast.makeText(getActivity(),"You have selected this seat.",Toast.LENGTH_SHORT);
                    //toast.show();
                } else if (colorid == (-6005925)) {
                    Toast toast = Toast.makeText(getActivity(), "Sorry, this seat is already booked.", Toast.LENGTH_LONG);
                    toast.show();
                } else if (colorid == (-7886485)) {
                    //Toast toast= Toast.makeText(getActivity(),"You have unselected this seat.",Toast.LENGTH_LONG);
                    //toast.show();
                    D4.setBackgroundColor(Color.parseColor("#E6C200"));
                    seats_counter--;
                    arrayList.remove("D4");
                }
            }
        });
        return view;
    }


    public void counter() {

        int size;

        if (flag == 0) {
            temp = Integer.toString(seats_counter);
            arrayList1.add(temp);
            outer.add(arrayList1);
            flag = 1;
        } else {
            size = arrayList1.size();
            String get_currval = arrayList1.get(size - 1);
            arrayList1.remove(get_currval);
            temp = Integer.toString(seats_counter);
            arrayList1.add(temp);
            outer.add(arrayList1);
            //   System.out.println("Seat Count value changed:"+ temp);


        }
    }


    /* private void addListtoFirebase(){
         myRef.child("busList").removeValue();
         busListModelArrayList=staticData.getStudentList();
         for (BusListModel studentListModel:busListModelArrayList){
             myRef.child("busList").child(studentListModel.getId()).setValue(studentListModel);
         }
     }*/
    public void seat_information(View V) {

        Intent intent;
        outer.clear();
        outer.add(arrayList);
        counter();

        Log.e(TAG, "seat_information: " + seats_counter);
        Log.e(TAG, "total_information: " + arrayList1.size());

        if (seats_counter != 0) {
            bundle = new Bundle();

            bundle.putStringArrayList("seats", arrayList);
            bundle.putStringArrayList("total", arrayList1);
            addListtoFirebase(arrayList);
            staticData.sweetAlertDialog("Bus Ticket Booked","Get your QR Code", SweetAlertDialog.SUCCESS_TYPE);
            setFragment(new DoneFragment(),busListModelList,seats_counter);
           /* System.out.println("The arraylist items are :"+ outer);
            System.out.println("The movie is : "+ movie);
            System.out.println("The date is: "+ date);*/

          /*  intent = new Intent(SeatSelection.this,Confirmation.class);
            intent.putExtra("seat_information",bundle);
            intent.putExtra("movie_id", Integer.toString(movie));
            intent.putExtra("date", date);
            intent.putExtra("theatre", theatre);
            intent.putExtra("time", time);
            startActivity(intent);*/
        } else {
            Toast toast = Toast.makeText(getActivity(), "Please select a seat to proceed", Toast.LENGTH_LONG);
            toast.show();
        }
    }
    private void setFragment(Fragment fragment,BusListModel busListModelList,int seatCount) {
        // create a FragmentManager
        Bundle bundle=new Bundle();
        bundle.putSerializable("list",busListModelList);
        bundle.putInt("seatCount",seatCount);
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