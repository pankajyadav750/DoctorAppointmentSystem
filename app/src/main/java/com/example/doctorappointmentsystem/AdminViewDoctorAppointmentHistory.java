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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class AdminViewDoctorAppointmentHistory extends AppCompatActivity {

    private RecyclerView Patient_Appointment_history_RecyclerList;
    private FirebaseAuth mAuth;
    private DatabaseReference Appointment_history_Ref,Confirm_BookingList_Ref,BookingAppointmentTime_Ref,DoctorRef, PaymentDone_REFF,Payment_report_REF;
    private String current_Doctor;
    private Toolbar Patient_Appointment_history_toolbar;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_doctor_appointment_history);

        progressDialog=new ProgressDialog(this);

        current_Doctor=getIntent().getExtras().get("profile_doctor_id").toString();


        Patient_Appointment_history_RecyclerList = findViewById(R.id.patient_appointment_history_Recycler_List);
        Patient_Appointment_history_RecyclerList.setLayoutManager(new LinearLayoutManager(this));

        Patient_Appointment_history_toolbar = findViewById(R.id.patient_Appointment_history_toolbar_profile_toolbar);
        Patient_Appointment_history_toolbar.setTitle("Appointment History");

        mAuth = FirebaseAuth.getInstance();
        Appointment_history_Ref= FirebaseDatabase.getInstance().getReference().child("PreviousBookingAppointmentList");
        DoctorRef = FirebaseDatabase.getInstance().getReference().child("Doctors");
        BookingAppointmentTime_Ref= FirebaseDatabase.getInstance().getReference().child("BookingAppointmentTime");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<getterSetterForBookingReq> options = new FirebaseRecyclerOptions.Builder<getterSetterForBookingReq>()
                .setQuery(Appointment_history_Ref.child(current_Doctor), getterSetterForBookingReq.class)
                .build();
        FirebaseRecyclerAdapter<getterSetterForBookingReq, AdminViewDoctorAppointmentHistory.RequestViewHolder> adapter = new FirebaseRecyclerAdapter<getterSetterForBookingReq, AdminViewDoctorAppointmentHistory.RequestViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull final AdminViewDoctorAppointmentHistory.RequestViewHolder requestViewHolder, final int i, @NonNull final getterSetterForBookingReq contacts)
            {
                final String list_of_user = getRef(i).getKey();
                final DatabaseReference getTypeRef = getRef(i).getRef();


                Appointment_history_Ref.child(current_Doctor).child(list_of_user).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists())
                        {
                            final DatabaseReference itemsRef = FirebaseDatabase.getInstance().getReference().child("PreviousBookingAppointmentList").child(current_Doctor).child(list_of_user);
                            ValueEventListener eventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                        final String name = ds.getKey();
                                        // Toast.makeText(getApplicationContext(),""+name,Toast.LENGTH_LONG).show();


                                        itemsRef.child(name).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                            {
                                                if (dataSnapshot.exists())
                                                {
                                                    final String dname=dataSnapshot.child("name").getValue().toString();
                                                    final String dphoto=dataSnapshot.child("photo").getValue().toString();
                                                    final String date=dataSnapshot.child("date").getValue().toString();
                                                    final String status=dataSnapshot.child("status").getValue().toString();
                                                    final String medicine=dataSnapshot.child("medicine").getValue().toString();
                                                    final String dateNtime=dataSnapshot.child("dateNTime").getValue().toString();
                                                    Picasso.get().load(dphoto).into(requestViewHolder.setRequesterPhoto);
                                                    requestViewHolder.setRequesterName.setText(dname);
                                                    requestViewHolder.SetRequestrStatus.setText(date+"\n"+status);

                                                    //           Toast.makeText(getApplicationContext(),""+dname,Toast.LENGTH_LONG).show();

                                                  /*  requestViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view)
                                                        {
                                                            String visite_user=getRef(i).getKey();
                                                            Intent intent=new Intent(getApplicationContext(),DoctorReportGenerateActivity.class);
                                                            intent.putExtra("profile_user_id",name);
                                                            intent.putExtra("doctor_name",dname);
                                                            intent.putExtra("doctor_photo",dphoto);
                                                            intent.putExtra("doctor_date",date);
                                                            intent.putExtra("doctor_status",status);
                                                            intent.putExtra("doctor_medicine",medicine);
                                                            intent.putExtra("doctor_dateNtime",dateNtime);



                                                            startActivity(intent);
                                                        }
                                                    });

                                                   */

                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {}
                            };
                            itemsRef.addListenerForSingleValueEvent(eventListener);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @NonNull
            @Override
            public AdminViewDoctorAppointmentHistory.RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_request_inflate, parent, false);
                AdminViewDoctorAppointmentHistory.RequestViewHolder holder = new AdminViewDoctorAppointmentHistory.RequestViewHolder(view);


                return holder;


            }
        };
        Patient_Appointment_history_RecyclerList.setAdapter(adapter);
        adapter.startListening();


    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        TextView setRequesterName, SetRequestrStatus,Setcontacts,doctor_charg;
        CircleImageView setRequesterPhoto;
        Button Accept, Cancel;
        ImageView online_icon;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);

            online_icon=itemView.findViewById(R.id.online_icon);
            setRequesterName = itemView.findViewById(R.id.chat_disp_user_name);
            SetRequestrStatus = itemView.findViewById(R.id.chat_disp_user_status);
            setRequesterPhoto = itemView.findViewById(R.id.chat_disp_patient_profilee);
            //Setcontacts = itemView.findViewById(R.id.disp_user_contact);
            //doctor_charg=itemView.findViewById(R.id.disp_doctor_charge);

            // Accept.setText("DoneAppointmet");
            //Cancel.setText("Cancel");
            //Accept.setText("Pay");

        }
    }
}
