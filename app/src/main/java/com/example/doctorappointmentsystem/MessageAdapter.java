package com.example.doctorappointmentsystem;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import android.view.View;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>
{
    private List<Messages> userMessagesList;
    private FirebaseAuth mAuth;
    private DatabaseReference usersRef,Doctor_REF;

    public MessageAdapter (List<Messages> userMessagesList)
    {
        this.userMessagesList = userMessagesList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_message_layout, parent, false);

        mAuth = FirebaseAuth.getInstance();

        return new MessageViewHolder(view);

    }


    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder holder, int position)
    {

        String messageSenderId = mAuth.getCurrentUser().getUid();
        Messages messages = userMessagesList.get(position);


        final String fromUserID = messages.getFrom();
        String fromMessageType = messages.getType();

      /* holder.receiverMessageText.setVisibility(View.GONE);
        holder.receiverProfileImage.setVisibility(View.GONE);
        holder.senderMessageText.setVisibility(View.GONE);
        holder.messageSenderPicture.setVisibility(View.GONE);
        holder.messageReceiverPicture.setVisibility(View.GONE);

       */


        if (fromMessageType.equals("text"))
        {
            holder.receiverMessageText.setVisibility(View.INVISIBLE);
            holder.receiverProfileImage.setVisibility(View.INVISIBLE);
            holder.senderMessageText.setVisibility(View.INVISIBLE);

            if (fromUserID.equals(messageSenderId))
            {
                holder.senderMessageText.setVisibility(View.VISIBLE);

                holder.senderMessageText.setBackgroundResource(R.drawable.sender_message_layout);
                holder.senderMessageText.setTextColor(Color.BLACK);
                holder.senderMessageText.setText(messages.getMessage());
              //  holder.senderMessageText.setText(messages.getMessage() + "\n \n" + messages.getTime() + " - " + messages.getDate());
            }
            else
            {
                usersRef = FirebaseDatabase.getInstance().getReference().child("Patients");
                Doctor_REF = FirebaseDatabase.getInstance().getReference().child("Doctors");

                usersRef.child(fromUserID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.hasChild("image"))
                        {
                            String receiverImage = dataSnapshot.child("image").getValue().toString();

                            Picasso.get().load(receiverImage).placeholder(R.drawable.profile).into(holder.receiverProfileImage);
                        }
                        else {

                            Doctor_REF.child(fromUserID).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                {
                                    if(dataSnapshot.exists()) {
                                        String receiverImage = dataSnapshot.child("image").getValue().toString();

                                        Picasso.get().load(receiverImage).placeholder(R.drawable.profile).into(holder.receiverProfileImage);
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                holder.receiverProfileImage.setVisibility(View.VISIBLE);
                holder.receiverMessageText.setVisibility(View.VISIBLE);

                holder.receiverMessageText.setBackgroundResource(R.drawable.reciever_message_layout);
                holder.receiverMessageText.setTextColor(Color.BLACK);
                holder.receiverMessageText.setText(messages.getMessage());
               // holder.receiverMessageText.setText(messages.getMessage() + "\n \n" + messages.getTime() + " - " + messages.getDate());
            }
        }

    }


    @Override
    public int getItemCount() {
        return userMessagesList.size();
    }


    public class MessageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView senderMessageText, receiverMessageText;
        public CircleImageView receiverProfileImage;
        public ImageView messageSenderPicture, messageReceiverPicture;


        public MessageViewHolder(@NonNull View itemView)
        {
            super(itemView);

            senderMessageText = (TextView) itemView.findViewById(R.id.sender_messsage_text);
            receiverMessageText = (TextView) itemView.findViewById(R.id.receiver_message_text);
            receiverProfileImage = (CircleImageView) itemView.findViewById(R.id.message_profile_image);
           // messageReceiverPicture = itemView.findViewById(R.id.message_receiver_image_view);
           // messageSenderPicture = itemView.findViewById(R.id.message_sender_image_view);
        }
    }





}
