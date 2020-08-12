package com.example.doctorappointmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ReadAboutHealthActivity extends AppCompatActivity {

    public String fruitNames[] = {
            "Fungle Infections",
            "Maleriya",
            "Home Remedise",
            "Herbs",
            "Dental Care",
            "Weight Loss",
            "Beauty Tips",
            "Fitness",
            "Yoga",
            "Women Health",
            "Ayurved",
            "Healthy Meal",
            "Homeopathy",
    };


    public int fruitImage[] = {
            R.drawable.fungle,
            R.drawable.maleriya,
            R.drawable.homeopaty,
            R.drawable.hearbs,
            R.drawable.teeth,
            R.drawable.weight,
            R.drawable.beauty,
            R.drawable.fitness,
            R.drawable.yoga,
            R.drawable.womens,
            R.drawable.ayurved,
            R.drawable.healthy_meal,
            R.drawable.homeopathy,

    };

    private ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_about_health);

        lst = findViewById(R.id.lst);

        registerForContextMenu(lst);

        MyAdapter myAdapter = new MyAdapter(getApplicationContext(), fruitNames, fruitImage);

        lst.setAdapter(myAdapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                //  showPopUp(lst);

                Log.v("AAA","on click");

                Intent intent = new Intent(getApplicationContext(),HealthDetailsActivity.class);

                intent.putExtra("v1", fruitNames[i]);
                intent.putExtra("v2", fruitImage[i]);
                intent.putExtra("v3", i);

                startActivity(intent);


            }
        });

    }
}

class MyAdapter extends BaseAdapter {


    String fruitNames[];
    int fruitImage[];
    Context context;


    MyAdapter(Context context, String fruitNames[], int fruitImage[]) {
        this.context = context;
        this.fruitNames = fruitNames;
        this.fruitImage = fruitImage;


    }

    @Override
    public int getCount() {
        return fruitNames.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TextView txt1, txt2;
        ImageView imageView;

        view = LayoutInflater.from(context).inflate(R.layout.base_adapter, viewGroup, false);
        txt2 = view.findViewById(R.id.txt2);

        imageView = view.findViewById(R.id.img1);
        imageView.setImageResource(fruitImage[i]);
        txt2.setText(fruitNames[i]);

        return view;
    }
}