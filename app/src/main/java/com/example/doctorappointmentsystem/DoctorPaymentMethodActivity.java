package com.example.doctorappointmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

public class DoctorPaymentMethodActivity extends AppCompatActivity {
  private String DoctorId;
  private TextView doctar_name,Doctor_UpI_id,Doctor_booking_amount,doctor_payment_note;
  private CircleImageView Doctor_profile_pic;
  private DatabaseReference DoctorReff,Confirm_booking,Payment_Methode_REF, AdminUpiId_REF,Payment_report_REF,Patient_ref;
  private String current_patient;
  private FirebaseAuth mAuth;
  private Toolbar ttoolbar;
  private Button payment;
  private String TAG ="main";
  private final int UPI_PAYMENT = 0;
  private  int amount=1;
  private  DatabaseReference PaymentDone_REF;
  private  String Doctor_name,doctor_photo,patient_name,Patient_profile;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_doctor_payment_method);
    doctor_payment_note=findViewById(R.id.doctor_payment_method_profile_message);
    doctar_name=findViewById(R.id.doctor_payment_method_profile_name);
    Doctor_UpI_id=findViewById(R.id.doctor_payment_method_profile_UpiId);
    Doctor_booking_amount=findViewById(R.id.doctor_payment_method_profile_amount);
    Doctor_profile_pic=findViewById(R.id.doctor_payment_method_profile_image);
    ttoolbar=findViewById(R.id.doctor_payment_method_profile_toolbar);
    payment=findViewById(R.id.doctor_payment_method_pay_done);
    Payment_report_REF=FirebaseDatabase.getInstance().getReference().child("PaymentReport");
    mAuth=FirebaseAuth.getInstance();
    current_patient=mAuth.getCurrentUser().getUid();
    AdminUpiId_REF = FirebaseDatabase.getInstance().getReference().child("AdminUpiId");
    Payment_Methode_REF=FirebaseDatabase.getInstance().getReference().child("BookingPaymentDone");
    Patient_ref=FirebaseDatabase.getInstance().getReference().child("Patients");
    DoctorReff= FirebaseDatabase.getInstance().getReference().child("Doctors");
    Confirm_booking=FirebaseDatabase.getInstance().getReference().child("ConfirmBookingList");
    PaymentDone_REF=FirebaseDatabase.getInstance().getReference().child("ConfirmPayment");
    ttoolbar.setTitle("UPI PAYMENT GATEWAY");
    Doctor_booking_amount.setText(String.valueOf(amount));

    DoctorId = getIntent().getExtras().get("DoctorIdes").toString();

    DoctorReff.child(DoctorId).addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot)
      {
        if (dataSnapshot.exists())
        {

          AdminUpiId_REF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
              String adminUpi=dataSnapshot.child("AdminUPIID").getValue().toString();
              Doctor_UpI_id.setText(adminUpi);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
          });
          Doctor_name = dataSnapshot.child("name").getValue().toString();
          doctor_photo = dataSnapshot.child("image").getValue().toString();
          doctar_name.setText(Doctor_name);
          Picasso.get().load(doctor_photo).into(Doctor_profile_pic);
          doctor_payment_note.setText("Booking Payment to Mr. "+Doctor_name);




        }
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError)
      {

      }
    });

    Patient_ref.child(current_patient).addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(@NonNull DataSnapshot dataSnapshot)
      {
        if (dataSnapshot.exists())
        {
          patient_name=dataSnapshot.child("name").getValue().toString();
          Patient_profile=dataSnapshot.child("image").getValue().toString();


        }

      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });

       /* Confirm_booking.child(DoctorId).child(current_patient).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {

                    String bookingCharge=dataSnapshot.child("bookingCharge").getValue().toString();
                    Doctor_booking_amount.setText(bookingCharge);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        */
    payment.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view)
      {

        if (TextUtils.isEmpty( doctar_name.getText().toString().trim())){
          Toast.makeText(getApplicationContext()," Name is invalid", Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(Doctor_UpI_id.getText().toString().trim())){
          Toast.makeText(getApplicationContext()," UPI ID is invalid", Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(doctor_payment_note.getText().toString().trim())){
          Toast.makeText(getApplicationContext()," Note is invalid", Toast.LENGTH_SHORT).show();

        }else if (TextUtils.isEmpty(Doctor_booking_amount.getText().toString().trim())){
          Toast.makeText(getApplicationContext()," Amount is invalid", Toast.LENGTH_SHORT).show();
        }else{

          payUsingUpi(doctar_name.getText().toString(), Doctor_UpI_id.getText().toString(),
                  doctor_payment_note.getText().toString(), Doctor_booking_amount.getText().toString());

        }

      }
    });
  }



  void payUsingUpi(  String name1,String upiId1, String note1, String amount1) {

    String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    Log.e("main ", "name "+name1 +"--up--"+upiId1+"--"+ note1+"--"+amount1);
    // main: name pavan n--up--pavan.n.sap@okaxis--Test UPI Payment--5.00


    Uri uri = Uri.parse("upi://pay").buildUpon()
            .appendQueryParameter("pa", upiId1)
            .appendQueryParameter("pn", name1)
            //.appendQueryParameter("mc", "")
            //.appendQueryParameter("tid", "02125412")
            //.appendQueryParameter("tr", "25584584")
            .appendQueryParameter("tn", note1)
            .appendQueryParameter("am", amount1)
            .appendQueryParameter("cu", "INR")
            //.appendQueryParameter("refUrl", "blueapp")
            .build();


    //    Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
    //  upiPayIntent.setData(uri);

    // will always show a dialog to user to choose an app
    //  Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(uri);
    intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);


    // check if intent resolves
    if(null != intent.resolveActivity(getPackageManager())) {
      startActivityForResult(intent, UPI_PAYMENT);
    } else {
      Toast.makeText(getApplicationContext(),"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
    }

  }



  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data)
  {
    super.onActivityResult(requestCode, resultCode, data);
    Log.e("main ", "response "+resultCode );
        /*
       E/main: response -1
       E/UPI: onActivityResult: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPIPAY: upiPaymentDataOperation: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPI: payment successfull: 922118921612
         */


    switch (requestCode) {
      case UPI_PAYMENT:
        if ((RESULT_OK == resultCode) || (resultCode == 11)) {
          if (data != null) {
            String trxt = data.getStringExtra("response");
            Log.e("UPI", "onActivityResult: " + trxt);
            ArrayList<String> dataList = new ArrayList<>();
            dataList.add(trxt);
            upiPaymentDataOperation(dataList);
          } else {
            Log.e("UPI", "onActivityResult: " + "Return data is null");
            ArrayList<String> dataList = new ArrayList<>();
            dataList.add("nothing");
            upiPaymentDataOperation(dataList);
          }
        } else {
          //when user simply back without payment
          Log.e("UPI", "onActivityResult: " + "Return data is null");
          ArrayList<String> dataList = new ArrayList<>();
          dataList.add("nothing");
          upiPaymentDataOperation(dataList);
        }
        break;
    }
  }

  private void upiPaymentDataOperation(ArrayList<String> data) {
    if (isConnectionAvailable(DoctorPaymentMethodActivity.this)) {
      String str = data.get(0);
      Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
      String paymentCancel = "";
      if(str == null) str = "discard";
      String status = "";
      String approvalRefNo = "";
      String response[] = str.split("&");
      for (int i = 0; i < response.length; i++) {
        String equalStr[] = response[i].split("=");
        if(equalStr.length >= 2) {
          if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
            status = equalStr[1].toLowerCase();
          }
          else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
            approvalRefNo = equalStr[1];
          }
        }
        else {
          paymentCancel = "Payment cancelled by user.";
        }
      }

      if (status.equals("success")) {
        //Code to handle successful transaction here.

        PaymentDone_REF.child(DoctorId).child(current_patient).child("PaymentStatus").setValue("paymentDone").addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task)
          {
            if(task.isSuccessful())
            {
              PaymentDone_REF.child(current_patient).child(DoctorId).child("PaymentStatus").setValue("paymentDone").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task)
                {
                  if(task.isSuccessful())
                  {

                    final String date1;
                   final String pdatee,pTimee;




                    final Calendar calendar = Calendar.getInstance();
                    final int year = calendar.get(Calendar.YEAR);
                    final int month = calendar.get(Calendar.MONTH);
                    final int day = calendar.get(Calendar.DAY_OF_MONTH);
                    final int hour = calendar.get(Calendar.HOUR);
                    final int minut = calendar.get(Calendar.MINUTE);
                    final int second = calendar.get(Calendar.SECOND);

                    SimpleDateFormat currentDate=new SimpleDateFormat("hh:mm a");

                    pTimee=currentDate.format(calendar.getTime());

                    String ampm="AM";



                    if(hour==12 && minut>=1 && second>=1)
                    {
                      if (ampm.equals("AM"))
                      {
                        ampm="PM";
                      }
                      else
                      {
                        ampm="AM";
                      }
                    }



                    int month1=month;
                    month1++;

                    date1=day+" / "+month1+" / "+year;
                    final String date=hour+" - "+minut+" - "+second+" "+ampm+"-"+day+" - "+month1+" - "+year;


                    final HashMap<String, String> paymentDetails_doctor=new HashMap<>();

                    paymentDetails_doctor.put("status","paymentDone");
                    paymentDetails_doctor.put("date",pTimee+" / "+date1);
                    paymentDetails_doctor.put("doctor_name",Doctor_name);
                    paymentDetails_doctor.put("doctor_profile",doctor_photo);


                    final HashMap<String, String> paymentDetails_patient=new HashMap<>();

                    paymentDetails_patient.put("status","paymentDone");
                    paymentDetails_patient.put("date",pTimee+" / "+date1);
                    paymentDetails_patient.put("patient_name",patient_name);
                    paymentDetails_patient.put("patient_profile",Patient_profile);




                    Payment_report_REF.child(current_patient).child(date).child(DoctorId).setValue(paymentDetails_doctor).addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task)
                      {
                        if(task.isSuccessful())
                        {
                          Payment_report_REF.child(DoctorId).child(date).child(current_patient).setValue(paymentDetails_patient).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                              if(task.isSuccessful())
                              {
                                Toast.makeText(DoctorPaymentMethodActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                                onBackPressed();

                              }

                            }
                          });

                        }

                      }
                    });




                  }

                }
              });

            }

          }
        });

        Log.e("UPI", "payment successfull: "+approvalRefNo);
      }
      else if("Payment cancelled by user.".equals(paymentCancel)) {
        Toast.makeText(DoctorPaymentMethodActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
        Log.e("UPI", "Cancelled by user: "+approvalRefNo);

      }
      else {
        Toast.makeText(DoctorPaymentMethodActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
        Log.e("UPI", "failed payment: "+approvalRefNo);

      }
    } else {
      Log.e("UPI", "Internet issue: ");

      Toast.makeText(DoctorPaymentMethodActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
    }
  }






  public static boolean isConnectionAvailable(Context context) {
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivityManager != null) {
      NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
      if (netInfo != null && netInfo.isConnected()
              && netInfo.isConnectedOrConnecting()
              && netInfo.isAvailable()) {
        return true;
      }
    }
    return false;
  }


  @Override
  public void onBackPressed() {
    super.onBackPressed();


    Intent intent =new Intent(getApplicationContext(),PatientViewConfirmBookingActivity.class);
    startActivity(intent);
  }
}
