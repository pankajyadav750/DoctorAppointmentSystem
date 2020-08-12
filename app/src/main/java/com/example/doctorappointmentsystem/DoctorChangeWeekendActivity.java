package com.example.doctorappointmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorChangeWeekendActivity extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{

    private Spinner select_weekend;
    private DatabaseReference Doctor_Weekend_REf;
    private Button ChangeWeekend;
    private FirebaseAuth mAuth;
    private String current_Doctor;
    String [] selection=new String[30];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_change_weekend);
        ChangeWeekend=findViewById(R.id.change_weekenda);


        select_weekend=findViewById(R.id.doctor_profile_weekend_spiner);
        Doctor_Weekend_REf= FirebaseDatabase.getInstance().getReference().child("DoctorWeekend");
        mAuth=FirebaseAuth.getInstance();
        current_Doctor=mAuth.getCurrentUser().getUid();


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.doctor_off_day, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_weekend.setAdapter(adapter);
        select_weekend.setOnItemSelectedListener(this);

        ChangeWeekend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changedWeekend();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {


        int j=0;


        selection[j] = parent.getItemAtPosition(position).toString();

        j++;

    }



    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }


    private void changedWeekend()
    {
        Doctor_Weekend_REf.child(current_Doctor).child("Weekend").setValue(selection[0]).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"your Weekend updated",Toast.LENGTH_LONG).show();
                }

            }
        });


    }




}
