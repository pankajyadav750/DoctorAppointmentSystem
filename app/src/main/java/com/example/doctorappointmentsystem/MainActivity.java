package com.example.doctorappointmentsystem;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import de.hdodenhof.circleimageview.CircleImageView;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    int images[] = {R.drawable.slider2, R.drawable.slider2, R.drawable.slider3, R.drawable.slider4, R.drawable.slider5};
    private Toolbar toolbar,statee;
    private DrawerLayout myDrawer;
    private TextView userName;
    private CircleImageView  userImage;
    private NavigationView my_navigation_view;
    private ViewFlipper myFlipper;
    private Button meet_doctor, pharmancy, cold_cough_fever, depression_anxity, stomachAche_acidity, skin_care;
    private DatabaseReference PatientREF,DoctorsREF;
    private FirebaseAuth mAuth;
    private String CurrentPatient,currentPatient2;
    private FirebaseUser currentUser;
    private String state,patient_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_layout);



        PatientREF= FirebaseDatabase.getInstance().getReference().child("Patients");
        DoctorsREF= FirebaseDatabase.getInstance().getReference().child("Doctors");
        mAuth=FirebaseAuth.getInstance();

         currentUser=mAuth.getCurrentUser();



        assingValue();
        toolbar = findViewById(R.id.myy_toolbar);
        myDrawer = findViewById(R.id.drawer_layout);
        my_navigation_view = findViewById(R.id.navigation_view);
        myFlipper = findViewById(R.id.view_flipper);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        //  state=getIntent().getExtras().get("state").toString();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, myDrawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        myDrawer.addDrawerListener(toggle);
        toggle.syncState();
        my_navigation_view.setNavigationItemSelectedListener(MainActivity.this);



        View hView = my_navigation_view.inflateHeaderView(R.layout.header_drawer);
        userImage = (CircleImageView) hView.findViewById(R.id.profile_image);
        userName = (TextView)hView.findViewById(R.id.name_of_current_user);
        //imgvw .setImageResource();








        for (int image : images) {
            fliperImages(image);
        }


        meet_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MeetDoctorActivity.class);
                startActivity(intent);
            }
        });

        pharmancy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update=new Intent(getApplicationContext(),UpdatePatientProfileActivity.class);
                startActivity(update);
            }
        });


        cold_cough_fever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MeetDoctorActivity.class);
                startActivity(intent);
            }
        });

        depression_anxity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"click on doctor for booking",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),OurDoctorsActivity.class);
                startActivity(intent);
            }
        });


        stomachAche_acidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LmyBooking=new Intent(getApplicationContext(),PatientViewConfirmBookingActivity.class);
                startActivity(LmyBooking);
            }
        });


        skin_care.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent payment=new Intent(getApplicationContext(),PaymentConfirmedReportActivity.class);
                startActivity(payment);
            }
        });


    }

    private void assingValue() {
        meet_doctor = findViewById(R.id.meet_doctor);
        pharmancy = findViewById(R.id.pharamacy);
        cold_cough_fever = findViewById(R.id.cold_cough_fever);
        depression_anxity = findViewById(R.id.depression_anxity);
        stomachAche_acidity = findViewById(R.id.stomach_acidity);
        skin_care = findViewById(R.id.skin_care);

        userName = findViewById(R.id.name_of_current_user);
        userImage =findViewById(R.id.profile_image);
    }

    private void fliperImages(int image) {
        ImageView imageView = new ImageView(this);

        // final ImageView.ScaleType center = ImageView.ScaleType.CENTER;
        // imageView.setScaleType(center);
        imageView.setImageResource(image);

        myFlipper.addView(imageView);
        myFlipper.setFlipInterval(2000);
        myFlipper.setAutoStart(true);


        myFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        myFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {

        if (myDrawer.isDrawerOpen(GravityCompat.START)) {
            myDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }

    }




    protected void onStart() {
        super.onStart();

        if (currentUser == null) {
            Intent loginType_intent = new Intent(getApplicationContext(), LoginTypeActivity.class);
            loginType_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginType_intent);
            finish();
        }
        else {
            CurrentPatient=mAuth.getCurrentUser().getUid();

            DoctorsREF.child(CurrentPatient).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if (dataSnapshot.hasChild("name")) {
                        mAuth.signOut();
                        Intent booking_request_intent = new Intent(getApplicationContext(), LoginTypeActivity.class);
                        booking_request_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(booking_request_intent);
                        finish();

                    }
                    else
                    {
                        gotoPateintSetProfile();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




        }





    }



    private void gotoPateintSetProfile()
    {


        PatientREF.child(CurrentPatient).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name") && (dataSnapshot.hasChild("image"))))
                {
                    String name=dataSnapshot.child("name").getValue().toString();
                    String image=dataSnapshot.child("image").getValue().toString();
                    userName.setText(name);
                    Picasso.get().load(image).into(userImage);


                } else
                    {

                  /*  DoctorsREF.child(CurrentPatient).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            if (dataSnapshot.exists())
                            {
                                mAuth.signOut();
                                Intent loginType_intent = new Intent(getApplicationContext(), LoginTypeActivity.class);
                                loginType_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(loginType_intent);
                                finish();


                            }

                            else
                            {

                   */
                                Intent set_patient_intent = new Intent(getApplicationContext(), Set_Pateint_profile_Activity.class);
                                set_patient_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(set_patient_intent);
                                finish();






                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }




    private  void updatePatieintState(String state)
    {
        String SaveCurrentTime,SaveCurrentDate;

        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
        SaveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("hh:mm a");
        SaveCurrentTime=currentTime.format(calendar.getTime());


        HashMap<String,Object> onlineStateMap=new HashMap<>();
        onlineStateMap.put("time",SaveCurrentTime);
        onlineStateMap.put("date",SaveCurrentDate);
        onlineStateMap.put("state",state);

        PatientREF.child(CurrentPatient).child("Patient_State").updateChildren(onlineStateMap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.patient_logout,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.logout_id:

               updatePatieintState("Offline");
                mAuth.signOut();
                Intent loginType_intent = new Intent(getApplicationContext(), LoginTypeActivity.class);
                loginType_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginType_intent);
                finish();


                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.my_appointments:
                Intent myBooking=new Intent(getApplicationContext(),PatientViewConfirmBookingActivity.class);
                startActivity(myBooking);
                break;

            case R.id.my_bookings:

                Intent myPayement=new Intent(getApplicationContext(),PaymentConfirmedReportActivity.class);
                startActivity(myPayement);

                break;

            case R.id.my_orders:
                Toast.makeText(getApplicationContext(),"click to get reciept",Toast.LENGTH_SHORT).show();
                Intent appointment_history_doctor=new Intent(getApplicationContext(),PatientAppointmentBookingHistoryActivity.class);
                startActivity(appointment_history_doctor);
                break;

            case R.id.my_cunsultations:
                Intent consultDoctor=new Intent(getApplicationContext(),PatientChatingWithDoctorActivity.class);
                startActivity(consultDoctor);
                break;

            case R.id.read_about_health:
                Intent helthintent=new Intent(getApplicationContext(),ReadAboutHealthActivity.class);
                startActivity(helthintent);
                break;

            case R.id.logout_id:
                Toast.makeText(getApplicationContext(), "logout ", Toast.LENGTH_SHORT).show();
                break;


            case R.id.patient_update_health:
                Intent patientProfileUpdate=new Intent(getApplicationContext(),UpdatePatientProfileActivity.class);
                startActivity(patientProfileUpdate);

                break;
        }
        myDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
