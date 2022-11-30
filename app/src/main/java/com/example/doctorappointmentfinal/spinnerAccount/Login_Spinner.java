package com.example.doctorappointmentfinal.spinnerAccount;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorappointmentfinal.MainActivityDoctor;
import com.example.doctorappointmentfinal.MainActivityPatient;
import com.example.doctorappointmentfinal.R;
import com.example.doctorappointmentfinal.appclass.Doctor;
import com.example.doctorappointmentfinal.appclass.Patient;
import com.example.doctorappointmentfinal.appclass.People;
import com.example.doctorappointmentfinal.appclass.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Login_Spinner extends AppCompatActivity {
    private Spinner spinner;
    private EditText txtUsername, txtPassword;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUsername=findViewById(R.id.text_username);
        txtPassword=findViewById(R.id.text_password);
        spinner = findViewById(R.id.typeUser_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.typeUser_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    private void Toast_MakeText(){
        Toast.makeText(this,"Wrong username/password",Toast.LENGTH_LONG).show();
    }
    public void changeActivity(Class cls){
        Intent i=new Intent(this,cls);
        startActivity(i);
    }
    public void btnLogin_onClick(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String username=txtUsername.getText().toString();
        String password=txtPassword.getText().toString();
        db.collection("Users").whereEqualTo("Username",username).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().isEmpty()) {
                                Toast_MakeText();
                                return;
                            }
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User u=User.covertFromQuery(document);
                                if(u.Password.equals(password)) User.CurrentUser=u;
                                else{
                                    Toast_MakeText();
                                    return;
                                }
                                break;
                            }
                            if(User.CurrentUser.ID.startsWith("P")){
                                db.collection("Patients").whereEqualTo("ID",User.CurrentUser.ID).get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            for (QueryDocumentSnapshot document1 : task.getResult()) {
                                                                Patient p = (Patient) People.convertFromQuery(document1);
                                                                Patient.CurrentPatient = p;
                                                                break;
                                                            }
                                                        }
                                                        changeActivity(MainActivityPatient.class);
                                                    }
                                                });
                            }
                            else if(User.CurrentUser.ID.startsWith("D")){
                                db.collection("Doctors").whereEqualTo("ID",User.CurrentUser.ID).get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document1 : task.getResult()) {
                                                        Doctor d=(Doctor) People.convertFromQuery(document1);
                                                        Doctor.CurrentDoctor=d;
                                                        break;
                                                    }
                                                    changeActivity(MainActivityDoctor.class);
                                                }
                                            }
                                        });
                            }
                        }
                        else Toast_MakeText();
                    }
                });
    }

    public void signUp_Click(View view) {
        changeActivity(Sign_Up_Spinner.class);
    }
}
