package com.example.doctorappointmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DoctorReportGenerateActivity extends AppCompatActivity {
    private String dname,Did,image,current_patient,date,medicine,doctor_name,pname,dateNtime;
    private ImageView profile_patient;
    private FirebaseAuth mAuth;
    private TextView dHospital_name,dHospital_address,dContact,DName,DSpecialist,DEducation,DSuggetion,PName,PGender,PContact,
            patient_message,DateNTime;
    private DatabaseReference DoctorREF,PatientREF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_report_generate);

        mAuth=FirebaseAuth.getInstance();
        current_patient=mAuth.getCurrentUser().getUid();

        DoctorREF= FirebaseDatabase.getInstance().getReference().child("Doctors");
        PatientREF=FirebaseDatabase.getInstance().getReference().child("Patients");
        dHospital_address=findViewById(R.id.location_hospital);
        DateNTime=findViewById(R.id.date);
        dHospital_name=findViewById(R.id.hospital_name);
        dContact=findViewById(R.id.hospital_contact_doctor);
        patient_message=findViewById(R.id.patient_status);
        DName=findViewById(R.id.report_doctor_name);
        DSpecialist=findViewById(R.id.report_patient_specilist);
        DEducation=findViewById(R.id.report_doctor_education);
        DSuggetion=findViewById(R.id.patient_status);
        PName=findViewById(R.id.report_patient_name);
        PGender=findViewById(R.id.report_patient_gender);
        PContact=findViewById(R.id.report_patient_contact);

        Did = getIntent().getExtras().get("profile_user_id").toString();
        dname=getIntent().getExtras().get("doctor_name").toString();
        profile_patient=findViewById(R.id.patient_report_image);
        image=getIntent().getExtras().get("doctor_photo").toString();
        date=getIntent().getExtras().get("doctor_date").toString();
        medicine=getIntent().getExtras().get("doctor_medicine").toString();
        dateNtime=getIntent().getExtras().get("doctor_dateNtime").toString();

        DateNTime.setText("Date: "+dateNtime);



        Toast.makeText(getApplicationContext(),"name: "+dname+"\n"+"id: "+Did,Toast.LENGTH_LONG).show();

        DoctorREF.child(current_patient).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    String dhospital=dataSnapshot.child("hospital").getValue().toString();
                    String dlocation=dataSnapshot.child("hospital_address").getValue().toString();
                    String dcontact=dataSnapshot.child("contact").getValue().toString();
                    doctor_name=dataSnapshot.child("name").getValue().toString();
                    String dpsecialist=dataSnapshot.child("specilist_in").getValue().toString();

                    String deducation=dataSnapshot.child("education").getValue().toString();

                    dHospital_address.setText(dlocation);
                    dHospital_name.setText(dhospital);
                    dContact.setText("mob: "+dcontact);
                    DName.setText("Dr. "+doctor_name);
                    DSpecialist.setText(dpsecialist);
                    DEducation.setText(deducation);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        PatientREF.child(Did).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    pname=dataSnapshot.child("name").getValue().toString();
                    String pgender=dataSnapshot.child("gender").getValue().toString();
                    String pimage=dataSnapshot.child("image").getValue().toString();
                    String pcontact=dataSnapshot.child("contact").getValue().toString();

                    PName.setText(pname);
                    PGender.setText(pgender);
                    PContact.setText(pcontact);
                    Picasso.get().load(pimage).into(profile_patient);

                    patient_message.setText("This is reciept of Mr. / Mrs. "+pname +" who has successfully "+"\n"+ " Treated by Doctor "+dname+" accourding to the problem of Pateint"+"\n"+"Pateint should advised to take "+medicine);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
