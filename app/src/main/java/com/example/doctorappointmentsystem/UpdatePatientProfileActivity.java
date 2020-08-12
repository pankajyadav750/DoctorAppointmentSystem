package com.example.doctorappointmentsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;

public class UpdatePatientProfileActivity extends AppCompatActivity {

    private CircleImageView patient_imageView;
    private EditText set_patientName,set_patientContact,set_patientGmail,set_patientAddress;
    private TextView set_patientDob;
    private RadioGroup set_patientGender;
    private String Set_patientGender;
    private Button savePateintDetails;
    private DatePickerDialog.OnDateSetListener setListener;
    private int gallaryPics=104;
    private DatabaseReference Patient_REF;
    private StorageReference PatientstorageReference;
    private FirebaseAuth mAuth;
    private String CurrentPatient;
    private String flag="";
    private String pdownLoadUrlPhoto="",pdownLoadUrlPhot;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_patient_profile);

        progressDialog=new ProgressDialog(this);


        getSupportActionBar().setTitle("SetPatientProfile");
        patient_imageView=findViewById(R.id.patient_profile_image_icon);
        set_patientName=findViewById(R.id.set_patient_profile_name_edit);
        set_patientAddress=findViewById(R.id.set_patient_profile_address_edit);
        set_patientContact=findViewById(R.id.set_patient_profile_contact_edit);
        set_patientGmail=findViewById(R.id.set_patient_profile_gmail_edit);
        set_patientGender=findViewById(R.id.choose_patient_profile_gender);
        savePateintDetails=findViewById(R.id.save_patient_details_button);
        set_patientDob=findViewById(R.id.set_patient_profile_dob_edit);

        Patient_REF= FirebaseDatabase.getInstance().getReference().child("Patients");
        PatientstorageReference= FirebaseStorage.getInstance().getReference().child("PatientProfile");
        mAuth=FirebaseAuth.getInstance();
        CurrentPatient=mAuth.getCurrentUser().getUid();


        Patient_REF.child(CurrentPatient).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    String name=dataSnapshot.child("name").getValue().toString();
                    String address=dataSnapshot.child("address").getValue().toString();
                    String contact=dataSnapshot.child("contact").getValue().toString();
                    String gmail=dataSnapshot.child("gmail").getValue().toString();
                    String gender=dataSnapshot.child("gender").getValue().toString();
                    String dob=dataSnapshot.child("dob").getValue().toString();
                    String image=dataSnapshot.child("image").getValue().toString();

                    Picasso.get().load(image).into(patient_imageView);

                    pdownLoadUrlPhot=image;

                     set_patientName.setText(name);
                    set_patientAddress.setText(address);
                    set_patientContact.setText(contact);
                    set_patientGmail.setText(gmail);
                    set_patientDob.setText(dob);

                    if(gender.equals("male"))
                        ((RadioButton)findViewById(R.id.patient_male)).setChecked(true);
                    else
                        ((RadioButton)findViewById(R.id.patient_female)).setChecked(true);



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        patient_imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Intent gallaryIntent = new Intent();
                gallaryIntent.setAction(Intent.ACTION_GET_CONTENT);
                gallaryIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(gallaryIntent, "chose image"), gallaryPics);
            }
        });


        savePateintDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SavePatientDetails();
            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == gallaryPics && resultCode == RESULT_OK && data != null) {
            // Uri Image_uri = data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();
                patient_imageView.setImageURI(resultUri);
                flag="set";
                final StorageReference filepath = PatientstorageReference.child(CurrentPatient + ".jpg");


                filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                pdownLoadUrlPhoto = uri.toString();


                                pdownLoadUrlPhot=pdownLoadUrlPhoto;

                               Patient_REF.child(CurrentPatient).child("image").setValue(pdownLoadUrlPhoto).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task)
                                    {
                                        if (task.isSuccessful())
                                        {
                                            Toast.makeText(getApplicationContext(), "Profile photo upload successfully", Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                });


                            }
                        });


                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {


                                Toast.makeText(getApplicationContext(), "error" + exception.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });

            }

        }
    }



    private void SavePatientDetails() {


        Set_patientGender = ((RadioButton) findViewById(set_patientGender.getCheckedRadioButtonId())).getText().toString();


        String patient_name = set_patientName.getText().toString();

        String patient_contact = set_patientContact.getText().toString();

        String patient_gmail = set_patientGmail.getText().toString();

        String patient_gender = Set_patientGender;

        String patient_dob = set_patientDob.getText().toString();

        String patient_parmanent_address = set_patientAddress.getText().toString();


         if (TextUtils.isEmpty(patient_name)) {
            Toast.makeText(getApplicationContext(), "please  set name first  ", Toast.LENGTH_LONG).show();
        }
         else if (TextUtils.isEmpty(patient_gmail))
         {
             Toast.makeText(getApplicationContext(), "please  set gmail first  ", Toast.LENGTH_LONG).show();
         }
         else if (TextUtils.isEmpty(patient_contact)) {
            Toast.makeText(getApplicationContext(), "please  set contact first  ", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(patient_gmail)) {
            Toast.makeText(getApplicationContext(), "please  select doctor specialization first ", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(patient_dob)) {
            Toast.makeText(getApplicationContext(), "please set your date of birth first", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(patient_parmanent_address)) {
            Toast.makeText(getApplicationContext(), "please set your parmanet address first", Toast.LENGTH_LONG).show();
        } else {

            final HashMap<String, String> profileMap = new HashMap<>();
            profileMap.put("uid", CurrentPatient);
            profileMap.put("name", patient_name);
            profileMap.put("contact", patient_contact);
            profileMap.put("image", pdownLoadUrlPhot);
            profileMap.put("dob", patient_dob);
            profileMap.put("gender", patient_gender);
             profileMap.put("gmail", patient_gmail);
            profileMap.put("address", patient_parmanent_address);
            progressDialog.setTitle("Setting your profile");
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();


            Patient_REF.child(CurrentPatient).setValue(profileMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                progressDialog.dismiss();

                                Toast.makeText(getApplicationContext(), "profile Updated successfully..." + pdownLoadUrlPhoto, Toast.LENGTH_SHORT).show();

                                Intent find_friend_intent = new Intent(getApplicationContext(), MainActivity.class);
                                find_friend_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(find_friend_intent);
                                finish();
                            } else {

                                progressDialog.dismiss();

                                String message = task.getException().toString();

                                Toast.makeText(getApplicationContext(), "error" + message, Toast.LENGTH_SHORT).show();
                            }


                        }
                    });


        }
       }
    }
