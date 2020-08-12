package com.example.doctorappointmentsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;
import java.util.HashMap;

public class Set_Pateint_profile_Activity extends AppCompatActivity {
    private CircleImageView patient_imageView;
    private EditText set_patientName,set_patientContact,set_patientGmail,set_patientAddress;
    private TextView set_patientDob;
    private RadioGroup set_patientGender;
    private String Set_patientGender;
    private Button savePateintDetails;
    private DatePickerDialog.OnDateSetListener setListener;
    private int gallaryPics=104;
    private DatabaseReference Patient_REF,securityQuestion_REF;
    private StorageReference PatientstorageReference;
    private FirebaseAuth mAuth;
    private String CurrentPatient;
    private String flag="";
    private String pdownLoadUrlPhoto="";
    private ProgressDialog progressDialog;
    private  Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set__pateint_profile_);

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
        securityQuestion_REF=FirebaseDatabase.getInstance().getReference().child("SecurityQuestion");

      mAuth=FirebaseAuth.getInstance();
      CurrentPatient=mAuth.getCurrentUser().getUid();



        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        set_patientDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Set_Pateint_profile_Activity.this, android.R.style.Theme_Holo_Dialog_MinWidth
                        , setListener, year, month, day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month = month + 1;
                String date = day + "/" + month + "/" + year;

                set_patientDob.setText(date);

            }
        };



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

                 resultUri = result.getUri();
                patient_imageView.setImageURI(resultUri);
                flag="set";
                pdownLoadUrlPhoto="add_photo";

            }

        }
    }



    private void SavePatientDetails()
    {


        Set_patientGender = ((RadioButton) findViewById(set_patientGender.getCheckedRadioButtonId())).getText().toString();


        String patient_name = set_patientName.getText().toString();

        String patient_contact =set_patientContact.getText().toString();

        String patient_gmail = set_patientGmail.getText().toString();

        String patient_gender = Set_patientGender;

        String patient_dob = set_patientDob.getText().toString();

        String patient_parmanent_address = set_patientAddress.getText().toString();





        if (TextUtils.isEmpty(pdownLoadUrlPhoto)) {
            Toast.makeText(getApplicationContext(), "please set photo click top circle profile icon", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(patient_name)) {
            Toast.makeText(getApplicationContext(), "please  set name first  ", Toast.LENGTH_LONG).show();
        }

        else if(TextUtils.isEmpty(patient_gmail))
        {
            Toast.makeText(getApplicationContext(), "please  set gmail first  ", Toast.LENGTH_LONG).show();

        }
        else if (TextUtils.isEmpty(patient_contact)) {
            Toast.makeText(getApplicationContext(), "please  set contact first  ", Toast.LENGTH_LONG).show();
        }

        else if (TextUtils.isEmpty(patient_gmail)) {
            Toast.makeText(getApplicationContext(), "please  select doctor specialization first ", Toast.LENGTH_LONG).show();
        }

        else if (TextUtils.isEmpty(patient_dob)) {
            Toast.makeText(getApplicationContext(), "please set your date of birth first", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(patient_parmanent_address)) {
            Toast.makeText(getApplicationContext(), "please set your parmanet address first", Toast.LENGTH_LONG).show();
        }





        else {

            final HashMap<String, String> profileMap = new HashMap<>();
            profileMap.put("uid",CurrentPatient);
            profileMap.put("name", patient_name);
            profileMap.put("contact",patient_contact);
            profileMap.put("dob", patient_dob);
            profileMap.put("gender",patient_gender);
            profileMap.put("gmail",patient_gmail);
            profileMap.put("address", patient_parmanent_address);

            progressDialog.setTitle("Setting your profile");
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();




            final StorageReference filepath = PatientstorageReference.child(CurrentPatient + ".jpg");


            filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri)
                        {
                            pdownLoadUrlPhoto = uri.toString();


                            Patient_REF.child(CurrentPatient).setValue(profileMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                                Patient_REF.child(CurrentPatient).child("image").setValue(pdownLoadUrlPhoto).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful())
                                                        {
                                                            Toast.makeText(getApplicationContext(), "profile Updated successfully..."+pdownLoadUrlPhoto, Toast.LENGTH_SHORT).show();
                                                            Intent find_friend_intent = new Intent(getApplicationContext(), MainActivity.class);
                                                            find_friend_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                            startActivity(find_friend_intent);
                                                            finish();
                                                            progressDialog.dismiss();

                                                        }
                                                    }
                                                });


                                            } else {

                                                progressDialog.dismiss();

                                                String message = task.getException().toString();

                                                Toast.makeText(getApplicationContext(), "error" + message, Toast.LENGTH_SHORT).show();
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
