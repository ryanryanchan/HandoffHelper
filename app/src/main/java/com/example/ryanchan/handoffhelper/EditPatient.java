package com.example.ryanchan.handoffhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
    Firebase FB = new Firebase("https://handoffhelper-657e2.firebaseio.com/");


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

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
        }

        EditText bedEdit = (EditText)findViewById(R.id.EditBedNumber2);
        bedEdit.append(bed+"");

        EditText ageEdit = (EditText)findViewById(R.id.EditAge2);
        ageEdit.append(age+"");

        if (sex == "M")
            ((RadioButton) findViewById(R.id.male2)).setChecked(true);
        else
            ((RadioButton) findViewById(R.id.female2)).setChecked(true);

        if (severity != 6){
            if (severity == 1)
                ((RadioButton) findViewById(R.id.radio12)).setChecked(true);
            else if (severity == 1)
                ((RadioButton) findViewById(R.id.radio22)).setChecked(true);
            else if (severity == 1)
                ((RadioButton) findViewById(R.id.radio32)).setChecked(true);
            else if (severity == 1)
                ((RadioButton) findViewById(R.id.radio42)).setChecked(true);
            else if (severity == 1)
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
        genderGroup = (RadioGroup) findViewById(R.id.gender);
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.male:
                        sex = "M";
                        break;
                    case R.id.female:
                        sex = "F";
                        break;
                }
            }
        });
        return sex;
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

            // print feedback
//            Context context = getApplicationContext();
//            Toast toast = Toast.makeText(context, Integer.toString(age), Toast.LENGTH_SHORT);
//            toast.show();

            FB.push().setValue(patient);


            // After uploading new patient, heads back to main page
            Intent intent = new Intent(EditPatient.this, PatientProfile.class);
            startActivity(intent);


        }
    }
}
