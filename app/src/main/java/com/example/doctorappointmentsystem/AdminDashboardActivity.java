package com.example.doctorappointmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AdminDashboardActivity extends AppCompatActivity {

    private Button manageDoctor,managePatient,manageBoking,manageAccount,manageSettings,addDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        assignValuesOnAdminPanel();


        manageDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(getApplicationContext(),ManageDoctorActivity.class);
                Toast.makeText(getApplicationContext(),"click on doctor to know more",Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        });


        managePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent patient_intent=new Intent(getApplicationContext(),AdminManage_PatientActivity.class);
                Toast.makeText(getApplicationContext(),"click on patient to know more",Toast.LENGTH_SHORT).show();
                startActivity(patient_intent);
            }
        });


        manageAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent manageAccunt=new Intent(getApplicationContext(),AdminManageDoctorAccountsActivity.class);
                startActivity(manageAccunt);
            }
        });


        manageBoking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               Intent applyCharges=new Intent(getApplicationContext(),AdminAppliedChargesActivity.class);
               startActivity(applyCharges);
            }
        });


        manageSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intentleave=new Intent(getApplicationContext(),AdminSetUpiIdActivity.class);
                startActivity(intentleave);
            }
        });



        addDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Intent intent=new Intent(getApplicationContext(),AddDoctorActivity.class);
                startActivity(intent);
            }
        });






    }

    private void assignValuesOnAdminPanel()
    {
        manageDoctor=findViewById(R.id.manage_doctor);
        managePatient=findViewById(R.id.manage_patients);
        manageBoking=findViewById(R.id.manage_bookings);
        manageAccount=findViewById(R.id.manage_Accounts);
        manageSettings=findViewById(R.id.manage_settings);
        addDoctor=findViewById(R.id.add_doctor);
    }
}
