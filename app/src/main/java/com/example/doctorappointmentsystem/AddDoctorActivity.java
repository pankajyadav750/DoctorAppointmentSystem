package com.example.doctorappointmentsystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddDoctorActivity extends AppCompatActivity {

    private Button Add_doctor;
    private TextView show_doctor_id_password;
    private EditText Add_doctor_username, Add_doctor_password, Add_doctor_name;
    private FirebaseAuth mAuth;
    private DatabaseReference doctorRef;
    private String currentUser;
    private ProgressDialog loading_bar;

    DBHandler dbHandler;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);
        dbHandler = new DBHandler(this);


        loading_bar = new ProgressDialog(this);

        Add_doctor = findViewById(R.id.addDoctor_login);
        mAuth = FirebaseAuth.getInstance();
        doctorRef = FirebaseDatabase.getInstance().getReference().child("Doctor");
        Add_doctor_name = findViewById(R.id.Adddoctor_login_Name_edit);
        show_doctor_id_password = findViewById(R.id.goto_doctor_userName_password_display);
        Add_doctor_password = findViewById(R.id.Adddoctor_login_pass_edit);
        Add_doctor_username = findViewById(R.id.Adddoctor_login_edit);


        Add_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(Add_doctor_username.getText().toString()) && TextUtils.isEmpty(Add_doctor_password.getText().toString()) &&
                        TextUtils.isEmpty(Add_doctor_name.getText().toString())) {

                    Toast.makeText(getApplicationContext(), "please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {

                    createDoctorAccountOnFirebase();

                }

            }
        });
        show_doctor_id_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), displayDoctorUsernamePasswordActivity.class);
                startActivity(intent);
            }
        });

    }


    private void createDoctorAccountOnSqliteDatabase() {


        try {

            database = dbHandler.getWritableDatabase();


            ContentValues values = new ContentValues();

            values.put(DBHandler.DOCTOR_USER_NAME, Add_doctor_username.getText().toString());
            values.put(DBHandler.DOCTOR_PASSWORD, Add_doctor_password.getText().toString());
            values.put(DBHandler.DOCTOR_NAME, Add_doctor_name.getText().toString());


            database.insert(DBHandler.DOCTOR_TABLE_NAME, null, values);

            Toast.makeText(getApplicationContext(), "record saved successfully", Toast.LENGTH_SHORT).show();

            database.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "record not saved due to= " + e, Toast.LENGTH_SHORT).show();
        }


    }


    private void createDoctorAccountOnFirebase() {
       // "Doctors_" +

        String Doctoremail = Add_doctor_username.getText().toString();
        String Docrorpassword = Add_doctor_password.getText().toString();
        if (TextUtils.isEmpty(Doctoremail)) {
            Toast.makeText(getApplicationContext(), "please Enter Email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(Docrorpassword)) {
            Toast.makeText(getApplicationContext(), "please Enter password", Toast.LENGTH_SHORT).show();
        } else {
            //firebase authentication
            loading_bar.setTitle("Creating New Doctor Account");
            loading_bar.setMessage("Please wait while Creating new account for you..");
            loading_bar.setCanceledOnTouchOutside(true);
            loading_bar.show();
            mAuth.createUserWithEmailAndPassword(Doctoremail, Docrorpassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                createDoctorAccountOnSqliteDatabase();

                                Toast.makeText(getApplicationContext(), " Doctor account created succefull  " , Toast.LENGTH_SHORT).show();
                                loading_bar.dismiss();
                            } else {

                                String message = task.getException().toString();
                                Toast.makeText(getApplicationContext(), "resion=" + message, Toast.LENGTH_LONG).show();
                                loading_bar.dismiss();
                            }

                        }
                    });
        }
    }

}
