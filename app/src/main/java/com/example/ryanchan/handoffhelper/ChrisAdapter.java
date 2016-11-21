package com.example.ryanchan.handoffhelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Chris on 11/20/2016.
 */

public class ChrisAdapter extends RecyclerView.Adapter<PatientHolder> {
    private final List<Patient> patients = new ArrayList<Patient>();
    private Context context;
    private int itemResource;
    private int mSort = 0;

    public ChrisAdapter(Context context, int itemResource) {
        super();
        this.context = context;
        this.itemResource = itemResource;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position){
        return patients.get(position).hashCode();
    }

    @Override
    public PatientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(this.itemResource,parent,false);
        return new PatientHolder(this.context,view);
    }

    @Override
    public void onBindViewHolder(PatientHolder holder, int position) {
        Patient patient = this.patients.get(position);
        holder.bindPatient(patient);
    }

    @Override
    public int getItemCount() {
        return this.patients.size();
    }

    //populates list of test patients
    public void populatePatientList() {
        for (int i = 0; i < 20; i++) {
            Patient temp = new Patient(i + "", "M", 69);
            temp.setChiefComplaint("HELP ME PLEASE I'M DYING BRO HELP ME PLEASE I'M DYING BRO HELP ME PLEASE I'M DYING BRO");
            if (i%6 != 0)
                temp.setSeverity(i%6);
            temp.setDiagnosis("THIS NIGGA BE TRIPPIN");
            temp.setTestsOrdered("CT Scan (Negative)\nUA (Positive)");
            this.patients.add(temp);
        }
    }

    public void sortPatients() {
        if (this.mSort == 0) {
            Collections.sort(this.patients, Patient.PatientSeverityComparator);
            Toast.makeText(this.context, "Sort by severity", Toast.LENGTH_SHORT).show();
            this.mSort = 1;
        } else {
            Collections.sort(this.patients, Patient.PatientBedComparator);
            Toast.makeText(this.context, "Sort by bed", Toast.LENGTH_SHORT).show();
            this.mSort = 0;
        }
        notifyDataSetChanged();
    }

}


