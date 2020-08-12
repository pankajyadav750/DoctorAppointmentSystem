package com.example.doctorappointmentsystem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Doctor_LiveActivity extends AppCompatActivity {
    private TextView startDate,endDate;
    private Button confirmLive;
    private int startmMonth,startDay,startYear,endDayy,endMonthh;
    private FirebaseAuth mAuth;
    private String current_doctor;
    private DatabaseReference docotrLeave_REF,Doctor_REF, ConfirmLeaveRequest_Ref;
    private String leaveState="new";
    private ProgressDialog progressDialog;
    private String name,photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor__live);

        startDate=findViewById(R.id.doctor_profile_leave_start_edit);
        endDate=findViewById(R.id.doctor_profile_leave_end_edit);
        confirmLive=findViewById(R.id.live_confirm_button);
        mAuth=FirebaseAuth.getInstance();
        current_doctor=mAuth.getCurrentUser().getUid();
        progressDialog=new ProgressDialog(this);

         docotrLeave_REF=FirebaseDatabase.getInstance().getReference().child("DoctorLeaveRequest");
        ConfirmLeaveRequest_Ref= FirebaseDatabase.getInstance().getReference().child("DoctorConfirmLeaveRequest");
        Doctor_REF=FirebaseDatabase.getInstance().getReference().child("Doctors");


        ConfirmLeaveRequest_Ref.child(current_doctor).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.hasChild("From"))
                {
                        leaveState="sent";
                        confirmLive.setText("Cancel Leave");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        startDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {

                final Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);


                final int day2 = calendar.get(Calendar.DAY_OF_MONTH);
                final int month2 = calendar.get(Calendar.MONTH);
                final int year2 = calendar.get(Calendar.YEAR);

                final DatePickerDialog datePickerDialog=new DatePickerDialog(Doctor_LiveActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {




                        Calendar c=Calendar.getInstance();

                        c.set(Calendar.MONTH,month);
                        c.set(Calendar.YEAR,year);
                        c.set(Calendar.DAY_OF_MONTH,day);

                        confirmLive.setEnabled(true);


                        month++;
                        int month12=month2;
                        month12++;

                        String date=DateFormat.getDateInstance().format(c.getTime());
                        startDay=day;
                        startmMonth=month;
                        startYear=year;
                        String date1=day+" / "+month+" / "+year;




                        if(year==year2)
                        {
                            if (month==month12)
                            {

                                if(day==day2)
                                {
                                    Toast.makeText(getApplicationContext(),"leave is not available today."+"\n"+"try to choose three days after from current date..",Toast.LENGTH_LONG).show();
                                    confirmLive.setEnabled(false);

                                }

                                else
                                {
                                    if(day>day2)
                                    {

                                        int advanced_day=day2+3;

                                        if(day>advanced_day)
                                        {
                                            Toast.makeText(getApplicationContext(),"you can take leave ",Toast.LENGTH_LONG).show();


                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(),"leave is not available."+"\n"+"try to choose three days after from current date..",Toast.LENGTH_LONG).show();
                                            confirmLive.setEnabled(false);

                                        }

                                    }

                                    else  {
                                        Toast.makeText(getApplicationContext(),"you can not take leave in past",Toast.LENGTH_LONG).show();
                                        confirmLive.setEnabled(false);
                                    }


                                }


                            }
                            else
                            {
                                if(month>month12)
                                {
                                    if(day2==30)
                                    {
                                        int advanced=0;
                                        int advancede=advanced+3;

                                        if(day>advancede)
                                        {
                                            Toast.makeText(getApplicationContext(),"you can take leave",Toast.LENGTH_LONG).show();

                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(),"leave is not available today."+"\n"+"try to choose three days after from current date..",Toast.LENGTH_LONG).show();
                                            confirmLive.setEnabled(false);
                                        }


                                    }
                                    else if (day2==31)
                                    {
                                        int advanced=1;
                                        int advancede=advanced+3;

                                        if(day>advancede)
                                        {
                                            Toast.makeText(getApplicationContext(),"you can take leave",Toast.LENGTH_LONG).show();

                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(),"leave is not available today."+"\n"+"try to choose three days after from current date..",Toast.LENGTH_LONG).show();
                                            confirmLive.setEnabled(false);
                                        }

                                    }


                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"leave is not available today."+"\n"+"try to choose three days after from current date..",Toast.LENGTH_LONG).show();
                                    confirmLive.setEnabled(false);
                                }

                            }

                        }
                        else
                        {
                            if(year>year2)
                            {
                                if(month12==12)
                                {
                                    if (day2==31)
                                    {
                                        int advanced1=1;
                                        int advancede1=advanced1+3;

                                        if(day>advancede1)
                                        {
                                            Toast.makeText(getApplicationContext(),"you can take leave",Toast.LENGTH_LONG).show();

                                        }
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(),"leave is not available today."+"\n"+"try to choose three days after from current date..",Toast.LENGTH_LONG).show();
                                            confirmLive.setEnabled(false);
                                        }
                                    }

                                }

                                else
                                {
                                    Toast.makeText(getApplicationContext(),"leave is not available one year advanced"+"\n"+"try to choose three days after from current date..",Toast.LENGTH_LONG).show();
                                    confirmLive.setEnabled(false);


                                }

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"leave is not available today."+"\n"+"try to choose three days after from current date..",Toast.LENGTH_LONG).show();
                                confirmLive.setEnabled(false);

                            }

                        }

                        startDate.setText(date1);



                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(startDate.getText().toString())) {

                    Toast.makeText(getApplicationContext(),"starting date is empty",Toast.LENGTH_LONG).show();

                } else {

                    final Calendar calendar = Calendar.getInstance();
                    final int endYear = calendar.get(Calendar.YEAR);
                    final int endMonth = calendar.get(Calendar.MONTH);
                    final int endDay = calendar.get(Calendar.DAY_OF_MONTH);


                    final int endDay2 = calendar.get(Calendar.DAY_OF_MONTH);
                    final int endMonth2 = calendar.get(Calendar.MONTH);
                    final int endYear2 = calendar.get(Calendar.YEAR);

                    final DatePickerDialog datePickerDialog = new DatePickerDialog(Doctor_LiveActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int endYear, int endMonth, int endDay) {


                            Calendar c = Calendar.getInstance();

                            c.set(Calendar.MONTH, endMonth);
                            c.set(Calendar.YEAR, endYear);
                            c.set(Calendar.DAY_OF_MONTH, endDay);

                            confirmLive.setEnabled(true);


                            endMonth++;
                            int month12 = endMonth2;
                            month12++;



                            String date = DateFormat.getDateInstance().format(c.getTime());
                            endDayy=endDay;
                            endMonthh=endMonth;
                            String date1 = endDay + " / " + endMonth + " / " + endYear;


                            if (endYear == startYear) {
                                if (endMonth == startmMonth) {

                                    if (startDay == endDay) {
                                        Toast.makeText(getApplicationContext(), "starting day and end date not shuld be same." + "\n" + "try to choose three days after from current date..", Toast.LENGTH_LONG).show();
                                        confirmLive.setEnabled(false);

                                    } else {
                                        if (endDay > startDay) {

                                            int advanced_day1 = startDay + 5;

                                            if (advanced_day1 > endDay) {
                                                Toast.makeText(getApplicationContext(), "you can take leave ", Toast.LENGTH_LONG).show();


                                            } else {
                                                Toast.makeText(getApplicationContext(), "leave is not available." + "\n" + "try to choose under 5 days from yor starting leave date..", Toast.LENGTH_LONG).show();
                                                confirmLive.setEnabled(false);

                                            }

                                        } else {
                                            Toast.makeText(getApplicationContext(), "you can not take leave in past", Toast.LENGTH_LONG).show();
                                            confirmLive.setEnabled(false);
                                        }


                                    }


                                } else {
                                    if (endMonth > startmMonth) {
                                        if (startDay == 26) {


                                            if (endDay == 1) {
                                                Toast.makeText(getApplicationContext(), "you can take leave", Toast.LENGTH_LONG).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), "leave is not available." + "\n" + "try to choose under 5 days from yor starting leave date..", Toast.LENGTH_LONG).show();
                                                confirmLive.setEnabled(false);

                                            }


                                        } else if (startDay == 27) {

                                            if (endDay == 1 || endDay == 2) {
                                                Toast.makeText(getApplicationContext(), "you can take leave", Toast.LENGTH_LONG).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), "leave is not available." + "\n" + "try to choose under 5 days from yor starting leave date..", Toast.LENGTH_LONG).show();
                                                confirmLive.setEnabled(false);

                                            }

                                        } else if (startDay == 28) {

                                            if (endDay == 1 || endDay == 2 || endDay == 3) {
                                                Toast.makeText(getApplicationContext(), "you can take leave", Toast.LENGTH_LONG).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), "leave is not available." + "\n" + "try to choose under 5 days from yor starting leave date..", Toast.LENGTH_LONG).show();
                                                confirmLive.setEnabled(false);

                                            }

                                        } else if (startDay == 29) {

                                            if (endDay == 1 || endDay == 2 || endDay == 3 || endDay == 4) {
                                                Toast.makeText(getApplicationContext(), "you can take leave", Toast.LENGTH_LONG).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), "leave is not available." + "\n" + "try to choose under 5 days from yor starting leave date..", Toast.LENGTH_LONG).show();
                                                confirmLive.setEnabled(false);

                                            }

                                        } else if (startDay == 30) {

                                            if (endDay == 1 || endDay == 2 || endDay == 3 || endDay == 4 || endDay == 5) {
                                                Toast.makeText(getApplicationContext(), "you can take leave", Toast.LENGTH_LONG).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), "leave is not available." + "\n" + "try to choose under 5 days from yor starting leave date..", Toast.LENGTH_LONG).show();
                                                confirmLive.setEnabled(false);

                                            }

                                        } else if (startDay == 31) {

                                            if (endDay == 1 || endDay == 2 || endDay == 3 || endDay == 4 || endDay == 5) {
                                                Toast.makeText(getApplicationContext(), "you can take leave", Toast.LENGTH_LONG).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), "leave is not available." + "\n" + "try to choose under 5 days from yor starting leave date..", Toast.LENGTH_LONG).show();
                                                confirmLive.setEnabled(false);

                                            }

                                        }


                                    } else {
                                        Toast.makeText(getApplicationContext(), "leave is not available today." + "\n" + "try to choose three days after from current date..", Toast.LENGTH_LONG).show();
                                        confirmLive.setEnabled(false);
                                    }

                                }

                            } else {
                                if (endYear > startYear) {
                                    if (endMonth == 12) {

                                        if (startDay == 26) {


                                            if (endDay == 1) {
                                                Toast.makeText(getApplicationContext(), "you can take leave", Toast.LENGTH_LONG).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), "leave is not available." + "\n" + "try to choose under 5 days from yor starting leave date..", Toast.LENGTH_LONG).show();
                                                confirmLive.setEnabled(false);

                                            }


                                        } else if (startDay == 27) {

                                            if (endDay == 1 || endDay == 2) {
                                                Toast.makeText(getApplicationContext(), "you can take leave", Toast.LENGTH_LONG).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), "leave is not available." + "\n" + "try to choose under 5 days from yor starting leave date..", Toast.LENGTH_LONG).show();
                                                confirmLive.setEnabled(false);

                                            }

                                        } else if (startDay == 28) {

                                            if (endDay == 1 || endDay == 2 || endDay == 3) {
                                                Toast.makeText(getApplicationContext(), "you can take leave", Toast.LENGTH_LONG).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), "leave is not available." + "\n" + "try to choose under 5 days from yor starting leave date..", Toast.LENGTH_LONG).show();
                                                confirmLive.setEnabled(false);

                                            }

                                        } else if (startDay == 29) {

                                            if (endDay == 1 || endDay == 2 || endDay == 3 || endDay == 4) {
                                                Toast.makeText(getApplicationContext(), "you can take leave", Toast.LENGTH_LONG).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), "leave is not available." + "\n" + "try to choose under 5 days from yor starting leave date..", Toast.LENGTH_LONG).show();
                                                confirmLive.setEnabled(false);

                                            }

                                        } else if (startDay == 30) {

                                            if (endDay == 1 || endDay == 2 || endDay == 3 || endDay == 4 || endDay == 5) {
                                                Toast.makeText(getApplicationContext(), "you can take leave", Toast.LENGTH_LONG).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), "leave is not available." + "\n" + "try to choose under 5 days from yor starting leave date..", Toast.LENGTH_LONG).show();
                                                confirmLive.setEnabled(false);

                                            }

                                        } else if (startDay == 31) {

                                            if (endDay == 1 || endDay == 2 || endDay == 3 || endDay == 4 || endDay == 5) {
                                                Toast.makeText(getApplicationContext(), "you can take leave", Toast.LENGTH_LONG).show();

                                            } else {
                                                Toast.makeText(getApplicationContext(), "leave is not available." + "\n" + "try to choose under 5 days from yor starting leave date..", Toast.LENGTH_LONG).show();
                                                confirmLive.setEnabled(false);

                                            }

                                        }


                                    } else {
                                        Toast.makeText(getApplicationContext(), "leave is not available one year advanced" + "\n" + "try to choose three days after from current date..", Toast.LENGTH_LONG).show();
                                        confirmLive.setEnabled(false);


                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), "you can not take leave in one year past", Toast.LENGTH_LONG).show();
                                    confirmLive.setEnabled(false);

                                }

                            }


                            endDate.setText(date1);


                        }
                    }, endYear, endMonth, endDay);
                    datePickerDialog.show();


                }
            }
        });

        confirmLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(leaveState.equals("new"))
                {
                    leaveRequestSend();

                }

                if(leaveState.equals("sent"))
                {
                    leaveRequestCancel();

                }

            }
        });



    }

    private void leaveRequestCancel()
    {

        progressDialog.setTitle("Deleting your Leave");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        ConfirmLeaveRequest_Ref.child(current_doctor).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {

                    Toast.makeText(getApplicationContext(),"Leave canceled",Toast.LENGTH_LONG).show();
                    leaveState="new";
                    confirmLive.setText("Take Leave");
                    progressDialog.dismiss();

                }

            }
        });

    }

    private void leaveRequestSend()
    {

        String startingDateofLeave=startDate.getText().toString();
        String endingDateofLeave=endDate.getText().toString();

        if(TextUtils.isEmpty(startingDateofLeave)&&TextUtils.isEmpty(endingDateofLeave))
        {
            Toast.makeText(getApplicationContext(),"choose date first",Toast.LENGTH_LONG).show();
            confirmLive.setEnabled(false);

        }
        else
        {

            Doctor_REF.child(current_doctor).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {
                    if(dataSnapshot.exists())
                    {
                       String name1 =dataSnapshot.child("name").getValue().toString();
                       String photo1 =dataSnapshot.child("image").getValue().toString();




                        progressDialog.setTitle("Setting your Leave");
                        progressDialog.setMessage("Please wait...");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();

                        final HashMap<String, String> docotr_leave_req = new HashMap<>();
                        docotr_leave_req.put("uid", current_doctor);
                        docotr_leave_req.put("From", startDate.getText().toString());
                        docotr_leave_req.put("To",endDate.getText().toString());
                        docotr_leave_req.put("endDay", String.valueOf(endDayy));
                        docotr_leave_req.put("endMonth", String.valueOf(endMonthh));
                        docotr_leave_req.put("name", name1);
                        docotr_leave_req.put("photo",photo1);
                        docotr_leave_req.put("startDay", String.valueOf(startDay));
                        docotr_leave_req.put("startMonth", String.valueOf(startmMonth));


                        ConfirmLeaveRequest_Ref.child(current_doctor).setValue(docotr_leave_req).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if(task.isSuccessful())
                                {
                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"your leave is saved successfully",Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(getApplicationContext(),Doctor_ProfileActivity.class);
                                    startActivity(intent);

                                    leaveState="sent";
                                    confirmLive.setText("Cancel Leave");
                                    progressDialog.dismiss();
                                }

                            }
                        });

                      /*  docotrLeave_REF.child(current_doctor).setValue(docotr_leave_req).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if (task.isSuccessful())
                                {

                                    leaveState="sent";
                                    confirmLive.setText("Cancel Leave");
                                    Toast.makeText(getApplicationContext(),"request send",Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                }

                            }
                        });

                       */

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }
}
