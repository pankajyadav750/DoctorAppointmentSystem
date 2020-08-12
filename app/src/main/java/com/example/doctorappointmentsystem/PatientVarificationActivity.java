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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class PatientVarificationActivity extends AppCompatActivity {

    private Button verify_patient,confirm_booking;
    private EditText authenticatio_key_edit,suggest_medicine;
    private String patient_Id,authenticatio_key;
    private DatabaseReference Confirm_booking_REF, PaymentDone_REF,previous_booking_appointment,Doctor_REF,Patient_REF,BookingAppointmentTime_Ref;
    private FirebaseAuth mAuth;
    private String current_doctor,doctorName,patientName,doctorPhoto,patientPhoto;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_varification);
        progressDialog=new ProgressDialog(this);
        verify_patient=findViewById(R.id.verify_patient_id);
        authenticatio_key_edit=findViewById(R.id.authentication_key_edit);
        confirm_booking=findViewById(R.id.confirm_patient_booking);
        suggest_medicine=findViewById(R.id.suggest_medicine_key_edit);
        mAuth=FirebaseAuth.getInstance();
        current_doctor=mAuth.getCurrentUser().getUid();
        Confirm_booking_REF= FirebaseDatabase.getInstance().getReference().child("ConfirmBookingList");
        previous_booking_appointment= FirebaseDatabase.getInstance().getReference().child("PreviousBookingAppointmentList");
        BookingAppointmentTime_Ref= FirebaseDatabase.getInstance().getReference().child("BookingAppointmentTime");
        Doctor_REF=FirebaseDatabase.getInstance().getReference().child("Doctors");
        Patient_REF=FirebaseDatabase.getInstance().getReference().child("Patients");
        PaymentDone_REF=FirebaseDatabase.getInstance().getReference().child("ConfirmPayment");


        patient_Id = getIntent().getExtras().get("Patient_ID").toString();
        authenticatio_key = getIntent().getExtras().get("patient_auth").toString();

        Doctor_REF.child(current_doctor).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                doctorName=dataSnapshot.child("name").getValue().toString();
                doctorPhoto=dataSnapshot.child("image").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Patient_REF.child(patient_Id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                patientName=dataSnapshot.child("name").getValue().toString();
                patientPhoto=dataSnapshot.child("image").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        verify_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (authenticatio_key_edit.getText().toString().equals(authenticatio_key))
                {
                    confirm_booking.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(),"patient verified successfull",Toast.LENGTH_LONG).show();
                    suggest_medicine.setVisibility(View.VISIBLE);
                }
                else {
                    Toast.makeText(getApplicationContext(),"incorrect Authentcation key",Toast.LENGTH_LONG).show();


                }

            }
        });

        confirm_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                String medicine=suggest_medicine.getText().toString();

                if(TextUtils.isEmpty(medicine))
                {
                    Toast.makeText(getApplicationContext(),"please suggest some medicine to patient",Toast.LENGTH_LONG).show();
                }

                else
                {


                    final String date1;
                    String pTimee;

                    final Calendar calendar = Calendar.getInstance();
                    final int year = calendar.get(Calendar.YEAR);

                    final int month = calendar.get(Calendar.MONTH);
                    final int day = calendar.get(Calendar.DAY_OF_MONTH);
                    final int hour = calendar.get(Calendar.HOUR);
                    final int minut = calendar.get(Calendar.MINUTE);
                    final int second = calendar.get(Calendar.SECOND);

                    String ampm="AM";

                    SimpleDateFormat currentDate=new SimpleDateFormat("hh:mm a");

                    pTimee=currentDate.format(calendar.getTime());



                    if(hour==12 && minut>=1 && second>=1)
                    {
                        if (ampm.equals("AM"))
                        {
                            ampm="PM";
                        }
                        else
                        {
                            ampm="AM";
                        }
                    }



                    int month1=month;
                    month1++;

                    date1=day+" / "+month1+" / "+year;
                    final String date=hour+" - "+minut+" - "+second+" "+ampm+"-"+day+" - "+month1+" - "+year;
                    final String datek=day+" / "+month1+" / "+year;
                    final HashMap<String, String> ConfirmBookingDetails_patient=new HashMap<>();
                    ConfirmBookingDetails_patient.put("status","BookingDone");
                    ConfirmBookingDetails_patient.put("date",pTimee+" / "+date1);
                    ConfirmBookingDetails_patient.put("dateNTime",datek);
                    ConfirmBookingDetails_patient.put("name",patientName);
                    ConfirmBookingDetails_patient.put("photo",patientPhoto);
                    ConfirmBookingDetails_patient.put("medicine",suggest_medicine.getText().toString());

                    final HashMap<String, String> ConfirmBookingDetails_doctor=new HashMap<>();
                    ConfirmBookingDetails_doctor.put("status","BookingDone");
                    ConfirmBookingDetails_doctor.put("date",pTimee+" / "+date1);
                    ConfirmBookingDetails_doctor.put("name",doctorName);
                    ConfirmBookingDetails_doctor.put("photo",doctorPhoto);
                    ConfirmBookingDetails_doctor.put("dateNTime",datek);
                    ConfirmBookingDetails_doctor.put("medicine",suggest_medicine.getText().toString());

                    progressDialog.setTitle("Please Wait...");
                    progressDialog.setMessage("We are confirming your Appointment...");
                    progressDialog.show();


                    previous_booking_appointment.child(current_doctor).child(date).child(patient_Id).setValue(ConfirmBookingDetails_patient).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                previous_booking_appointment.child(patient_Id).child(date).child(current_doctor).setValue(ConfirmBookingDetails_doctor).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        if (task.isSuccessful())
                                        {
                                            Confirm_booking_REF.child(current_doctor).child(patient_Id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task)
                                                {
                                                    if (task.isSuccessful())
                                                    {
                                                        Confirm_booking_REF.child(patient_Id).child(current_doctor).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task)
                                                            {
                                                                if (task.isSuccessful())
                                                                {

                                                                    BookingAppointmentTime_Ref.child(current_doctor).child(patient_Id).child("shedule").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task)
                                                                        {
                                                                            if(task.isSuccessful())
                                                                            {

                                                                                PaymentDone_REF.child(current_doctor).child(patient_Id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task)
                                                                                    {
                                                                                      if(task.isSuccessful())
                                                                                      {
                                                                                          PaymentDone_REF.child(patient_Id).child(current_doctor).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                              @Override
                                                                                              public void onComplete(@NonNull Task<Void> task)
                                                                                              {
                                                                                                  if(task.isSuccessful())
                                                                                                  {
                                                                                                      progressDialog.dismiss();

                                                                                                      Toast.makeText(getApplicationContext(),"appointment done successfully",Toast.LENGTH_LONG).show();

                                                                                                      Intent booking_request_intent=new Intent(getApplicationContext(),Doctor_ProfileActivity.class);
                                                                                                      booking_request_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                                      startActivity(booking_request_intent);
                                                                                                      finish();
                                                                                                  }
                                                                                              }
                                                                                          });
                                                                                      }
                                                                                    }
                                                                                });



                                                                            }

                                                                        }
                                                                    });




                                                                }

                                                            }
                                                        });


                                                    }

                                                }
                                            });

                                        }

                                    }
                                });

                            }

                        }
                    });

                }

            }
        });



    }


}
