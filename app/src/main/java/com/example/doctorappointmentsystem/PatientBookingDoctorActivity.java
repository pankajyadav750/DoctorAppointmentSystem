package com.example.doctorappointmentsystem;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

public class PatientBookingDoctorActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    private Toolbar bookingToolbar;
    private Spinner Patient_booking_time;
    private TextView patient_booking_doctor_name, Patient_booking_speciality, Patient_booking_date, booking_charged;
    private DatePickerDialog.OnDateSetListener setListener;
    private FirebaseAuth mAuth;
    private DatabaseReference Doctor_Ref, BookingRequest_Ref, ConfirmLeaveRequest_Ref, BookingAppointmentTime_Ref, doctor_weekend_REf, DoctorpersonlyCharged_REF, DoctorCharge_REF;
    private CircleImageView docotr_profile;
    private String DoctorId;
    private String CurrentPatient, booking_chargedP = "";
    private ProgressDialog progressDialog;
    private long today;
    int flag = 0;
    private String weekDay;


    private Button booking_Reqquest;
    String[] selection = new String[30];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_booking_doctor);
        DoctorpersonlyCharged_REF = FirebaseDatabase.getInstance().getReference().child("PersonlyDoctorBookingChareges");
        ConfirmLeaveRequest_Ref = FirebaseDatabase.getInstance().getReference().child("DoctorConfirmLeaveRequest");
        DoctorCharge_REF = FirebaseDatabase.getInstance().getReference().child("BookingChareges");
        bookingToolbar = findViewById(R.id.patient_book_meet_doctor_profile_toolbar);
        booking_charged = findViewById(R.id.patient_book_meet_doctor_profile_booking_amoun);
        Patient_booking_speciality = findViewById(R.id.patient_book_meet_doctor_profile_speciality);
        Patient_booking_time = findViewById(R.id.patient_book_appointment_time);
        Patient_booking_date = findViewById(R.id.patient_book_appointment_date);
        patient_booking_doctor_name = findViewById(R.id.patient_bookin_meet_doctor_profile_name);
        Doctor_Ref = FirebaseDatabase.getInstance().getReference().child("Doctors");
        bookingToolbar.setTitle("Select Date & Time");
        docotr_profile = findViewById(R.id.patient_book_doctor_profile_image);
        booking_Reqquest = findViewById(R.id.booking_request_button);
        BookingRequest_Ref = FirebaseDatabase.getInstance().getReference().child("BookingRequest");
        doctor_weekend_REf = FirebaseDatabase.getInstance().getReference().child("DoctorWeekend");

        mAuth = FirebaseAuth.getInstance();
        CurrentPatient = mAuth.getCurrentUser().getUid();
        progressDialog = new ProgressDialog(this);


        BookingAppointmentTime_Ref = FirebaseDatabase.getInstance().getReference().child("BookingAppointmentTime");


        DoctorId = getIntent().getExtras().get("profile_doctor_id").toString();


        Doctor_Ref.child(DoctorId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String doctor_name = dataSnapshot.child("name").getValue().toString();

                    String doctor_specialization = dataSnapshot.child("specilist_in").getValue().toString();

                    String doctor_image = dataSnapshot.child("image").getValue().toString();


                    Picasso.get().load(doctor_image).into(docotr_profile);
                    patient_booking_doctor_name.setText("Name: " + doctor_name);

                    Patient_booking_speciality.setText("Specialist: " + doctor_specialization);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        DoctorpersonlyCharged_REF.child(DoctorId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    booking_chargedP = dataSnapshot.child("Booking_charges").getValue().toString();
                    booking_charged.setText("booking Charge: " + booking_chargedP);
                } else {
                    DoctorCharge_REF.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                booking_chargedP = dataSnapshot.child("Booking_charges").getValue().toString();
                                booking_charged.setText("booking Charge: " + booking_chargedP);

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Patient_booking_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);


                final int day2 = calendar.get(Calendar.DAY_OF_MONTH);
                final int month2 = calendar.get(Calendar.MONTH);
                final int year2 = calendar.get(Calendar.YEAR);


                final DatePickerDialog datePickerDialog = new DatePickerDialog(PatientBookingDoctorActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, final int day) {


                        Calendar c = Calendar.getInstance();

                        c.set(Calendar.MONTH, month);
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.DAY_OF_MONTH, day);

                        booking_Reqquest.setEnabled(true);


                        String date = DateFormat.getDateInstance().format(c.getTime());
                        month++;
                        int month12 = month2;
                        month12++;
                        String date1 = day + " / " + month + " / " + year;


                        final int finalMonth = month;
                        ConfirmLeaveRequest_Ref.child(DoctorId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {

                                    String startDate = dataSnapshot.child("From").getValue().toString();
                                    String endDate = dataSnapshot.child("To").getValue().toString();
                                    String endDay = dataSnapshot.child("endDay").getValue().toString();
                                    String endMonth = dataSnapshot.child("endMonth").getValue().toString();
                                    String startDay = dataSnapshot.child("startDay").getValue().toString();
                                    String startMonth = dataSnapshot.child("startMonth").getValue().toString();
                                    int Lday = Integer.parseInt(endDay);
                                    int Lmonth = Integer.parseInt(endMonth);
                                    int Sday = Integer.parseInt(startDay);
                                    int Smonth = Integer.parseInt(startMonth);

                                    if (Smonth == Lmonth) {

                                        if (day >= Sday && Lday >= day) {
                                            booking_Reqquest.setEnabled(false);
                                            Toast.makeText(getApplicationContext(), "Doctor on leave till: " + endDate, Toast.LENGTH_LONG).show();
                                        }

                                    } else {
                                        if (Lmonth > Smonth) {
                                            if (finalMonth == Lmonth) {

                                                if (Sday > day && Lday >= day) {
                                                    booking_Reqquest.setEnabled(false);
                                                    Toast.makeText(getApplicationContext(), "Doctor on leave till: " + endDate, Toast.LENGTH_LONG).show();
                                                    // ChatDoctor.setEnabled(false);
                                                }


                                            } else {
                                                if (day >= Sday) {
                                                    booking_Reqquest.setEnabled(false);
                                                    Toast.makeText(getApplicationContext(), "Doctor on leave till: " + endDate, Toast.LENGTH_LONG).show();
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


                        if (year == year2) {
                            if (month == month12) {

                                if (day == day2) {
                                    Toast.makeText(getApplicationContext(), "you can not book today." + "\n" + "try to book tommorrow..", Toast.LENGTH_LONG).show();
                                    booking_Reqquest.setEnabled(false);

                                } else {
                                    if (day > day2) {

                                        int advanced_day = day2 + 3;

                                        if (day > advanced_day) {
                                            Toast.makeText(getApplicationContext(), "you can book only Three days advanced", Toast.LENGTH_LONG).show();
                                            booking_Reqquest.setEnabled(false);

                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext(), "you can not book in past", Toast.LENGTH_LONG).show();
                                        booking_Reqquest.setEnabled(false);
                                    }


                                }


                            } else {
                                if (month > month12) {
                                    if (day2 == 30) {
                                        if (day == 1 || day == 2) {
                                            Toast.makeText(getApplicationContext(), "you can book", Toast.LENGTH_LONG).show();

                                        } else {
                                            Toast.makeText(getApplicationContext(), "you can not book in 1 month advanced", Toast.LENGTH_LONG).show();
                                            booking_Reqquest.setEnabled(false);
                                        }


                                    } else if (day2 == 31) {
                                        if (day == 1 || day == 2 || day == 3) {
                                            Toast.makeText(getApplicationContext(), "can book", Toast.LENGTH_LONG).show();

                                        } else {
                                            Toast.makeText(getApplicationContext(), "you can not book in 1 month advanced", Toast.LENGTH_LONG).show();
                                            booking_Reqquest.setEnabled(false);
                                        }

                                    } else {
                                        Toast.makeText(getApplicationContext(), "you can not book in 1 month advanced", Toast.LENGTH_LONG).show();
                                        booking_Reqquest.setEnabled(false);
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), "you can not book in past", Toast.LENGTH_LONG).show();
                                    booking_Reqquest.setEnabled(false);
                                }

                            }

                        } else {
                            if (year > year2) {
                                if (month12 == 12) {
                                    if (day2 == 31) {
                                        if (day == 1 || day == 2 || day == 3) {
                                            Toast.makeText(getApplicationContext(), "can book", Toast.LENGTH_LONG).show();

                                        } else {
                                            Toast.makeText(getApplicationContext(), "you can not book in 1 year advanced", Toast.LENGTH_LONG).show();
                                            booking_Reqquest.setEnabled(false);
                                        }
                                    }

                                } else {

                                    Toast.makeText(getApplicationContext(), "you can not book in 1 year advanced", Toast.LENGTH_LONG).show();
                                    booking_Reqquest.setEnabled(false);
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "you can not book in past", Toast.LENGTH_LONG).show();
                                booking_Reqquest.setEnabled(false);

                            }

                        }

                        final String weekDay;
                        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
                        //Calendar calendar = Calendar.getInstance();
                        weekDay = dayFormat.format(c.getTime());
                        Patient_booking_date.setText(date1);


                        doctor_weekend_REf.child(DoctorId).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String weekend_day = dataSnapshot.child("Weekend").getValue().toString();
                                    if (weekDay.equals(weekend_day)) {
                                        booking_Reqquest.setEnabled(false);
                                        Toast.makeText(getApplicationContext(), "doctor is proper leave on " + weekend_day, Toast.LENGTH_LONG).show();

                                    }
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        booking_Reqquest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patientBookingDoctor();
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Select_shedule, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Patient_booking_time.setAdapter(adapter);
        Patient_booking_time.setOnItemSelectedListener(this);


    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {



      /*  if(day.equals("Saturday"))
        {
            booking_Reqquest.setEnabled(false);
            Toast.makeText(getApplicationContext(),"doctor is proper leave on Saturday",Toast.LENGTH_LONG).show();
        }

       */


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


        int j = 0;


        selection[j] = adapterView.getItemAtPosition(i).toString();

        j++;

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private void patientBookingDoctor() {

        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("we are requesting for your appointment...");

        progressDialog.show();
        String date = Patient_booking_date.getText().toString();
        final String booking_shedule = selection[0] + " / " + date;


        //ConfirmLeaveRequest_Ref.child(DoctorId).


        if (date.equals("")) {
            Toast.makeText(getApplicationContext(), "please choose booking date", Toast.LENGTH_SHORT).show();
        } else {


            BookingAppointmentTime_Ref.child(DoctorId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String request_type1 = "sent";
                    String request_type2 = "recieve";
                    int charg = 250;
                    for (DataSnapshot shedulee : dataSnapshot.getChildren()) {
                        String shed = shedulee.child("shedule").getValue().toString();
                        if (shed.equals(booking_shedule)) {
                            flag = 1;
                            break;
                        }
                    }

                    if (flag == 0) {
                        final HashMap<String, String> patient_sheduleMap = new HashMap<>();
                        patient_sheduleMap.put("uid", CurrentPatient);
                        patient_sheduleMap.put("request_type", request_type1);
                        patient_sheduleMap.put("shedule", booking_shedule);
                        patient_sheduleMap.put("bookingCharg", booking_chargedP);

                        final HashMap<String, String> doctor_sheduleMap = new HashMap<>();
                        doctor_sheduleMap.put("uid", DoctorId);
                        doctor_sheduleMap.put("request_type", request_type2);
                        doctor_sheduleMap.put("shedule", booking_shedule);
                        doctor_sheduleMap.put("bookingCharg", booking_chargedP);


                        progressDialog.setTitle("Requesting Doctor for Booking");
                        progressDialog.setMessage("Please wait...");
                        progressDialog.setCanceledOnTouchOutside(false);
                        // progressDialog.show();


                        BookingRequest_Ref.child(CurrentPatient).child(DoctorId).
                                setValue(patient_sheduleMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    BookingRequest_Ref.child(DoctorId).child(CurrentPatient).
                                            setValue(doctor_sheduleMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                if (task.isSuccessful()) {

                                                    progressDialog.dismiss();

                                                    Toast.makeText(getApplicationContext(), "Booked on:" + booking_shedule, Toast.LENGTH_LONG).show();
                                                    Intent booking_request_intent = new Intent(getApplicationContext(), PatientMeetDoctorProfileActivity.class);
                                                    booking_request_intent.putExtra("profile_doctor_id", DoctorId);
                                                    startActivity(booking_request_intent);
                                                }
                                            }

                                        }
                                    });

                                } else {
                                    String message = task.getException().toString();
                                    Toast.makeText(getApplicationContext(), "message: " + message, Toast.LENGTH_LONG).show();

                                }


                            }
                        });

                    }

                    if (flag == 1) {
                        Toast.makeText(PatientBookingDoctorActivity.this, "This appointment time is allready choosen by some patient request to choose another date and time for Appointment", Toast.LENGTH_LONG).show();
                        flag = 0;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
}
