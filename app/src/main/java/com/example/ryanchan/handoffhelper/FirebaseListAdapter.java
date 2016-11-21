package com.example.ryanchan.handoffhelper;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryanchan on 7/13/16.
 */
public abstract class FirebaseListAdapter<T> extends BaseAdapter {

    private Query FBRef;         //for making a firebase listener
    private Class<T> modelClass; //the type of data the list will hold, in this case a Chat
    private int mlayout;          //represents a single list item (?)
    private LayoutInflater minflater; //??
    private List<T> models;      //list of "models", in this case Chats
    private List<String> keys;   //list of keys, in this case...
    private ChildEventListener mlistener; //the thing that checks for changes
    private ChrisAdapter viewAdapter;
    private List<Patient> pList;


    public FirebaseListAdapter(Query FBRef, Class<T> modelClass, int layout, Activity activity) {
        this.FBRef = FBRef;
        this.modelClass = modelClass;
        this.mlayout = layout;
        minflater = activity.getLayoutInflater(); //instantiates a layout XML file apparently
        models = new ArrayList<T>();
        keys = new ArrayList<String>();


        mlistener = this.FBRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot data, String previousChildName) {

                T model = data.getValue(FirebaseListAdapter.this.modelClass); //getting the Patient data from the data added
                String key = data.getKey();

                //putting the data into the right location based on previousChildName
                if (previousChildName == null) {
                    models.add(0, model); //if nothing in the list, just add it in
                    keys.add(0, key);

                } else { //else add it after the "previous" one
                    int previousIndex = keys.indexOf(previousChildName);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex == models.size()) { //if this is the last spot in the list, just add it in
                        models.add(model);
                        keys.add(key);
                    } else {                        //otherwise specifically add it after the specified "previous"
                        models.add(nextIndex, model);
                        keys.add(nextIndex, key);
                    }
                }
                notifyDataSetChanged(); //tells the view to refresh itself
            }

            @Override
            public void onChildChanged(DataSnapshot data, String s) {
                //one of the models aka Patient have changed; replace it in the list and name mapping

                String key = data.getKey();
                T newModel = data.getValue(FirebaseListAdapter.this.modelClass);
                int index = keys.indexOf(key);

                //get the key and model, then change it by setting it
                models.set(index, newModel);

                notifyDataSetChanged(); //refresh view
            }


            @Override
            public void onChildRemoved(DataSnapshot data) {

                //remove a model/Patient from the list and name mapping
                String key = data.getKey();
                int index = keys.indexOf(key);

                keys.remove(index);
                models.remove(index);

                notifyDataSetChanged(); //refresh view
            }

            @Override
            public void onChildMoved(DataSnapshot data, String previousChildName) {

                //A model has changed position in the list
                String key = data.getKey();
                T newModel = data.getValue(FirebaseListAdapter.this.modelClass);
                int index = keys.indexOf(key);

                models.remove(index);
                keys.remove(index);

                //we basically just called onChildRemoved and then onChildAdded

                if (previousChildName == null) {
                    models.add(0, newModel);
                    keys.add(0, key);
                } else {
                    int previousIndex = keys.indexOf(previousChildName);
                    int nextIndex = previousIndex + 1;
                    if (nextIndex == models.size()) {
                        models.add(newModel);
                        keys.add(nextIndex, key);
                    }
                }

                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.e("FirebaseListAdapter", "LISTEN WAS CANCELLED, no more updates will occur");
            }
        });
    }
    public void cleanup() {
        FBRef.removeEventListener(mlistener);
        models.clear();
        keys.clear();

    }
        @Override
        public int getCount(){
            return models.size();
        }

        @Override
        public Object getItem(int i) {
            return models.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        public ChildEventListener getMlistener() {return mlistener;}
        @Override
        public View getView(int i, View view, ViewGroup viewGroup){
            if(view == null){
                view = minflater.inflate(mlayout, viewGroup, false);
            }

            T model = models.get(i);

            populateView(view, model);
            return view;
        }
        public Query getFBRef() {
          return FBRef;
        }
        public List<String> getKeys(){
            return keys;
        }
        protected abstract void populateView(View v, T model);
}

