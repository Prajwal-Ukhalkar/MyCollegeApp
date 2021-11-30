package com.example.collegeapp;

import android.app.Application;

import com.onesignal.OneSignal;

public class Notification extends Application {

    private static final String ONESIGNAL_APP_ID = "ee5ade2e-b2aa-4e59-90f5-dddbd141b59a";

    @Override
    public void onCreate() {
        super.onCreate ();


        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }
}
