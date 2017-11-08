package com.example.computer.signinapp;

/**
 * Created by Computer on 9/18/2017.
 */


import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.observation.utils.Proximity;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;
import com.estimote.mgmtsdk.feature.settings.api.ScheduledPeriod;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

//import com.estimote.*;

public class BeaconApp extends Application {
    public static boolean startingtime=true;
    public static boolean endingtime=false;

    double timespent;
    String finaldata="";
    String arr[];
    String temp2[];
    String time1;
    Date d1,d2;
    int month,year,day;
    String months[]={"January","February","March","April","May","June","July","August","September","October","November","December"};
    String diff,format;

    public static ArrayList<Beacon> nearbeacons=new ArrayList<Beacon>();


    public static Proximity computeAccuracy(Beacon beacon)
    {
        Log.e("rssi",Integer.toString(beacon.getRssi()));
        if (beacon.getRssi() == 0)
        {

            return Proximity.UNKNOWN;
        }


        if(beacon.getRssi()>=-70)

        {


            return Proximity.NEAR;
        }

        return Proximity.UNKNOWN;

    }

    FirebaseAuth firebaseAuth;
    FirebaseUser currentuser;
    private BeaconManager beaconManager;
    DatabaseReference databaseReference,childref,subchild;
    @Override
    public void onCreate() {
        super.onCreate();

        EstimoteSDK.initialize(getApplicationContext(), "com.example.computer.signinapp", "<>Here goes your application token");

        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {


            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> beacons) {
                if (!beacons.isEmpty()) {

                    nearbeacons.clear();
                    Proximity p1=Proximity.NEAR;
                    Proximity p2=Proximity.IMMEDIATE;

                    for(Beacon nearbae:beacons){

                        Proximity p=(computeAccuracy(nearbae));

                        if(p.equals(p1)){
                            nearbeacons.add(nearbae);

                        }

                    }
                    if(nearbeacons.size()>=2){


                        if(startingtime==true)
                        {
                           /* SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
                            String time = localDateFormat.format(new Date());*/

                            d1=new Date();

                            startingtime=false;
                            Log.e("starttime",d1.toString());


                        }

                    }
                    else     {
                        if(nearbeacons.size()<=1){

                            endingtime=true;
                        }


                        if(endingtime&&(startingtime==false)){
                            timespent=0;
                            d2=new Date();
                            diff="";
                            long mills = d2.getTime() - d1.getTime();
                            int Hours = (int)mills/(1000 * 60 * 60);
                            int Mins = (int)mills / (1000*60)%60;
                            // int sec=(int)(mills / (1000/100))%60;

                            diff = Hours + ":" + Mins;




                            Log.e("timespent",diff);

                            endingtime=false;
                            startingtime=true;
                            showNotification(diff);
                        }

                    }




                    Log.e("length",Integer.toString(nearbeacons.size()));


                }




            }



        });



        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {

                beaconManager.startRanging(new BeaconRegion("Ranging region",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null));
            }
        });
    }



    public void showNotification(String time) {

        time1=time;
        arr=time.split(":");
        Date date=new Date(); // your date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
        format=(month+1)+"-"+day+"-"+year;
        Toast.makeText(getApplicationContext(),"Location Trigger",Toast.LENGTH_LONG).show();

        firebaseAuth = FirebaseAuth.getInstance();
        currentuser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        childref = databaseReference.child("USER_LOCATION").child(currentuser.getUid());


        childref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.hasChild(Integer.toString(year))))
                {


                    databaseReference.child("USER_LOCATION").child(currentuser.getUid()).child(Integer.toString(year)).child(months[month]).child(format).child("Location1").setValue(time1);

                }
                else{

                    subchild= childref.child(Integer.toString(year)).child(months[month]).child(format).child("Location1");
                    subchild.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String value="";
                            value= dataSnapshot.getValue(String.class);
                            temp2 = value.split(":");

                            int i=Integer.parseInt(temp2[0]) + Integer.parseInt(arr[0]);

                            int j=Integer.parseInt(temp2[1]) + Integer.parseInt(arr[1]);
                            if(j<60){
                                finaldata = (i) + ":" + (j);
                                databaseReference.child("USER_LOCATION").child(currentuser.getUid()).child(Integer.toString(year)).child(months[month]).child(format).child("Location1").setValue(finaldata);
                                Log.e("finaldata",finaldata);
                            }
                            else{

                                int min=j%60;

                                int hour=j/60;
                                String str = new Integer(hour).toString();
                                str.substring(0,str.indexOf('.'));
                                int v = Integer.valueOf(str);

                                finaldata = (i+v) + ":" + (j+min);
                                databaseReference.child("USER_LOCATION").child(currentuser.getUid()).child(Integer.toString(year)).child(months[month]).child(format).child("Location1").setValue(finaldata);
                                Log.e("finaldata",finaldata);

                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








    }
}
