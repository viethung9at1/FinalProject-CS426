package com.example.doctorappointmentfinal.model;

public class PatientProfile {
    private String profileFullName;
    private String profileGender;
    private String profileDob;
    private String profilePic;

    public PatientProfile(String profileFullName, String profileGender, String profileDob, String profilePic) {
        this.profileFullName = profileFullName;
        this.profileGender = profileGender;
        this.profileDob = profileDob;
        this.profilePic = profilePic;
    }

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

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
