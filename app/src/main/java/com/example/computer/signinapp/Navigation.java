package com.example.computer.signinapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Computer on 9/20/2017.
 */

public class Navigation extends AppCompatActivity {
    TextView uemail;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,childref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navhead);
        firebaseAuth = FirebaseAuth.getInstance();
        //FirebaseUser user = firebaseAuth.getCurrentUser();
        uemail = (TextView) findViewById(R.id.navEname);

        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        childref = databaseReference.child("USER_ACCOUNT").child(currentuser.getUid());
        childref.child("ename").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                uemail.setText("Hey " + value);
                //  navename.setText("Hey " + value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }
}
