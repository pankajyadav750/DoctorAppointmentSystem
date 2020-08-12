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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Patient_Login_Activity extends AppCompatActivity {

    private EditText patient_login_username,Patient_login_password;
    private TextView goto_forget_password_pateint,goto_patient_registration;
    private Button Login_patient;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__login_);
        patient_login_username=findViewById(R.id.patient_login_username_edit);
        Patient_login_password=findViewById(R.id.patient_login_pass_edit);
        goto_forget_password_pateint=findViewById(R.id.goto_patient_forget_password);
        goto_patient_registration=findViewById(R.id.goto_patient_registration);
        Login_patient=findViewById(R.id.logIn_patient);
        progressDialog =new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        Login_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
              login_patientWithFirebaseAuthentication();
            }
        });

        goto_patient_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Patient_Registration_Activity.class);
                startActivity(intent);
            }
        });

        goto_forget_password_pateint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(getApplicationContext(),PatientForgetPasswordActivity.class);
                startActivity(intent);

            }
        });
    }

    private void login_patientWithFirebaseAuthentication()
    {

            final String Patient_username="Patients_" +patient_login_username.getText().toString();
            String Patient_password=Patient_login_password.getText().toString();

            if(TextUtils.isEmpty(Patient_username) && TextUtils.isEmpty(Patient_password))
            {
                Toast.makeText(getApplicationContext(),"please do not leave any field",Toast.LENGTH_SHORT).show();
            }
            else
            {

                progressDialog.setTitle("Login Patient");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                mAuth.signInWithEmailAndPassword(Patient_username,Patient_password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if(task.isSuccessful())
                                {

                                    //CurrentUserId=mAuth.getCurrentUser().getUid();
                                    //String currentUserId=mAuth.getCurrentUser().getUid();
                                    progressDialog.dismiss();



                                    Intent loginType_intent = new Intent(getApplicationContext(), MainActivity.class);
                                    loginType_intent.putExtra("Pusername",Patient_username);
                                    loginType_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(loginType_intent);
                                    finish();
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
