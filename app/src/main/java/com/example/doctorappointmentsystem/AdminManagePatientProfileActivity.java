package com.example.doctorappointmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AdminManagePatientProfileActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private  String PatientId;
    private NavigationView admin_manage_patient_profile_navigation;
    private Toolbar admin_manage_patient_toolbar;
    private CircleImageView pateint_imageView,userImage;
    private DrawerLayout admin_manage_patient_drawer;
    private TextView admin_patient_prfile_name, admin_manage_patient_profile_dob,admin_manage_patient_profile_gender,admin_manage_patient_profile_contact,
            admin_manage_patient_profile_address,userName;
    private DatabaseReference Patient_REF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer4_admin_manage_patient);
        PatientId = getIntent().getExtras().get("profile_doctor_id").toString();

        admin_manage_patient_drawer=findViewById(R.id.admin_manage_patient_profile_drawer_layout);
        admin_manage_patient_toolbar=findViewById(R.id.admin_managea_patient_profile_toolbar);
        admin_manage_patient_profile_navigation=findViewById(R.id.admin_manage_patient_navigation_view);
        admin_patient_prfile_name=findViewById(R.id.admin_manage_patient_profile_name);
        admin_manage_patient_profile_dob=findViewById(R.id.admin_manage_patient_dob);
        admin_manage_patient_profile_gender=findViewById(R.id.admin_manage_patient_gender);
        admin_manage_patient_profile_contact=findViewById(R.id.admin_manage_patient_contact);
        admin_manage_patient_profile_address=findViewById(R.id.admin_manage_patient_address);
        pateint_imageView=findViewById(R.id.admin_manage_patient_profile_image);
        Patient_REF= FirebaseDatabase.getInstance().getReference().child("Patients");

        Toast.makeText(getApplicationContext(),"patient id : "+PatientId,Toast.LENGTH_LONG);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, admin_manage_patient_drawer, admin_manage_patient_toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        admin_manage_patient_drawer.addDrawerListener(toggle);
        toggle.syncState();
        admin_manage_patient_profile_navigation.setNavigationItemSelectedListener(AdminManagePatientProfileActivity.this);

        View hView =  admin_manage_patient_profile_navigation.inflateHeaderView(R.layout.header_drawer);
        userImage = (CircleImageView) hView.findViewById(R.id.profile_image);
        userName = (TextView)hView.findViewById(R.id.name_of_current_user);


        Patient_REF.child(PatientId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {

                if(dataSnapshot.exists())
                {

                    String pName=dataSnapshot.child("name").getValue().toString();
                    String pContact=dataSnapshot.child("contact").getValue().toString();
                    String pDob=dataSnapshot.child("dob").getValue().toString();
                    String paddress=dataSnapshot.child("address").getValue().toString();
                    String pImage=dataSnapshot.child("image").getValue().toString();
                    String pGender=dataSnapshot.child("gender").getValue().toString();

                    Picasso.get().load(pImage).into(pateint_imageView);
                    Picasso.get().load(pImage).into(userImage);
                    userName.setText(pName);
                    admin_patient_prfile_name.setText(pName);
                    admin_manage_patient_profile_contact.setText(pContact);
                    admin_manage_patient_profile_address.setText(paddress);
                    admin_manage_patient_profile_gender.setText(pGender);
                    admin_manage_patient_profile_dob.setText(pDob);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        switch(item.getItemId())
        {


            case R.id.admin_manage_payment:
           Intent patient_payment=new Intent(getApplicationContext(),AdminViewPatientPaymenHistory.class);
           patient_payment.putExtra("profile_patient_id", PatientId);
           startActivity(patient_payment);
                break;

            case R.id.admin_manage_patient_confirm_booking:
                Intent patient_booking=new Intent(getApplicationContext(),AdminViewPatientConfirmBooking.class);
                patient_booking.putExtra("profile_patient_id", PatientId);
                startActivity(patient_booking);


                break;


            case R.id.admin_manage_appointment:
                Intent patient_appoint=new Intent(getApplicationContext(),AdminManagePatientAppointmentHistory.class);
                patient_appoint.putExtra("profile_patient_id", PatientId);
                startActivity(patient_appoint);


                break;




        }
        admin_manage_patient_drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}
