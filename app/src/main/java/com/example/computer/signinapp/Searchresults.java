package com.example.computer.signinapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Searchresults extends AppCompatActivity {
    ListView mUsersList;
    ArrayList<User_account> users=new ArrayList<User_account>();
    DatabaseReference databaseReference,childref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresults);
      mUsersList=(ListView)findViewById(R.id.Users_list);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("USER_ACCOUNT");
        Toast.makeText(getApplicationContext(),
                "Saving Data ...",Toast.LENGTH_SHORT).show();
        Bundle bundle = getIntent().getExtras();
        final String query = bundle.getString("query");
        final  EmployeeRecordAdaptor accountArrayAdapter = new EmployeeRecordAdaptor(this, users);
        //final EmployeeRecordAdaptor<User_account> accountArrayAdapter= new EmployeeRecordAdaptor(this,android.R.layout.simple_expandable_list_item_1,users);
         mUsersList.setAdapter(accountArrayAdapter);


databaseReference.addChildEventListener(new ChildEventListener() {
    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

       //
       if (dataSnapshot.hasChildren()) {
          // Toast.makeText(getApplicationContext(), dataSnapshot.getValue().toString(),Toast.LENGTH_SHORT).show();

           User_account user=dataSnapshot.getValue(User_account.class);
           if(user.getEname().contains(query)){


                    users.add( user);
           }

        }

        accountArrayAdapter.notifyDataSetChanged();

    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }


    });




    }
}
