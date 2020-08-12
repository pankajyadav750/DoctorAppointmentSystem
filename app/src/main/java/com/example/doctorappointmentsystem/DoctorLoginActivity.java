package com.example.doctorappointmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Constants;

public class DoctorLoginActivity extends AppCompatActivity {

    private EditText doctor_userName,doctor_password;
    private Button doctor_login;
    private TextView goto_forget_password;
    private ProgressDialog progressDialog;
    private DatabaseReference DoctorRef;
    private FirebaseAuth mAuth;
    private String CurrentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);
        progressDialog=new ProgressDialog(this);
        DoctorRef=FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
         mAuth.signOut();

        doctor_userName=findViewById(R.id.doctor_login_username_edit);
        doctor_password=findViewById(R.id.doctor_login_pass_edit);
        doctor_login=findViewById(R.id.logIn_doctor);
        goto_forget_password=findViewById(R.id.goto_forget_password);

        doctor_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                loginDoctorWithFirebaseAuthentication();

            }
        });

        goto_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             Intent intent=new Intent(getApplicationContext(),DoctorResetPassword.class);
             startActivity(intent);
            }
        });
    }

    private void loginDoctorWithFirebaseAuthentication()
    {
        //"Doctors_" +
        String Doctor_username=doctor_userName.getText().toString();
        String Doctor_password=doctor_password.getText().toString();

          if(TextUtils.isEmpty(Doctor_username) && TextUtils.isEmpty(Doctor_password))
          {
              Toast.makeText(getApplicationContext(),"please do not leave any field",Toast.LENGTH_SHORT).show();
          }
          else
          {

              progressDialog.setTitle("Login Doctor");
              progressDialog.setMessage("Please wait...");
              progressDialog.setCanceledOnTouchOutside(false);
              progressDialog.show();

              mAuth.signInWithEmailAndPassword(Doctor_username,Doctor_password)
                      .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                          @Override
                          public void onComplete(@NonNull Task<AuthResult> task)
                          {
                              if(task.isSuccessful())
                              {

                                  CurrentUserId=mAuth.getCurrentUser().getUid();


                                  //String currentUserId=mAuth.getCurrentUser().getUid();

                                  progressDialog.dismiss();

                                  Intent intent=new Intent(getApplicationContext(),Doctor_ProfileActivity.class);
                                  startActivity(intent);

                              }
                              else
                              {
                                  progressDialog.dismiss();
                                  String message=task.getException().toString();
                                  Toast.makeText(getApplicationContext(),"error="+message,Toast.LENGTH_SHORT).show();

                              }

                          }
                      });

          }

    }


}
