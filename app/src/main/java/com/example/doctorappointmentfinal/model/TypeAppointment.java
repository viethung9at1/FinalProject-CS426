package com.example.doctorappointmentfinal.model;

import java.util.ArrayList;

public class TypeAppointment {
    private String typeAppointment;
    private String picAppointment;
    public static TypeAppointment CurrentType;

    public TypeAppointment(String typeAppointment, String pic) {
        this.typeAppointment = typeAppointment;
        this.picAppointment = pic;
    }

    public String getTypeAppointment() {
        return typeAppointment;
    }

    public void setTypeAppointment(String typeAppointment) {
        this.typeAppointment = typeAppointment;
    }

    public String getPicAppointment() {
        return picAppointment;
    }

    public void setPicAppointment(String picAppointment) {
        this.picAppointment = picAppointment;
    }
}
