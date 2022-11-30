package com.example.doctorappointmentfinal.model;

public class AddPatientProfile {
    private String profileFullName;

    public String getProfileFullName() {
        return profileFullName;
    }

    public void setProfileFullName(String profileFullName) {
        this.profileFullName = profileFullName;
    }

    public String getProfileGender() {
        return profileGender;
    }

    public void setProfileGender(String profileGender) {
        this.profileGender = profileGender;
    }

    public String getProfileDob() {
        return profileDob;
    }

    public void setProfileDob(String profileDob) {
        this.profileDob = profileDob;
    }

    private String profileGender;

    public AddPatientProfile(String profileFullName, String profileGender, String profileDob) {
        this.profileFullName = profileFullName;
        this.profileGender = profileGender;
        this.profileDob = profileDob;
    }

    private String profileDob;

}
