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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

import static android.R.attr.duration;

public class PatientProfile extends AppCompatActivity {
    private String bed;
    private String sex;
    private String age;
    private String chiefComplaint;
    private String diagnosis;
    private String testsOrdered;
    private String severity;
    private String planOfCare;
    private String contingency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_profile);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bed = extras.getString("PATIENT_BED"); //required
            sex = extras.getString("PATIENT_SEX"); //required
            age = extras.getString("PATIENT_AGE"); //required
            chiefComplaint = extras.getString("PATIENT_COMPLAINT");
            diagnosis = extras.getString("PATIENT_DIAGNOSIS");
            testsOrdered = extras.getString("PATIENT_TESTS");
            severity = extras.getString("PATIENT_SEVERITY");
            planOfCare = extras.getString("PATIENT_PLAN");
            contingency = extras.getString("PATIENT_CONTINGENCY");
        }


        int severe = Integer.parseInt(severity);
        StringBuilder message = new StringBuilder();

        if (sex.equals("M")) {
            message.append("Bed number: " + bed + "\n" + age + " year old male");
        } else {
            message.append("Bed number: " + bed + "\n" + age + " year old female");
        }

        TextView descriptionView = (TextView) findViewById(R.id.PatientDescription);
        descriptionView.setText(message.toString());

        message.setLength(0);

        switch (severe) {
            case 1:
                //critical condition
                message.append("Critical Condition:\n");
                break;
            case 2:
                //somewhat critical
                message.append("Somewhat Critical Condition:\n");
                break;
            case 3:
                //moderate
                message.append("Moderate Condition:\n");
                break;
            case 4:
                //somewhat stable
                message.append("Somewhat Stable Condtiion:\n");
                break;
            case 5:
                //stable
                message.append("Stable Condition:\n");
                break;
            default:
                //No condition specified
                message.append("No Condition Specified:\n");
                break;
        }

        if (chiefComplaint.equals("")) {
            message.append("No complaint specified\n");
        } else {
            message.append(chiefComplaint);
        }

        TextView conditionView = (TextView) findViewById(R.id.PatientCondition);
        conditionView.setText(message.toString());

        message.setLength(0); //reset the stringbuilder, creating a new one makes CPU perform extra work
        message.append("DIAGNOSIS:\n");
        if (diagnosis.equals("")) {
            message.append("No diagnosis provided");
        } else {
            message.append(diagnosis);
        }
        TextView diagnosisView = (TextView) findViewById(R.id.PatientDiagnosis);
        diagnosisView.setText(message.toString());

        message.setLength(0);
        message.append("ORDERED:\n");
        if (testsOrdered.equals("")) {
            message.append("No tests ordered\n");
        } else {
            message.append(testsOrdered);
        }

        TextView orderView = (TextView) findViewById(R.id.PatientOrder);
        orderView.setText(message.toString());

        message.setLength(0);
        message.append("Plan of Care:\n");
        if (planOfCare.equals("")) {
            message.append("   -----------");
        } else {
            message.append("HEREHAR\n");
            message.append(planOfCare);
            message.append("\n");
        }
        TextView careView = (TextView) findViewById(R.id.PatientCare);
        careView.setText(message.toString());

        message.setLength(0);
        message.append("Contingency Plan:\n");
        if (contingency.equals("")) {
            message.append("    ---------------");
        } else {
            message.append(contingency);
        }

        TextView contingencyView = (TextView) findViewById(R.id.PatientContingency);
        contingencyView.setText(message.toString());


    }




//    public void savePatient(View view) {
//
//        int age = -1;
//
//        // constructs a patient
//        EditText bed = (EditText) findViewById(R.id.EditBedNumber);
//        String bedNo = bed.getText().toString();
//
//        EditText years = (EditText) findViewById(R.id.EditAge);
//        if (!years.getText().toString().equals("")) {
//
//            age = Integer.parseInt(years.getText().toString());
//        }
//
//        EditText sex = (EditText) findViewById(R.id.EditGender);
//        String gender = sex.getText().toString();
//
//
//        if (bedNo.equals("") || gender.equals("") || age < 0) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(PatientProfile.this);
//            builder.setMessage(R.string.dialog_message)
//                    .setTitle(R.string.dialog_title);
//
//
//            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//                    // User clicked OK button
//                    dialog.dismiss();
//                }
//            });
//
//            AlertDialog dialog = builder.create();
//            dialog.show();
//
//        } else {
//
//            Patient patient = new Patient(bedNo, gender, age);
//
//            EditText complaint = (EditText) findViewById(R.id.EditCondition);
//            String condition = complaint.getText().toString();
//            patient.setChiefComplaint(condition);
//
//            EditText diagnostic = (EditText) findViewById(R.id.EditDiagnosis);
//            String diagnosis = diagnostic.getText().toString();
//            patient.setDiagnosis(diagnosis);
//
//            EditText test = (EditText) findViewById(R.id.EditTestsOrdered);
//            String tests = test.getText().toString();
//            patient.setTestsOrdered(tests);
//
//            EditText plan = (EditText) findViewById(R.id.EditPlanOfCare);
//            String care = plan.getText().toString();
//            patient.setPlanOfCare(care);
//
//            EditText backup = (EditText) findViewById(R.id.EditContingency);
//            String contingency = backup.getText().toString();
//            patient.setContingency(contingency);
//
//            // print feedback
//            Context context = getApplicationContext();
//            Toast toast = Toast.makeText(context, Integer.toString(age), Toast.LENGTH_SHORT);
//            toast.show();
//
//
//            // After uploading new patient, heads back to main page
//            Intent intent = new Intent(PatientProfile.this, MainActivity.class);
//            startActivity(intent);
//
//
//        }
//    }


}
