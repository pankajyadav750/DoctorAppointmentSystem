package com.example.doctorappointmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class ApplyDoctorPersonelChargesActivity extends AppCompatActivity {

    private EditText booking_mountP;
    private Button apply_amountP;
    private DatabaseReference bookingAmount_REFF;
    private String doctorId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_doctor_personel_charges);
        booking_mountP = findViewById(R.id.personly_amount_booking_edit);
        apply_amountP = findViewById(R.id.personly_apply_charges_id);
        bookingAmount_REFF = FirebaseDatabase.getInstance().getReference().child("PersonlyDoctorBookingChareges");
        doctorId = getIntent().getExtras().get("profile_doctor_id").toString();


        apply_amountP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ammount = booking_mountP.getText().toString();

                if (TextUtils.isEmpty(ammount)) {
                    Toast.makeText(getApplicationContext(), "please enter amount then click on apply charges button", Toast.LENGTH_LONG).show();
                } else {
                    bookingAmount_REFF.child(doctorId).child("Booking_charges").setValue(ammount).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), AdminDashboardActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "booking charges applied succesfully", Toast.LENGTH_LONG).show();

                            }

                        }
                    });
                }

            }
        });




    }

    @Override
    protected void onStart()
    {
        super.onStart();

        bookingAmount_REFF.child(doctorId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    String ammount1=dataSnapshot.child("Booking_charges").getValue().toString();
                    booking_mountP.setText(ammount1);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
