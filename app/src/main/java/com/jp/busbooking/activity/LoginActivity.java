package com.jp.busbooking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jp.busbooking.R;

public class LoginActivity extends Fragment implements View.OnClickListener {

    RadioGroup radioGroup;
    RadioButton admin, staff, student;
    TextView login;
    private String type = "admin";
    EditText userId, password;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.activity_login,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userId = view.findViewById(R.id.userId);
        password = view.findViewById(R.id.password);
        radioGroup = view.findViewById(R.id.radioGroupChoose);
        admin = view.findViewById(R.id.admin);
        staff = view.findViewById(R.id.staff);
        student = view.findViewById(R.id.student);
        login = view.findViewById(R.id.login);
        admin.setChecked(true);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.admin:
                        type = "admin";
                        break;
                    case R.id.staff:
                        type = "staff";
                        break;
                    case R.id.student:
                        type = "student";
                        break;
                }
            }
        });
       /* studentModelArrayList=dbHelper.getStudent();
        if (studentModelArrayList.size()>0){
            for (StudentModel studentModel:studentModelArrayList){
                if (!dbHelper.checkData(DBHelper.dbStudentDaily,DBHelper.userId,studentModel.getUserId(),DBHelper.date,commonMethod.date())){
                    dbHelper.insertstudentDaily(studentModel.getUserId(),studentModel.getStaffId(),studentModel.getStudentName(),studentModel.getEmail(),
                            studentModel.getStudentPassword(),studentModel.getImage(),studentModel.getChooseClass(),studentModel.getAttendance(),
                            commonMethod.date());
                }
            }
        }*/
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                validation();
                break;
        }
    }

    private void validation() {

        String userID = userId.getText().toString();
        String pass = password.getText().toString();
        if (userID.length() > 0) {
            userId.setError(null);
            if (pass.length() > 0) {
                password.setError(null);
//                switch (type) {
//                    case "admin":
                if (userID.equals("admin") && pass.equals("admin")) {

                    setFragment(new CheckNumberActivity());
                    Toast.makeText(getActivity(), "Admin Login", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Invaild Login", Toast.LENGTH_SHORT).show();
                }
            } else {
                password.requestFocus();
                password.setError("required password");
            }
        } else {
            userId.requestFocus();
            userId.setError("required userId");
        }

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
