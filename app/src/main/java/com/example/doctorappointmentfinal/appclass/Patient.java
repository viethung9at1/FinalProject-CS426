package com.example.doctorappointmentfinal.appclass;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Patient extends People {
    public String Email;
    public static Patient CurrentPatient;
    public String BelongToID;
    public Patient(String id, String name, boolean g, Date dob, String phoneNumber, String email, String address, String idBelongTo, String picImage){
        super(id, name, g, dob, phoneNumber,address, picImage);
        this.Email=email;
        this.Address=address;
        BelongToID=idBelongTo;
    }
    public Patient(){}
}
