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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MonthlyActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser currentuser;
    float min=8f;
    float max = 0f;
    float value = 0f;
    int j=0;
    DatabaseReference databaseReference,childref;
    BarChart barChart;
    ArrayList<String> dates;
    String format;
    Random random;
    BarDataSet barDataSet;
    ArrayList<BarEntry> barEntries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly);
        Intent in = getIntent();
        String value1 = in.getStringExtra("weekdata1");
        String value2 = in.getStringExtra("weekdata2");
        barChart = (BarChart) findViewById(R.id.bargraph);
        if(value1!=null&&value2!=null){
            createRandomBarGraph(value1, value2);
        }
        else{
            createRandomBarGraph("2017/10/01", "2017/10/31");
        }



    }

    public void createRandomBarGraph(String Date1, String Date2){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        try {
            Date date1 = simpleDateFormat.parse(Date1);
            Date date2 = simpleDateFormat.parse(Date2);

            Calendar mDate1 = Calendar.getInstance();
            Calendar mDate2 = Calendar.getInstance();
            mDate1.clear();
            mDate2.clear();

            mDate1.setTime(date1);
            mDate2.setTime(date2);

            dates = new ArrayList<>();
            dates = getList(mDate1, mDate2);

            barEntries = new ArrayList<>();

            random = new Random();
            firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser currentuser = firebaseAuth.getCurrentUser();
            databaseReference = FirebaseDatabase.getInstance().getReference();
            for (String st : dates) {
                String temp[]=st.split("/");
                String format=temp[1]+"-"+temp[2]+"-"+temp[0];

                childref = databaseReference.child("USER_LOCATION").child(currentuser.getUid()).child("2017").child("October").child(format);


                childref
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                               for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                   /*Log.e("getdate",snapshot.toString());
                                    String data = snapshot.child("Location1").getValue(String.class);

                                    String temp[] = data.split(":");
                                    String locationtime = temp[0] + "." + temp[1];

                                    value = Float.parseFloat(locationtime);
                                    barEntries.add(new BarEntry(value, j));
                                    j++;*/
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
            }
                // int randomNumber = random.ne
            // xtInt(max + 1 - min) + min;
            for(int j = 0; j< dates.size();j++){
                max = 8f;
                value = random.nextFloat()*(max + 0.5f - min) + min;
                barEntries.add(new BarEntry(value,j));
            }

            }catch(ParseException e){
                e.printStackTrace();
            }

        barDataSet = new BarDataSet(barEntries,"Dates");
        BarData barData = new BarData(dates,barDataSet);
        barChart.setData(barData);
        barChart.setDescription("Monthly Activity!");

    }

    public ArrayList<String> getList(Calendar startDate, Calendar endDate){
        ArrayList<String> list = new ArrayList<String>();
        while(startDate.compareTo(endDate)<=0){
            list.add(getDate(startDate));
            startDate.add(Calendar.DAY_OF_MONTH,1);
        }
        return list;
    }

    public String getDate(Calendar cld){
        String curDate = cld.get(Calendar.YEAR) + "/" + (cld.get(Calendar.MONTH) + 1) + "/"
                +cld.get(Calendar.DAY_OF_MONTH);
        try{
            Date date = new SimpleDateFormat("yyyy/MM/dd").parse(curDate);
            curDate =  new SimpleDateFormat("yyyy/MM/dd").format(date);
        }catch(ParseException e){
            e.printStackTrace();
        }
        return curDate;
    }
}
