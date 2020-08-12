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

public class DoctorConfirmedPaymentReport extends AppCompatActivity {

    private RecyclerView doctor_payment_confirm_RecyclerList;
    private FirebaseAuth mAuth;
    private DatabaseReference PaymentReff,Confirm_BookingList_Ref,BookingAppointmentTime_Ref,DoctorRef, PaymentDone_REFF,Payment_report_REF;
    private String current_Doctor;
    private Toolbar doctor_payment_confirm_toolbar;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_confirmed_payment_report);

        progressDialog=new ProgressDialog(this);


        doctor_payment_confirm_RecyclerList = findViewById(R.id.doctor_payment_report_confirm_Recycler_List);
        doctor_payment_confirm_RecyclerList.setLayoutManager(new LinearLayoutManager(this));

        doctor_payment_confirm_toolbar = findViewById(R.id.doctor_payment_report_confrm_profile_toolbar);
        doctor_payment_confirm_toolbar.setTitle("ConfirmedPayment");

        mAuth = FirebaseAuth.getInstance();
        current_Doctor = mAuth.getCurrentUser().getUid();
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
        FirebaseRecyclerAdapter<getterSetterForBookingReq,DoctorConfirmedPaymentReport.RequestViewHolder> adapter = new FirebaseRecyclerAdapter<getterSetterForBookingReq, DoctorConfirmedPaymentReport.RequestViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull final DoctorConfirmedPaymentReport.RequestViewHolder requestViewHolder, int i, @NonNull final getterSetterForBookingReq contacts)
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
                                                    String dname=dataSnapshot.child("patient_name").getValue().toString();
                                                    String dphoto=dataSnapshot.child("patient_profile").getValue().toString();
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
            public DoctorConfirmedPaymentReport.RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_request_inflate, parent, false);
                DoctorConfirmedPaymentReport.RequestViewHolder holder = new DoctorConfirmedPaymentReport.RequestViewHolder(view);


                return holder;


            }
        };
        doctor_payment_confirm_RecyclerList.setAdapter(adapter);
        adapter.startListening();


    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        TextView setRequesterName, SetRequestrStatus,Setcontacts,doctor_charg;
        CircleImageView setRequesterPhoto;
        Button Accept, Cancel;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);

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
