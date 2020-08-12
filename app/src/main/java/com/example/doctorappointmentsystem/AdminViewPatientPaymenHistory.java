package com.example.doctorappointmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.ProgressDialog;
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

public class AdminViewPatientPaymenHistory extends AppCompatActivity {

    private RecyclerView payment_confirm_RecyclerList;
    private FirebaseAuth mAuth;
    private DatabaseReference PaymentReff,Confirm_BookingList_Ref,BookingAppointmentTime_Ref,DoctorRef, PaymentDone_REFF,Payment_report_REF;
    private String current_Doctor;
    private Toolbar payment_confirm_toolbar;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_patient_paymen_history);


        progressDialog=new ProgressDialog(this);

        current_Doctor=getIntent().getExtras().get("profile_patient_id").toString();



        payment_confirm_RecyclerList = findViewById(R.id.payment_report_confirm_Recycler_List);
        payment_confirm_RecyclerList.setLayoutManager(new LinearLayoutManager(this));

        payment_confirm_toolbar = findViewById(R.id.payment_report_confrm_profile_toolbar);
        payment_confirm_toolbar.setTitle("ConfirmedPayment");

        mAuth = FirebaseAuth.getInstance();
        PaymentReff= FirebaseDatabase.getInstance().getReference().child("PaymentReport");
        DoctorRef = FirebaseDatabase.getInstance().getReference().child("Doctors");
        BookingAppointmentTime_Ref= FirebaseDatabase.getInstance().getReference().child("BookingAppointmentTime");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<getterSetterForBookingReq> options = new FirebaseRecyclerOptions.Builder<getterSetterForBookingReq>()
                .setQuery(PaymentReff.child(current_Doctor), getterSetterForBookingReq.class)
                .build();
        FirebaseRecyclerAdapter<getterSetterForBookingReq, AdminViewPatientPaymenHistory.RequestViewHolder> adapter = new FirebaseRecyclerAdapter<getterSetterForBookingReq, AdminViewPatientPaymenHistory.RequestViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull final AdminViewPatientPaymenHistory.RequestViewHolder requestViewHolder, int i, @NonNull final getterSetterForBookingReq contacts)
            {
                final String list_of_user = getRef(i).getKey();
                final DatabaseReference getTypeRef = getRef(i).getRef();




                PaymentReff.child(current_Doctor).child(list_of_user).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists())
                        {
                            final DatabaseReference itemsRef = FirebaseDatabase.getInstance().getReference().child("PaymentReport").child(current_Doctor).child(list_of_user);
                            ValueEventListener eventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                        String name = ds.getKey();
                                        // Toast.makeText(getApplicationContext(),""+name,Toast.LENGTH_LONG).show();


                                        itemsRef.child(name).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                            {
                                                if (dataSnapshot.exists())
                                                {
                                                    String dname=dataSnapshot.child("doctor_name").getValue().toString();
                                                    String dphoto=dataSnapshot.child("doctor_profile").getValue().toString();
                                                    String date=dataSnapshot.child("date").getValue().toString();
                                                    String status=dataSnapshot.child("status").getValue().toString();
                                                    Picasso.get().load(dphoto).into(requestViewHolder.setRequesterPhoto);
                                                    requestViewHolder.setRequesterName.setText(dname);
                                                    requestViewHolder.SetRequestrStatus.setText(date+"\n"+status);

                                                    //           Toast.makeText(getApplicationContext(),""+dname,Toast.LENGTH_LONG).show();

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
            public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_request_inflate, parent, false);
                AdminViewPatientPaymenHistory.RequestViewHolder holder = new AdminViewPatientPaymenHistory.RequestViewHolder(view);


                return holder;


            }
        };
        payment_confirm_RecyclerList.setAdapter(adapter);
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
