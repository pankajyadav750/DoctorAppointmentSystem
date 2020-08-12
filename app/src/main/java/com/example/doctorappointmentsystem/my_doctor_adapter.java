package com.example.doctorappointmentsystem;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class my_doctor_adapter extends RecyclerView.Adapter<my_doctor_adapter.MyViewHolder> {

    private List<Doctor_List> mList = new ArrayList<Doctor_List>();
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private Context mContext;
    public my_doctor_adapter(List<Doctor_List> list, Context context){
        mList = list;
        mContext = context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.manage_doctor_layout_for_inflate,parent,false);

        MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;

        //return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {



        holder.doctorName.setText(mList.get(position).getDoctor_name());
        holder.doctorProfession.setText(mList.get(position).getProfesion());
        Picasso.get().load(mList.get(position).getImage()).into(holder.DoctorProfile);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String visite_doctor=mList.get(position).getUid();
                Toast.makeText(mContext,"Please Scroll on Text for more",Toast.LENGTH_SHORT).show();
                Intent select_doctor_intent = new Intent(mContext.getApplicationContext(), PatientMeetDoctorProfileActivity.class);
                select_doctor_intent.putExtra("profile_doctor_id", visite_doctor);
                mContext.startActivity(select_doctor_intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView doctorName,doctorProfession;
        ImageView DoctorProfile;
        public MyViewHolder(View itemView) {
            super(itemView);
            doctorName=itemView.findViewById(R.id.manage_doctor_name_infalete);
            doctorProfession=itemView.findViewById(R.id.manage_doctor_profesion_inflate);
            DoctorProfile= itemView.findViewById(R.id.manage_doctor_image_inflate);
        }
    }
}


