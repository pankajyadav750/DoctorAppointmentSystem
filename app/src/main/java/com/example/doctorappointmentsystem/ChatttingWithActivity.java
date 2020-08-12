package com.example.doctorappointmentsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.widget.Toolbar;

public class ChatttingWithActivity extends AppCompatActivity {

    private  String MessageRecieveId,MessageReciverName,MessageRecievedImage,last_seen,state;
    private TextView userName,userLastSeen;
    private CircleImageView userImage;
    private Toolbar chat_toolbar;
    private ImageButton SendMessageButton;
    private ScrollView myScrollview;
    private EditText MessageInputText;
    private FirebaseAuth mAuth;
    private String messageSenderID;
    private DatabaseReference RootRef;
    private final List<Messages> messagesList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private MessageAdapter messageAdapter;
    private RecyclerView MessagesList;
    private DatabaseReference Patient_REF,Doctor_REF,DoctorState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chattting_with);


        MessageRecieveId=getIntent().getExtras().get("visit_patient_id").toString();
        MessageReciverName=getIntent().getExtras().get("visit_patient_name").toString();
        MessageRecievedImage=getIntent().getExtras().get("visit_patient_image").toString();

        Patient_REF=FirebaseDatabase.getInstance().getReference().child("Patients");
        Doctor_REF=FirebaseDatabase.getInstance().getReference().child("Doctors");
        DoctorState=FirebaseDatabase.getInstance().getReference().child("DoctorState");

        // last_seen=getIntent().getExtras().get("last_seen").toString();
        // state=getIntent().getExtras().get("state").toString();

        MessageInputText=findViewById(R.id.input_group_message);

        initiliazeControlls();
        userName.setText(MessageReciverName);
        Picasso.get().load(MessageRecievedImage).into(userImage);

        displayLastSeen();




        SendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SendMessage();
            }
        });





        Toast.makeText(getApplicationContext(),"name= "+MessageReciverName+"\n"+"id: "+MessageRecieveId,Toast.LENGTH_LONG ).show();
    }




    private void initiliazeControlls()
    {

        chat_toolbar = (Toolbar) findViewById(R.id.manage_chating_profile_toolbar);
        setSupportActionBar(chat_toolbar);
        SendMessageButton=findViewById(R.id.send_message);
        mAuth=FirebaseAuth.getInstance();
        messageSenderID=mAuth.getCurrentUser().getUid();
        RootRef= FirebaseDatabase.getInstance().getReference().child("Messages");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View actionBarView = layoutInflater.inflate(R.layout.custom_chat_bar, null);
        actionBar.setCustomView(actionBarView);

        userName = (TextView) findViewById(R.id.custom_chat_name);
        userLastSeen = (TextView) findViewById(R.id.custom_chat_last_seen);
        userImage = (CircleImageView) findViewById(R.id.custom_chat_profile_image);

        messageAdapter = new MessageAdapter(messagesList);
        MessagesList = (RecyclerView) findViewById(R.id.private_messages_list_of_doctor_patient);
        linearLayoutManager = new LinearLayoutManager(this);
        MessagesList.setLayoutManager(linearLayoutManager);
        MessagesList.setAdapter(messageAdapter);



    }

    private void displayLastSeen()
    {

        DoctorState.child(MessageRecieveId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    if(dataSnapshot.hasChild("Doctor_State"))
                    {
                        String date=dataSnapshot.child("Doctor_State").child("date").getValue().toString();
                        String state=dataSnapshot.child("Doctor_State").child("state").getValue().toString();
                        String time=dataSnapshot.child("Doctor_State").child("time").getValue().toString();

                        if(state.equals("Online"))
                        {
                            userLastSeen.setText("Online");
                        }
                        else {
                            userLastSeen.setText("last seen"+"\n"+time+" / "+date);
                        }
                    }
                }

                else
                {

                    Patient_REF.child(MessageRecieveId).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            if(dataSnapshot.hasChild("Patient_State"))
                            {
                                String date=dataSnapshot.child("Patient_State").child("date").getValue().toString();
                                String state=dataSnapshot.child("Patient_State").child("state").getValue().toString();
                                String time=dataSnapshot.child("Patient_State").child("time").getValue().toString();

                                if(state.equals("Online"))
                                {
                                    userLastSeen.setText("Online");

                                }
                                else {
                                    userLastSeen.setText("last seen"+"\n"+time+" / "+date);
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();

        DoctorState.child(messageSenderID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                    if (dataSnapshot.hasChild("Doctor_State"))
                    {
                        //calling function
                        updateDoctorState("Online");
                    }


                }
                else
                {
                    Patient_REF.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                        {
                            if(dataSnapshot.hasChild("Patient_State"))
                            {
                                updatePatieintState("Online");

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        RootRef.child("Messages").child(messageSenderID).child(MessageRecieveId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
            {
                Messages messages = dataSnapshot.getValue(Messages.class);

                messagesList.add(messages);
                messageAdapter.notifyDataSetChanged();
                MessagesList.smoothScrollToPosition(MessagesList.getAdapter().getItemCount());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void SendMessage()
    {
        String messageText = MessageInputText.getText().toString();

        if (TextUtils.isEmpty(messageText))
        {
            Toast.makeText(this, "first write your message...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String messageSenderRef = "Messages/" + messageSenderID + "/" + MessageRecieveId;
            String messageReceiverRef = "Messages/" + MessageRecieveId + "/" + messageSenderID;

            DatabaseReference userMessageKeyRef = RootRef.child(messageSenderID).child(MessageRecieveId).push();

            String messagePushID = userMessageKeyRef.getKey();

            Map messageTextBody = new HashMap();
            messageTextBody.put("message", messageText);
            messageTextBody.put("type", "text");
            messageTextBody.put("from", messageSenderID);
            // messageTextBody.put("to", MessageRecieveId);
            //  messageTextBody.put("messageID", messagePushID);
            //  messageTextBody.put("time", saveCurrentTime);
            // messageTextBody.put("date", saveCurrentDate);

            Map messageBodyDetails = new HashMap();
            messageBodyDetails.put(messageSenderRef + "/" + messagePushID, messageTextBody);
            messageBodyDetails.put( messageReceiverRef + "/" + messagePushID, messageTextBody);

            RootRef.updateChildren(messageBodyDetails).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task)
                {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(getApplicationContext(), "Message Sent Successfully...", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                    MessageInputText.setText("");

                }
            });
        }
    }
    private  void updatePatieintState(String state)
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

        Patient_REF.child(messageSenderID).child("Patient_State").updateChildren(onlineStateMap);
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

        DoctorState.child(messageSenderID).child("Doctor_State").updateChildren(onlineStateMap);
    }
}
