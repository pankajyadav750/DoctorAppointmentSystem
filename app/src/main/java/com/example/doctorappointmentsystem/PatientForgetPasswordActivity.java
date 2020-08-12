package com.example.doctorappointmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PatientForgetPasswordActivity extends AppCompatActivity {
    private EditText gmail;
    private Button verifygmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_forget_password);
        gmail=findViewById(R.id.patient_forget_email_edit);
        verifygmail=findViewById(R.id.verify_email_id);

        verifygmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=gmail.getText().toString();
                Intent intent=new Intent(getApplicationContext(),AttemptSecurtiyQuestionsActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });
    }
}
