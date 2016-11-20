package com.example.ryanchan.handoffhelper;

import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.duration;

public class AddPatient extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

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

        EditText sex = (EditText) findViewById(R.id.EditGender);
        String gender = sex.getText().toString();


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
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, Integer.toString(age), Toast.LENGTH_SHORT);
            toast.show();


            // After uploading new patient, heads back to main page
            Intent intent = new Intent(AddPatient.this, MainActivity.class);
            startActivity(intent);


        }
    }


}
