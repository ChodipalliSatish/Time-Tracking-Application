package com.example.computer.signinapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BeaconDetails extends AppCompatActivity {
 Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_details);
        b1=(Button)findViewById(R.id.addbeacon);
        b2=(Button)findViewById(R.id.removebeacon);

        b1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                Intent addbeacon = new Intent(getApplicationContext(), BeaconAdd.class);
                startActivity(addbeacon);

            }


        });
//
//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent removebeacon=new Intent(getApplicationContext(),BeaconSearch.class);
//                startActivity(removebeacon);
//            }
//        });
}
}
