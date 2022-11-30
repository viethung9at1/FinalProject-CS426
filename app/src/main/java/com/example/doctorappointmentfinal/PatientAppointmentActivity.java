package com.example.doctorappointmentfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.doctorappointmentfinal.adapter.TypeAppointmentAdapter;
import com.example.doctorappointmentfinal.fragmentAppointment.PatientChooseDoctorFragment;
import com.example.doctorappointmentfinal.fragmentAppointment.PatientChooseProfileFragment;
import com.example.doctorappointmentfinal.model.TypeAppointment;

import java.util.ArrayList;
import java.util.List;

public class PatientAppointmentActivity extends AppCompatActivity implements TypeAppointmentAdapter.ItemClickListener{


    TypeAppointmentAdapter typeAppointmentAdapter;
    RecyclerView recyclerViewTypeAppointmentList;
    //List<TypeAppointment> typeAppointmentList;
    //RecyclerView.LayoutManager layoutManager;
    ArrayList<TypeAppointment> typeAppointmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_appointment);
        recyclerViewTypeAppointmentList=findViewById(R.id.recyclerTypeAppointment);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerViewTypeAppointmentList.setLayoutManager(linearLayoutManager);
        typeAppointmentList=new ArrayList<>();
        typeAppointmentList.add(new TypeAppointment("Go to clinic", "typeappointment1clinic"));
        typeAppointmentList.add(new TypeAppointment("Video call now", "typeappointment2vid"));
        typeAppointmentList.add(new TypeAppointment("Video call later", "typeappointment3later"));
        typeAppointmentAdapter=new TypeAppointmentAdapter(typeAppointmentList, this);
        recyclerViewTypeAppointmentList.setAdapter(typeAppointmentAdapter);
        //Button newFragment=findViewById(R.id.buttonTmp);
        //TextView textViewTmp=findViewById(R.id.textViewTmp);
        /*newFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = null;
                fragment=new PatientChooseDoctorFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
                transaction.replace(R.id.patientAppointmentContainer,fragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();
                newFragment.setVisibility(View.GONE);
                textViewTmp.setVisibility(View.GONE);
            }
        });*/

        /*recyclerTypeAppointment=(RecyclerView) findViewById(R.id.recyclerTypeAppointment);
        recyclerTypeAppointment.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerTypeAppointment.setLayoutManager(layoutManager);
        loadTypeAppointment();*/
    }

    private void recyclerViewTypeAppointment() {



    }

    private void loadTypeAppointment() {

    }
    public static boolean WithCallNow=false;
    @Override
    public void onItemClick(TypeAppointment typeappointment) {
        /*if(typeappointment.getTypeAppointment().equals("Go to clinic")||typeappointment.getTypeAppointment().equals("Video call later"))
            WithCallNow=false;
        else WithCallNow=true;*/

        //bundle
        Bundle bundle=new Bundle();
        String value=typeappointment.getTypeAppointment();
        bundle.putString("messageToProfile", value);
        TypeAppointment.CurrentType=typeappointment;
        // new fragment
        Fragment fragment =new PatientChooseProfileFragment();
        // set argument
        fragment.setArguments(bundle);
        //transact fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
        transaction.add(R.id.patientAppointmentContainer,fragment, "step2ChoosePatient");
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();

        TextView chooseTypeAppointment=(TextView) findViewById(R.id.chooseTypeAppointment);
        RecyclerView recyclerTypeAppointment=(RecyclerView) findViewById(R.id.recyclerTypeAppointment);
        chooseTypeAppointment.setVisibility(View.INVISIBLE);
        recyclerTypeAppointment.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();

        if (fm.getBackStackEntryCount() >= 0) {
            TextView chooseTypeAppointment=(TextView) findViewById(R.id.chooseTypeAppointment);
            RecyclerView recyclerTypeAppointment=(RecyclerView) findViewById(R.id.recyclerTypeAppointment);
            chooseTypeAppointment.setVisibility(View.VISIBLE);
            recyclerTypeAppointment.setVisibility(View.VISIBLE);
        }

        super.onBackPressed();
    }
}