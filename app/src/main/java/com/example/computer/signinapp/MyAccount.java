package com.example.computer.signinapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;

public class MyAccount extends AppCompatActivity {
    EditText ed1,ed2,ed3,ed4,ed5,ed6,ed7;
    Button b1;
    String role,gender;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentuser;

    DatabaseReference databaseReference,childref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
       // FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();
        ed1 = (EditText)findViewById(R.id.Ename);
        ed2 = (EditText)findViewById(R.id.Uemail);
        ed3 = (EditText)findViewById(R.id.Epassword);
        ed4 = (EditText)findViewById(R.id.Ephone);
        ed5 = (EditText)findViewById(R.id.Edob);
        b1 = (Button)findViewById(R.id.SaveEmp);

        currentuser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("USER_ACCOUNT").child(currentuser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = String.valueOf(dataSnapshot.child("ename").getValue());
                ed1.setText(name);
                String email = String.valueOf(dataSnapshot.child("email").getValue());
                ed2.setText(email);
                String password = String.valueOf(dataSnapshot.child("password").getValue());
                ed3.setText(password);
                String phone = String.valueOf(dataSnapshot.child("phone").getValue());
                ed4.setText(phone);
                String dob = String.valueOf(dataSnapshot.child("dob").getValue());
                ed5.setText(dob);
                role=String.valueOf(dataSnapshot.child("role").getValue());
                gender=String.valueOf(dataSnapshot.child("gender").getValue());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                SaveDetails(v);

            }
        });




    }


    public void SaveDetails(View view) {

        databaseReference= FirebaseDatabase.getInstance().getReference();
         currentuser = firebaseAuth.getCurrentUser();
        currentuser.updateEmail(ed2.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            currentuser.updatePassword(ed3.getText().toString().trim())
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                databaseReference = FirebaseDatabase.getInstance().getReference();
                                                String ename=ed1.getText().toString().trim();
                                                String email2=ed2.getText().toString().trim();
                                                String pwd2=ed3.getText().toString().trim();
                                                String dateofbirth=ed5.getText().toString().trim();

                                                String phone1=ed4.getText().toString().trim();
                                                User_account user= new User_account(ename,email2,pwd2,dateofbirth,phone1,role,gender);
                                                childref=databaseReference.child("USER_ACCOUNT").child(currentuser.getUid());
                                                Map<String,Object> detailsmap = new HashMap<String,Object>();
                                                detailsmap.put("ename",ename);
                                                detailsmap.put("email",email2);
                                                detailsmap.put("password",pwd2);
                                                detailsmap.put("dob",dateofbirth);
                                                detailsmap.put("phone",phone1);
                                                detailsmap.put("role",role);
                                                detailsmap.put("gender",gender);
                                                childref.updateChildren(detailsmap);


                                                
                                                if(!role.equals("1")){

                                                    Toast.makeText(getApplicationContext(),
                                                            "Saving Data ...",Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(getApplicationContext(), signinActivity.class);
                                                    startActivity(intent);

                                                }
                                                else{
                                                    Toast.makeText(getApplicationContext(),
                                                            "Saving Data ...",Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(getApplicationContext(), AdminSignIn.class);
                                                    startActivity(intent);

                                                }

                                            }
                                        }
                                    });

                        }
                    }
                });




    }
}
