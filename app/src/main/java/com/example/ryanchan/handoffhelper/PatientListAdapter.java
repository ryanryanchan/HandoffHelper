package com.example.ryanchan.handoffhelper;

import android.app.Activity;
import android.view.View;

import com.firebase.client.Query;

/**
 * Created by User on 11/19/2016.
 */
public class PatientListAdapter extends FirebaseListAdapter<Patient> {
    private String bed;
    private String sex;
    private int age;

    public PatientListAdapter(Query ref, Activity activity, int layout, String bed, String sex, int age) {
        super(ref, Patient.class, layout, activity);
        this.bed= bed;
        this.sex = sex;
        this.age = age;
    }


    @Override
    protected void populateView(View view, Patient patient){
        //get button with text view on it
        //textview: Bolded Bed # on left, followed by Age, Sex, Condition
    }
}
