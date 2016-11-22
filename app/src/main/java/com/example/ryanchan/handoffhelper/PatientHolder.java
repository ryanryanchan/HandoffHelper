package com.example.ryanchan.handoffhelper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Chris on 11/20/2016.
 */

public class PatientHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private TextView patientBed;
    private TextView patientAgeSex;
    private TextView patientCC;
    private RelativeLayout patientRelativeLayout;

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

    public void bindPatient(Patient patient, String doctor) {
        if (doctor.equals(patient.getDoctor()) || doctor.equals(patient.getHandoff())) {
            this.patient = patient;
            this.patientBed.setText("BED " + patient.getBed());
            this.patientAgeSex.setText("" + patient.getAge() + "yo " + patient.getSex());
            this.patientCC.setText(patient.getChiefComplaint());
            Drawable patientBoxDrawable = (Drawable) this.patientRelativeLayout.getBackground();
            if (patientBoxDrawable instanceof GradientDrawable) {
                GradientDrawable patientBoxGD = (GradientDrawable) patientBoxDrawable;
                patientBoxGD.setColor(severityColor(this.patient));
                patientBoxGD.setStroke(3, severityColor(this.patient));
            } else if (patientBoxDrawable instanceof ColorDrawable) {
            }
        } else {
            this.patientRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.empty_layout);
//            this.patientRelativeLayout.setVisibility(View.GONE);
            this.patientAgeSex = (TextView)itemView.findViewById(R.id.empty_text);
//            this.patientAgeSex.setVisibility(View.GONE);
            this.patientCC = (TextView)itemView.findViewById(R.id.empty_text);
//            this.patientCC.setVisibility(View.GONE);
            this.patientBed = (TextView)itemView.findViewById(R.id.empty_text);
//            this.patientBed.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        if (this.patient != null) {
            Intent intent = new Intent(this.context, PatientProfile.class);
            String age = Integer.toString(patient.getAge());
            String severity = Integer.toString(patient.getSeverity());
            intent.putExtra("PATIENT_BED",patient.getBed());
            intent.putExtra("PATIENT_SEX",patient.getSex());
            intent.putExtra("PATIENT_AGE",age);
            intent.putExtra("PATIENT_COMPLAINT",patient.getChiefComplaint());
            intent.putExtra("PATIENT_DIAGNOSIS",patient.getDiagnosis());
            intent.putExtra("PATIENT_TESTS",patient.getTestsOrdered());
            intent.putExtra("PATIENT_SEVERITY",severity);
            intent.putExtra("PATIENT_PLAN",patient.getPlanOfCare());
            intent.putExtra("PATIENT_CONTINGENCY",patient.getContingency());
            intent.putExtra("PATIENT_DOCTOR", patient.getDoctor());
            intent.putExtra("PATIENT_HANDOFF", patient.getHandoff());
            context.startActivity(intent);
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
