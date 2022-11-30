 package com.example.doctorappointmentfinal;

import static com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess.dateToString;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess;
import com.example.doctorappointmentfinal.appclass.Doctor;
import com.example.doctorappointmentfinal.appclass.Faculty;
import com.example.doctorappointmentfinal.appclass.HospitalOrder;
import com.example.doctorappointmentfinal.appclass.Order;
import com.example.doctorappointmentfinal.appclass.Patient;
import com.example.doctorappointmentfinal.appclass.User;
import com.example.doctorappointmentfinal.appclass.VideoCallOrder;
import com.example.doctorappointmentfinal.fragmentPatient.PatientHistoryFragment;
import com.example.doctorappointmentfinal.fragmentPatient.PatientHomeFragment;
import com.example.doctorappointmentfinal.fragmentPatient.PatientProfileFragment;
import com.example.doctorappointmentfinal.fragmentPatient.PatientSettingsFragment;
import com.google.android.gms.tasks.OnCompleteListener;

import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

 public class MainActivityPatient extends AppCompatActivity {
    BottomNavigationView bottomNavigationViewPatient;
    private DatabaseReference mDatabase;
    private FirebaseFirestore db;
    final Fragment patientHomeFragment=new PatientHomeFragment();
    final Fragment patientProfileFragment=new PatientProfileFragment();
    //final Fragment patientAppointmentFragment=new PatientAppointmentFragment();
    final Fragment patientHistoryFragment=new PatientHistoryFragment();
    final Fragment patientSettingsFragment = new PatientSettingsFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = patientHomeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_patient);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        db=FirebaseFirestore.getInstance();
        //facultyCreate(); //run only the first time, please don't uncomment
        //doctorCreate(); //run only the first time, please don't uncomment
        //userCreate(); //run only the first time, please don't uncomment
        //loadUser(); //run only the first time, please don't uncomment
        //loadDoctor(); //run only the first time, please don't uncomment
        //loadPatients(); //run only the first time, please don't uncomment

        bottomNavigationViewPatient=(BottomNavigationView) findViewById(R.id.bottom_nav_patient);
        //getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, homeFragment).commit();
        fm.beginTransaction().add(R.id.mainPatientContainer, patientSettingsFragment, "5").hide(patientSettingsFragment).commit();
        fm.beginTransaction().add(R.id.mainPatientContainer, patientHistoryFragment, "4").hide(patientHistoryFragment).commit();
        //fm.beginTransaction().add(R.id.mainPatientContainer, patientAppointmentFragment, "3").hide(patientAppointmentFragment).commit();
        fm.beginTransaction().add(R.id.mainPatientContainer, patientProfileFragment, "2").hide(patientProfileFragment).commit();
        fm.beginTransaction().add(R.id.mainPatientContainer, patientHomeFragment, "1").commit();

        bottomNavigationViewPatient.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.patientHomeId:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, homeFragment).commit();
                        fm.beginTransaction().hide(active).show(patientHomeFragment).commit();
                        active=patientHomeFragment;
                        return true;
                    case R.id.patientProfileId:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, profileFragment).commit();
                        fm.beginTransaction().hide(active).show(patientProfileFragment).commit();
                        active=patientProfileFragment;
                        return true;
                    /*case R.id.patientAppointmentId:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, profileFragment).commit();
                        fm.beginTransaction().hide(active).show(patientAppointmentFragment).commit();
                        active=patientAppointmentFragment;
                        return true;*/
                    case R.id.patientHistoryId:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, historyFragment).commit();
                        fm.beginTransaction().hide(active).show(patientHistoryFragment).commit();
                        active=patientHistoryFragment;
                        return true;
                    case R.id.patientSettingsId:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, chatFragment).commit();
                        fm.beginTransaction().hide(active).show(patientSettingsFragment).commit();
                        active=patientSettingsFragment;
                        return true;
                }
                return false;
            }
        });


        FloatingActionButton fab=(FloatingActionButton) findViewById(R.id.addAppointment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Fragment fragment = null;
                fragment=new PatientAppointmentFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
                transaction.replace(R.id.mainPatientContainer,fragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();*/
                Intent appointmentActivity=new Intent(MainActivityPatient.this, PatientAppointmentActivity.class);
                startActivity(appointmentActivity);
            }
        });


    }
    private void userCreate(){
        User[] user=new User[12];
        user[0]=new User("D1","4I4IlI7U","EuK");
        user[1]=new User("D2","5YOhy5XBS5I484q","486MmcNpJOTmgGUB65");
        user[2]=new User("D3","c00grhgX7gSH7cgv","XB");
        user[3]=new User("D4","5ss5h4052MEs","4Qm20B");
        user[4]=new User("D5","X","a3bC8toW");
        user[5]=new User("D6","RAf2i","C8mUDVm1g2UhinQ");
        user[6]=new User("D7","If","5HbT12R");
        user[7]=new User("D8","83M42AGTUDAEiJ4Ua4MB","8j2oMyp1");
        user[8]=new User("D9","sx3B8740UjM","8ml");
        user[9]=new User("P1","74OGr551Qd1xt78Rgiw3","3l5H8lSAtCunQO1");
        user[10]=new User("P4","H","1MkVN");
        user[11]=new User("P14","g8fbl607YD6h3q","7YmG6j");
        for(int i=0;i<user.length;i++) {
            Map<String, Object> users = new HashMap<>();
            users.put("ID", user[i].ID);
            users.put("Username", user[i].Username);
            users.put("Password",user[i].Password);
            db.collection("Users").add(user[i]);
        }
    }

    private void facultyCreate() {
        Faculty[] faculty=new Faculty[6];
        faculty[0]=new Faculty("F1","Cardiology","s1heart");
        faculty[1]=new Faculty("F2","Neurology","s2brain");
        faculty[2]=new Faculty("F3","Gastroenterology","s3gastroenterology");
        faculty[3]=new Faculty("F4","Otolaryngology","s4otolaryngology");
        faculty[4]=new Faculty("F5","Urology","s5urology");
        faculty[5]=new Faculty("F6","Eye","s6eye");
        Map<String, Object> u=new HashMap<>();
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        String TAG="123";
        for(int i=0;i<faculty.length;i++) {
            Map<String, Object> user = new HashMap<>();
            user.put("ID", faculty[i].ID);
            user.put("Name", faculty[i].Name);
            user.put("PicName",faculty[i].PicName);
            db.collection("Faculty").add(user);
        }
    }
    public static void doctorCreate(){
        Doctor[] doctor=new Doctor[9];
        Patient[] patient=new Patient[18];
        HospitalOrder[] hospitalOrders=new HospitalOrder[18];
        VideoCallOrder[] videoCallOrders=new VideoCallOrder[18];
        doctor[0]=new Doctor("D1","Jeremia Korinn",false,new Date(97,6,12),"6738031978","F2",23,"PO Box 6652", "doctormale1.png");
        doctor[1]=new Doctor("D2","Marle Kas",true,new Date(06,9,6),"5759818119","F2",9,"9642 Sharon St","doctorfemale3.png");
        doctor[2]=new Doctor("D3","Erli Marchell",false,new Date(51,3,24),"2453138021","F4",6,"160 Franklin St�", "doctormale1.png");
        doctor[3]=new Doctor("D4","Schneide Rah",true,new Date(86,2,7),"4612088990","F4",32,"4310 Isle Of Pines Dr", "doctorfemale2.png");
        doctor[4]=new Doctor("D5","Maribe Jenefe",false,new Date(95,5,27),"9844381639","F2",25,"82792 Hartford Ave","doctormale1.png");
        doctor[5]=new Doctor("D6","Leo Sante",false,new Date(24,3,5),"0318411960","F3",8,"1307 W Lafayette St","doctormale2.png");
        doctor[6]=new Doctor("D7","Dair Manoa",false,new Date(75,11,11),"6932046208","F5",12,"9937 Hawthorn Hill Ct","doctormale1.png");
        doctor[7]=new Doctor("D8","Jonit Joesp",false,new Date(26,1,25),"1259248300","F1",27,"2725 Benson Mill Rd","doctormale2.png");
        doctor[8]=new Doctor("D9","Vianne Nahee",false,new Date(78,2,28),"2021572579","F3",16,"1533 Berkeley Way, Apt 4","doctormale3.png");
        patient[0]=new Patient("P1","Whit Arl",true,new Date(9,4,24),"3267975539","paul@civelec.com","14411 Old Columbia Pike","P1","person");
        patient[1]=new Patient("P2","Jayes Yar",false,new Date(75,1,15),"0724192196","GARY.FOSTER@aecom.COM","441 Mohonk Rd","P1","person");
        patient[2]=new Patient("P3","Aiyan Tarel",false,new Date(99,3,14),"3650272052","mrklink@gmail.com","1402 Denson Ave","P1","person");
        patient[3]=new Patient("P4","Jari Ziomar",true,new Date(22,8,17),"2913079517","rcribbs@hlaengineers.com","8400 Old Melones Rd, Spc 27","P4","person");
        patient[4]=new Patient("P5","Thelm Lashawnn",true,new Date(94,11,23),"0691599142","smina002@fiu.edu","11806 Evesborough Dr","P4","person");
        patient[5]=new Patient("P6","Ladaish Eldre",false,new Date(82,6,17),"7692591715","jbarfield2@gmail.com","3752 Darcus St","P4","person");
        patient[6]=new Patient("P7","Lachasit Tawny",false,new Date(93,3,21),"7306554129","juancbl@yahoo.com","10120 120th St","P4","person");
        patient[7]=new Patient("P8","Korri Allesandr",false,new Date(64,3,4),"8039176767","gator7eng@gmail.com","2135 Little Orchard St, Spc 94","P4","person");
        patient[8]=new Patient("P9","Holla Li",false,new Date(06,2,15),"9751345808","rogueangel50@comcast.net","1706 Laurel Dr","P4","person");
        patient[9]=new Patient("P10","Shaylyn Randa",false,new Date(89,7,21),"3355886880","joe@joekowalski.com","9642 Sharon St","P4","person");
        patient[10]=new Patient("P11","Lom Kachin",true,new Date(74,2,5),"6152367741","SH_DAVIDSON@MESCOBLDG.COM","11 Bell Schoolhouse Rd","P4","person");
        patient[11]=new Patient("P12","Tashawnd Maxwel",false,new Date(26,2,16),"9199346602","janiceda@hotmail.com","5070 Seventh Rd S, Apt T1","P4","person");
        patient[12]=new Patient("P13","Voness Trin",true,new Date(77,4,8),"2608925106","lulymrtnz@comcast.net","125 Windhover Way","P4","person");
        patient[13]=new Patient("P14","Shaleth Christphe",false,new Date(59,8,16),"7772199837","DBrannon@BnGEngineers.com","207 W William Cannon Dr","P14","person");
        patient[14]=new Patient("P15","Brittane Rahee",true,new Date(59,10,28),"6130234649","Ira.brandell@aecom.com","4101 216th St","P14","person");
        patient[15]=new Patient("P16","Estrell Courtne",true,new Date(65,2,7),"6404451218","jenniferysantiago@hotmail.com","40020 Milkwood Ln","P14","person");
        patient[16]=new Patient("P17","Kailan Vest",false,new Date(13,7,10),"1528821489","nickcarin@yahoo.com","14714 Hill Rd","P14","person");
        patient[17]=new Patient("P18","Aria Tyse",false,new Date(07,4,29),"5721962813","mlcochrane@bellsouth.net","272 Delaware Ave","P14","person");
        Random rd=new Random();
        for(int i=0;i<hospitalOrders.length;i++){
            Time sTime=new Time(rd.nextInt(24),rd.nextInt(60),rd.nextInt(60));
            Time eTime=new Time(rd.nextInt(24),rd.nextInt(60),rd.nextInt(60));
            while(sTime.after(eTime)){
                sTime=new Time(rd.nextInt(24),rd.nextInt(60),rd.nextInt(60));
                eTime=new Time(rd.nextInt(24),rd.nextInt(60),rd.nextInt(60));
            }
            Date dApp=new Date(rd.nextInt(22)+100,rd.nextInt(12),rd.nextInt(32));
            videoCallOrders[i]=new VideoCallOrder("V"+Integer.toString(i+1),"D"+Integer.toString(rd.nextInt(9)+1),sTime, eTime,"P"+Integer.toString(rd.nextInt(18)+1),rd.nextFloat()+rd.nextInt(10),dApp);
            Map<String, Object> order=Order.convertToMap(videoCallOrders[i]);
            //db.collection("Orders").add(order);
        }
        for(int i=0;i<hospitalOrders.length;i++){
            Time sTime=new Time(rd.nextInt(24),rd.nextInt(60),rd.nextInt(60));
            Time eTime=new Time(rd.nextInt(24),rd.nextInt(60),rd.nextInt(60));
            while(sTime.after(eTime)){
                sTime=new Time(rd.nextInt(24),rd.nextInt(60),rd.nextInt(60));
                eTime=new Time(rd.nextInt(24),rd.nextInt(60),rd.nextInt(60));
            }
            Date dApp=new Date(rd.nextInt(22)+100,rd.nextInt(12),rd.nextInt(32));
            int doctorIndex=rd.nextInt(9);
            hospitalOrders[i]=new HospitalOrder("H"+Integer.toString(i+1),"D"+Integer.toString(doctorIndex+1),sTime, eTime,"P"+Integer.toString(rd.nextInt(18)+1),rd.nextFloat()+rd.nextInt(10),dApp,doctor[doctorIndex].Address,Character.toString(FirebaseNumberAndDateTimeProcess.randomChar('A','F'))+Integer.toString(FirebaseNumberAndDateTimeProcess.randomInt(1,10)));
            Map<String, Object> order=Order.convertToMap(hospitalOrders[i]);
            //db.collection("Orders").add(order);
        }
        String TAG="123";
        for(int i=0;i<doctor.length;i++) {
            Map<String, Object> user = new HashMap<>();
            user.put("ID", doctor[i].ID);
            user.put("Name", doctor[i].Name);
            user.put("Gender",doctor[i].Gender);
            user.put("DateOfBirth", dateToString(doctor[i].DateOfBrith,"dd/MM/yyyy"));
            user.put("PhoneNumber",doctor[i].PhoneNumber);
            user.put("Specialize",doctor[i].SpecialityID);
            user.put("Experience",doctor[i].Experience);
            user.put("Address",doctor[i].Address);
            FirebaseStorage firebaseStorage= FirebaseStorage.getInstance();
            StorageReference sRef=firebaseStorage.getReference().child("/DoctorImage");
            sRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {
                    int numOfFemalePic, numOfMalePic;
                    numOfFemalePic=numOfMalePic=0;
                    for(StorageReference item:listResult.getItems()){
                        if(item.getName().startsWith("doctorfemale")) numOfFemalePic++;
                        else if(item.getName().startsWith("doctormale")) numOfMalePic++;
                    }
                    Random rd=new Random();
                    if (!Boolean.parseBoolean(user.get("Gender").toString())) {
                        int c=rd.nextInt(numOfMalePic+1);
                        user.put("PicImage","doctormale"+Integer.toString(c));
                    }
                    else {
                        int c=rd.nextInt(numOfFemalePic+1);
                        user.put("PicImage","doctorfemale"+Integer.toString(c));
                    }
                    //FirebaseNumberAndDateTimeProcess.db.collection("Doctors").add(user);
                }
            });
        }
        for(int i=0;i<patient.length;i++) {
            Map<String, Object> user = new HashMap<>();
            user.put("ID", patient[i].ID);
            user.put("Name", patient[i].Name);
            user.put("Gender", patient[i].Gender);
            user.put("DateOfBirth", dateToString(patient[i].DateOfBrith,"dd/MM/yyyy"));
            user.put("PhoneNumber", patient[i].PhoneNumber);
            user.put("Email", patient[i].Email);
            user.put("Address", patient[i].Address);
            user.put("BelongToID",patient[i].BelongToID);
            user.put("PicImage","person");
            FirebaseNumberAndDateTimeProcess.db.collection("Patients").add(user);
        }
    }
    private ArrayList<User> loadUser(){
        ArrayList<User> userList=new ArrayList<>();// the array that store data
        db.collection("Patients").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){
                                User u=document.toObject(User.class);
                                userList.add(u);
                                //document.getData();//return each record as Map<String, Object>
                            }
                        }
                        else{
                            Log.d("123","Error");
                        }
                    }
                });
        return userList;
    }
    private ArrayList<Doctor> loadDoctor(){
        ArrayList<Doctor> doctorList=new ArrayList<>();
        db.collection("Doctors").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){
                                Doctor d=new Doctor();
                                Map<String,Object> u=document.getData();
                                d.ID=u.get("ID").toString();
                                d.Name=u.get("Name").toString();
                                d.Gender=Boolean.parseBoolean(u.get("Gender").toString());
                                d.PhoneNumber=u.get("PhoneNumber").toString();
                                d.SpecialityID=u.get("Specialize").toString();
                                d.Experience=Integer.parseInt(u.get("Experience").toString());
                                d.DateOfBrith= FirebaseNumberAndDateTimeProcess.stringToDate(u.get("DateOfBirth").toString(),"dd/MM/yyyy");
                                doctorList.add(d);
                            }
                        }
                    }
                });
        return doctorList;
    }
    private ArrayList<Patient> loadPatients(){
        ArrayList<Patient> patientList=new ArrayList<>();
        db.collection("Patients").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){
                                Patient p=new Patient();
                                Map<String,Object> u=document.getData();
                                p.ID=u.get("ID").toString();
                                p.Name=u.get("Name").toString();
                                p.Gender=Boolean.parseBoolean(u.get("Gender").toString());
                                p.PhoneNumber=u.get("PhoneNumber").toString();
                                p.Email=u.get("Email").toString();
                                p.Address=u.get("Address").toString();
                                p.DateOfBrith= FirebaseNumberAndDateTimeProcess.stringToDate(u.get("DateOfBirth").toString(),"dd/MM/yyyy");
                                patientList.add(p);
                            }
                        }
                    }
                });
        return patientList;
    }
    public ArrayList<Order> loadOrder(){
        ArrayList<Order> orders=new ArrayList<>();
        db.collection("Orders").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult()){

                            }
                        }
                    }
                });
        return orders;
    }
}