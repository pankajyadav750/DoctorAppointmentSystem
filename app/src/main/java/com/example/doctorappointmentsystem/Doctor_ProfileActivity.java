package com.example.doctorappointmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.view.LayoutInflater.from;

public class
Doctor_ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private CircleImageView doctor_profile_image,userImage;
    private Toolbar my_doctor_toolbar;
    private DrawerLayout my_doctor_drawer;
    private NavigationView my_doctor_navigationView;
    private  FirebaseAuth mAuth;
    private Spinner select_weekend;
    private DatabaseReference Doctor_Ref,Doctor_Weekend_REf;
    private String current_Doctor;
    private Button ChangeWeekend,Doctor_on_live;
    private TextView doctor_prfile_name,doctor_profile_speciliazation,doctor_profile_hospital,doctor_profile_dob,userName;
    String [] selection=new String[30];
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer_layout2);
        mAuth=FirebaseAuth.getInstance();
        currentUser=mAuth.getCurrentUser();
        current_Doctor=mAuth.getCurrentUser().getUid();
        Doctor_Ref= FirebaseDatabase.getInstance().getReference().child("Doctors");
        my_doctor_toolbar=findViewById(R.id.my_profile_toolbar);
        ChangeWeekend=findViewById(R.id.change_weekend);
        Doctor_on_live=findViewById(R.id.on_live);
        Doctor_Weekend_REf=FirebaseDatabase.getInstance().getReference().child("DoctorWeekend");

        doctor_profile_image=findViewById(R.id.doctor_profile_image);
        select_weekend=findViewById(R.id.doctor_profile_weekend_spiner);

        my_doctor_drawer=findViewById(R.id.doctor_profile_drawer_layout);
        my_doctor_navigationView=findViewById(R.id.doctor_navigation_view);


        setSupportActionBar( my_doctor_toolbar);
        getSupportActionBar().setTitle("");

        doctor_prfile_name=findViewById(R.id.doctor_profile_name_edit);
        doctor_profile_speciliazation=findViewById(R.id.doctor_profile_speciliazation_edit);
        doctor_profile_hospital=findViewById(R.id.doctor_profile_Hospial_edit);

        doctor_profile_dob=findViewById(R.id.doctor_profile_Dob_edit);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, my_doctor_drawer, my_doctor_toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        my_doctor_drawer.addDrawerListener(toggle);
        toggle.syncState();
        my_doctor_navigationView.setNavigationItemSelectedListener(Doctor_ProfileActivity.this);

        View hView =  my_doctor_navigationView.inflateHeaderView(R.layout.header_drawer2);
        userImage = (CircleImageView) hView.findViewById(R.id.doctor_profile_image_drawer);
        userName = (TextView)hView.findViewById(R.id.name_of_current_doctor);




        ChangeWeekend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),DoctorChangeWeekendActivity.class);
                startActivity(intent);

            }
        });
        Doctor_on_live.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(getApplicationContext(),Doctor_LiveActivity.class);
                startActivity(intent);

            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();

        Doctor_Ref.child(current_Doctor).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if ((dataSnapshot.exists()) && (dataSnapshot.hasChild("name") && (dataSnapshot.hasChild("image")) && (dataSnapshot.hasChild("specilist_in"))
                        && (dataSnapshot.hasChild("image")) && (dataSnapshot.hasChild("hospital")) ))
                {

                    String doctor_name=dataSnapshot.child("name").getValue().toString();

                    String doctor_specialization=dataSnapshot.child("specilist_in").getValue().toString();
                    String doctor_dob=dataSnapshot.child("dob").getValue().toString();
                    String doctor_hospital=dataSnapshot.child("hospital").getValue().toString();
                    String doctor_image=dataSnapshot.child("image").getValue().toString();
                    Picasso.get().load(doctor_image).into(userImage);

                    doctor_prfile_name.setText("Name: "+doctor_name);
                    doctor_profile_speciliazation.setText("Specialist: "+doctor_specialization);
                    doctor_profile_dob.setText("DOB: "+doctor_dob);
                    doctor_profile_hospital.setText("Hospital: "+doctor_hospital);
                    userName.setText(doctor_name);

                    Picasso.get().load(doctor_image).into(doctor_profile_image);




                }
                else {
                    Intent intent=new Intent(getApplicationContext(),SetDoctorProfileActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });

    }





    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.booking_req_id:
                Intent bookingreq_intent=new Intent(getApplicationContext(),DoctorViewBookinRequestActivity.class);
                startActivity(bookingreq_intent);
                break;

            case R.id.chat_req_id:
                Intent chatreq_intent=new Intent(getApplicationContext(),DoctorViewChatRequestActivity.class);
                startActivity(chatreq_intent);
                break;

            case R.id.confirm_booking_req_id:
                Intent confirmbookingreq_intent=new Intent(getApplicationContext(),DoctorViewConfirmBookingActivity.class);
                startActivity(confirmbookingreq_intent);
                break;

            case R.id.confirm_chating_req_id:
                Intent doctr_chatingWith=new Intent(getApplicationContext(),DoctorChatingPatientActivity.class);
                startActivity(doctr_chatingWith);
                break;

            case R.id.payment_history_of_patient:
                Intent doctr_payment_history=new Intent(getApplicationContext(),DoctorConfirmedPaymentReport.class);
                startActivity(doctr_payment_history);
                break;

            case R.id.appointment_history_of_patient:
                Intent doctr_appointment_history=new Intent(getApplicationContext(),DoctorAppointmentBookingHistoryActivity.class);
                startActivity(doctr_appointment_history);
                break;

            case R.id.doctor_update_profile:
                Intent doctorprofile_update=new Intent(getApplicationContext(),UpdateDoctorProfileActivity.class);
                startActivity(doctorprofile_update);
                break;
        }



        my_doctor_drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    private  void updateDoctorState()
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
        onlineStateMap.put("state","Offline");

        Doctor_Ref.child(current_Doctor).child("Doctor_State").updateChildren(onlineStateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"Logout doctor",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mAuth.signOut();
        Intent booking_request_intent = new Intent(getApplicationContext(), LoginTypeActivity.class);
        booking_request_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(booking_request_intent);
        finish();

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
                mAuth.signOut();
                Intent loginType_intent = new Intent(getApplicationContext(), LoginTypeActivity.class);
                loginType_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginType_intent);
                finish();


                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
