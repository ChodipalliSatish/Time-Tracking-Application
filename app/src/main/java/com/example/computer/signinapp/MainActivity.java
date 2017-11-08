package com.example.computer.signinapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.observation.utils.Proximity;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    Button b1,b2;
    EditText ed1,ed2;


    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,childref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        b1 = (Button)findViewById(R.id.button);
        ed1 = (EditText)findViewById(R.id.editText);
        ed2 = (EditText)findViewById(R.id.editText2);
        firebaseAuth=FirebaseAuth.getInstance();

        b2 = (Button)findViewById(R.id.button3);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    signin(v);

            }
        });

        b2 = (Button)findViewById(R.id.button3);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                signup(vi);
            }
        });


    }



    public void signup(View view) {
        Intent intent = new Intent(this, signupActivity.class);
        /*EditText editText = (EditText) findViewById(R.id.name);
        String message = editText.getText().toString();*/
        // intent.putExtra("uname", message);
        startActivity(intent);
        // Do something in response to button
    }

    public void signin(View view) {
        String email=ed1.getText().toString().trim();
        String pwd=ed2.getText().toString().trim();
        firebaseAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser currentuser = firebaseAuth.getCurrentUser();
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
                else {

                    Toast.makeText(getApplicationContext(),"You are not an Authenticated User",Toast.LENGTH_LONG).show();
                }
            }
        });

        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        //intent.putExtra("name", message);

    }





}
