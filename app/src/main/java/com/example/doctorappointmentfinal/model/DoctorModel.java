package com.example.doctorappointmentfinal.model;

public class DoctorModel {
    private String doctorFullName;
    private Integer doctorYearsOfExperience;
    private float doctorRating;
    private String doctorAddressOfClinic;
    private String doctorPic;

    public DoctorModel(String doctorFullName, Integer doctorYearsOfExperience, float doctorRating, String doctorAddressOfClinic, String doctorPic) {
        this.doctorFullName = doctorFullName;
        this.doctorYearsOfExperience = doctorYearsOfExperience;
        this.doctorRating = doctorRating;
        this.doctorAddressOfClinic = doctorAddressOfClinic;
        this.doctorPic = doctorPic;
    }

    public String getDoctorFullName() {
        return doctorFullName;
    }

    public void setDoctorFullName(String doctorFullName) {
        this.doctorFullName = doctorFullName;
    }

    public Integer getDoctorYearsOfExperience() {
        return doctorYearsOfExperience;
    }

    public void setDoctorYearsOfExperience(Integer doctorYearsOfExperience) {
        this.doctorYearsOfExperience = doctorYearsOfExperience;
    }

    public float getDoctorRating() {
        return doctorRating;
    }

    public void setDoctorRating(float doctorRating) {
        this.doctorRating = doctorRating;
    }

    public String getDoctorAddressOfClinic() {
        return doctorAddressOfClinic;
    }

    public void setDoctorAddressOfClinic(String doctorAddressOfClinic) {
        this.doctorAddressOfClinic = doctorAddressOfClinic;
    }

    public String getDoctorPic() {
        return doctorPic;
    }

    public void setDoctorPic(String doctorPic) {
        this.doctorPic = doctorPic;
    }
}
