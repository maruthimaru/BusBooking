package com.jp.busbooking.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.jp.busbooking.R;
import com.jp.busbooking.helper.AESUtils;
import com.jp.busbooking.helper.CommonClass;
import com.jp.busbooking.helper.Constance;
import com.jp.busbooking.pojo.BusListModel;

import java.util.ArrayList;

public class DoneFragment extends Fragment {
    ImageView imageView,userImage;
    Button button,done;
    EditText editText;
    String EditTextValue;
    TextView userName,userMobile;
    Thread thread;
    public final static int QRcodeWidth = 500;
    Bitmap bitmap;
    BusListModel busListModelList;
    View view;
    ArrayList<String> stringArrayList;
    int seatCount;
    CommonClass commonClass;
    TextView busname,seat,seatSelected;
    private String TAG=DoneFragment.class.getSimpleName();
    DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_done,container,false);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        editText = (EditText) view.findViewById(R.id.editText);
        button = (Button) view.findViewById(R.id.button);
        busname =  view.findViewById(R.id.busName);
        seat =  view.findViewById(R.id.seat);
        seatSelected=view.findViewById(R.id.seatSelected);
        done = (Button) view.findViewById(R.id.done);
        userImage=view.findViewById(R.id.userImage);
        userName=view.findViewById(R.id.userName);
        userMobile=view.findViewById(R.id.userMobile);
        commonClass=new CommonClass(getActivity());

        Bundle b = getArguments();
        if (!b.equals(null)) {
            busListModelList = (BusListModel) b.getSerializable("list");
            seatCount=b.getInt("seatCount");
            stringArrayList= (ArrayList<String>) b.getSerializable("arrayList");
            seatSelected.setText("Selected Seats = "+stringArrayList.toString());
            Log.e(TAG, "onCreateView: "+seatCount );
            busname.setText(busListModelList.getBusName());
            seat.setText("Total Cost = "+seatCount +"x"+busListModelList.getAmount()+" = ₹"+ seatCount*Integer.parseInt(busListModelList.getAmount()));
        }

        databaseReference= FirebaseDatabase.getInstance().getReference("userList");

        databaseReference.addValueEventListener(valueEventListener);
        try {

            String encrypted = "";
            String sourceStr = Constance.mobile;
            try {
                encrypted = AESUtils.encrypt(sourceStr);
                Log.d("TEST", "encrypted:" + encrypted);
            } catch (Exception e) {
                e.printStackTrace();
            }
            bitmap = TextToImageEncode(encrypted);
            commonClass.imageLoad(imageView,bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constance.mobile="";
                setFragment(new ChoosePlaceFragment());
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EditTextValue = editText.getText().toString();

                try {
                    bitmap = TextToImageEncode(EditTextValue);
                    commonClass.imageLoad(imageView,bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

            }
        });
        return view;
    }


    //    public void getListToFirebase(){
    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                Log.e(TAG, "onDataChange: mobile : "+ Constance.mobile + " count : "+postSnapshot.getChildrenCount() );
//                    StudentListModel studentListModel =postSnapshot.getValue(StudentListModel.class);
//                    userModelArrayList.add(studentListModel);
//                    staticData.getStudentList();
                Log.e(TAG, "onDataChange: mobile : "+ Constance.mobile + " value : " + postSnapshot.child("mobile").getValue(String.class) );
                if (postSnapshot.child("mobile").getValue(String.class).toLowerCase().trim().equals(Constance.mobile)){
                    String age = postSnapshot.child("age").getValue(String.class);
                    String mobile = postSnapshot.child("mobile").getValue(String.class);
                    String name = postSnapshot.child("name").getValue(String.class);
                    String base64 = postSnapshot.child("base64").getValue(String.class);
                    byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    userMobile.setText(mobile);
                    userName.setText(name);
                    commonClass.imageLoad(userImage,decodedByte);

                }
            }
        }
        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.QRCodeBlackColor):getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_8888);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    private void setFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        // create a FragmentTransaction to begin the transaction and replace the Fragment
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.framLayout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit(); // save the changes
    }

}
