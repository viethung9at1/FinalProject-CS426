package com.example.doctorappointmentfinal.appclass;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.sql.Time;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FirebaseNumberAndDateTimeProcess {
    public static FirebaseFirestore db;
    public static StorageReference storageReference;
    public static String dateToString(Date d, String pattern){
        String res;
        //String pattern="E, dd/MM/yyyy";
        Format df=new SimpleDateFormat(pattern);
        res=df.format(d);
        return res;
    }
    public static Date stringToDate(String s, String pattern){
        Date d=new Date();
        DateFormat df=new SimpleDateFormat(pattern);
        try {
            d=df.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }
    public static Time stringToTime(String s) throws ParseException {
        DateFormat df=new SimpleDateFormat("hh:mm:ss");
        Date d=df.parse(s);
        return new Time(d.getHours(),d.getMinutes(), d.getSeconds());
    }
    public static String timeToString(Time s, String format){
        DateFormat df=new SimpleDateFormat(format);
        return df.format(s);
    }
    public static int randomInt(int min, int max){
        return new Random().nextInt(max-min)+min;
    }
    public static char randomChar(char min, char max){
        return (char) (new Random().nextInt(max-min)+min);
    }
}
