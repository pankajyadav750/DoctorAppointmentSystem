package com.example.doctorappointmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MeetDoctorActivity extends AppCompatActivity {

  Button meet_doctor_womens_health,meet_doctor_child_specialist,meet_doctor_dental_care,meet_doctor_ear_nose_throat,meet_doctor_homeopathy,meet_doctor_bone_joints,
          meet_doctor_digestive_issues,meet_doctor_mental_wellness,meet_doctor_physotheropy,meet_doctor_diabetes_management,meet_doctor_brain_nerves,
          meet_doctor_urinary_issues,meet_doctor_kidney_issues,meet_doctor_ayurveda,meet_doctor_lungs_breathing,meet_doctor_heart,meet_doctor_gernal_surgary,
          meet_doctor_cancer,meet_doctor_skin_heair,meet_doctor_general_physician,meet_doctor_sex_specialist,meet_doctor_eye_specialist;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_meet_doctor);
    getSupportActionBar().setTitle("Find & Book");
    meet_doctor_womens_health=findViewById(R.id.meet_doctor_womens_health);
    meet_doctor_child_specialist=findViewById(R.id.meet_doctor_child_specilist_button);
    meet_doctor_dental_care=findViewById(R.id.meet_doctor_dental_care_btn);
    meet_doctor_ear_nose_throat=findViewById(R.id.meet_doctor_ear_nose_throat_button);
    meet_doctor_homeopathy=findViewById(R.id.meet_doctor_homeopathy_btn);
    meet_doctor_bone_joints=findViewById(R.id.meet_doctor_bone_joints_btn);
    meet_doctor_digestive_issues=findViewById(R.id.meet_doctor_digestive_issues_btn);
    meet_doctor_mental_wellness=findViewById(R.id.meet_doctor_mental_wellness_btn);
    meet_doctor_physotheropy=findViewById(R.id.meet_doctor_physiotherapy_btn);
    meet_doctor_diabetes_management=findViewById(R.id.meet_doctor_diabetes_management_btn);
    meet_doctor_brain_nerves=findViewById(R.id.meet_doctor_brain_nerves_btn);
    meet_doctor_urinary_issues=findViewById(R.id.meet_doctor_urinary_issues_btn);
    meet_doctor_kidney_issues=findViewById(R.id.meet_doctor_kidney_issues_btn);
    meet_doctor_ayurveda=findViewById(R.id.meet_doctor_ayurveda_btn);
    meet_doctor_lungs_breathing=findViewById(R.id.meet_doctor_lung_breathing_btn);
    meet_doctor_heart=findViewById(R.id.meet_doctor_heart_btn);
    meet_doctor_gernal_surgary=findViewById(R.id.meet_doctor_general_surgery_btn);
    meet_doctor_cancer=findViewById(R.id.meet_cancer_btn);
    meet_doctor_skin_heair=findViewById(R.id.meet_skin_heir_btn);
    meet_doctor_general_physician=findViewById(R.id.meet_general_physician_btn);
    meet_doctor_sex_specialist=findViewById(R.id.meet_doctor_sex_specialist);
    meet_doctor_eye_specialist=findViewById(R.id.meet_doctor_eye_specialist);

    meet_doctor_womens_health.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="Womens Health";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });
    meet_doctor_child_specialist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="Child Specialist";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });
    meet_doctor_dental_care.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="Dental Care";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });

    meet_doctor_ear_nose_throat.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="Ear Nose Throat";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });

    meet_doctor_homeopathy.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="Homeopathy";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });

    meet_doctor_bone_joints.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="Bone and Joints";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });

    meet_doctor_digestive_issues.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="Digestive Issues";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });

    meet_doctor_mental_wellness.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="Mental Wellness";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });

    meet_doctor_physotheropy.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="Physiotherapy";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });


    meet_doctor_diabetes_management.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="Diabetes Management";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });


    meet_doctor_brain_nerves.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="Brain and Nerves";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });

    meet_doctor_urinary_issues.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="Urinary Issues";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });

    meet_doctor_kidney_issues.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="Kidney Issues";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });

    meet_doctor_ayurveda.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="Ayurveda";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });

    meet_doctor_lungs_breathing.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="Lungs and Breathing";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });

    meet_doctor_heart.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="heart";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });
    meet_doctor_gernal_surgary.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="General Surgery";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });

    meet_doctor_cancer.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="Cancer";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });

    meet_doctor_skin_heair.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="Skin and Heir";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });

    meet_doctor_general_physician.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="General Physician";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });

    meet_doctor_general_physician.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="General Physician";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });
    meet_doctor_general_physician.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="General Physician";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });

    meet_doctor_sex_specialist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="Sex Specialist";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });

    meet_doctor_eye_specialist.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        String womens="Eye Specialist";
        Intent intent=new Intent(getApplicationContext(),Meet_Doctor_ShortList_Activity.class);
        intent.putExtra("speciality",womens);
        startActivity(intent);

      }
    });

        /*
         <item>General Surgery</item>
        <item>Dental Care</item>
        <item>Skin Specialist</item>
        <item>Womens Health</item>
        <item>Ear Nose Throat </item>
        <item>Homeopathy</item>
        <item>Bone and Joints</item>
        <item>Eye Specialist</item>
        <item>Sex Specialist</item>
        <item>Digestive issues</item>
        <item>Mental Wellness</item>
        <item>Physiotherapy</item>
        <item>Diabetes Management</item>
        <item>Brain and Nerves</item>
        <item>Urinary issues</item>
        <item>Kidney issues</item>
        <item>General Surgery</item>
        <item>Lungs and Breathing</item>
        <item>Heart</item>
        <item>Cancer</item>
        <item>Child Specialist</item>
        <item>Ayurveda</item>
        <item>Genral Physician</item>
        <item>Diet and Nutrition</item>

         */





  }
}
