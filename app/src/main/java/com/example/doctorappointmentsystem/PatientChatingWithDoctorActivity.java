package com.example.doctorappointmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class PatientChatingWithDoctorActivity extends AppCompatActivity {
    private RecyclerView manage_Patient_chat_RecyclerList;
    private FirebaseAuth mAuth;
    private DatabaseReference Booking_ref, Patient_REF, Confirm_ChatingList_Ref, DoctorRef,DoctorState;
    private String current_Patient;
    private Toolbar manage_Patient_chat_profile_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_chating_with_doctor);

        manage_Patient_chat_RecyclerList = findViewById(R.id.patient_chating_to_patient_Recycler_List);
        manage_Patient_chat_RecyclerList.setLayoutManager(new LinearLayoutManager(this));

        manage_Patient_chat_profile_toolbar = findViewById(R.id.patient_chating_to_patient_profile_toolbar);

        mAuth = FirebaseAuth.getInstance();
        current_Patient = mAuth.getCurrentUser().getUid();
        // Booking_ref = FirebaseDatabase.getInstance().getReference().child("BookingRequest");
        // Confirm_BookingList_Ref = FirebaseDatabase.getInstance().getReference().child("ConfirmBookingList");
        //BookingAppointmentTime_Ref= FirebaseDatabase.getInstance().getReference().child("BookingAppointmentTime");
        Patient_REF = FirebaseDatabase.getInstance().getReference().child("Patients");
        Confirm_ChatingList_Ref = FirebaseDatabase.getInstance().getReference().child("ConfirmChatingList");
        DoctorState=FirebaseDatabase.getInstance().getReference().child("DoctorState");

        DoctorRef = FirebaseDatabase.getInstance().getReference().child("Doctors");

        manage_Patient_chat_profile_toolbar.setTitle("Patient Chat List with Doctor");

    }

    public void onStart() {
        super.onStart();


        updatePatieintState("Online");

        FirebaseRecyclerOptions<getterSetterForBookingReq> options = new FirebaseRecyclerOptions.Builder<getterSetterForBookingReq>()
                .setQuery(Confirm_ChatingList_Ref.child(current_Patient), getterSetterForBookingReq.class)
                .build();
        FirebaseRecyclerAdapter<getterSetterForBookingReq, PatientChatingWithDoctorActivity.RequestViewHolder> adapter = new FirebaseRecyclerAdapter<getterSetterForBookingReq, PatientChatingWithDoctorActivity.RequestViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final PatientChatingWithDoctorActivity.RequestViewHolder requestViewHolder, int i, @NonNull final getterSetterForBookingReq contacts) {

                final String list_of_user = getRef(i).getKey();
                DatabaseReference getTypeRef = getRef(i).getRef();
                final String[] image = {"default"};

                DoctorRef.child(list_of_user).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {

                            if (dataSnapshot.hasChild("image")) {


                                image[0] = dataSnapshot.child("image").getValue().toString();

                                // final String contact = dataSnapshot.child("contact").getValue().toString();
                                Picasso.get().load(image[0]).into(requestViewHolder.setRequesterPhoto);

                            }


                            final String name = dataSnapshot.child("name").getValue().toString();
                            requestViewHolder.setRequesterName.setText(name);


                            DoctorState.child(list_of_user).addValueEventListener(new ValueEventListener()
                            {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                {
                                    if (dataSnapshot.hasChild("Doctor_State")) {
                                        final String lastSeenDate = dataSnapshot.child("Doctor_State").child("date").getValue().toString();
                                        final String lastSeenTime = dataSnapshot.child("Doctor_State").child("time").getValue().toString();
                                        final String state = dataSnapshot.child("Doctor_State").child("state").getValue().toString();
                                        final String lastSeen = lastSeenTime + " / " + lastSeenDate;


                                        if (state.equals("Online")) {

                                            requestViewHolder.SetRequestrStatus.setText("Online Now");
                                            requestViewHolder.online_icon.setVisibility(View.VISIBLE);
                                        } else {
                                            requestViewHolder.SetRequestrStatus.setText("Last Seen:" + "\n" + lastSeenTime + " / " + lastSeenDate + "\n" + state);
                                            requestViewHolder.online_icon.setVisibility(View.INVISIBLE);
                                        }

                                    } else {
                                        requestViewHolder.online_icon.setVisibility(View.INVISIBLE);

                                        requestViewHolder.SetRequestrStatus.setText("Offline");
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });



                            requestViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(getApplicationContext(), ChatttingWithActivity.class);
                                    intent.putExtra("visit_patient_id", list_of_user);
                                    intent.putExtra("visit_patient_name", name);
                                    // intent.putExtra("last_seen",lastSeen);
                                    //  intent.putExtra("state",state);
                                    intent.putExtra("visit_patient_image", image[0]);
                                    startActivity(intent);

                                }
                            });

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @NonNull
            @Override
            public PatientChatingWithDoctorActivity.RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_request_inflate, parent, false);
                PatientChatingWithDoctorActivity.RequestViewHolder holder = new PatientChatingWithDoctorActivity.RequestViewHolder(view);
                return holder;


            }
        };


        manage_Patient_chat_RecyclerList.setAdapter(adapter);
        adapter.startListening();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseUser current_user;
        current_user = mAuth.getCurrentUser();

        if (current_user != null) {
            updatePatieintState("Offline");
        }
    }



    private void updatePatieintState(String state) {
        String SaveCurrentTime, SaveCurrentDate;

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd,yyyy");
        SaveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        SaveCurrentTime = currentTime.format(calendar.getTime());


        HashMap<String, Object> onlineStateMap = new HashMap<>();
        onlineStateMap.put("time", SaveCurrentTime);
        onlineStateMap.put("date", SaveCurrentDate);
        onlineStateMap.put("state", state);

        Patient_REF.child(current_Patient).child("Patient_State").updateChildren(onlineStateMap);
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        TextView setRequesterName, SetRequestrStatus;
        CircleImageView setRequesterPhoto;
        ImageView online_icon;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            online_icon = itemView.findViewById(R.id.online_icon);
            setRequesterName = itemView.findViewById(R.id.chat_disp_user_name);
            SetRequestrStatus = itemView.findViewById(R.id.chat_disp_user_status);
            setRequesterPhoto = itemView.findViewById(R.id.chat_disp_patient_profilee);
        }
    }
}
