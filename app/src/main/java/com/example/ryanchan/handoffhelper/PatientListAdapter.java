package com.example.ryanchan.handoffhelper;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Query;

/**
 * Created by User on 11/19/2016.
 */
public class PatientListAdapter extends FirebaseListAdapter<Patient> {

    public PatientListAdapter(Query ref, Activity activity, int layout){
        super(ref, Patient.class, layout, activity);
    }


    @Override
    protected void populateView(View view, Patient patient){
        //get button with text view on it
        //textview: Bolded Bed # on left, followed by Age, Sex, Condition

        CharSequence bed = "BED " + patient.getBed();
        CharSequence text2 = ""+patient.getAge() +"yo " + patient.getSex();
        CharSequence cc =  patient.getChiefComplaint();

        Log.d("TESTING", (String)bed);
        Log.d("TESTING", (String)text2);
        Log.d("TESTING", (String)cc);




        TextView bedText = (TextView) view.findViewById(R.id.patientBed);
        bedText.setText(bed);

        TextView ageText = (TextView) view.findViewById(R.id.patientAgeSex);
        ageText.setText(text2);

        TextView sexText = (TextView) view.findViewById(R.id.patientCC);
        sexText.setText(cc);
    }
}
