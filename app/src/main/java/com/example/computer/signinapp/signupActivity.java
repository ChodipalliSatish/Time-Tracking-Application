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

public class signupActivity extends AppCompatActivity {
    Button b1,b2;
    EditText email,pwd,name,dob,phone,role,gender;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
       // Intent intent = new Intent(this, signupActivity.class);
        firebaseAuth=FirebaseAuth.getInstance();
        b1 = (Button)findViewById(R.id.reg);
        email=(EditText)findViewById(R.id.email);

        pwd=(EditText)findViewById(R.id.password);
        name=(EditText)findViewById(R.id.name);
        dob=(EditText)findViewById(R.id.dob);
        phone=(EditText)findViewById(R.id.phone);
        role=(EditText)findViewById(R.id.role);
        gender=(EditText)findViewById(R.id.gender);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Toast.makeText(getApplicationContext(),
                            "Redirecting...",Toast.LENGTH_SHORT).show();

                   successReg(v);

            }
        });
    }
    public void successReg(View view) {


        databaseReference= FirebaseDatabase.getInstance().getReference();

        String email1=email.getText().toString().trim();
        String pwd1=pwd.getText().toString().trim();


        firebaseAuth.createUserWithEmailAndPassword(email1, pwd1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful())
               {
                   String email2=email.getText().toString().trim();
                   String pwd2=pwd.getText().toString().trim();
                   String dateofbirth=dob.getText().toString().trim();
                   String uname=name.getText().toString().trim();
                   String phone1=phone.getText().toString().trim();
                   String role1=role.getText().toString().trim();
                   String gender1=gender.getText().toString().trim();
                   Toast.makeText(getApplicationContext(),"Registered successfully",Toast.LENGTH_LONG).show();
                   FirebaseUser currentuser=firebaseAuth.getCurrentUser();
                   User_account user= new User_account(uname,email2,pwd2,dateofbirth,phone1,role1,gender1);
                   databaseReference.child("USER_ACCOUNT").child(currentuser.getUid()).setValue(user);
                   Toast.makeText(getApplicationContext(),"Registered successfully",Toast.LENGTH_LONG).show();
                   Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                   startActivity(intent);
               }
               else{
                   Toast.makeText(getApplicationContext()," Not Registered",Toast.LENGTH_LONG).show();
               }
            }
        });
        /*Intent intent = new Intent(getApplicationContext(), SuccessReg.class);
        *//**//*EditText editText = (EditText) findViewById(R.id.name);
        String message = editText.getText().toString();*//**//*
        // intent.putExtra("uname", message);
        startActivity(intent);
        // Do something in response to button*/
    }
}
