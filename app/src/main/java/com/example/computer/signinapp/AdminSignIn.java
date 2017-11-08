package com.example.computer.signinapp;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminSignIn extends AppCompatActivity {
    TextView uemail,navename;
    Button b1,b2;
    Button b5,b6,b7;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,childref;
    String temp;
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mtoggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_sign_in);
        mDrawerlayout=(DrawerLayout)findViewById(R.id.drawer);
        mtoggle =new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open,R.string.close);
        mDrawerlayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseAuth = FirebaseAuth.getInstance();

        b5 = (Button)findViewById(R.id.daily_button1);
        b6 = (Button)findViewById(R.id.weekly_button1);
        b7 = (Button)findViewById(R.id.monthly_button1);
        //FirebaseUser user = firebaseAuth.getCurrentUser();
        uemail = (TextView) findViewById(R.id.textAdminName);

        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        childref = databaseReference.child("USER_ACCOUNT").child(currentuser.getUid());
        childref.child("ename").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                uemail.setText("Welcome " + value);

                //  navename.setText("Hey " + value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daily(v);

            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekly(v);
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monthly(v);
            }
        });

    }



    public boolean onOptionsItemSelected(MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void MyAccount(MenuItem item) {
        Intent intent = new Intent(this, MyAccount.class);
        startActivity(intent);
    }

    public void logout(MenuItem item) {
        Intent intent = new Intent(this, Logout.class);
        startActivity(intent);
    }

    public void beacon(MenuItem item) {
        Intent intent = new Intent(this, BeaconDetails.class);
        startActivity(intent);
    }
    public void FetchUsers(MenuItem item){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);

    }

    public void daily(View view){
        Intent daily = new Intent(getApplicationContext(), DailyActivity.class);
        startActivity(daily);
    }
    public void weekly(View view){
        Intent weekly = new Intent(getApplicationContext(), WeeklyActivity.class);
        startActivity(weekly);
    }
    public void monthly(View view){
        Intent monthly = new Intent(getApplicationContext(), MonthlyActivity.class);
        startActivity(monthly);
    }
}
