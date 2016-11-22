package com.example.ryanchan.handoffhelper;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.DataSnapshot;

import java.util.List;

import static android.R.attr.data;

/**
 * Created by User on 11/19/2016.
 */
public class PatientListAdapter extends FirebaseListAdapter<Patient> {
    private ChrisAdapter viewAdapter;
    private List<Patient> pList;
    private ChildEventListener childListener;
    private Query FireB;
    private List<String> keys;   //list of keys, in this case...


    public PatientListAdapter(Query ref, Activity activity, int layout){
        super(ref, Patient.class, layout, activity);
        viewAdapter = MainActivity.getViewAdapter();
        pList = viewAdapter.getPatients();
        FireB = super.getFBRef();
        childListener = super.getMlistener();
        keys = super.getKeys();

        childListener = this.FireB.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Patient p = dataSnapshot.getValue(Patient.class); //getting the Patient data from the data added
//                String key = dataSnapshot.getKey();
//
//                if (s == null) {
//                    pList.add(0, p); //if nothing in the list, just add it in
//                    keys.add(0, key);
//
//                } else { //else add it after the "previous" one
//                    int previousIndex = keys.indexOf(s);
//                    int nextIndex = previousIndex + 1;
//                    if (nextIndex == pList.size()) { //if this is the last spot in the list, just add it in
//                        pList.add(p);
//                        keys.add(key);
//                    } else {                        //otherwise specifically add it after the specified "previous"
//                        pList.add(nextIndex, p);
//                        keys.add(nextIndex, key);
//                    }
//                }
                notifyDataSetChanged(); //tells the view to refresh itself
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                    //one of the models aka Patient have changed; replace it in the list and name mapping
//
//                    String key = dataSnapshot.getKey();
//                    Patient newP = dataSnapshot.getValue(Patient.class);
//                    int index = keys.indexOf(key);
//
//                    //get the key and model, then change it by setting it
//                    pList.set(index, newP);

                    notifyDataSetChanged(); //refresh view
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                //remove a model/Patient from the list and name mapping
//                String key = dataSnapshot.getKey();
//                int index = keys.indexOf(key);
//
//                keys.remove(index);
//                pList.remove(index);

                notifyDataSetChanged(); //refresh view
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//                //A model has changed position in the list
//                String key = dataSnapshot.getKey();
//                Patient newP = dataSnapshot.getValue(Patient.class);
//                int index = keys.indexOf(key);
//
//                pList.remove(index);
//                keys.remove(index);
//
//                //we basically just called onChildRemoved and then onChildAdded
//
//                if (s == null) {
//                    pList.add(0, newP);
//                    keys.add(0, key);
//                } else {
//                    int previousIndex = keys.indexOf(s);
//                    int nextIndex = previousIndex + 1;
//                    if (nextIndex == pList.size()) {
//                        pList.add(newP);
//                        keys.add(nextIndex, key);
//                    }
//                }

                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });


    }


    @Override
    protected void populateView(View view, Patient patient){
        //get button with text view on it
        //textview: Bolded Bed # on left, followed by Age, Sex, Condition

        CharSequence bed = "BED " + patient.getBed();
        CharSequence text2 = ""+patient.getAge() +" y/o " + patient.getSex();
        CharSequence cc =  patient.getChiefComplaint();

//        Log.d("TESTING", (String)bed);
//        Log.d("TESTING", (String)text2);
//        Log.d("TESTING", (String)cc);


        TextView bedText = (TextView) view.findViewById(R.id.patientBed);
        bedText.setText(bed);

        TextView ageText = (TextView) view.findViewById(R.id.patientAgeSex);
        ageText.setText(text2);

        TextView sexText = (TextView) view.findViewById(R.id.patientCC);
        sexText.setText(cc);
    }
}
