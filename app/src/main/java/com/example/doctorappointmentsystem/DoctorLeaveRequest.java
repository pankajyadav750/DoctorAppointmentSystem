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

public class DoctorLeaveRequest extends AppCompatActivity {
    private RecyclerView manage_leave_request_RecyclerList;
    private FirebaseAuth mAuth;
    private DatabaseReference doctorLeave_ref, Patient_REF,ConfirmLeaveRequest_Ref,BookingAppointmentTime_Ref,DoctorRef;
    private String current_Doctor;
    private Toolbar manage_doctor_profile_toolbar;
    private ProgressDialog progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_leave_request);

        manage_leave_request_RecyclerList = findViewById(R.id.doctor_leave_Recycler_List);
        manage_leave_request_RecyclerList.setLayoutManager(new LinearLayoutManager(this));

        manage_doctor_profile_toolbar = findViewById(R.id.doctor_leave_profile_toolbar);
        manage_doctor_profile_toolbar.setTitle("Doctor Leave Request");
        mAuth = FirebaseAuth.getInstance();
        doctorLeave_ref = FirebaseDatabase.getInstance().getReference().child("DoctorLeaveRequest");
        ConfirmLeaveRequest_Ref= FirebaseDatabase.getInstance().getReference().child("DoctorConfirmLeaveRequest");
        DoctorRef = FirebaseDatabase.getInstance().getReference().child("Doctors");



    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<getterSetterForBookingReq> options=new FirebaseRecyclerOptions.Builder<getterSetterForBookingReq>()
                .setQuery(doctorLeave_ref,getterSetterForBookingReq.class)
                .build();

        FirebaseRecyclerAdapter<getterSetterForBookingReq,FindsFriendsViewHolder> adapter=
                new FirebaseRecyclerAdapter<getterSetterForBookingReq, FindsFriendsViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final FindsFriendsViewHolder holder, final int i, @NonNull getterSetterForBookingReq model)
                    {

                        holder.itemView.findViewById(R.id.Request_cancel).setVisibility(View.VISIBLE);
                        holder.itemView.findViewById(R.id.Request_accept).setVisibility(View.VISIBLE);
                        final String list_of_user = getRef(i).getKey();
                        final DatabaseReference getTypeRef = getRef(i).getRef();
                        final String[] startDate = {"default"};
                        final String[] endDate = {"default"};
                        final String[] endDay = {"default"};
                        final String[] endMonth = {"default"};

                     getTypeRef.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                         {
                             if(dataSnapshot.exists())
                             {


                               final String   startDate2=dataSnapshot.child("From").getValue().toString();
                              final String   endDate2=dataSnapshot.child("To").getValue().toString();

                                 final String endDay=dataSnapshot.child("endDay").getValue().toString();
                                 final String startDay=dataSnapshot.child("startDay").getValue().toString();
                                 final String startMonth=dataSnapshot.child("startMonth").getValue().toString();

                                 final String endMonth=dataSnapshot.child("endMonth").getValue().toString();
                                 String image=dataSnapshot.child("photo").getValue().toString();
                                 String name=dataSnapshot.child("name").getValue().toString();
                                 holder.userStatus.setText("From: "+startDate2);
                                 holder.userContact.setText("To: "+endDate2);
                                 holder.userName.setText(name);
                                 Picasso.get().load(image).into(holder.userProfile);

                                 holder.itemView.findViewById(R.id.Request_accept).setOnClickListener(new View.OnClickListener()
                                 {
                                     @Override
                                     public void onClick(View v) {

                                         String request_type1="confirm";
                                         final HashMap<String, String> docotr_leave_req = new HashMap<>();
                                         docotr_leave_req.put("request_type", request_type1);
                                         docotr_leave_req.put("From", startDate2);
                                         docotr_leave_req.put("To", endDate2);
                                         docotr_leave_req.put("endDay", endDay);
                                         docotr_leave_req.put("endMonth", endMonth);
                                         docotr_leave_req.put("startDay", startDay);
                                         docotr_leave_req.put("startMonth", startMonth);


                                         ConfirmLeaveRequest_Ref.child(list_of_user).setValue(docotr_leave_req).addOnCompleteListener(new OnCompleteListener<Void>() {
                                             @Override
                                             public void onComplete(@NonNull Task<Void> task)
                                             {
                                                 if(task.isSuccessful())
                                                 {
                                                     doctorLeave_ref.child(list_of_user).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                         @Override
                                                         public void onComplete(@NonNull Task<Void> task)
                                                         {
                                                             if (task.isSuccessful())
                                                             {
                                                                 Toast.makeText(getApplicationContext(),"leave request confirm successfully",Toast.LENGTH_LONG).show();
                                                             }

                                                         }
                                                     });

                                                 }

                                             }
                                         });


                                     }
                                 });

                                 holder.itemView.findViewById(R.id.Request_cancel).setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v)
                                     {

                                         Toast.makeText(getApplicationContext(),"leave request cancel successfully",Toast.LENGTH_LONG).show();
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
                    public FindsFriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        //inflating layout user_details


                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.request_layout_inflate,parent,false);
                        FindsFriendsViewHolder viewHolder=new FindsFriendsViewHolder(view);
                        return viewHolder;
                    }
                };

        manage_leave_request_RecyclerList.setAdapter(adapter);
        adapter.startListening();




    }

    public static class FindsFriendsViewHolder extends RecyclerView.ViewHolder
    {
        //accessing and assigning id and from user_details layoput
        TextView userName,userStatus,userContact;
        CircleImageView userProfile;
        Button Accept, Cancel;
        public FindsFriendsViewHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.disp_user_name);
            userStatus=itemView.findViewById(R.id.disp_user_status);
            userContact=itemView.findViewById(R.id.disp_user_contact);
            userProfile=itemView.findViewById(R.id.disp_patient_profilee);
            Accept = itemView.findViewById(R.id.Request_accept);
            Cancel = itemView.findViewById(R.id.Request_cancel);
        }
    }
}
