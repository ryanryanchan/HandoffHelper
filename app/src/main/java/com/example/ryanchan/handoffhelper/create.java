package com.example.ryanchan.handoffhelper;

import com.firebase.client.Firebase;

/**
 * Created by ryanchan on 7/8/16.
 */

public class create extends android.app.Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
