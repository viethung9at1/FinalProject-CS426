package com.example.doctorappointmentfinal.model;

public class SpecialtyModel {
    String specialtyName;
    String specialtyPic;

    public SpecialtyModel(String specialtyName, String specialtyPic) {
        this.specialtyName = specialtyName;
        this.specialtyPic = specialtyPic;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public String getSpecialtyPic() {
        return specialtyPic;
    }

    public void setSpecialtyPic(String specialtyPic) {
        this.specialtyPic = specialtyPic;
    }
}
