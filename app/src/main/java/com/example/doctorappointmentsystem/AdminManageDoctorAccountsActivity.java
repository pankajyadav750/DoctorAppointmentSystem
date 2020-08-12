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

public class AdminManageDoctorAccountsActivity extends AppCompatActivity {

    private RecyclerView Admin_manage_Account_RecyclerList;
    private FirebaseAuth mAuth;
    private DatabaseReference chating_ref, Patient_REF, Confirm_chatingList_Ref, DoctorRef,  Doctor_UPI;
    private String current_Doctor;
    private Toolbar Admin_manage_doctor_account_toolbar;
    private ProgressDialog progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_admin_accounts);

        Admin_manage_Account_RecyclerList = findViewById(R.id.admin_doctor_chating_Recycler_List);
        Admin_manage_Account_RecyclerList.setLayoutManager(new LinearLayoutManager(this));

        Admin_manage_doctor_account_toolbar = findViewById(R.id.admin_doctor_chating_profile_toolbar);

        mAuth = FirebaseAuth.getInstance();

        chating_ref = FirebaseDatabase.getInstance().getReference().child("ChatingRequest");
        Doctor_UPI=FirebaseDatabase.getInstance().getReference().child("DoctorUPI");
        Confirm_chatingList_Ref = FirebaseDatabase.getInstance().getReference().child("ConfirmChatingList");

        Patient_REF = FirebaseDatabase.getInstance().getReference().child("Patients");

        DoctorRef = FirebaseDatabase.getInstance().getReference().child("Doctors");

        Admin_manage_doctor_account_toolbar.setTitle("Doctor UPI Id");

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<getterSetterForBookingReq> options=new FirebaseRecyclerOptions.Builder<getterSetterForBookingReq>()
                .setQuery(Doctor_UPI,getterSetterForBookingReq.class)
                .build();
        FirebaseRecyclerAdapter<getterSetterForBookingReq,FindsFriendsViewHolder> adapter=
                new FirebaseRecyclerAdapter<getterSetterForBookingReq, FindsFriendsViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final FindsFriendsViewHolder holder, final int position, @NonNull final getterSetterForBookingReq model)
                    {
                        //setting the value

                        String usr_list=getRef(position).getKey();

                        Doctor_UPI.child(usr_list).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                            {
                                if(dataSnapshot.exists())
                                {
                                    String name=dataSnapshot.child("name").getValue().toString();
                                    String upi_id=dataSnapshot.child("UPI_ID").getValue().toString();
                                    holder.setRequesterName.setText(name);
                                    holder. SetRequestrStatus.setText(upi_id);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        holder.setRequesterName.setText(model.getName());
                        holder. SetRequestrStatus.setText(model.getShedule());
                        Picasso.get().load(model.getImage()).into(holder.setRequesterPhoto);

                        DoctorRef.child(usr_list).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if(dataSnapshot.exists())
                                {
                                    String image=dataSnapshot.child("image").getValue().toString();
                                    Picasso.get().load(image).into(holder.setRequesterPhoto);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    }

                    @NonNull
                    @Override
                    public FindsFriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        //inflating layout user_details


                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_request_inflate,parent,false);
                        FindsFriendsViewHolder viewHolder=new FindsFriendsViewHolder(view);
                        return viewHolder;
                    }
                };

        Admin_manage_Account_RecyclerList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class FindsFriendsViewHolder extends RecyclerView.ViewHolder
    {
        //accessing and assigning id and from user_details layoput

        TextView setRequesterName, SetRequestrStatus;
        CircleImageView setRequesterPhoto;


        public FindsFriendsViewHolder(@NonNull View itemView) {
            super(itemView);

            setRequesterName = itemView.findViewById(R.id.chat_disp_user_name);
            SetRequestrStatus = itemView.findViewById(R.id.chat_disp_user_status);
            setRequesterPhoto = itemView.findViewById(R.id.chat_disp_patient_profilee);




        }
    }

}
