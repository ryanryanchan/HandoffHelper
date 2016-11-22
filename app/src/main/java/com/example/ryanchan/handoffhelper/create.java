package com.example.ryanchan.handoffhelper;

import com.firebase.client.Firebase;



public class create extends android.app.Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
