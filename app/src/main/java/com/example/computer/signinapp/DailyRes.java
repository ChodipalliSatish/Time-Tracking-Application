package com.example.computer.signinapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DailyRes extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser currentuser;

    DatabaseReference databaseReference,childref;
    BarChart barChart;
    String months[]={"January","February","March","April","May","June","July","August","September","October","November","December"};

    ArrayList<String> dates;
    String format,locationtime;
    Random random;
    BarDataSet barDataSet;
    ArrayList<BarEntry> barEntries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_res);
        firebaseAuth = FirebaseAuth.getInstance();
        Date date=new Date(); // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        format=(month+1)+"-"+day+"-"+year;

        barChart = (BarChart) findViewById(R.id.dailybargraph);
        barEntries=new ArrayList<>();
        currentuser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("USER_LOCATION/"+currentuser.getUid()+"/"+Integer.toString(year)+"/"+months[month]+"/"+format+"/"+"Location1");


        Bundle bundle = getIntent().getExtras();
        if(bundle!=null ){
            String getdate= bundle.getString("getdate");

            String temp[]=getdate.split("-");
          //  Log.e("getdate",);
            //String format=temp[0]+"-"+temp[1]+"-"+temp[2];
            databaseReference = FirebaseDatabase.getInstance().getReference("USER_LOCATION/"+currentuser.getUid()+"/"+Integer.toString(year)+"/"+months[Integer.parseInt(temp[0])-1]+"/"+getdate+"/"+"Location1");
        }
        else{
            databaseReference = FirebaseDatabase.getInstance().getReference("USER_LOCATION/"+currentuser.getUid()+"/"+Integer.toString(year)+"/"+months[month]+"/"+format+"/"+"Location1");

        }


        //childref=databaseReference.child("USER_LOCATION").child(currentuser.getUid()).child(format).child("Location1");



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue()!=null){
                    String time= String.valueOf(dataSnapshot.getValue());
                    String temp[]=time.split(":");
                    locationtime=temp[0]+"."+temp[1];

                    barEntries.add(new BarEntry(Float.parseFloat(locationtime),0));

                    barEntries.add(new BarEntry(0.2f,1));
                    barEntries.add(new BarEntry(0.1f,2));

                    barDataSet = new BarDataSet(barEntries,"Time spent");
                    ArrayList<String>locations=new ArrayList<>();
                    locations.add("Location1");
                    locations.add("Location2");
                    locations.add("Location3");


                    BarData theData=new BarData(locations,barDataSet);
                    barChart.setData(theData);
                    barChart.setDescription("Daily Activity");
                    barChart.setTouchEnabled(true);
                    barChart.setDragEnabled(true);
                    barChart.setScaleEnabled(true);

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }
}
