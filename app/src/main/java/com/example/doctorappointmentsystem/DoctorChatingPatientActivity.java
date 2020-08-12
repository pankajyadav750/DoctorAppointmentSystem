package com.example.doctorappointmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class DoctorChatingPatientActivity extends AppCompatActivity {

    private RecyclerView manage_Doctor_chat_RecyclerList;
    private FirebaseAuth mAuth;
    private DatabaseReference Booking_ref, Patient_REF,Confirm_ChatingList_Ref,BookingAppointmentTime_Ref,DoctorState,DoctorRef;
    private String current_Doctor;
    private Toolbar manage_Doctor_chat_profile_toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_chating_patient);

        manage_Doctor_chat_RecyclerList = findViewById(R.id.doctor_chating_to_patient_Recycler_List);
        manage_Doctor_chat_RecyclerList.setLayoutManager(new LinearLayoutManager(this));

        manage_Doctor_chat_profile_toolbar= findViewById(R.id.doctor_chating_to_patient_profile_toolbar);

        mAuth = FirebaseAuth.getInstance();
        current_Doctor = mAuth.getCurrentUser().getUid();
        // Booking_ref = FirebaseDatabase.getInstance().getReference().child("BookingRequest");
        // Confirm_BookingList_Ref = FirebaseDatabase.getInstance().getReference().child("ConfirmBookingList");
        //BookingAppointmentTime_Ref= FirebaseDatabase.getInstance().getReference().child("BookingAppointmentTime");
        Patient_REF = FirebaseDatabase.getInstance().getReference().child("Patients");
        Confirm_ChatingList_Ref = FirebaseDatabase.getInstance().getReference().child("ConfirmChatingList");

        DoctorRef= FirebaseDatabase.getInstance().getReference().child("Doctors");


        manage_Doctor_chat_profile_toolbar.setTitle("Patient Chat List");

        DoctorState=FirebaseDatabase.getInstance().getReference().child("DoctorState");
    }




    @Override
    public void onStart() {
        super.onStart();

        updateDoctorState("Online");

        FirebaseRecyclerOptions<getterSetterForBookingReq> options = new FirebaseRecyclerOptions.Builder<getterSetterForBookingReq>()
                .setQuery(Confirm_ChatingList_Ref.child(current_Doctor), getterSetterForBookingReq.class)
                .build();
        FirebaseRecyclerAdapter<getterSetterForBookingReq, DoctorChatingPatientActivity.RequestViewHolder> adapter = new FirebaseRecyclerAdapter<getterSetterForBookingReq, DoctorChatingPatientActivity.RequestViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final DoctorChatingPatientActivity.RequestViewHolder requestViewHolder, int i, @NonNull final getterSetterForBookingReq contacts) {

                final String list_of_user = getRef(i).getKey();
                DatabaseReference getTypeRef = getRef(i).getRef();
                final String[] image = {"default"};

                Patient_REF.child(list_of_user).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists()) {

                            if (dataSnapshot.hasChild("image")) {


                                image[0] = dataSnapshot.child("image").getValue().toString();

                                // final String contact = dataSnapshot.child("contact").getValue().toString();
                                Picasso.get().load(image[0]).into(requestViewHolder.setRequesterPhoto);

                            }


                            final String name = dataSnapshot.child("name").getValue().toString();
                            requestViewHolder.setRequesterName.setText(name);

                            if (dataSnapshot.hasChild("Patient_State"))
                            {
                                final String lastSeenDate = dataSnapshot.child("Patient_State").child("date").getValue().toString();
                                final String lastSeenTime = dataSnapshot.child("Patient_State").child("time").getValue().toString();
                                final String state = dataSnapshot.child("Patient_State").child("state").getValue().toString();
                                final String lastSeen = lastSeenTime + " / " + lastSeenDate;




                                if(state.equals("Online"))
                                {
                                    requestViewHolder.SetRequestrStatus.setText("Online now");
                                    requestViewHolder.online_icon.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    requestViewHolder.online_icon.setVisibility(View.INVISIBLE);
                                    requestViewHolder.SetRequestrStatus.setText("Last Seen:" + "\n" + lastSeenTime + " / " + lastSeenDate + "\n" + state);
                                }
                            }
                            else
                            {
                                requestViewHolder.online_icon.setVisibility(View.INVISIBLE);
                                requestViewHolder.SetRequestrStatus.setText("Offline");
                            }



                            requestViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view)
                                {
                                    Intent intent=new Intent(getApplicationContext(),ChatttingWithActivity.class);
                                    intent.putExtra("visit_patient_id",list_of_user);
                                    intent.putExtra("visit_patient_name",name);
                                    // intent.putExtra("last_seen",lastSeen);
                                    //intent.putExtra("state",state);
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
            public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_request_inflate, parent, false);
                RequestViewHolder holder = new RequestViewHolder(view);
                return holder;


            }
        };


        manage_Doctor_chat_RecyclerList.setAdapter(adapter);
        adapter.startListening();


    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        updateDoctorState("Offline");

    }

    private  void updateDoctorState(String state)
    {
        String SaveCurrentTime,SaveCurrentDate;

        Calendar calendar=Calendar.getInstance();

        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyyy");
        SaveCurrentDate=currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime= new SimpleDateFormat("hh:mm a");
        SaveCurrentTime=currentTime.format(calendar.getTime());


        HashMap<String,Object> onlineStateMap=new HashMap<>();
        onlineStateMap.put("time",SaveCurrentTime);
        onlineStateMap.put("date",SaveCurrentDate);
        onlineStateMap.put("state",state);

        DoctorState.child(current_Doctor).child("Doctor_State").updateChildren(onlineStateMap);
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        TextView setRequesterName, SetRequestrStatus;
        CircleImageView setRequesterPhoto;
        ImageView online_icon;


        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);
            online_icon=itemView.findViewById(R.id.online_icon);
            setRequesterName = itemView.findViewById(R.id.chat_disp_user_name);
            SetRequestrStatus = itemView.findViewById(R.id.chat_disp_user_status);
            setRequesterPhoto = itemView.findViewById(R.id.chat_disp_patient_profilee);




        }
    }
}
