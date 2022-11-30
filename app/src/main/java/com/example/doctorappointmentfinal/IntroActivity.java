package com.example.doctorappointmentfinal;

import static com.example.doctorappointmentfinal.MainActivityPatient.doctorCreate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess;
import com.example.doctorappointmentfinal.spinnerAccount.Login_Spinner;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class IntroActivity extends AppCompatActivity {

    Handler handler;
    Runnable runnable;
    ImageView logoimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseNumberAndDateTimeProcess.db= FirebaseFirestore.getInstance();
        FirebaseNumberAndDateTimeProcess.storageReference= FirebaseStorage.getInstance().getReference();
        setContentView(R.layout.activity_intro);
        logoimg=findViewById(R.id.logoimg);
        logoimg.animate().alpha(1).setDuration(0);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //doctorCreate();
                Intent dsp=new Intent(IntroActivity.this, Login_Spinner.class);
                //Intent dsp=new Intent(IntroActivity.this, Sign_Up_Spinner.class);
                //Intent dsp=new Intent(IntroActivity.this, MainActivityPatient.class);
                //Intent dsp=new Intent(IntroActivity.this, MainActivityDoctor.class);
                startActivity(dsp);
                //finish();
            }
        }, 3000);
    }
}