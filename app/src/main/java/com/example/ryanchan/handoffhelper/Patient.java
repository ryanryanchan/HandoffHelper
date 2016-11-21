package com.example.ryanchan.handoffhelper;
import org.json.*;

import java.util.Comparator;

/**
 * Created by ryanchan on 11/15/16.
 */

public class Patient {
    private String bed;
    private String sex;
    private int age;
    private String chiefComplaint;
    private String diagnosis;
    private String testsOrdered;
    private int severity;
    private String planOfCare;
    private String contingency;


    Patient(){

    }

    Patient( String bed, String sex, int age){
        this.bed = bed;
        this.sex = sex;
        this.age = age;
        this.chiefComplaint = "";
        this.diagnosis = "";
        this.testsOrdered = "";
        this.severity = 6;
        this.planOfCare = "";
        this.contingency = "";

    }




    //getters
    public int getAge() {
        return age;
    }

    public int getSeverity() {
        return severity;
    }

    public String getBed() {return bed;}

    public String getChiefComplaint() {return chiefComplaint;}

    public String getSex() {return sex;}

    public String getContingency() {
        return contingency;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getPlanOfCare() {
        return planOfCare;
    }

    public String getTestsOrdered() {
        return testsOrdered;
    }

    //setters
    public void setAge(int age) {
        this.age = age;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public void setContingency(String contingency) {
        this.contingency = contingency;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setPlanOfCare(String planOfCare) {
        this.planOfCare = planOfCare;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public void setTestsOrdered(String testsOrdered) {
        this.testsOrdered = testsOrdered;
    }


    public static Comparator<Patient> PatientBedComparator = new Comparator<Patient>(){
        public int compare(Patient p1, Patient p2){
            String p1bed = p1.getBed();
            String p2bed = p2.getBed();
            return Integer.valueOf(p1bed)- Integer.valueOf(p2bed);
        }
    };

    public static Comparator<Patient> PatientSeverityComparator = new Comparator<Patient>() {
        public int compare(Patient p1, Patient p2){
            int p1severity = p1.getSeverity();
            int p2severity = p2.getSeverity();
            return p1severity-p2severity;
        }
    };

}

