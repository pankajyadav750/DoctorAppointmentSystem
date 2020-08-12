package com.example.doctorappointmentsystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {


    final static String DATABASE_NAME = "doctor_appointment_system";

    final static String TABLE_NAME = "admin_info";
    final static String ID = "_id";
    final static String ADMIN_USER_ID = "admin_user_id";
    final static String ADMIN_PASSWORD = "admin_password";

    final static String DOCTOR_TABLE_NAME = "doctor_username_password";
    final static String DOCTOR_ID = "_id";
    final static String DOCTOR_USER_NAME = "doctor_user_id";
    final static String DOCTOR_PASSWORD = "doctor_password";
    final static String DOCTOR_NAME = "doctor_name";

    final static int DATABASE_VERSION = 10;


    public DBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER  PRIMARY KEY , " +
                ADMIN_USER_ID + " TEXT," +
                ADMIN_PASSWORD + " TEXT " + " )";

        sqLiteDatabase.execSQL(CREATE_TABLE);


        String CREATE_TABLE_DOCTOR = "CREATE TABLE " + DOCTOR_TABLE_NAME + " (" +
                DOCTOR_ID + " INTEGER  PRIMARY KEY , " +
                DOCTOR_USER_NAME + " TEXT," +
                DOCTOR_NAME + " TEXT," +
                DOCTOR_PASSWORD + " TEXT " + " )";

        sqLiteDatabase.execSQL(CREATE_TABLE_DOCTOR);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DOCTOR_TABLE_NAME);
        onCreate(sqLiteDatabase);

    }


}



















