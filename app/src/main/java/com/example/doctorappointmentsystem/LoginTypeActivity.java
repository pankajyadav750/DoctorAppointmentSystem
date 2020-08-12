package com.example.doctorappointmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class LoginTypeActivity extends AppCompatActivity {
    Button doctorLogin,patientLogin,adminLogin;
    private boolean mPermission;
    private int Req_Code=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);
        doctorLogin=findViewById(R.id.doctortypelogin);
        patientLogin=findViewById(R.id.patienttypelogin);
        adminLogin=findViewById(R.id.admintypelogin);

        adminCheckPermisson();




        adminLogin.setEnabled(false);


        doctorLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),DoctorLoginActivity.class);
                startActivity(intent);
            }
        });

        patientLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Patient_Login_Activity.class);
                startActivity(intent);
            }
        });


        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {


            }
        }
        String EmeiNumber = telephonyManager.getDeviceId();

        if (EmeiNumber.equals("867045040057892")) {

            adminLogin.setVisibility(View.VISIBLE);
            adminLogin.setEnabled(true);

            adminLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(getApplicationContext(),AdminLoginActivity.class);
                    startActivity(intent);
                }
            });

        }



    }

    private void adminCheckPermisson()
    {
        if(!mPermission)
        {
            requestPermission();
            return;
        }
    }

    private void requestPermission()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},Req_Code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            mPermission=true;

        }


    }
}
