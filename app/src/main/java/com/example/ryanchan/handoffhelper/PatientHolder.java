package com.example.ryanchan.handoffhelper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Chris on 11/20/2016.
 */

public class PatientHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final TextView patientBed;
    private final TextView patientAgeSex;
    private final TextView patientCC;
    private final RelativeLayout patientRelativeLayout;

    private Patient patient;
    private Context context;

    public PatientHolder(Context context, View itemView) {
        super(itemView);

        this.context = context;

        this.patientBed = (TextView) itemView.findViewById(R.id.patientBed);
        this.patientAgeSex = (TextView) itemView.findViewById(R.id.patientAgeSex);
        this.patientCC = (TextView) itemView.findViewById(R.id.patientCC);
        this.patientRelativeLayout = (RelativeLayout)itemView.findViewById(R.id.patientListViewBox);

        itemView.setOnClickListener(this);
    }

    public void bindPatient(Patient patient) {
        this.patient = patient;
        this.patientBed.setText("BED " + patient.getBed());
        this.patientAgeSex.setText("" + patient.getAge() + " yo" + patient.getSex());
        this.patientCC.setText(patient.getChiefComplaint());
        Drawable patientBoxDrawable = (Drawable) this.patientRelativeLayout.getBackground();
        if (patientBoxDrawable instanceof GradientDrawable){
            GradientDrawable patientBoxGD = (GradientDrawable) patientBoxDrawable;
            patientBoxGD.setColor(severityColor(this.patient));
            patientBoxGD.setStroke(3,severityColor(this.patient));
        }
        else if (patientBoxDrawable instanceof ColorDrawable){
        }
    }

    @Override
    public void onClick(View v) {
        if (this.patient != null) {
            String message = "DAMN SON this will take us to patient page someday.\n" +
                    "You clicked the patient in bed " + patient.getBed() +
                    " who is a " + patient.getAge() + " year old " + patient.getSex() +
                    ".";
            Toast.makeText(this.context, message, Toast.LENGTH_LONG).show();
        }
    }


    private int severityColor(Patient currentPatient) {
        int currentSeverity = currentPatient.getSeverity();
        if (currentSeverity == 1)
            return Color.rgb(255, 0, 0);
        else if (currentSeverity == 2)
            return Color.rgb(255, 165, 0);
        else if (currentSeverity == 3)
            return Color.rgb(255, 255, 0);
        else if (currentSeverity == 4)
            return Color.rgb(152, 251, 152);
        else if (currentSeverity == 5)
            return Color.rgb(50, 205, 50);
        else
            return Color.rgb(255, 255, 255);
    }
}
