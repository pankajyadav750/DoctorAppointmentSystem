package com.example.doctorappointmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class ManageDoctorActivity extends AppCompatActivity {
    private RecyclerView manage_doctor_RecyclerList;
    private FirebaseAuth mAuth;
    private DatabaseReference Doctor_ref;
    private String current_Doctor;
    private Toolbar manage_doctor_profile_toolbar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_doctor);
        progressDialog=new ProgressDialog(this);
        manage_doctor_RecyclerList=findViewById(R.id.doctor_list_Recycler_List);
        manage_doctor_RecyclerList.setLayoutManager(new LinearLayoutManager(this));
        manage_doctor_profile_toolbar=findViewById(R.id.doctor_manage_profile_toolbar);

        mAuth=FirebaseAuth.getInstance();
        //current_Doctor=mAuth.getCurrentUser().getUid();
        Doctor_ref= FirebaseDatabase.getInstance().getReference().child("Doctors");
        manage_doctor_profile_toolbar.setTitle("Manage Doctor");
    }


    @Override
    protected void onStart() {
        super.onStart();

        progressDialog.setTitle("Please wait..");
        progressDialog.setMessage("we are loading doctor...");
        progressDialog.show();

            FirebaseRecyclerOptions<Doctor_List> options = new FirebaseRecyclerOptions.Builder<Doctor_List>()
                    .setQuery(Doctor_ref, Doctor_List.class)
                    .build();
            FirebaseRecyclerAdapter<Doctor_List, FindsFriendsViewHolder> adapter =
                    new FirebaseRecyclerAdapter<Doctor_List, FindsFriendsViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull FindsFriendsViewHolder holder, final int position, @NonNull Doctor_List model) {

                            progressDialog.dismiss();
                            holder.doctorName.setText(model.getDoctor_name());
                            holder.doctorProfession.setText(model.getProfesion());

                            Picasso.get().load(model.getImage()).into(holder.DoctorProfile);

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String visite_user = getRef(position).getKey();
                                    Toast.makeText(getApplicationContext(), "Scroll on text to know more", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), AdminManageDoctorProfileActivity.class);
                                    intent.putExtra("profile_doctor_id", visite_user);
                                    startActivity(intent);
                                }
                            });

                        }

                        @NonNull
                        @Override
                        public FindsFriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            //inflating layout user_details


                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_doctor_layout_for_inflate, parent, false);
                            FindsFriendsViewHolder viewHolder = new FindsFriendsViewHolder(view);
                            return viewHolder;
                        }
                    };
            manage_doctor_RecyclerList.setAdapter(adapter);
            adapter.startListening();
        }

        public static class FindsFriendsViewHolder extends RecyclerView.ViewHolder {
            //accessing and assigning id and from user_details layoput
            TextView doctorName, doctorProfession;
            ImageView DoctorProfile;

            public FindsFriendsViewHolder(@NonNull View itemView) {
                super(itemView);
                doctorName = itemView.findViewById(R.id.manage_doctor_name_infalete);
                doctorProfession = itemView.findViewById(R.id.manage_doctor_profesion_inflate);
                DoctorProfile = itemView.findViewById(R.id.manage_doctor_image_inflate);
            }
        }

    }




