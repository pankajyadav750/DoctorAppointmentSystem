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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminAppliedChargesActivity extends AppCompatActivity {

    private EditText booking_mount;
    private Button apply_amount;
    private DatabaseReference bookingAmount_REF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_applied_charges);
        booking_mount = findViewById(R.id.amount_booking_edit);
        apply_amount = findViewById(R.id.apply_charges_id);
        bookingAmount_REF = FirebaseDatabase.getInstance().getReference().child("BookingChareges");

        apply_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ammount = booking_mount.getText().toString();

                if (TextUtils.isEmpty(ammount)) {
                    Toast.makeText(getApplicationContext(), "please enter amount then click on apply charges button", Toast.LENGTH_LONG).show();
                } else {
                    bookingAmount_REF.child("Booking_charges").setValue(ammount).addOnCompleteListener(new OnCompleteListener<Void>() {
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

        bookingAmount_REF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    String ammount1=dataSnapshot.child("Booking_charges").getValue().toString();
                    booking_mount.setText(ammount1);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
