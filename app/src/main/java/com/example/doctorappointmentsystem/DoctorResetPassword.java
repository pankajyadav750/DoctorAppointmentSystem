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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class DoctorResetPassword extends AppCompatActivity
{
   private Button reset_password;
    private EditText Email_rset;
    private FirebaseAuth mAuth;
    private ProgressDialog  progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_reset_password);
        reset_password=findViewById(R.id.reset_link_btn);
        Email_rset=findViewById(R.id.email_reset_password);
        mAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email=Email_rset.getText().toString();
                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(getApplicationContext(),"enter email first",Toast.LENGTH_LONG).show();
                }
                else
                {
                    progressDialog.setTitle("Reset your password");
                    progressDialog.setMessage("After completing this process check your Email...");
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.show();
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                progressDialog.dismiss();
                                Intent intent=new Intent(getApplicationContext(),DoctorLoginActivity.class);
                                Toast.makeText(getApplicationContext(),"check your personel email we have sent reset password link",Toast.LENGTH_LONG).show();
                                startActivity(intent);
                            }


                        }
                    });
                }
            }
        });
    }
}
