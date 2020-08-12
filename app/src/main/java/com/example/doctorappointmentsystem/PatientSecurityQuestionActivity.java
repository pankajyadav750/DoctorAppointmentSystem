package com.example.doctorappointmentsystem;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class PatientSecurityQuestionActivity extends AppCompatActivity {
    private EditText Sec_question1, Sec_question2, Sec_question3;
    private Button save_details;
    private ProgressDialog   progressDialog;
    private DatabaseReference PatientPassword_REF;
    private String patient_username,patient_password,resultStr="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_security_question);
        progressDialog=new ProgressDialog(this);

        Sec_question1 = findViewById(R.id.set_patient_question1_edit);
        Sec_question2 = findViewById(R.id.set_patient_question2_edit);
        Sec_question3 = findViewById(R.id.set_patient_question3_edit);
        save_details = findViewById(R.id.save_patient_answer_button);


        PatientPassword_REF= FirebaseDatabase.getInstance().getReference().child("PatientPassword");

        patient_username =getIntent().getExtras().get("Pusername").toString();
        patient_password =getIntent().getExtras().get("password").toString();


        String str= patient_username.toString();


        for (int i=0;i<str.length();i++)
        {

            if (str.charAt(i)>64 && str.charAt(i)<=122)
            {

                resultStr=resultStr+str.charAt(i);
            }
        }


        save_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                savePatientQuestion();
            }
        });
    }

    private void savePatientQuestion() {
        String sec_q1 = Sec_question1.getText().toString();
        String sec_q2 = Sec_question2.getText().toString();
        String sec_q3 = Sec_question3.getText().toString();

        if (TextUtils.isEmpty(sec_q1)) {
            Toast.makeText(getApplicationContext(), "please answer  1st Question and move forword", Toast.LENGTH_LONG).show();

        } else if (TextUtils.isEmpty(sec_q2)) {
            Toast.makeText(getApplicationContext(), "please answer  1st Question and move forword", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(sec_q3)) {
            Toast.makeText(getApplicationContext(), "please answer  1st Question and move forword", Toast.LENGTH_LONG).show();
        }

        else
        {

            final HashMap<String,String> seQustion_map=new HashMap<>();
            seQustion_map.put("security_question1",sec_q1);
            seQustion_map.put("security_question2",sec_q2);
            seQustion_map.put("security_question3",sec_q3);
            seQustion_map.put("username",patient_username);
            seQustion_map.put("password",patient_password);



            progressDialog.setTitle("Setting your profile");
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();


            PatientPassword_REF.child(resultStr).setValue(seQustion_map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        Intent intent =new Intent(getApplicationContext(),Set_Pateint_profile_Activity.class);
                        Toast.makeText(getApplicationContext(),"set your profile first",Toast.LENGTH_LONG).show();
                        startActivity(intent);

                    }

                }
            });
        }
    }
}
