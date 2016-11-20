package com.example.ryanchan.handoffhelper;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class MainActivity extends AppCompatActivity {
    private List<Patient> myPatients = new ArrayList<Patient>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populatePatientList();
        populateListView();
        registerClickCallback();

        //Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(myToolbar);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private void populatePatientList(){
        myPatients.add(new Patient("1","M",69));
        myPatients.add(new Patient("2","M",69));
        myPatients.add(new Patient("3","M",69));
        myPatients.add(new Patient("4","M",69));
        myPatients.add(new Patient("CD","M",69));
    }

    private void populateListView() {
        ArrayAdapter<Patient> adapter = new PatientListAdapter();
        ListView list = (ListView) findViewById(R.id.patientListView);
        list.setAdapter(adapter);
    }

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

            TextView ageText = (TextView) itemView.findViewById(R.id.patientAge);
            ageText.setText(""+currentPatient.getAge());

            TextView sexText = (TextView) itemView.findViewById(R.id.patientSex);
            sexText.setText(currentPatient.getSex());

            return itemView;
        }


    }
}
