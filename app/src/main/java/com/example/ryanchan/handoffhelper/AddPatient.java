package com.example.ryanchan.handoffhelper;

import android.content.Intent;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.firebase.client.Firebase;

public class AddPatient extends AppCompatActivity {

    Firebase FB = new Firebase("https://temp-hh.firebaseio.com/");


    private RadioGroup radioGroup;
    private RadioGroup genderGroup;
    private int severity;
    private String gender = "";
    private String doctor = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        severity = addListenerOnButton();

        gender = GenderButton();

    }

    public String GenderButton(){
        genderGroup = (RadioGroup) findViewById(R.id.gender);
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.male:
                        gender = "Male";
                        break;
                    case R.id.female:
                        gender = "Female";
                        break;
                }
            }
        });
        return gender;
    }

    public int addListenerOnButton(){

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.radio1:
                        severity = 1;
                        break;

                    case R.id.radio2:
                        severity = 2;

                        break;
                    case R.id.radio3:
                        severity = 3;

                        break;
                    case R.id.radio4:
                        severity = 4;

                        break;
                    case R.id.radio5:
                        severity = 5;

                        break;
                }
            }
        });
        return severity;
    }

    public void savePatient(View view) {

        int age = -1;

        // constructs a patient
        EditText bed = (EditText) findViewById(R.id.EditBedNumber);
        String bedNo = bed.getText().toString();

        EditText years = (EditText) findViewById(R.id.EditAge);
        if (!years.getText().toString().equals("")) {

            age = Integer.parseInt(years.getText().toString());
        }




        if (bedNo.equals("") || gender.equals("") || age < 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPatient.this);
            builder.setMessage(R.string.dialog_message)
                    .setTitle(R.string.dialog_title);


            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        } else {

            Patient patient = new Patient(bedNo, gender, age);

            patient.setSeverity(severity);

            EditText complaint = (EditText) findViewById(R.id.EditCondition);
            String condition = complaint.getText().toString();
            patient.setChiefComplaint(condition);

            EditText diagnostic = (EditText) findViewById(R.id.EditDiagnosis);
            String diagnosis = diagnostic.getText().toString();
            patient.setDiagnosis(diagnosis);

            EditText test = (EditText) findViewById(R.id.EditTestsOrdered);
            String tests = test.getText().toString();
            patient.setTestsOrdered(tests);

            EditText plan = (EditText) findViewById(R.id.EditPlanOfCare);
            String care = plan.getText().toString();
            patient.setPlanOfCare(care);

            EditText backup = (EditText) findViewById(R.id.EditContingency);
            String contingency = backup.getText().toString();
            patient.setContingency(contingency);

            patient.setDoctor(getIntent().getStringExtra("doctor"));

            // print feedback
//            Context context = getApplicationContext();
//            Toast toast = Toast.makeText(context, Integer.toString(age), Toast.LENGTH_SHORT);
//            toast.show();

            FB.child(patient.getBed()).setValue(patient);

            Log.d("WHO ARE YOU",patient.getDoctor());
            // After uploading new patient, heads back to main page
            Intent intent = new Intent(AddPatient.this, MainActivity.class);
            intent.putExtra("email",patient.getDoctor());

            startActivity(intent);


        }
    }


}
