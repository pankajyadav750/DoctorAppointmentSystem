package com.example.doctorappointmentsystem;

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

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorViewConfirmBookingActivity extends AppCompatActivity {


    private RecyclerView manage_Confirm_booking__RecyclerList;
    private FirebaseAuth mAuth;
    private DatabaseReference Booking_ref, Patient_REF,Confirm_BookingList_Ref,BookingAppointmentTime_Ref,DoctorRef;
    private String current_Doctor;
    private Toolbar manage_doctor_confirm_booking_profile_toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_confirm_booking);

        manage_Confirm_booking__RecyclerList = findViewById(R.id.doctor_booking_confirm_Recycler_List);
        manage_Confirm_booking__RecyclerList.setLayoutManager(new LinearLayoutManager(this));

        manage_doctor_confirm_booking_profile_toolbar = findViewById(R.id.doctor_booking_confrm_profile_toolbar);

        mAuth = FirebaseAuth.getInstance();
        current_Doctor = mAuth.getCurrentUser().getUid();
        Booking_ref = FirebaseDatabase.getInstance().getReference().child("BookingRequest");
        Confirm_BookingList_Ref = FirebaseDatabase.getInstance().getReference().child("ConfirmBookingList");
        BookingAppointmentTime_Ref= FirebaseDatabase.getInstance().getReference().child("BookingAppointmentTime");
        Patient_REF = FirebaseDatabase.getInstance().getReference().child("Patients");

        DoctorRef= FirebaseDatabase.getInstance().getReference().child("Doctors");

        manage_doctor_confirm_booking_profile_toolbar.setTitle("Patient Confirm Booking List");


    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<getterSetterForBookingReq> options = new FirebaseRecyclerOptions.Builder<getterSetterForBookingReq>()
                .setQuery(Confirm_BookingList_Ref.child(current_Doctor), getterSetterForBookingReq.class)
                .build();
        FirebaseRecyclerAdapter<getterSetterForBookingReq, RequestViewHolder> adapter = new FirebaseRecyclerAdapter<getterSetterForBookingReq, RequestViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final RequestViewHolder requestViewHolder, int i, @NonNull final getterSetterForBookingReq contacts) {
                requestViewHolder.itemView.findViewById(R.id.Request_cancel).setVisibility(View.VISIBLE);
                requestViewHolder.itemView.findViewById(R.id.Request_accept).setVisibility(View.VISIBLE);
                final String list_of_user = getRef(i).getKey();
                DatabaseReference getTypeRef = getRef(i).getRef();



                getTypeRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {


                            final String authentication_key=dataSnapshot.child("authentication_key").getValue().toString();

                            final String shedule = dataSnapshot.child("shedule").getValue().toString();

                            final String doctore_charge = dataSnapshot.child("bookingCharge").getValue().toString();
                            requestViewHolder.SetRequestrStatus.setText(shedule);

                            requestViewHolder.doctor_charge.setText(doctore_charge);

                            Patient_REF.child(list_of_user).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.hasChild("image")) {


                                        final String image = dataSnapshot.child("image").getValue().toString();
                                        final String name = dataSnapshot.child("name").getValue().toString();
                                        final String contact = dataSnapshot.child("contact").getValue().toString();
                                        Picasso.get().load(image).into(requestViewHolder.setRequesterPhoto);
                                        requestViewHolder.setRequesterName.setText(name);
                                        requestViewHolder.Setcontacts.setText(contact);


                                        requestViewHolder.itemView.findViewById(R.id.Request_accept).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                Intent intent=new Intent(getApplicationContext(),PatientVarificationActivity.class);
                                                intent.putExtra("Patient_ID",list_of_user);
                                                intent.putExtra("patient_auth",authentication_key);
                                                startActivity(intent);
                                              //  Toast.makeText(getApplicationContext(), "appointments is done successfully", Toast.LENGTH_SHORT).show();


                                            }
                                        });
                                        requestViewHolder.itemView.findViewById(R.id.Request_cancel).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Toast.makeText(getApplicationContext(), "appointments is canceled successfully ", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        } else {


                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_layout_inflate, parent, false);
                RequestViewHolder holder = new RequestViewHolder(view);

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
        manage_Confirm_booking__RecyclerList.setAdapter(adapter);
        adapter.startListening();


    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        TextView setRequesterName, SetRequestrStatus,Setcontacts,doctor_charge;
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
            doctor_charge=itemView.findViewById(R.id.disp_doctor_charge);

            Accept.setText("VerifyPatient");

        }
    }
}
