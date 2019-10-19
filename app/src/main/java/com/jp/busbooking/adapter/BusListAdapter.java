package com.jp.busbooking.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jp.busbooking.R;
import com.jp.busbooking.pojo.BusListModel;

import java.util.List;

public class BusListAdapter extends RecyclerView.Adapter<BusListAdapter.StudentModel> {

    Context context;
    List<BusListModel> BusListModelList;
    ItemListener listener;
    public interface ItemListener {
       void onclickUpdate(BusListModel BusListModelList);
    }

    public BusListAdapter(Context context, List<BusListModel> BusListModelList, ItemListener listener) {
        this.context = context;
        this.listener = listener;
        this.BusListModelList = BusListModelList;
    }

    @NonNull
    @Override
    public StudentModel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.homelist_demo,null);
        return new StudentModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentModel holder, int position) {
    final BusListModel busListModel=BusListModelList.get(position);
        holder.name.setText(busListModel.getBusName());
        holder.acnon.setText(busListModel.getAcNon());
        holder.seats.setText("seats : "+busListModel.getSeats());
        holder.prize.setText("₹"+busListModel.getAmount());
        holder.rating.setText(busListModel.getRatings());
        holder.startTime.setText(busListModel.getFrom());
        holder.endTime.setText(busListModel.getTo());
        holder.memberRating.setText(busListModel.getRatingMember());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
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

        TextView name,acnon,seats,rating,memberRating,startTime,endTime,prize;
        CardView cardView;
        StudentModel(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            acnon=itemView.findViewById(R.id.acnon);
            seats=itemView.findViewById(R.id.seats);
            rating=itemView.findViewById(R.id.rating);
            memberRating=itemView.findViewById(R.id.memberRating);
            startTime=itemView.findViewById(R.id.startTime);
            endTime=itemView.findViewById(R.id.endTime);
            prize=itemView.findViewById(R.id.prize);
            cardView=itemView.findViewById(R.id.card_view);
        }
    }
}
