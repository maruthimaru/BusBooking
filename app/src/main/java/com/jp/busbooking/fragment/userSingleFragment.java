package com.jp.busbooking.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jp.busbooking.R;
import com.jp.busbooking.activity.UserMapsActivity;
import com.jp.busbooking.helper.CommonClass;
import com.jp.busbooking.helper.Constance;
import com.jp.busbooking.pojo.BusListModel;
import com.jp.busbooking.pojo.UserModel;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class userSingleFragment extends Fragment {
View view;
UserModel userModel;
EditText userName,mobileNo,age,gmobileNo;
TextView cancel,viewmap;
ImageView userImage,addImage;
CommonClass commonClass;
    DatabaseReference databaseReference;
    List<UserModel> userModelArrayList =new ArrayList<>();
    DatabaseReference reference;
    String busId;
    private ArrayList<String> arrayList=new ArrayList<>();
    BusListModel busListModelList;
    private Bitmap imageBitmap;
    private String TAG=userSingleFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_user_single,container,false );

        userName=view.findViewById(R.id.UserName);
        mobileNo=view.findViewById(R.id.mobileNo);
        gmobileNo=view.findViewById(R.id.gmobileNo);
        age=view.findViewById(R.id.age);
        cancel=view.findViewById(R.id.cancel);
        viewmap=view.findViewById(R.id.viewmap);
        userImage=view.findViewById(R.id.userImage);
        addImage=view.findViewById(R.id.addImage);
        commonClass=new CommonClass(getActivity());

        final Bundle b = getArguments();
        if (!b.equals(null)) {
            userModelArrayList = (List<UserModel>) b.getSerializable(Constance.list);
            busId = userModelArrayList.get(0).getBusid();
            byte[] decodedString = Base64.decode(userModelArrayList.get(0).getBase64(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            commonClass.imageLoad(userImage,decodedByte);
            userName.setText(userModelArrayList.get(0).getName());
            mobileNo.setText(userModelArrayList.get(0).getMobile());
            gmobileNo.setText(userModelArrayList.get(0).getGmobile());
            age.setText(userModelArrayList.get(0).getAge());
            if (!userModelArrayList.get(0).getCheckenStatus().equals("no")){
                cancel.setVisibility(View.GONE);
            }
        }
        databaseReference= FirebaseDatabase.getInstance().getReference("userList");
        reference= FirebaseDatabase.getInstance().getReference("SeatsSelectedList");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child(userModelArrayList.get(0).getBusid()).addListenerForSingleValueEvent(valueEventListenerSeat);
            }
        });

        viewmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                   Intent  intent=new Intent(getActivity(), UserMapsActivity.class);
                        intent.putExtra(Constance.busId,  userModelArrayList.get(0).getBusid());
                        startActivity(intent);
            }
        });

        return view;
    }

    ValueEventListener valueEventListenerSeat = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            userModelArrayList.clear();
            if (userModelArrayList.get(0).getArrayList() != null) {
                Log.e(TAG, "onDataChange: tempArrayList  : " + userModelArrayList.get(0).getArrayList().size());
//                for (String seat:userModelArrayList.get(0).getArrayList()) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Log.e(TAG, "onDataChange: init  " + postSnapshot.getValue());
                    if (!userModelArrayList.get(0).getArrayList().contains(postSnapshot.getValue().toString())) {
                        Log.e(TAG, "onDataChange: array list : " + postSnapshot.getValue());
                        arrayList.add(postSnapshot.getValue().toString());
                    }
                }
//                }
                userModelArrayList.get(0).setCheckenStatus("cancel");
                databaseReference.child(userModelArrayList.get(0).getMobile()).setValue(userModelArrayList.get(0));
                Log.e(TAG, "onclickUpdate: arrayList : " + arrayList + " size : " + arrayList.size());
                reference.child(userModelArrayList.get(0).getBusid()).setValue(arrayList);
                commonClass.sendSMS(userModelArrayList.get(0).getGmobile(),"From BusBooking App \n You have cancel " +
                        "the booking, your payment will be soon re-transfer or you can book with in next week.." );
                setFragment(new LoginFragment());
            }else {
                userModelArrayList.get(0).setCheckenStatus("cancel");
                databaseReference.child(userModelArrayList.get(0).getMobile()).setValue(userModelArrayList.get(0));
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            commonClass.imageLoad(userImage,imageBitmap);
        }
    }

    public String getImageData(Bitmap bmp) {

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bao); // bmp is bitmap busid user image file
        byte[] byteArray = bao.toByteArray();
        String imageB64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        //  store & retrieve this string to firebase
        return imageB64;
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
}
