package com.example.doctorappointmentfinal.model;

public class Patient_Appointment_List {
    public void setApp_time(String app_time) {
        this.app_time = app_time;
    }

    public void setDoctorPhoto(String doctorPhoto) {
        this.doctorPhoto = doctorPhoto;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    String app_time;

    public String getApp_time() {
        return app_time;
    }

    public String getDoctorPhoto() {
        return doctorPhoto;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getPatientName() {
        return patientName;
    }

    String doctorPhoto;
    String doctorName;
    String faculty;
    String patientName;

    public Patient_Appointment_List(String app_time, String doctorPhoto, String doctorName, String faculty, String patientName) {
        this.app_time = app_time;
        this.doctorPhoto = doctorPhoto;
        this.doctorName = doctorName;
        this.faculty = faculty;
        this.patientName = patientName;
    }
}
