package com.example.doctorappointmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Meet_Doctor_ShortList_Activity extends AppCompatActivity {

    private RecyclerView manage_doctor_RecyclerList;
    private FirebaseAuth mAuth;
    private DatabaseReference Doctor_ref;
    private String current_Doctor;
    private Toolbar manage_doctor_profile_toolbar;
    private String speciality;
    private  my_doctor_adapter adapter;
    private List<Doctor_List> doctor_lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet__doctor__short_list_);

        speciality=getIntent().getExtras().get("speciality").toString();



        manage_doctor_RecyclerList=findViewById(R.id.doctor_shortlisted_list_Recycler_List);
        manage_doctor_RecyclerList.setLayoutManager(new LinearLayoutManager(this));
        manage_doctor_RecyclerList.setHasFixedSize(true);
        manage_doctor_profile_toolbar=findViewById(R.id.doctor_shortlist_manage_profile_toolbar);

        mAuth=FirebaseAuth.getInstance();
        current_Doctor=mAuth.getCurrentUser().getUid();
        Doctor_ref= FirebaseDatabase.getInstance().getReference("Doctors");
        manage_doctor_profile_toolbar.setTitle("List of ShortListed Doctor");

        doctor_lists=new ArrayList<>();

        adapter=new my_doctor_adapter(doctor_lists, getApplicationContext());

        manage_doctor_RecyclerList.setAdapter(adapter);



        Query query1=Doctor_ref.orderByChild("specilist_in").equalTo(speciality);
        query1.addValueEventListener(valueEventListener);






    }

    ValueEventListener valueEventListener=new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if(dataSnapshot.exists())
            {
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    Doctor_List doctor_list=snapshot.getValue(Doctor_List.class);
                    doctor_lists.add(doctor_list);
                }
                adapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            String message=databaseError.getMessage();
            Toast.makeText(getApplicationContext(),"error: "+message,Toast.LENGTH_LONG).show();

        }
    };
}
