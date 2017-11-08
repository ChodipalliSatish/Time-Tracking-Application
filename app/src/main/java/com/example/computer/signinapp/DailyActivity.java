package com.example.computer.signinapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class DailyActivity extends AppCompatActivity {
    Button b1,b2;
    EditText E1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);
        b1 = (Button)findViewById(R.id.today);
        b2=(Button)findViewById(R.id.get);
        E1=(EditText)findViewById(R.id.getdate) ;
        b1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                Intent dailyres = new Intent(getApplicationContext(), DailyRes.class);
                startActivity(dailyres);

            }


        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getres=new Intent(getApplicationContext(),DailyRes.class);
                String getdate=E1.getText().toString().trim();

                getres.putExtra("getdate",getdate);
                startActivity(getres);
            }
        });
    }




}
