package com.example.doctorappointmentsystem;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class SetDoctorProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Button Set_doctor_profile;
    private TextView doctor_profile_dob;
    private DatePickerDialog.OnDateSetListener setListener;
    private EditText Set_doctor_name, Set_doctor_contact, Set_state, Set_distic,Set_pin, Set_doctor_hospital, Set_doctor_education, Set_doctor_permanent_address,Set_doctor_expereance,Set_doctor_bio;
    private Spinner Set_Doctor_specialist_in;
    private RadioGroup Set_doctor_gender;
    private String SetDoctorGender;
    private CircleImageView Set_doctor_profile_photo;
    private FirebaseAuth mAuth;
    private String currentDoctorId,tag="d";
    private DatabaseReference DoctorRef,Doctor_UPI;
    private ProgressDialog progressDialog;
    private int gallaryPics = 101;
    private StorageReference StorageRef;
    private String downLoadUrlPhoto = "",downLoadUrlPhot;
    private String flag="";
    private Uri resultUri;

    String [] selection=new String[30];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_doctor_profile);
        Set_state=findViewById(R.id.doctor_profile_state_edit);
        Set_distic=findViewById(R.id.doctor_profile_upiId_edit);



        doctor_profile_dob = findViewById(R.id.doctor_profile_dob_edit);
        Set_doctor_gender = findViewById(R.id.doctor_profile_gender);
        Set_doctor_bio=findViewById(R.id.doctor_profile_add_your_Bio_edit);

        StorageRef = FirebaseStorage.getInstance().getReference().child("Profile");
        Doctor_UPI=FirebaseDatabase.getInstance().getReference().child("DoctorUPI");

        Set_doctor_name = findViewById(R.id.doctor_profile_name_edit);
        Set_doctor_contact = findViewById(R.id.doctor_profile_contac_edit);
        Set_doctor_hospital = findViewById(R.id.doctor_profile_hospital_edit);
        Set_doctor_expereance = findViewById(R.id.doctor_profile_expereance_edit);
        Set_doctor_education = findViewById(R.id.doctor_profile_educational_edit);
        Set_doctor_profile_photo = findViewById(R.id.doctor_profile_image_icon);
        Set_Doctor_specialist_in = findViewById(R.id.doctor_profile_Speclist_in_edit);
        Set_doctor_profile = findViewById(R.id.save_doctor_details_button);
        Set_doctor_permanent_address = findViewById(R.id.doctor_profile_your_permanent_address_edit);


        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        currentDoctorId = mAuth.getCurrentUser().getUid();
        DoctorRef = FirebaseDatabase.getInstance().getReference().child("Doctors");


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        doctor_profile_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SetDoctorProfileActivity.this, android.R.style.Theme_Holo_Dialog_MinWidth
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

                doctor_profile_dob.setText(date);

            }
        };


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Select_catogories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Set_Doctor_specialist_in.setAdapter(adapter);
        Set_Doctor_specialist_in.setOnItemSelectedListener(this);







        Set_doctor_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                setDoctorProfile();


            }
        });

        Set_doctor_profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent gallaryIntent = new Intent();
                gallaryIntent.setAction(Intent.ACTION_GET_CONTENT);
                gallaryIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(gallaryIntent, "chose image"), gallaryPics);

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
                Set_doctor_profile_photo.setImageURI(resultUri);
                downLoadUrlPhot="photo_select";
                flag="set";


            }

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



        int j=0;


            selection[j] = adapterView.getItemAtPosition(i).toString();

             j++;




    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void setDoctorProfile() {


        SetDoctorGender = ((RadioButton) findViewById(Set_doctor_gender.getCheckedRadioButtonId())).getText().toString();


        String doctor_name = Set_doctor_name.getText().toString();
        String doctor_exp = Set_doctor_expereance.getText().toString();
        String doctor_contact = Set_doctor_contact.getText().toString();
        String doctor_hospital = Set_doctor_hospital.getText().toString();
        String doctor_eduction = Set_doctor_education.getText().toString();
        String doctor_gender = SetDoctorGender;
        String doctor_dob = doctor_profile_dob.getText().toString();
        String doctor_parmanent_address = Set_doctor_permanent_address.getText().toString();
        String doctor_bio=Set_doctor_bio.getText().toString();
        String doctor_state=Set_state.getText().toString();
        final String doctor_distic=Set_distic.getText().toString();


        if (TextUtils.isEmpty(downLoadUrlPhot)) {
            Toast.makeText(getApplicationContext(), "please set photo click top circle profile icon", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(doctor_name)) {
            Toast.makeText(getApplicationContext(), "please  set name first  ", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(doctor_contact)) {
            Toast.makeText(getApplicationContext(), "please  set contact first  ", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(doctor_dob)) {
            Toast.makeText(getApplicationContext(), "please set your date of birth first", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(doctor_eduction)) {
            Toast.makeText(getApplicationContext(), "please set qualicication first", Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(doctor_exp)) {
            Toast.makeText(getApplicationContext(), "please  select doctor experience first ", Toast.LENGTH_LONG).show();
        }else if (TextUtils.isEmpty(doctor_hospital)) {
            Toast.makeText(getApplicationContext(), "please set your hospital first", Toast.LENGTH_LONG).show();
        } /*else if (TextUtils.isEmpty(selection[1])) {
            Toast.makeText(getApplicationContext(), "please  select doctor specialization first ", Toast.LENGTH_LONG).show();
        } */else if (TextUtils.isEmpty(doctor_parmanent_address)) {
            Toast.makeText(getApplicationContext(), "please set your parmanet address first", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(doctor_bio)) {
            Toast.makeText(getApplicationContext(), "please set doctor bio first", Toast.LENGTH_LONG).show();
        }

        else if (TextUtils.isEmpty(doctor_state)) {
            Toast.makeText(getApplicationContext(), "please set doctor state first", Toast.LENGTH_LONG).show();
        }

        else if (TextUtils.isEmpty(doctor_distic)) {
            Toast.makeText(getApplicationContext(), "please set doctor distic first", Toast.LENGTH_LONG).show();
        }



        else {

            final HashMap<String,String> upiInfo=new HashMap<>();
            upiInfo.put("name",doctor_name);
            upiInfo.put("UPI_ID",doctor_distic);

            final HashMap<String, String> profileMap = new HashMap<>();
            profileMap.put("uid", currentDoctorId);
            profileMap.put("name", doctor_name);
            profileMap.put("contact", doctor_contact);
            profileMap.put("education", doctor_eduction);
           // profileMap.put("image", downLoadUrlPhoto);
            profileMap.put("experence", doctor_exp);
            profileMap.put("dob", doctor_dob);
            profileMap.put("gender", doctor_gender);
            profileMap.put("specilist_in", selection[0]);
            profileMap.put("hospital", doctor_hospital);
            profileMap.put("address", doctor_parmanent_address);
            profileMap.put("Bio", doctor_bio);
            profileMap.put("hospital_address", doctor_state);
            profileMap.put("patient_upiId", doctor_distic);
            //profileMap.put("upiId", "8418856281@ybl");

            progressDialog.setTitle("Setting your profile");
            progressDialog.setMessage("Please wait...");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();


            final StorageReference filepath = StorageRef.child(currentDoctorId + ".jpg");


            filepath.putFile(resultUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downLoadUrlPhoto = uri.toString();


                            DoctorRef.child(currentDoctorId).setValue(profileMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {

                                                DoctorRef.child(currentDoctorId).child("image").setValue(downLoadUrlPhoto).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task)
                                                    {
                                                     if (task.isSuccessful())
                                                     {

                                                         Doctor_UPI.child(currentDoctorId).setValue(upiInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                             @Override
                                                             public void onComplete(@NonNull Task<Void> task)
                                                             {

                                                                 if(task.isSuccessful())
                                                                 {
                                                                     Toast.makeText(getApplicationContext(), "profile Updated successfully...", Toast.LENGTH_SHORT).show();

                                                                     Intent loginType_intent = new Intent(getApplicationContext(), Doctor_ProfileActivity.class);
                                                                     loginType_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                     startActivity(loginType_intent);
                                                                     finish();
                                                                     progressDialog.dismiss();
                                                                 }
                                                             }
                                                         });

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
