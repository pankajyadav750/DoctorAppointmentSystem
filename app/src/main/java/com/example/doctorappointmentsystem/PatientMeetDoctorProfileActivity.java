package com.example.doctorappointmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;

public class PatientMeetDoctorProfileActivity extends AppCompatActivity {
    private DatabaseReference DoctorREF,PatientREF,ChatNotification_Ref,BookingRequestRef, DoctorConfirmLeave_Ref,BookingAppointmentTime_Ref,ChatingRequestRef,confirmBookingList_REF,confirmChatList_REF;
    private CircleImageView patient_meet_doctor_profile_image;
    private Toolbar toolbar;
    private String CurrentPatient;
    private Button BookDoctor,ChatDoctor;
    private FirebaseAuth mAuth;
    private TextView patient_meet_doctor_profile_name,patient_meet_doctor_profile_bio,
                      patient_meet_doctor_profile_specialist,patient_meet_doctor_profile_gender,patient_meet_doctor_profile_contact
                      ,patient_meet_doctor_profile_hospital, patient_meet_doctor_profile_experience, patient_meet_doctor_profile_education;

    private  String DoctorBookingState="new";
    private String DoctorId;
    private  String buttonName;
    private  String DoctorChatingState="new";
    private String date1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_meet_doctor_profile);

        DoctorREF= FirebaseDatabase.getInstance().getReference().child("Doctors");
        ChatNotification_Ref=FirebaseDatabase.getInstance().getReference().child("ChatNotifications");
        DoctorConfirmLeave_Ref=FirebaseDatabase.getInstance().getReference().child("DoctorConfirmLeaveRequest");
        PatientREF=FirebaseDatabase.getInstance().getReference().child("Patients");
        BookingAppointmentTime_Ref= FirebaseDatabase.getInstance().getReference().child("BookingAppointmentTime");
        BookingRequestRef=FirebaseDatabase.getInstance().getReference().child("BookingRequest");
        confirmBookingList_REF=FirebaseDatabase.getInstance().getReference().child("ConfirmBookingList");
        confirmChatList_REF=FirebaseDatabase.getInstance().getReference().child("ConfirmChatingList");
        mAuth=FirebaseAuth.getInstance();
        CurrentPatient=mAuth.getCurrentUser().getUid();
        toolbar=findViewById(R.id.patient_meet_doctor_profile_toolbar);
        patient_meet_doctor_profile_image=findViewById(R.id.patient_meet_doctor_profile_image);
        patient_meet_doctor_profile_name=findViewById(R.id.patient_meet_doctor_profile_name);
        patient_meet_doctor_profile_bio=findViewById(R.id.patient_meet_doctor_profile_bio);
        patient_meet_doctor_profile_specialist=findViewById(R.id.patient_meet_doctor_profile_specialist);
        patient_meet_doctor_profile_gender=findViewById(R.id.patient_meet_doctor_profile_gender);
        patient_meet_doctor_profile_contact=findViewById(R.id.patient_meet_doctor_profile_contact);
        patient_meet_doctor_profile_hospital=findViewById(R.id.patient_meet_doctor_profile_hospital);
        patient_meet_doctor_profile_experience=findViewById(R.id.patient_meet_doctor_profile_experience);
        patient_meet_doctor_profile_education=findViewById(R.id.patient_meet_doctor_profile_education);
        ChatingRequestRef=FirebaseDatabase.getInstance().getReference().child("ChatingRequest");
        BookDoctor=findViewById(R.id.patient_book_doctor);
        ChatDoctor=findViewById(R.id.patient_chat_doctor);
        BookingAppointmentTime_Ref = FirebaseDatabase.getInstance().getReference().child("BookingAppointmentTime");
        mAuth=FirebaseAuth.getInstance();
        CurrentPatient=mAuth.getCurrentUser().getUid();

        toolbar.setTitle("BookDoctor");
        DoctorId=getIntent().getExtras().get("profile_doctor_id").toString();


          buttonName=BookDoctor.getText().toString();

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int hour = calendar.get(Calendar.HOUR);
        final int minut = calendar.get(Calendar.MINUTE);
        final int second = calendar.get(Calendar.SECOND);
        final int ampm = calendar.get(Calendar.AM_PM);

        int month1=month;
        month1++;

        date1=day+" / "+month1+" / "+year;

        final int finalMonth = month1;
        DoctorConfirmLeave_Ref.child(DoctorId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists()) {

                    String startDate = dataSnapshot.child("From").getValue().toString();
                    String endDate = dataSnapshot.child("To").getValue().toString();
                    String endDay = dataSnapshot.child("endDay").getValue().toString();
                    String endMonth = dataSnapshot.child("endMonth").getValue().toString();
                    String startDay = dataSnapshot.child("startDay").getValue().toString();
                    String startMonth = dataSnapshot.child("startMonth").getValue().toString();
                    int Lday = Integer.parseInt(endDay);
                    int Lmonth = Integer.parseInt(endMonth);
                    int Sday=Integer.parseInt(startDay);
                    int Smonth=Integer.parseInt(startMonth);

                    if(Smonth==Lmonth)
                    {

                        if(day>=Sday && Lday>=day)
                        {
                            BookDoctor.setEnabled(false);
                            ChatDoctor.setEnabled(false);
                             Toast.makeText(getApplicationContext(),"Doctor on leave till: "+endDate,Toast.LENGTH_LONG).show();
                        }
                        if(day>Lday)
                        {
                            DoctorConfirmLeave_Ref.child(DoctorId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if(task.isSuccessful())
                                    {

                                    }

                                }
                            });
                        }
                    }
                    else {
                        if(Lmonth>Smonth)
                        {
                            if(finalMonth==Lmonth)
                            {

                                if (Sday>day && Lday>=day)
                                {
                                    BookDoctor.setEnabled(false);
                                    Toast.makeText(getApplicationContext(),"Doctor on leave till: "+endDate,Toast.LENGTH_LONG).show();
                                    ChatDoctor.setEnabled(false);
                                }

                                if(day>Lday)
                                {
                                    DoctorConfirmLeave_Ref.child(DoctorId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {

                                            }

                                        }
                                    });
                                }
                            }

                            else
                            {
                                if(day>=Sday)
                                {
                                    BookDoctor.setEnabled(false);
                                    Toast.makeText(getApplicationContext(),"Doctor on leave till: "+endDate,Toast.LENGTH_LONG).show();
                                }
                            }

                        }
                    }






                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








        DoctorREF.child(DoctorId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String doctor_name=dataSnapshot.child("name").getValue().toString();

                    String doctor_specialization=dataSnapshot.child("specilist_in").getValue().toString();
                    String doctor_hospital=dataSnapshot.child("hospital").getValue().toString();
                    String doctor_image=dataSnapshot.child("image").getValue().toString();

                    String doctor_gender=dataSnapshot.child("gender").getValue().toString();
                    String doctor_education=dataSnapshot.child("education").getValue().toString();
                    String doctor_contact=dataSnapshot.child("contact").getValue().toString();
                    //String doctor_address=dataSnapshot.child("address").getValue().toString();
                    String doctor_experience=dataSnapshot.child("experence").getValue().toString();
                    String doctor_bio=dataSnapshot.child("Bio").getValue().toString();

                    Picasso.get().load(doctor_image).into(patient_meet_doctor_profile_image);
                    patient_meet_doctor_profile_name.setText("Name: "+doctor_name);
                    patient_meet_doctor_profile_bio.setText("About: "+doctor_bio);
                    patient_meet_doctor_profile_specialist.setText("Specialist: "+doctor_specialization);
                    patient_meet_doctor_profile_gender.setText("Gender: "+doctor_gender);
                    patient_meet_doctor_profile_experience.setText("Experience: "+doctor_experience);
                    patient_meet_doctor_profile_education.setText("Qualification: "+doctor_education);
                    patient_meet_doctor_profile_contact.setText("Contact: "+doctor_contact);
                    patient_meet_doctor_profile_hospital.setText("Hospital: "+doctor_hospital);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        BookingRequestRef.child(CurrentPatient).child(DoctorId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    final String request_type = dataSnapshot.child("request_type").getValue().toString();
                    if (request_type.equals("sent"))
                    {
                        DoctorBookingState = "booking_request_sent";
                        BookDoctor.setText("Cancel booking");
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        confirmBookingList_REF.child(CurrentPatient).child(DoctorId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("authentication_key"))
                {


                        DoctorBookingState = "booking_confirm";
                        BookDoctor.setText("Cancel Appointment");


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ChatingRequestRef.child(CurrentPatient).child(DoctorId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    final String chatrequst_type=dataSnapshot.child("request_type").getValue().toString();

                    if (chatrequst_type.equals("patient_sent"))
                    {
                        DoctorChatingState="chating_request_sent";
                        ChatDoctor.setText("Cancel Chat");

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        confirmChatList_REF.child(CurrentPatient).child(DoctorId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    final String chatrequst_type=dataSnapshot.child("status").getValue().toString();

                    if (chatrequst_type.equals("patient_friends"))
                    {
                        DoctorChatingState="consultation";
                        ChatDoctor.setText("Remove chatList");

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        BookDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                BookDoctor.setEnabled(false);

                if(DoctorBookingState.equals("new"))
                {
                    BookDoctor.setEnabled(true);
                    Toast.makeText(getApplicationContext(),"Please Scroll on Text for more",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), PatientBookingDoctorActivity.class);
                intent.putExtra("profile_doctor_id", DoctorId);
                startActivity(intent);
                }


                if(DoctorBookingState.equals("booking_request_sent"))
                {
                    patientCancelBookingRequest();


                }

                if(DoctorBookingState.equals("booking_confirm"))
                {
                    cancleAppointment();
                }

            }
        });

        ChatDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ChatDoctor.setEnabled(false);
                if(DoctorChatingState.equals("new"))
                {
                    sendingChatRequest();
                }

                if(DoctorChatingState.equals("chating_request_sent"))
                {
                    cancelChatRequest();

                }
                if(DoctorChatingState.equals("consultation"))
                {
                    removeFromChatList();
                }

            }
        });


    }

    private void cancleAppointment()
    {

        confirmBookingList_REF.child(CurrentPatient).child(DoctorId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {

                    confirmBookingList_REF.child(DoctorId).child(CurrentPatient).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                BookingAppointmentTime_Ref.child(DoctorId).child(CurrentPatient).child("shedule").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        if(task.isSuccessful())
                                        {

                                            BookDoctor.setEnabled(true);
                                            DoctorBookingState="new";
                                            Toast.makeText(getApplicationContext(),"cancel appointment",Toast.LENGTH_LONG).show();
                                            BookDoctor.setText("Book Doctor");

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

    private void removeFromChatList()
    {
        confirmChatList_REF.child(CurrentPatient).child(DoctorId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {

                    confirmChatList_REF.child(DoctorId).child(CurrentPatient).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                ChatDoctor.setEnabled(true);
                                DoctorChatingState="new";
                                Toast.makeText(getApplicationContext(),"removed from chatlist",Toast.LENGTH_LONG).show();
                                ChatDoctor.setText("Chat Doctor");

                            }

                        }
                    });

                }

            }
        });

    }


    private void patientCancelBookingRequest()
    {
        BookingRequestRef.child(CurrentPatient).child(DoctorId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    BookingRequestRef.child(DoctorId).child(CurrentPatient).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                        if (task.isSuccessful())
                                        {
                                            BookDoctor.setEnabled(true);
                                            BookDoctor.setText("BOOK DOCTOR");
                                            DoctorBookingState="new";
                                        }
                            }


                        }
                    });
                }
            }
        });


    }


    private void cancelChatRequest()
    {

        ChatingRequestRef.child(DoctorId).child(CurrentPatient).child("request_type").removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {
                            ChatingRequestRef.child(CurrentPatient).child(DoctorId).child("request_type").removeValue()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {
                                                ChatDoctor.setEnabled(true);
                                                DoctorChatingState="new";
                                                Toast.makeText(getApplicationContext(),"chating Request removed",Toast.LENGTH_LONG).show();
                                                ChatDoctor.setText("ChatDoctor");

                                            }

                                        }
                                    });

                        }

                    }
                });
    }

    private void sendingChatRequest()
    {
        ChatingRequestRef.child(DoctorId).child(CurrentPatient).child("request_type").setValue("doctor_recieved").
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {

                        if(task.isSuccessful())
                        {
                            ChatingRequestRef.child(CurrentPatient).child(DoctorId).child("request_type").setValue("patient_sent").
                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {

                                            if(task.isSuccessful())
                                            {

                                                HashMap<String,String> chatNotificationMap=new HashMap<>();

                                                chatNotificationMap.put("from",CurrentPatient);
                                                chatNotificationMap.put("type","request");

                                                ChatNotification_Ref.child(DoctorId).push().setValue(chatNotificationMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task)
                                                    {
                                                        if(task.isSuccessful())
                                                        {

                                                            Toast.makeText(getApplicationContext(),"chating Request send to Doctor",Toast.LENGTH_LONG).show();
                                                            ChatDoctor.setEnabled(true);
                                                            ChatDoctor.setText("Cancel Chat");
                                                            DoctorChatingState="chating_request_sent";

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
