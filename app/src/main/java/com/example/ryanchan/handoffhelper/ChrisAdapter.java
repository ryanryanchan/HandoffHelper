package com.example.ryanchan.handoffhelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Chris on 11/20/2016.
 */

public class ChrisAdapter extends RecyclerView.Adapter<PatientHolder> {

    private final List<Patient> patients = new ArrayList<Patient>();
    private List<Integer> viewablePatients = new ArrayList<Integer>();

    private Context context;
    private int itemResource;
    private int mSort = 0;
    private Firebase fireGod;
    private List<String> keys;   //list of keys, in this case...
    private String doctor;
    private String handoff;

    public ChrisAdapter(Context context, int itemResource, String doctor) {
        super();
        this.doctor = doctor;
        this.context = context;
        this.itemResource = itemResource;
        setHasStableIds(true);
        fireGod = new Firebase("https://temp-hh.firebaseio.com/");
        keys = new ArrayList<String>();

        fireGod.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Patient p = dataSnapshot.getValue(Patient.class); //getting the Patient data from the data added
                String key = dataSnapshot.getKey();

                if (s == null) {
                    patients.add(0, p); //if nothing in the list, just add it in
                    keys.add(0, key);

                } else { //else add it after the "previous" one
                    int previousIndex = keys.indexOf(s);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex == patients.size()) { //if this is the last spot in the list, just add it in
                        patients.add(p);
                        keys.add(key);
                    } else {                        //otherwise specifically add it after the specified "previous"
                        patients.add(nextIndex, p);
                        keys.add(nextIndex, key);
                    }
                }
                notifyDataSetChanged(); //tells the view to refresh itself
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //one of the models aka Patient have changed; replace it in the list and name mapping

                String key = dataSnapshot.getKey();
                Patient newP = dataSnapshot.getValue(Patient.class);
                int index = keys.indexOf(key);

                //get the key and model, then change it by setting it
                patients.set(index, newP);

                notifyDataSetChanged(); //refresh view
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                remove a model/Patient from the list and name mapping
                String key = dataSnapshot.getKey();
                int index = keys.indexOf(key);

                keys.remove(index);
                patients.remove(index);

                notifyDataSetChanged(); //refresh view
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                //A model has changed position in the list
                String key = dataSnapshot.getKey();
                Patient newP = dataSnapshot.getValue(Patient.class);
                int index = keys.indexOf(key);

                patients.remove(index);
                keys.remove(index);

                //we basically just called onChildRemoved and then onChildAdded

                if (s == null) {
                    patients.add(0, newP);
                    keys.add(0, key);
                } else {
                    int previousIndex = keys.indexOf(s);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex == patients.size()) {
                        patients.add(newP);
                        keys.add(nextIndex, key);
                    }
                }

                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("PatientListAdapter", "LISTEN WAS CANCELLED, no more updates will occur");
            }
        });

        fixpatients();
    }

    @Override
    public long getItemId(int position){
        return patients.get(position).hashCode();
    }

    @Override
    public PatientHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.fixpatients();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(this.itemResource,parent,false);
        return new PatientHolder(this.context,view);
    }

    @Override
    public void onBindViewHolder(PatientHolder holder, int position) {
        this.fixpatients();
////        int newpos = patients.indexOf(patients.get(position));
//        Log.d("POS:NEWPOS",position+":"+newpos);
        Patient patient = patients.get(viewablePatients.get(position));
        holder.bindPatient(patient,doctor);
    }

    @Override
    public int getItemCount() {
//        return viewablePatients.size();
//        return patients.size();
        int count = 0;
        for (int i = 0; i < patients.size(); i++){

            if(doctor != null){
                if ( doctor.equals(patients.get(i).getDoctor()) ||
                        doctor.equals(patients.get(i).getHandoff())){
                    count++;
                }
            }

        }
        return count;
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

    private void fixpatients(){
        this.viewablePatients = new ArrayList<Integer>();
        for (int i = 0; i < patients.size(); i++){
            if (doctor.equals(patients.get(i).getDoctor()) || doctor.equals(patients.get(i).getHandoff())){
                viewablePatients.add(i);
            }
        }
//        Log.d("VIEWABLE",viewablePatients.size()+"");
//        Log.d("PATIENTS",patients.size()+"");
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setDoctor(String doctor){ this.doctor = doctor;}

    public void setHandoff(String handoff){this.handoff = handoff;
       for(int i = 0; i < patients.size(); i++){
           if (patients.get(i).getDoctor()!= null && patients.get(i).getHandoff() != null && doctor != null){
               if (patients.get(i).getDoctor().equals(doctor) || patients.get(i).getHandoff().equals(doctor)) {
                   String taskId = patients.get(i).getBed();
                   //Firebase objRef = fireGod.child(taskId);
                   Firebase taskRef = fireGod.child(taskId);
                   Firebase statusRef = taskRef.child("handoff");
                   Firebase statusRef2 = taskRef.child("doctor");
                   statusRef2.setValue(doctor);
                   statusRef.setValue(handoff);
               }
           }

       }
    }
}


