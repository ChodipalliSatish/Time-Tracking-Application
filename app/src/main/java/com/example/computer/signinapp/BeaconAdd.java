package com.example.computer.signinapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BeaconAdd extends AppCompatActivity {
    Button b1;
    EditText Beacon_Desc,Beacon_Major,Beacon_Minor,Beacon_name,Beacon_UUID;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_add);
        firebaseAuth=FirebaseAuth.getInstance();
        b1 = (Button)findViewById(R.id.save);
        Beacon_Desc=(EditText)findViewById(R.id.Desc);
        Beacon_Major=(EditText)findViewById(R.id.Major);
        Beacon_Minor=(EditText)findViewById(R.id.Minor);
        Beacon_name=(EditText)findViewById(R.id.Bname);
        Beacon_UUID=(EditText)findViewById(R.id.UUID);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),
                        "Redirecting...",Toast.LENGTH_SHORT).show();

                beaconAdd(v);

            }
        });


    }

    public void beaconAdd(View view) {

        databaseReference= FirebaseDatabase.getInstance().getReference();

                    String Desc=Beacon_Desc.getText().toString().trim();
                    String Major=Beacon_Major.getText().toString().trim();
                    String Minor=Beacon_Minor.getText().toString().trim();
                    String Bname=Beacon_name.getText().toString().trim();
                    String UUID=Beacon_UUID.getText().toString().trim();
                //    Toast.makeText(getApplicationContext(),"Beacon Added",Toast.LENGTH_LONG).show();

                    //FirebaseUser currentuser=firebaseAuth.getCurrentUser();

                   Beacon_vlaues beacon_vlaues= new Beacon_vlaues(Desc,Major,Minor,Bname,UUID);

                    databaseReference.child("TBL_Beacon").setValue(beacon_vlaues);
                    Toast.makeText(getApplicationContext(),"Beacon Added ",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), BeaconDetails.class);
                    startActivity(intent);

            }

        /*Intent intent = new Intent(getApplicationContext(), SuccessReg.class);
        *//**//*EditText editText = (EditText) findViewById(R.id.name);
        String message = editText.getText().toString();*//**//*
        // intent.putExtra("uname", message);
        startActivity(intent);
        // Do something in response to button*/
    }


