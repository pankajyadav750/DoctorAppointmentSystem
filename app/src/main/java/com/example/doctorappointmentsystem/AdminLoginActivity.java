package com.example.doctorappointmentsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AdminLoginActivity extends AppCompatActivity {
    DBHandler dbHandler;
    SQLiteDatabase database;
    private Button Login_buton;
    private TextView goto_register;
    private EditText login_admin_username, login_admin_password;
    private DatabaseReference AdminUsrName;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        dbHandler = new DBHandler(this);

        progressDialog=new ProgressDialog(this);
        Login_buton = findViewById(R.id.logIn_admin);
        goto_register = findViewById(R.id.goto_Admin_register);
        login_admin_username = findViewById(R.id.admin_login_edit);
        login_admin_password = findViewById(R.id.admin_login_pass_edit);
        AdminUsrName= FirebaseDatabase.getInstance().getReference().child("AdminUserNamePassword");

        goto_register.setVisibility(View.INVISIBLE);

        goto_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

         /*       HashMap<String,String> adminmap=new HashMap();

                adminmap.put("userName","yadavpkmax@gmail.com");
                adminmap.put("password","pankaj123");

                AdminUsrName.setValue(adminmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"successfull",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

          */
                Intent intent = new Intent(getApplicationContext(), RegisterAdminActivity.class);
                startActivity(intent);
            }
        });

        Login_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                progressDialog.setTitle("Please Wait...");
                progressDialog.setMessage("Admin Authenticating...");
                progressDialog.show();


                AdminUsrName.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists())
                        {
                            progressDialog.dismiss();
                            String userName=dataSnapshot.child("userName").getValue().toString();
                            String  Password=dataSnapshot.child("password").getValue().toString();

                            String Auser = login_admin_username.getText().toString();
                            String Apassword = login_admin_password.getText().toString();


                            if(Auser.equals(userName) && Apassword.equals(Password))
                            {
                                Intent intent = new Intent(getApplicationContext(), AdminDashboardActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "login Successfull", Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "username or password incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });


    }
}
