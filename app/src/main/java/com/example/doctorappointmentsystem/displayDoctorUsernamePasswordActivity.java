package com.example.doctorappointmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class displayDoctorUsernamePasswordActivity extends AppCompatActivity {

    private ListView doctor_username_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_doctor_username_password);

        doctor_username_password = findViewById(R.id.doctor_userName_password_listview);

        DBHandler dbHandler = new DBHandler(this);
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT " +DBHandler.DOCTOR_ID + " ,  "+ DBHandler.DOCTOR_USER_NAME + " , "+ DBHandler.DOCTOR_NAME + " , " +DBHandler.DOCTOR_PASSWORD +  "  FROM " + DBHandler.DOCTOR_TABLE_NAME, null);

        MyCursorAdapter adapter = new MyCursorAdapter(this, cursor, false);

        doctor_username_password.setAdapter(adapter);
    }


}

    class MyCursorAdapter extends CursorAdapter {


        public MyCursorAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            return LayoutInflater.from(context).inflate(R.layout.doctor_username_password, viewGroup, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {


            TextView id = view.findViewById(R.id.display_doctor_username_password_id);
            TextView name = view.findViewById(R.id.display_doctor_username_password_name);
            TextView username = view.findViewById(R.id.display_doctor_username_password_username);
            TextView password= view.findViewById(R.id.display_doctor_username_password_password);




            String  doctor_id = cursor.getString(cursor.getColumnIndexOrThrow(DBHandler.DOCTOR_ID));
            String doctor_username = cursor.getString(cursor.getColumnIndexOrThrow(DBHandler.DOCTOR_USER_NAME));
            String doctor_password = cursor.getString(cursor.getColumnIndexOrThrow(DBHandler.DOCTOR_PASSWORD));
            String doctor_name = cursor.getString(cursor.getColumnIndexOrThrow(DBHandler.DOCTOR_NAME));



            id.setText(doctor_id);
            name.setText(doctor_name);
            username.setText(doctor_username);
            password.setText(doctor_password);

        }
}
