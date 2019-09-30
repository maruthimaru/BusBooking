package com.jp.busbooking.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jp.busbooking.R;
import com.jp.busbooking.helper.Constance;
import com.jp.busbooking.pojo.BusListModel;
import com.jp.busbooking.pojo.userModel;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

public class userInfoFragment extends Fragment {
View view;
userModel userModel;
EditText userName,mobileNo,age;
FloatingActionButton floatingActionButton;
ImageView userImage,addImage;

    String busId;
    BusListModel busListModelList;
    private Bitmap imageBitmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_user_info,container,false );

        userName=view.findViewById(R.id.UserName);
        mobileNo=view.findViewById(R.id.mobileNo);
        age=view.findViewById(R.id.age);
        floatingActionButton=view.findViewById(R.id.floatingActionButton);
        userImage=view.findViewById(R.id.userImage);
        addImage=view.findViewById(R.id.addImage);

        final Bundle b = getArguments();
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
                            Bitmap bm;
                            if (imageBitmap==null) {
                                bm = ((BitmapDrawable) userImage.getDrawable()).getBitmap();
                            }else {
                                bm=imageBitmap;
                            }
                            String base64="";
                            if (bm!=null) {
                                base64=getImageData(bm);
                            }
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("userList").child(mobile);
                            userModel=new userModel(name,mobile , aget,base64);
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

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
        return view;
    }

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
            userImage.setImageBitmap(imageBitmap);
        }
    }

    public String getImageData(Bitmap bmp) {

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bao); // bmp is bitmap from user image file
        bmp.recycle();
        byte[] byteArray = bao.toByteArray();
        String imageB64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
        //  store & retrieve this string to firebase
        return imageB64;
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
