package com.example.ryanchan.handoffhelper;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {



    //menu stuff
    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    //mainscreen stuff
    private List<Patient> myPatients = new ArrayList<Patient>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //patients in listview
        populatePatientList();
        populateListView();
        registerClickCallback();

        //setup navigation drawer layout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (LinearLayout) findViewById(R.id.left_drawer);


        //setup actionbar toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


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

    //populates list of test patients
    private void populatePatientList() {
        for (int i = 0; i < 20; i++) {
            Patient temp = new Patient(i + "", "M", 69);
            temp.setChiefComplaint("HELP ME PLEASE I'M DYING BRO HELP ME PLEASE I'M DYING BRO HELP ME PLEASE I'M DYING BRO");
            myPatients.add(temp);
        }
    }
    //puts them in the screen
    private void populateListView() {
        ArrayAdapter<Patient> adapter = new PatientListAdapter();
        ListView list = (ListView) findViewById(R.id.patientListView);
        list.setAdapter(adapter);
    }

    //makes list clickable
    private void registerClickCallback(){
        ListView list = (ListView)findViewById(R.id.patientListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id){
                Patient clickedPatient = myPatients.get(position);
                String message = "DAMN SON this will take us to patient page someday.\n" +
                        "You clicked the patient in bed " + clickedPatient.getBed() +
                        " who is a " + clickedPatient.getAge() + " year old " + clickedPatient.getSex() +
                        ".";
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }



    private class PatientListAdapter extends ArrayAdapter<Patient> {
        public PatientListAdapter() {
            super(MainActivity.this, R.layout.listview_patient, myPatients);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null)
                itemView = getLayoutInflater().inflate(R.layout.listview_patient, parent, false);

            Patient currentPatient = myPatients.get(position);

            TextView bedText = (TextView) itemView.findViewById(R.id.patientBed);
            bedText.setText("BED " + currentPatient.getBed());

            TextView ageText = (TextView) itemView.findViewById(R.id.patientAgeSex);
            ageText.setText(""+currentPatient.getAge() +"yo " + currentPatient.getSex());

            TextView sexText = (TextView) itemView.findViewById(R.id.patientCC);
            sexText.setText(currentPatient.getChiefComplaint());

            return itemView;
        }


    }




}



