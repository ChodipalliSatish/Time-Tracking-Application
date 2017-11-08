package com.example.computer.signinapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

/**
 * Created by Computer on 9/21/2017.
 */

public class BeaconTrigger extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,childref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main2);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        Bundle bundle = getIntent().getExtras();
        String trigger = bundle.getString("trigger");
        String endtime = bundle.getString("endtime");
        if(trigger!=null){
            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Location loc=new Location("B9407F30-F5F8-466E-AFF9-25556B57FE6D","Near",currentuser.getUid());
            databaseReference.child("USER_LOCATION").child(currentuser.getUid()).child(Long.toString(new Date().getTime())).child("Location1").setValue(endtime);
            trigger=null;
            Toast.makeText(getApplicationContext(),"Beacon Triggered",Toast.LENGTH_LONG).show();

        }

        databaseReference = FirebaseDatabase.getInstance().getReference();
        childref = databaseReference.child("USER_ACCOUNT").child(currentuser.getUid());
        childref.child("role").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String temp="1";
                String value = dataSnapshot.getValue(String.class);
                if(value.equals(temp)){

                    Intent intent = new Intent(getApplicationContext(), AdminSignIn.class);
                    startActivity(intent);

                }
                else{
                    Intent intent = new Intent(getApplicationContext(), signinActivity.class);
                    startActivity(intent);

                }

                //  navename.setText("Hey " + value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






    }
}
