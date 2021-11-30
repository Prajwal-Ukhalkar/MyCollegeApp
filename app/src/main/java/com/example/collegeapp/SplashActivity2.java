package com.example.collegeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;



public class SplashActivity2 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        getSupportActionBar ().hide ();


        Thread td = new Thread(){
            public void run(){
                try {
                    sleep(7350);

                }catch (Exception ex){
                    ex.printStackTrace();

                }

                finally {  //finally madhe jo code must run karaycha last la to asto :)
                    Intent intent = new Intent(SplashActivity2.this,MainActivity.class);
                    startActivity(intent);
                    finish(); // it will show that splash screen only once..

                }

            }

        };
        td.start();
    }

}
