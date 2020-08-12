package com.example.doctorappointmentsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
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
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorViewBookinRequestActivity extends AppCompatActivity {


    private static final int MAX_LENGTH =5;
    private RecyclerView manage_booking_request_RecyclerList;
    private FirebaseAuth mAuth;
    private DatabaseReference Booking_ref, Patient_REF,Confirm_BookingList_Ref,BookingAppointmentTime_Ref,DoctorRef;
    private String current_Doctor;
    private Toolbar manage_doctor_profile_toolbar;
    private ProgressDialog progressBar;
    private  int lineCount=0,min=1,max=10000,output;
    private Integer a[]=new Integer[1];
    private  Random r;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_view_bookin_request);
        progressBar=new ProgressDialog(this);
        r=new Random();

        manage_booking_request_RecyclerList = findViewById(R.id.doctor_booking_Recycler_List);
        manage_booking_request_RecyclerList.setLayoutManager(new LinearLayoutManager(this));

        manage_doctor_profile_toolbar = findViewById(R.id.doctor_booking_profile_toolbar);

        mAuth = FirebaseAuth.getInstance();
        current_Doctor = mAuth.getCurrentUser().getUid();
        Booking_ref = FirebaseDatabase.getInstance().getReference().child("BookingRequest");
        Confirm_BookingList_Ref = FirebaseDatabase.getInstance().getReference().child("ConfirmBookingList");
        BookingAppointmentTime_Ref= FirebaseDatabase.getInstance().getReference().child("BookingAppointmentTime");
        Patient_REF = FirebaseDatabase.getInstance().getReference().child("Patients");

        DoctorRef= FirebaseDatabase.getInstance().getReference().child("Doctors");

        manage_doctor_profile_toolbar.setTitle("Patient Booking List");






    }



    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<getterSetterForBookingReq> options = new FirebaseRecyclerOptions.Builder<getterSetterForBookingReq>()
                .setQuery(Booking_ref.child(current_Doctor), getterSetterForBookingReq.class)
                .build();
        final FirebaseRecyclerAdapter<getterSetterForBookingReq, RequestViewHolder> adapter = new FirebaseRecyclerAdapter<getterSetterForBookingReq, RequestViewHolder>(options) {
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
                            final String shedule = dataSnapshot.child("shedule").getValue().toString();
                            requestViewHolder.SetRequestrStatus.setText(shedule);
                            final String charge=dataSnapshot.child("bookingCharg").getValue().toString();

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
                                               // Toast.makeText(getApplicationContext(), "successfully confirm booking", Toast.LENGTH_SHORT).show();


                                                DoctorRef.child(current_Doctor).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                    {

                                                        if (dataSnapshot.exists())
                                                        {
                                                              //int charg=250;
                                                            String image1 =dataSnapshot.child("image").getValue().toString();
                                                            String doctor_name =dataSnapshot.child("name").getValue().toString();
                                                            String doctor_contact=dataSnapshot.child("contact").getValue().toString();
                                                           // Toast.makeText(getApplicationContext(),"m: "+ image1,Toast.LENGTH_LONG).show();


                                                            if(max>min)
                                                            {
                                                                output=r.nextInt((max-min)+1)+min;
                                                            }



                                                            final HashMap<String, String> doctor_sheduleMap = new HashMap<>();
                                                            doctor_sheduleMap.put("image", image1);
                                                            doctor_sheduleMap.put("name", doctor_name);
                                                            doctor_sheduleMap.put("shedule", shedule);
                                                            doctor_sheduleMap.put("contact", doctor_contact);
                                                            doctor_sheduleMap.put("authentication_key", String.valueOf(output));
                                                            doctor_sheduleMap.put("bookingCharge", charge);

                                                            final HashMap<String, String> patient_sheduleMap = new HashMap<>();
                                                            patient_sheduleMap.put("image", image);
                                                            patient_sheduleMap.put("name", name);
                                                            patient_sheduleMap.put("shedule", shedule);
                                                            patient_sheduleMap.put("contact", contact);
                                                            patient_sheduleMap.put("authentication_key", String.valueOf(output));
                                                            patient_sheduleMap.put("bookingCharge",charge);

                                                            progressBar.setTitle("Confirm booking");
                                                            progressBar.setMessage("Please wait while we confirming booking");
                                                            progressBar.setCanceledOnTouchOutside(false);
                                                            progressBar.show();



                                                            Confirm_BookingList_Ref.child(current_Doctor).child(list_of_user).setValue(patient_sheduleMap).
                                                                    addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task)
                                                                        {
                                                                            if(task.isSuccessful())
                                                                            {

                                                                                Confirm_BookingList_Ref.child(list_of_user).child(current_Doctor).setValue(doctor_sheduleMap).
                                                                                        addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<Void> task)
                                                                                            {
                                                                                                if(task.isSuccessful())
                                                                                                {

                                                                                                    BookingAppointmentTime_Ref.child(current_Doctor).child(list_of_user).child("shedule").
                                                                                                            setValue(shedule).addOnCompleteListener(new OnCompleteListener<Void>()
                                                                                                    {
                                                                                                        @Override
                                                                                                        public void onComplete(@NonNull Task<Void> task)
                                                                                                        {
                                                                                                         if (task.isSuccessful())
                                                                                                         {

                                                                                                                 Booking_ref.child(current_Doctor).child(list_of_user).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                     @Override
                                                                                                                     public void onComplete(@NonNull Task<Void> task) {

                                                                                                                         if (task.isSuccessful()) {

                                                                                                                             Booking_ref.child(list_of_user).child(current_Doctor).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                                 @Override
                                                                                                                                 public void onComplete(@NonNull Task<Void> task) {

                                                                                                                                     if (task.isSuccessful()) {
                                                                                                                                         Toast.makeText(getApplicationContext(), "successfully confirmed booking request", Toast.LENGTH_LONG).show();
                                                                                                                                         Intent intent = new Intent(getApplicationContext(), Doctor_ProfileActivity.class);
                                                                                                                                         startActivity(intent);
                                                                                                                                         progressBar.dismiss();

                                                                                                                                     }

                                                                                                                                 }
                                                                                                                             });
                                                                                                                         }

                                                                                                                     }
                                                                                                                 });



                                                                                                         }
                                                                                                        }
                                                                                                    });
                                                                                            }   }
                                                                                        });
                                                                            }


                                                                        }
                                                                    });



                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });

                                            }
                                        });
                                        requestViewHolder.itemView.findViewById(R.id.Request_cancel).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                Booking_ref.child(current_Doctor).child(list_of_user).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if (task.isSuccessful())
                                                        {

                                                            Booking_ref.child(list_of_user).child(current_Doctor).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {

                                                                    if (task.isSuccessful())
                                                                    {






                                                                                            Toast.makeText(getApplicationContext(), " Request canceled  successfully", Toast.LENGTH_LONG).show();
                                                                                            Intent intent=new Intent(getApplicationContext(),Doctor_ProfileActivity.class);
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

                return holder;


            }


        };


        manage_booking_request_RecyclerList.setAdapter(adapter);


        adapter.startListening();






    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        TextView setRequesterName, SetRequestrStatus,Setcontacts;
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
