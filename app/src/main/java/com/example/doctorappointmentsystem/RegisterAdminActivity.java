package com.example.doctorappointmentsystem;

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

import androidx.appcompat.app.AppCompatActivity;

public class RegisterAdminActivity extends AppCompatActivity {

    private Button registerAdmin;
    private EditText admin_username_regist, admin_password_regist;
    private TextView goto_Admin_login;


    DBHandler dbHandler;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);

        dbHandler = new DBHandler(this);

        registerAdmin = findViewById(R.id.Register_amin);
        goto_Admin_login=findViewById(R.id.goto_Admin_Login);
        admin_username_regist = findViewById(R.id.admin_register_edit);
        admin_password_regist = findViewById(R.id.admin_register_pass_edit);

        registerAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(admin_username_regist.getText().toString()) && TextUtils.isEmpty(admin_password_regist.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"please fill all the fields",Toast.LENGTH_SHORT).show();
                }
                else {

                    try {

                        database = dbHandler.getWritableDatabase();


                        ContentValues values = new ContentValues();

                        values.put(DBHandler.ADMIN_USER_ID, admin_username_regist.getText().toString());
                        values.put(DBHandler.ADMIN_PASSWORD, admin_password_regist.getText().toString());


                        database.insert(DBHandler.TABLE_NAME, null, values);

                        Toast.makeText(getApplicationContext(), "record saved successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), AdminLoginActivity.class);
                        startActivity(intent);


                        database.close();
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "record saved successfully", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


        goto_Admin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intet=new Intent(getApplicationContext(),AdminLoginActivity.class);
                        startActivity(intet);
            }
        });
    }


}
