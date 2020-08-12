package com.example.doctorappointmentsystem;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Patient_Registration_Activity extends AppCompatActivity {
    private EditText patient_register_username, patient_register_pass;
    private TextView goto_patient_login;
    private Button register_patient;
    private DatabaseReference PatientPassword_REF;
    private ProgressDialog loading_bar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient__registration_);
        patient_register_username = findViewById(R.id.patient_registration_username);
        patient_register_pass = findViewById(R.id.patient_registration_pass);
        goto_patient_login = findViewById(R.id.goto_patient_login);
        register_patient = findViewById(R.id.register_patient);
        loading_bar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        PatientPassword_REF = FirebaseDatabase.getInstance().getReference().child("PatientPassword");

        goto_patient_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Patient_Login_Activity.class);
                startActivity(intent);
            }
        });

        register_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(patient_register_username.getText().toString()) &&
                        TextUtils.isEmpty(patient_register_pass.getText().toString())) {

                    Toast.makeText(getApplicationContext(), "please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {

                    registerPatient();
                }
            }
        });
    }

    private void registerPatient() {

        String Patientemail = "Patients_" + patient_register_username.getText().toString();
        final String patientpassword = patient_register_pass.getText().toString();
        if (TextUtils.isEmpty(Patientemail)) {
            Toast.makeText(getApplicationContext(), "please Enter Email", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(patientpassword)) {
            Toast.makeText(getApplicationContext(), "please Enter password", Toast.LENGTH_SHORT).show();
        } else {
            //firebase authentication
            String sec_q1 = "";
            String sec_q2 = "";
            String sec_q3 = "";
            final HashMap<String, String> userName_Password = new HashMap<>();
            userName_Password.put("username", patient_register_username.getText().toString());
            userName_Password.put("password", patientpassword);


            loading_bar.setTitle("Creating New Patient Account");
            loading_bar.setMessage("Please wait while Creating new account for you..");
            loading_bar.setCanceledOnTouchOutside(true);
            loading_bar.show();
            mAuth.createUserWithEmailAndPassword(Patientemail, patientpassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                if (task.isSuccessful()) {

                                    Intent intent = new Intent(getApplicationContext(), PatientSecurityQuestionActivity.class);
                                    intent.putExtra("Pusername", patient_register_username.getText().toString());
                                    intent.putExtra("password",patientpassword);
                                    startActivity(intent);

                                    Toast.makeText(getApplicationContext(), " Doctor account created succefull  ", Toast.LENGTH_SHORT).show();
                                    loading_bar.dismiss();

                                }


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
