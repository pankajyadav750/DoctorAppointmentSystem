package com.example.doctorappointmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AttemptSecurtiyQuestionsActivity extends AppCompatActivity {

    private EditText question1,question2,question3;
    private Button verifyAnswer;
    private DatabaseReference getPassword_REF;
    private String patient_email,answer1,answer2,answer3,username,password,q1,q2,q3,resultStr="";
    private ProgressDialog progressDialog;
    private TextView u_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attempt_securtiy_questions);
        question1=findViewById(R.id.set_patient_q1);
        question2=findViewById(R.id.set_patient_q2);
        question3=findViewById(R.id.set_patient_q3);
        verifyAnswer=findViewById(R.id.save_patient_answer_btn);
        getPassword_REF= FirebaseDatabase.getInstance().getReference().child("PatientPassword");
        progressDialog=new ProgressDialog(this);
        u_password=findViewById(R.id.Upassord);

        patient_email = getIntent().getExtras().get("email").toString();

        String str= patient_email.toString();


        for (int i=0;i<str.length();i++)
        {

            if (str.charAt(i)>64 && str.charAt(i)<=122)
            {

                resultStr=resultStr+str.charAt(i);
            }
        }




        verifyAnswer.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {



               if(TextUtils.isEmpty(question1.getText().toString()))
               {
                   Toast.makeText(getApplicationContext(),"you not suppose to leave Empty of any field..",Toast.LENGTH_LONG).show();

               }
               else if(TextUtils.isEmpty(question2.getText().toString()))
               {
                   Toast.makeText(getApplicationContext(),"you not suppose to leave Empty of any field..",Toast.LENGTH_LONG).show();

               }
               else if(TextUtils.isEmpty(question3.getText().toString()))
               {
                   Toast.makeText(getApplicationContext(),"you not suppose to leave Empty of any field..",Toast.LENGTH_LONG).show();

               }

               else
                   {


                   progressDialog.setTitle("Validating your Answer...");
                   progressDialog.setMessage("please wait...");
                   progressDialog.setCanceledOnTouchOutside(false);
                   progressDialog.show();

                   getPassword_REF.child(resultStr).addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           if (dataSnapshot.exists()) {

                               answer1 = dataSnapshot.child("security_question1").getValue().toString();
                               answer2 = dataSnapshot.child("security_question2").getValue().toString();
                               answer3 = dataSnapshot.child("security_question3").getValue().toString();
                               username = dataSnapshot.child("username").getValue().toString();
                               password = dataSnapshot.child("password").getValue().toString();


                               // Toast.makeText(getApplicationContext(),"your password:"+answer1+"\n"+answer2+"\n"+answer3,Toast.LENGTH_LONG).show();
                               if (question1.getText().toString().equals(answer1))
                               {
                                   if(question2.getText().toString().equals(answer2))
                                   {
                                       if(question3.getText().toString().equals(answer3))
                                       {
                                           progressDialog.dismiss();
                                           u_password.setVisibility(View.VISIBLE);
                                          u_password.setText("your password: "+password);

                                       }
                                       else
                                       {
                                           progressDialog.dismiss();

                                           Toast.makeText(getApplicationContext(), ""+question3.getText().toString()+" is not right answer" , Toast.LENGTH_LONG).show();

                                       }
                                   }
                                   else
                                   {
                                       progressDialog.dismiss();

                                       Toast.makeText(getApplicationContext(), ""+question2.getText().toString()+" is not right answer" , Toast.LENGTH_LONG).show();


                                   }


                               }

                               else {
                                   progressDialog.dismiss();

                                   Toast.makeText(getApplicationContext(), ""+question1.getText().toString()+" is not right answer" , Toast.LENGTH_LONG).show();


                               }

                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });

               }
           }
       });


    }
}
