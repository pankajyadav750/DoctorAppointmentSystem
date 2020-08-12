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

public class AdminSetUpiIdActivity extends AppCompatActivity {
    private EditText upiIdEdit;
    private Button upiIdbtn;
    private DatabaseReference AdminUpiId_REF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_set_upi_id);


        upiIdEdit = findViewById(R.id.admin_Set_upiId);
        upiIdbtn = findViewById(R.id.set_upiId);
        AdminUpiId_REF = FirebaseDatabase.getInstance().getReference().child("AdminUpiId");

        upiIdbtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ammount = upiIdEdit.getText().toString();

                if (TextUtils.isEmpty(ammount)) {
                    Toast.makeText(getApplicationContext(), "please enter Upi id", Toast.LENGTH_LONG).show();
                } else {
                    AdminUpiId_REF.child("AdminUPIID").setValue(ammount).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(getApplicationContext(), AdminDashboardActivity.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Upi Id set Successfull", Toast.LENGTH_LONG).show();

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

        AdminUpiId_REF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    String ammount1=dataSnapshot.child("AdminUPIID").getValue().toString();
                    upiIdEdit.setText(ammount1);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
