package com.example.ryanchan.handoffhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.firebase.client.Firebase;

/**
 * Created by Howard on 11/21/2016.
 */

public class EditPatient extends AppCompatActivity {
    Firebase FB = new Firebase("https://temp-hh.firebaseio.com/");


    private RadioGroup radioGroup;
    private RadioGroup genderGroup;
    private String bed;
    private String sex;
    private String age;
    private String chiefComplaint;
    private String diagnosis;
    private String testsOrdered;
    private int severity;
    private String planOfCare;
    private String contingency;

    private String doctor = "";
    private String handoff = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_patient);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        severity = addListenerOnButton();

        sex = GenderButton();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bed = extras.getString("PATIENT_BED"); //required
            sex = extras.getString("PATIENT_SEX"); //required
            age = extras.getString("PATIENT_AGE"); //required
            if (extras.getString("PATIENT_COMPLAINT") != null)
                chiefComplaint = extras.getString("PATIENT_COMPLAINT");
            if (extras.getString("PATIENT_DIAGNOSIS") != null)
                diagnosis = extras.getString("PATIENT_DIAGNOSIS");
            if (extras.getString("PATIENT_TESTS") != null)
                testsOrdered = extras.getString("PATIENT_TESTS");
            if (extras.getString("PATIENT_SEVERITY") != null)
                severity = Integer.parseInt(extras.getString("PATIENT_SEVERITY"));
            if (extras.getString("PATIENT_PLAN") != null)
                planOfCare = extras.getString("PATIENT_PLAN");
            if (extras.getString("PATIENT_CONTINGENCY") != null)
                contingency = extras.getString("PATIENT_CONTINGENCY");
            if (extras.getString("PATIENT_DOCTOR") != null){
                doctor = extras.getString("PATIENT_DOCTOR");
            }
            if(extras.getString("PATIENT_HANDOFF") != null)
                handoff = extras.getString("PATIENT_HANDOFF");
        }

        EditText bedEdit = (EditText)findViewById(R.id.EditBedNumber2);
        bedEdit.setText( bed + "");

        EditText ageEdit = (EditText)findViewById(R.id.EditAge2);
        ageEdit.setText(age + "");

        if (sex == "M")
            ((RadioButton) findViewById(R.id.male2)).setChecked(true);
        else
            ((RadioButton) findViewById(R.id.female2)).setChecked(true);

        if (severity != 6){
            if (severity == 1)
                ((RadioButton) findViewById(R.id.radio12)).setChecked(true);
            else if (severity == 2)
                ((RadioButton) findViewById(R.id.radio22)).setChecked(true);
            else if (severity == 3)
                ((RadioButton) findViewById(R.id.radio32)).setChecked(true);
            else if (severity == 4)
                ((RadioButton) findViewById(R.id.radio42)).setChecked(true);
            else if (severity == 5)
                ((RadioButton) findViewById(R.id.radio52)).setChecked(true);
        }

        EditText chiefComplaintEdit = (EditText)findViewById(R.id.EditCondition2);
        chiefComplaintEdit.append(chiefComplaint+"");

        EditText diagnosisEdit = (EditText)findViewById(R.id.EditDiagnosis2);
        diagnosisEdit.append(diagnosis+"");

        EditText testsOrderedEdit = (EditText)findViewById(R.id.EditTestsOrdered2);
        testsOrderedEdit.append(testsOrdered+"");

        EditText planOfCareEdit = (EditText)findViewById(R.id.EditPlanOfCare2);
        planOfCareEdit.append(planOfCare+"");

        EditText contingencyEdit = (EditText)findViewById(R.id.EditContingency2);
        contingencyEdit.append(contingency+"");

    }

    public String GenderButton(){
        genderGroup = (RadioGroup) findViewById(R.id.gender2);
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.male2:
                        sex = "M";
                        break;
                    case R.id.female2:
                        sex = "F";
                        break;
                }
            }
        });
        return sex;
    }

    public int addListenerOnButton(){

        radioGroup = (RadioGroup) findViewById(R.id.radio_group2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.radio12:
                        severity = 1;
                        break;

                    case R.id.radio22:
                        severity = 2;

                        break;
                    case R.id.radio32:
                        severity = 3;

                        break;
                    case R.id.radio42:
                        severity = 4;

                        break;
                    case R.id.radio52:
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
        EditText bed = (EditText) findViewById(R.id.EditBedNumber2);
        String bedNo = bed.getText().toString();

        EditText years = (EditText) findViewById(R.id.EditAge2);
        if (!years.getText().toString().equals("")) {

            age = Integer.parseInt(years.getText().toString());
        }




        if (bedNo.equals("") || sex.equals("") || age < 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditPatient.this);
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

            Patient patient = new Patient(bedNo, sex, age);

            patient.setSeverity(severity);

            EditText complaint = (EditText) findViewById(R.id.EditCondition2);
            String condition = complaint.getText().toString();
            patient.setChiefComplaint(condition);

            EditText diagnostic = (EditText) findViewById(R.id.EditDiagnosis2);
            String diagnosis = diagnostic.getText().toString();
            patient.setDiagnosis(diagnosis);

            EditText test = (EditText) findViewById(R.id.EditTestsOrdered2);
            String tests = test.getText().toString();
            patient.setTestsOrdered(tests);

            EditText plan = (EditText) findViewById(R.id.EditPlanOfCare2);
            String care = plan.getText().toString();
            patient.setPlanOfCare(care);

            EditText backup = (EditText) findViewById(R.id.EditContingency2);
            String contingency = backup.getText().toString();
            patient.setContingency(contingency);

            patient.setDoctor(doctor);
            patient.setHandoff(handoff);

            FB.child(patient.getBed()).setValue(patient);


            // After uploading new patient, heads back to main page
            Intent intent = new Intent(EditPatient.this, PatientProfile.class);

            intent.putExtra("PATIENT_BED",patient.getBed());
            intent.putExtra("PATIENT_SEX",patient.getSex());
            intent.putExtra("PATIENT_AGE",Integer.toString(patient.getAge()));
            intent.putExtra("PATIENT_COMPLAINT",patient.getChiefComplaint());
            intent.putExtra("PATIENT_DIAGNOSIS",patient.getDiagnosis());
            intent.putExtra("PATIENT_TESTS",patient.getTestsOrdered());
            intent.putExtra("PATIENT_SEVERITY",Integer.toString(patient.getSeverity()));
            intent.putExtra("PATIENT_PLAN",patient.getPlanOfCare());
            intent.putExtra("PATIENT_CONTINGENCY",patient.getContingency());
            intent.putExtra("PATIENT_DOCTOR", patient.getDoctor());
            intent.putExtra("PATIENT_HANDOFF", patient.getHandoff());

            startActivity(intent);

        }
    }
}
