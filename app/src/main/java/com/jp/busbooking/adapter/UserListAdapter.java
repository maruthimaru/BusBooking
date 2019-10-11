package com.jp.busbooking.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jp.busbooking.R;
import com.jp.busbooking.helper.CommonClass;
import com.jp.busbooking.pojo.BusListModel;
import com.jp.busbooking.pojo.UserModel;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.StudentModel> {

    Context context;
    List<UserModel> BusListModelList;
    ItemListener listener;
    private CommonClass commonClass;

    public interface ItemListener {
       void onclickUpdate(UserModel BusListModelList);
    }

    public UserListAdapter(Context context, List<UserModel> BusListModelList, ItemListener listener) {
        this.context = context;
        this.listener = listener;
        this.BusListModelList = BusListModelList;
        commonClass=new CommonClass(context);
    }

    @NonNull
    @Override
    public StudentModel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.homelist_demo_user,null);
        return new StudentModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentModel holder, int position) {
    final UserModel busListModel=BusListModelList.get(position);
        holder.name.setText(busListModel.getName());
        holder.acnon.setText(busListModel.getMobile());
        if (busListModel.getArrayList()!=null) {
            holder.seats.setText("seats : " + busListModel.getArrayList().toString());
        }else {
            holder.seats.setText("age : " + busListModel.getAge());
        }
        byte[] decodedString = Base64.decode(busListModel.getBase64(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        commonClass.imageLoad(holder.orderimages,decodedByte);
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onclickUpdate(busListModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return BusListModelList.size();
    }

    class StudentModel extends RecyclerView.ViewHolder {

        TextView name,acnon,seats,rating,memberRating,startTime,endTime,cancel;
        CardView cardView;
        ImageView orderimages;
        StudentModel(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            acnon=itemView.findViewById(R.id.acnon);
            seats=itemView.findViewById(R.id.seats);
            rating=itemView.findViewById(R.id.rating);
            memberRating=itemView.findViewById(R.id.memberRating);
            startTime=itemView.findViewById(R.id.startTime);
            endTime=itemView.findViewById(R.id.endTime);
            cancel=itemView.findViewById(R.id.cancel);
            cardView=itemView.findViewById(R.id.card_view);
            orderimages=itemView.findViewById(R.id.orderimages);
        }
    }
}
