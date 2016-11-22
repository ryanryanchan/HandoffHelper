
package com.example.ryanchan.handoffhelper;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.type;


public class MainActivity extends AppCompatActivity  {

    private  static ChrisAdapter mainAdapter;

    //menu stuff
    private DrawerLayout mDrawerLayout;
    private RelativeLayout mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    //mainscreen stuff
   // private List<Patient> myPatients = new ArrayList<Patient>();


    private GoogleApiClient client;
    Firebase FB = new Firebase("https://handoffhelper-657e2.firebaseio.com/");
    private PatientListAdapter PLA;
    private ValueEventListener mConnectedListener;

    private Map<String, Patient> patients = new HashMap<String, Patient>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // creates the adapter for home screen
        mainAdapter = new ChrisAdapter(this, R.layout.listview_patient);
        //mainAdapter.populatePatientList();

        //attatching the adapter to the recyclerview
        RecyclerView listingsView = (RecyclerView)findViewById(R.id.patientListView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        int verticalSpacing = 50;
        VerticalSpaceItemDecorator itemDecorator =
                new VerticalSpaceItemDecorator(verticalSpacing);

        listingsView.setLayoutManager(layoutManager);
        listingsView.addItemDecoration(itemDecorator);
        listingsView.setHasFixedSize(true);
        listingsView.setAdapter(mainAdapter);


        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();


        String email = getIntent().getStringExtra("email");

        TextView textView = (TextView) findViewById(R.id.drawer_user);
        textView.setText("Hello, " + email);

        //setup navigation drawer layout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (RelativeLayout) findViewById(R.id.left_drawer);
        //spinner stuff

        Spinner pulldown = (Spinner) findViewById(R.id.doctor_pulldown);
        List<String> doctors_list = new ArrayList();



        doctors_list.add("Select an option"); //dummy option
        doctors_list.add("Doctor1");
        doctors_list.add("Doctor2");
        doctors_list.add("Doctor3");
        doctors_list.add("Doctor4");
        doctors_list.add("Doctor5");


        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, doctors_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pulldown.setAdapter(adapter);

        pulldown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("!!!!!!", ""+ adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        //setup actionbar toolbar
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }


        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()){

            case R.id.action_add:
                //go to new patient page
                Intent intent = new Intent(MainActivity.this, AddPatient.class);
                startActivity(intent);

                return true;

            case R.id.action_sort:
                //sorts
                mainAdapter.sortPatients();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_homescreen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_add).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    
    @Override
    public void onStart() {
        super.onStart();

        mConnectedListener = FB.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if(connected) {
                    Toast.makeText(MainActivity.this, "CONNECTED TO THIS", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //ok
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        FB.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
//        PLA.cleanup();
    }

    public static ChrisAdapter getViewAdapter() {
        return mainAdapter;
    }


    public void logout(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        finish();
        startActivity(intent);

    }
}



