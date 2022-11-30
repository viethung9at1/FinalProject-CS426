package com.example.doctorappointmentfinal.spinnerAccount;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorappointmentfinal.R;
import com.example.doctorappointmentfinal.appclass.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Sign_Up_Spinner extends AppCompatActivity {
    private Spinner spinner,spinnerSelectGender, spinnerSelectFaculty;
    private EditText edtEmail, edtExp;
    private LinearLayout layoutFaculty;
    private Button btnSignUp;
    FirebaseFirestore db;
    boolean[] loadState=new boolean[3];
    int doctorNum, patientNum;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        spinner = findViewById(R.id.typeUser_spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.typeUser_spinner, android.R.layout.simple_list_item_1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinnerSelectGender=findViewById(R.id.spinner_Gender);
        ArrayAdapter<CharSequence> adapterGender=ArrayAdapter.createFromResource(this,R.array.typeGender, android.R.layout.simple_list_item_1);
        spinnerSelectGender.setAdapter(adapterGender);
        spinnerSelectFaculty=findViewById(R.id.spinner_Faculty);
        //ArrayAdapter<CharSequence> adapterFaculty=ArrayAdapter.createFromResource(this,R.array.typeFaculty, android.R.layout.simple_spinner_item);
        //spinnerSelectFaculty.setAdapter(adapterFaculty);
        edtEmail=findViewById(R.id.text_email_2);
        layoutFaculty=findViewById(R.id.layoutFaculty);
        btnSignUp=findViewById(R.id.btn_create_account_2);
        edtExp=findViewById(R.id.text_experience_2);
        setSpinner();
        signUpClicked();
        doctorNum=patientNum=0;
        loadState[0]=loadState[1]=loadState[2]=false;
        db=FirebaseFirestore.getInstance();
        db.collection("Faculty").get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()) {
                                ArrayList<String> arrFaculty=new ArrayList<>();
                                for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                    Map<String, Object> m=documentSnapshot.getData();
                                    arrFaculty.add(m.get("Name").toString());
                                }
                                ArrayAdapter<String> adapterFaculty=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,arrFaculty);
                                spinnerSelectFaculty.setAdapter(adapterFaculty);
                                loadState[0]=true;
                            }
                        }
                    });
        /*db.collection("Users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                               Map<String, Object> m=documentSnapshot.getData();
                               if(m.get("ID").toString().startsWith("D")) doctorNum++;
                               else if(m.get("ID").toString().startsWith("P")) patientNum++;
                            }
                            loadState[0]=true;
                        }
                    }
                });*/
        db.collection("Patients").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        QuerySnapshot q=task.getResult();
                        patientNum=q.size();
                        loadState[1]=true;
                    }
                });
        db.collection("Doctors").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        QuerySnapshot q=task.getResult();
                        doctorNum=q.size();
                        loadState[2]=true;
                    }
                });
    }
    private void setSpinner(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    edtEmail.setVisibility(View.VISIBLE);
                    layoutFaculty.setVisibility(View.GONE);
                    edtExp.setVisibility(View.GONE);
                }
                else if(position==1) {
                    edtEmail.setVisibility(View.GONE);
                    layoutFaculty.setVisibility(View.VISIBLE);
                    edtExp.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void signUpClicked(){
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edPassword=findViewById(R.id.text_password_2);
                EditText edConfirm=findViewById(R.id.text_confirm_2);
                EditText edtName=findViewById(R.id.text_fullname_2);
                EditText edtPhoneNumber=findViewById(R.id.text_phone_2);
                EditText edtUsername=findViewById(R.id.text_username_2);
                EditText edtDOB=findViewById(R.id.text_DOB_2);
                EditText edtPassword=findViewById(R.id.text_password_2);
                EditText edtAddress=findViewById(R.id.text_address_2);
                int choiceType=spinner.getSelectedItemPosition();
                if(!loadState[0]||!loadState[1]||!loadState[2]){
                    Toast.makeText(Sign_Up_Spinner.this,"Loading data, please wait",Toast.LENGTH_LONG).show();
                    return;
                }

                if(edConfirm.getText().toString().isEmpty() || edtUsername.getText().toString().isEmpty() ||
                edPassword.getText().toString().isEmpty() || edtDOB.getText().toString().isEmpty() ||
                edtName.getText().toString().isEmpty()|| (edtEmail.getText().toString().isEmpty()&&choiceType==0)||
                        (edtAddress.getText().toString().isEmpty()) || (edtExp.getText().toString().isEmpty() && choiceType==1) ||
                        edtPhoneNumber.getText().toString().isEmpty()
                ){
                    Toast.makeText(Sign_Up_Spinner.this,"Please full fill the information",Toast.LENGTH_LONG).show();
                }
                if(!edPassword.getText().toString().equals(edConfirm.getText().toString())){
                    Toast.makeText(Sign_Up_Spinner.this, "Wrong password and confirmation", Toast.LENGTH_LONG).show();
                    return;
                }
                db.collection("Users").whereEqualTo("Username",edtUsername.getText().toString()).get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    if(!task.getResult().isEmpty()) Toast.makeText(Sign_Up_Spinner.this,"Change username",Toast.LENGTH_LONG).show();
                                    else {
                                        if(choiceType==0){
                                            Map<String, Object> m=new HashMap<>();
                                            m.put("ID","P"+Integer.toString(patientNum+1));
                                            m.put("Name",edtName.getText().toString());
                                            m.put("Gender",spinnerSelectGender.getSelectedItemId()==1?true:false);
                                            m.put("PhoneNumber",edtPhoneNumber.getText().toString());
                                            m.put("DateOfBirth",edtDOB.getText().toString());
                                            m.put("Address",edtAddress.getText().toString());
                                            m.put("Email",edtEmail.getText().toString());
                                            m.put("BelongToID","P"+Integer.toString(patientNum+1));
                                            m.put("PicImage","person");
                                            db.collection("Patients").add(m);
                                            m.clear();
                                            m.put("ID","P"+Integer.toString(patientNum+1));
                                            m.put("Username",edtUsername.getText().toString());
                                            m.put("Password",edtPassword.getText().toString());
                                            db.collection("Users").add(m);
                                            Intent intent=new Intent(Sign_Up_Spinner.this, Login_Spinner.class);
                                            startActivity(intent);
                                        }
                                        else if(choiceType==1){
                                            Map<String, Object> m=new HashMap<>();
                                            m.put("ID","D"+Integer.toString(doctorNum+1));
                                            m.put("Username",edtUsername.getText().toString());
                                            m.put("Password",edtPassword.getText().toString());
                                            db.collection("Users").add(m);
                                            m.clear();
                                            m.put("ID","D"+Integer.toString(doctorNum+1));
                                            m.put("Name",edtName.getText().toString());
                                            m.put("Gender",spinnerSelectGender.getSelectedItemId()==1?true:false);
                                            m.put("PhoneNumber",edtPhoneNumber.getText().toString());
                                            m.put("DateOfBirth",edtDOB.getText().toString());
                                            m.put("Experience",Integer.parseInt(edtExp.getText().toString()));
                                            m.put("Specialize","F"+Long.toString(spinnerSelectFaculty.getSelectedItemId()));
                                            m.put("Address",edtAddress.getText().toString());
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
                                                    if (!Boolean.parseBoolean(m.get("Gender").toString())) {
                                                        int c=rd.nextInt(numOfMalePic+1);
                                                        m.put("PicImage","doctormale"+Integer.toString(c));
                                                    }
                                                    else {
                                                        int c=rd.nextInt(numOfFemalePic+1);
                                                        m.put("PicImage","doctorfemale"+Integer.toString(c));
                                                    }
                                                    db.collection("Doctors").add(m);
                                                    Intent intent=new Intent(Sign_Up_Spinner.this, Login_Spinner.class);
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                        });
            }
        });
    }

}
