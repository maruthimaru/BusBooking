package com.jp.busbooking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jp.busbooking.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    RadioGroup radioGroup;
    RadioButton admin,staff,student;
    TextView login;
    private String type="admin";
    EditText userId,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userId=findViewById(R.id.userId);
        password=findViewById(R.id.password);
        radioGroup=findViewById(R.id.radioGroupChoose);
        admin=findViewById(R.id.admin);
        staff=findViewById(R.id.staff);
        student=findViewById(R.id.student);
        login=findViewById(R.id.login);
        admin.setChecked(true);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.admin:
                        type="admin";
                        break;
                    case R.id.staff:
                        type="staff";
                        break;
                    case R.id.student:
                        type="student";
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
        switch (view.getId()){
            case R.id.login:
                validation();
                break;
        }
    }

    private void validation() {

        String userID=userId.getText().toString();
        String pass=password.getText().toString();
        if (userID.length()>0){
            userId.setError(null);
            if (pass.length()>0){
                password.setError(null);
//                switch (type) {
//                    case "admin":
                        if (userID.equals("admin") && pass.equals("admin")) {
                            startActivity(new Intent(LoginActivity.this,CheckNumberActivity.class));
                            Toast.makeText(getApplicationContext(), "Admin Login", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "Invaild Login", Toast.LENGTH_SHORT).show();
                        }
//                        break;
//                    case "staff":
                     /*   if (dbHelper.checkData(DBHelper.dbStaff, DBHelper.staffId, userID, DBHelper.user_Password, pass)) {
                            ArrayList<StaffModel> staffModelArrayList = dbHelper.getStaff(DBHelper.staffId, userID, DBHelper.user_Password, pass);
                            dbHelper.insertLogin(staffModelArrayList.get(0).getStaffId(), staffModelArrayList.get(0).getStaffId(), staffModelArrayList.get(0).getStaffName(), "",
                                    staffModelArrayList.get(0).getStaffPassword(), "staff");
                            commonMethod.intentFinish(LoginActivity.this, StaffHomeActivity.class);
                            Toast.makeText(getApplicationContext(), "Staff Login", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "invalid Staff Login", Toast.LENGTH_SHORT).show();
                        }*/

//                        break;
//                    case "student":
                      /*  if (dbHelper.checkData(DBHelper.dbStudent, DBHelper.userId, userID, DBHelper.user_Password, pass)) {
                            ArrayList<StudentModel> studentModelArrayList = dbHelper.getStudent(DBHelper.userId, userID, DBHelper.user_Password, pass);
                            dbHelper.insertLogin(studentModelArrayList.get(0).getUserId(), studentModelArrayList.get(0).getStaffId(),
                                    studentModelArrayList.get(0).getStudentName(), studentModelArrayList.get(0).getEmail(),
                                    studentModelArrayList.get(0).getStudentPassword(), "student");
                            commonMethod.intentFinish(LoginActivity.this, StudentHomeActivity.class);
                            Toast.makeText(getApplicationContext(), "Student Login", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "invalid Student Login", Toast.LENGTH_SHORT).show();
                        }
//*/
//                        break;
//                }
            }else {
                password.requestFocus();
                password.setError("required password");
            }
        }else {
            userId.requestFocus();
            userId.setError("required userId");
        }

    }
}
