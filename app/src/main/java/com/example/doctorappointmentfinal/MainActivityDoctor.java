package com.example.doctorappointmentfinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.doctorappointmentfinal.fragmentDoctor.DoctorHomeFragment;
import com.example.doctorappointmentfinal.fragmentDoctor.DoctorSettingsFragment;
import com.example.doctorappointmentfinal.fragmentDoctor.DoctorVideoCallFragment;
import com.example.doctorappointmentfinal.fragmentPatient.PatientHomeFragment;
import com.example.doctorappointmentfinal.fragmentPatient.PatientSettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivityDoctor extends AppCompatActivity {

    BottomNavigationView bottomNavigationViewDoctor;
    final Fragment doctorHomeFragment=new DoctorHomeFragment();
    final Fragment doctorVideoCallFragment = new DoctorVideoCallFragment();
    final Fragment doctorSettingsFragment = new DoctorSettingsFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = doctorHomeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doctor);


        bottomNavigationViewDoctor=(BottomNavigationView) findViewById(R.id.bottom_nav_doctor);
        //getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, homeFragment).commit();
        fm.beginTransaction().add(R.id.mainDoctorContainer, doctorSettingsFragment, "4").hide(doctorSettingsFragment).commit();
        //fm.beginTransaction().add(R.id.mainPatientContainer, patientAppointmentFragment, "3").hide(patientAppointmentFragment).commit();
        fm.beginTransaction().add(R.id.mainDoctorContainer, doctorVideoCallFragment, "2").hide(doctorVideoCallFragment).commit();
        fm.beginTransaction().add(R.id.mainDoctorContainer, doctorHomeFragment, "1").commit();

        bottomNavigationViewDoctor.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.doctorHomeId:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, homeFragment).commit();
                        fm.beginTransaction().hide(active).show(doctorHomeFragment).commit();
                        active=doctorHomeFragment;
                        return true;
                    case R.id.doctorVidCallId:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, profileFragment).commit();

                        try {
                            JitsiMeetConferenceOptions options;
                            options = new JitsiMeetConferenceOptions.Builder()
                                    .setServerURL(new URL("https://meet.jit.si"))
                                    .setRoom("test123")
                                    .setAudioMuted(false)
                                    .setVideoMuted(false)
                                    .setAudioOnly(false)
                                    .setConfigOverride("requireDisplayName", true)
                                    .build();
                            JitsiMeetActivity.launch(MainActivityDoctor.this,options);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        fm.beginTransaction().hide(active).show(doctorVideoCallFragment).commit();
                        active=doctorVideoCallFragment;
                        return true;
                    /*case R.id.patientAppointmentId:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, profileFragment).commit();
                        fm.beginTransaction().hide(active).show(patientAppointmentFragment).commit();
                        active=patientAppointmentFragment;
                        return true;*/
                    case R.id.doctorSettingsId:
                        //getSupportFragmentManager().beginTransaction().replace(R.id.maincontainer, chatFragment).commit();
                        fm.beginTransaction().hide(active).show(doctorSettingsFragment).commit();
                        active=doctorSettingsFragment;
                        return true;
                }
                return false;
            }
        });
    }
}