package com.example.doctorappointmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DoctorViewChatRequestActivity extends AppCompatActivity {

    private RecyclerView manage_chat_request_RecyclerList;
    private FirebaseAuth mAuth;
    private DatabaseReference chating_ref, Patient_REF, Confirm_chatingList_Ref, DoctorRef;
    private String current_Doctor;
    private Toolbar manage_doctor_profile_toolbar;
    private ProgressDialog progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_chat_request);

        manage_chat_request_RecyclerList = findViewById(R.id.doctor_chating_Recycler_List);
        manage_chat_request_RecyclerList.setLayoutManager(new LinearLayoutManager(this));

        manage_doctor_profile_toolbar = findViewById(R.id.doctor_chating_profile_toolbar);

        mAuth = FirebaseAuth.getInstance();
        current_Doctor = mAuth.getCurrentUser().getUid();
        chating_ref = FirebaseDatabase.getInstance().getReference().child("ChatingRequest");
        Confirm_chatingList_Ref = FirebaseDatabase.getInstance().getReference().child("ConfirmChatingList");

        Patient_REF = FirebaseDatabase.getInstance().getReference().child("Patients");

        DoctorRef = FirebaseDatabase.getInstance().getReference().child("Doctors");

        manage_doctor_profile_toolbar.setTitle("Patient chating List");


    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<getterSetterForBookingReq> options = new FirebaseRecyclerOptions.Builder<getterSetterForBookingReq>()
                .setQuery(chating_ref.child(current_Doctor), getterSetterForBookingReq.class)
                .build();
        FirebaseRecyclerAdapter<getterSetterForBookingReq, DoctorViewBookinRequestActivity.RequestViewHolder> adapter = new FirebaseRecyclerAdapter<getterSetterForBookingReq, DoctorViewBookinRequestActivity.RequestViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final DoctorViewBookinRequestActivity.RequestViewHolder requestViewHolder, int i, @NonNull final getterSetterForBookingReq contacts) {
                requestViewHolder.itemView.findViewById(R.id.Request_cancel).setVisibility(View.VISIBLE);
                requestViewHolder.itemView.findViewById(R.id.Request_accept).setVisibility(View.VISIBLE);
                final String list_of_user = getRef(i).getKey();
                DatabaseReference getTypeRef = getRef(i).child("request_type").getRef();

                getTypeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {

                            Patient_REF.child(list_of_user).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                {
                                    String Pimage=dataSnapshot.child("image").getValue().toString();
                                    String Pname=dataSnapshot.child("name").getValue().toString();
                                    String Pcontact=dataSnapshot.child("contact").getValue().toString();
                                    String Pgender=dataSnapshot.child("gender").getValue().toString();

                                    requestViewHolder.setRequesterName.setText(Pname);
                                    Picasso.get().load(Pimage).into(requestViewHolder.setRequesterPhoto);
                                    requestViewHolder.Setcontacts.setText(Pcontact);
                                    requestViewHolder.SetRequestrStatus.setText(Pgender);


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError)
                    {

                    }
                });
                requestViewHolder.itemView.findViewById(R.id.Request_accept).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        Confirm_chatingList_Ref.child(current_Doctor).child(list_of_user).child("status").setValue("doctor_friends").
                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        if(task.isSuccessful())
                                        {
                                           Confirm_chatingList_Ref.child(list_of_user).child(current_Doctor).child("status").setValue("patient_friends").
                                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful())
                                                        {
                                                            chating_ref.child(current_Doctor).child(list_of_user).child("request_type")
                                                                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    chating_ref.child(list_of_user).child(current_Doctor).child("request_type")
                                                                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful())
                                                                            {


                                                                                Toast.makeText(getApplicationContext(), "Request accepted ", Toast.LENGTH_LONG).show();
                                                                                Intent intent=new Intent(getApplicationContext(),Doctor_ProfileActivity.class);
                                                                                startActivity(intent);
                                                                            }

                                                                        }
                                                                    });

                                                                }

                                                            }
                                                          });
                                                        }

                                                    }
                                                });
                                        }

                                    }
                                });

                    }
                });

                requestViewHolder.itemView.findViewById(R.id.Request_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {

                        chating_ref.child(current_Doctor).child(list_of_user).child("request_type")
                                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    chating_ref.child(list_of_user).child(current_Doctor).child("request_type")
                                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {

                                                Toast.makeText(getApplicationContext(), "Request Denied", Toast.LENGTH_LONG).show();
                                                Intent intent=new Intent(getApplicationContext(),DoctorViewChatRequestActivity.class);
                                                startActivity(intent);
                                            }

                                        }
                                    });

                                }

                            }
                        });

                    }
                });
            }

            @NonNull
            @Override
            public DoctorViewBookinRequestActivity.RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_layout_inflate, parent, false);
                DoctorViewBookinRequestActivity.RequestViewHolder holder = new DoctorViewBookinRequestActivity.RequestViewHolder(view);

                // if(!holder.setRequesterName.equals(null))
                //{
                //   return holder;

                // }
                //else
                //{
                String name = holder.setRequesterName.toString();
                Log.v("Name", name);
                return holder;
                //}

            }
        };
        manage_chat_request_RecyclerList.setAdapter(adapter);
        adapter.startListening();

    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        TextView setRequesterName, SetRequestrStatus, Setcontacts;
        CircleImageView setRequesterPhoto;
        Button Accept, Cancel;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);

            setRequesterName = itemView.findViewById(R.id.disp_user_name);
            SetRequestrStatus = itemView.findViewById(R.id.disp_user_status);
            setRequesterPhoto = itemView.findViewById(R.id.disp_patient_profilee);
            Setcontacts = itemView.findViewById(R.id.disp_user_contact);

            Accept = itemView.findViewById(R.id.Request_accept);
            Cancel = itemView.findViewById(R.id.Request_cancel);


        }
    }
}