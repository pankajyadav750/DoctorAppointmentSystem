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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AdminManageDoctorProfileActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private NavigationView admin_manage_doctor_profile_navigation;
    private CircleImageView admin_manage_doctor_profile_image,userImage;
    private Toolbar admin_manage_doctor_toolbar;
    private DrawerLayout admin_manage_doctor_drawer;
    private TextView admin_doctor_prfile_name,admin_manage_doctor_profile_speciliazation,admin_manage_doctor_profile_hospital,
            admin_manage_doctor_profile_dob,admin_manage_doctor_profile_gender,admin_manage_doctor_profile_contact,admin_manage_doctor_profile_education,
            admin_manage_doctor_profile_address,admin_manage_doctor_profile_experience,userName;
    private DatabaseReference Doctor_REF;
    private String doctorId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer3_admin_manage_doctor);
        admin_manage_doctor_profile_navigation=findViewById(R.id.admin_manage_doctor_navigation_view);
        admin_manage_doctor_profile_gender=findViewById(R.id.admin_manage_doctor_profile_gender);
        admin_manage_doctor_profile_experience=findViewById(R.id.admin_manage_doctor_profile_experience);
        admin_manage_doctor_profile_contact=findViewById(R.id.admin_manage_doctor_profile_contact);
        admin_manage_doctor_profile_address=findViewById(R.id.admin_manage_doctor_profile_address);
        admin_manage_doctor_profile_education=findViewById(R.id.admin_manage_doctor_profile_education);
        admin_manage_doctor_toolbar=findViewById(R.id.admin_manage_profile_toolbar);
        Doctor_REF= FirebaseDatabase.getInstance().getReference().child("Doctors");

        admin_doctor_prfile_name=findViewById(R.id.admin_manage_doctor_profile_name);
        admin_manage_doctor_profile_speciliazation=findViewById(R.id.admin_manage_doctor_profile_speciliazation);
        admin_manage_doctor_profile_hospital=findViewById(R.id.admin_manage_doctor_profile_Hospial);
        admin_manage_doctor_profile_dob=findViewById(R.id.admin_manage_doctor_profile_Dob);
        admin_manage_doctor_drawer=findViewById(R.id.admin_manage_doctor_profile_drawer_layout);
        admin_manage_doctor_profile_image=findViewById(R.id.admin_manage_doctor_profile_image);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, admin_manage_doctor_drawer, admin_manage_doctor_toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        );

        admin_manage_doctor_drawer.addDrawerListener(toggle);
        toggle.syncState();
        admin_manage_doctor_profile_navigation.setNavigationItemSelectedListener(AdminManageDoctorProfileActivity.this);

        View hView =  admin_manage_doctor_profile_navigation.inflateHeaderView(R.layout.header_drawer);
        userImage = (CircleImageView) hView.findViewById(R.id.profile_image);
        userName = (TextView)hView.findViewById(R.id.name_of_current_user);


         doctorId = getIntent().getExtras().get("profile_doctor_id").toString();

         retrive_doctor();


    }

    private void retrive_doctor() {

        Doctor_REF.child(doctorId).addValueEventListener(new ValueEventListener() {
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

                    String doctor_gender=dataSnapshot.child("gender").getValue().toString();
                    String doctor_education=dataSnapshot.child("education").getValue().toString();
                    String doctor_contact=dataSnapshot.child("contact").getValue().toString();
                    String doctor_address=dataSnapshot.child("address").getValue().toString();
                    String doctor_experience=dataSnapshot.child("experence").getValue().toString();

                      Picasso.get().load(doctor_image).into(userImage);
                    admin_doctor_prfile_name.setText(doctor_name);
                    admin_manage_doctor_profile_speciliazation.setText(doctor_specialization);
                    admin_manage_doctor_profile_dob.setText(doctor_dob);
                    admin_manage_doctor_profile_hospital.setText(doctor_hospital);
                    admin_manage_doctor_profile_gender.setText(doctor_gender);
                    admin_manage_doctor_profile_education.setText(doctor_education);
                    admin_manage_doctor_profile_address.setText(doctor_address);
                    admin_manage_doctor_profile_contact.setText(doctor_contact);
                    admin_manage_doctor_profile_experience.setText(doctor_experience);
                    userName.setText(doctor_name);








                    Picasso.get().load(doctor_image).into(admin_manage_doctor_profile_image);






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


            case R.id.admin_manage_doctor_confirm_booking_req_id:
                Intent adminiDconfirmBooking=new Intent(getApplicationContext(),AdminViewDoctorConfirmBooking.class);
                adminiDconfirmBooking.putExtra("profile_doctor_id",doctorId);
                startActivity(adminiDconfirmBooking);
                break;

            case R.id.admin_manage_doctor_charges:
               Intent applyPersonelCharges=new Intent(getApplicationContext(),ApplyDoctorPersonelChargesActivity.class);
               applyPersonelCharges.putExtra("profile_doctor_id",doctorId);
               startActivity(applyPersonelCharges);
                break;

            case R.id.admin_manage_doctor_payment_history:
                Intent adminiDconfirmpayment=new Intent(getApplicationContext(),AdminViewDoctorPaymentHistory.class);
                adminiDconfirmpayment.putExtra("profile_doctor_id",doctorId);
                startActivity(adminiDconfirmpayment);
                break;

            case R.id.admin_manage_doctor_appointment_history:
                Intent adminiDconfirmAppointment=new Intent(getApplicationContext(),AdminViewDoctorAppointmentHistory.class);
                adminiDconfirmAppointment.putExtra("profile_doctor_id",doctorId);
                startActivity(adminiDconfirmAppointment);
                break;




        }
        admin_manage_doctor_drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
