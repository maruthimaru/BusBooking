package com.jp.busbooking.helper;

import android.content.Context;

import com.jp.busbooking.pojo.BusListModel;

import java.util.ArrayList;
import java.util.List;

public class StaticData {
    Context context;

    public StaticData(Context context) {
        this.context = context;
    }

    public List<BusListModel> getStudentList(){
        List<BusListModel> studentListModelArrayList=new ArrayList<>();
        studentListModelArrayList.add(new BusListModel("1","No 1 AirTravels","30","4.0","600","23:00","05:30","651","A/C"
                ,"Bangalore","coimbatore"));
        studentListModelArrayList.add(new BusListModel("2","A1 Travels","52","4.2","800","22:00","04:30","1251","A/C"
                ,"Bangalore","coimbatore"));
        studentListModelArrayList.add(new BusListModel("3","National Travels","45","2.5","400","23:30","06:30","241","Non A/C"
                ,"Madurai","coimbatore"));
        studentListModelArrayList.add(new BusListModel("4","A1 Travels","52","4.2","1200","22:00","06:30","1251","A/C"
                ,"Madurai","coimbatore"));
        return studentListModelArrayList;
    }
}
