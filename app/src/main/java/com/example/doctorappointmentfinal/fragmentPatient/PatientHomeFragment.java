package com.example.doctorappointmentfinal.fragmentPatient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doctorappointmentfinal.R;
import com.example.doctorappointmentfinal.adapter.OrderPatientHomeAdapter;
import com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess;
import com.example.doctorappointmentfinal.appclass.Order;
import com.example.doctorappointmentfinal.appclass.Patient;
import com.example.doctorappointmentfinal.appclass.People;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class PatientHomeFragment extends Fragment implements OrderPatientHomeAdapter.ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientAppointmentHome.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientHomeFragment newInstance(String param1, String param2) {
        PatientHomeFragment fragment = new PatientHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_home, container, false);
        OrderPatientHomeAdapter orderPatientHomeAdapter;
        RecyclerView recyclerViewAppointmentPatientHome;
        recyclerViewAppointmentPatientHome = (RecyclerView) view.findViewById(R.id.recyclerview_patientNextAppointment);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewAppointmentPatientHome.setLayoutManager(linearLayoutManager);
        /*ArrayList<Patient_Appointment_List> patientAppointmentLists = new ArrayList<>();
        patientAppointmentLists.add(new Patient_Appointment_List("Wed, Aug 24, 2022 9:30 – 10:30 AM", "Avatar01",
                "Dr:Cristiano Ronaldo", "Cardiology", "Patient:Carlo Ancelotti"));
        patientAppointmentLists.add(new Patient_Appointment_List("Wed, Aug 28, 2022 9:30 – 10:30 AM", "Avatar01",
                "Dr:Lionel Messi", "Neurology", "Patient:Carlo Ancelotti"));
        patientAppointmentLists.add(new Patient_Appointment_List("Wed, Aug 28, 2022 9:30 – 10:30 AM", "Avatar01",
                "Dr:Roberto Carlos", "Otolaryngology", "Patient:Carlo Ancelotti"));*/

        ArrayList<Order> patientAppointmentLists = new ArrayList<>();
        FirebaseNumberAndDateTimeProcess.db.collection("Patients").whereEqualTo("BelongToID", Patient.CurrentPatient.ID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            Patient p=(Patient) People.convertFromQuery(documentSnapshot);
                            FirebaseNumberAndDateTimeProcess.db.collection("Orders").whereEqualTo("PatientID",p.ID).get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            for(QueryDocumentSnapshot documentSnapshot1: task.getResult()){
                                                try {
                                                    Order o=Order.convertFromQuery(documentSnapshot1);
                                                    Date currTime=new Date(System.currentTimeMillis());
                                                    if(o.DateAppoint.after(currTime)||o.DateAppoint.getDate()==currTime.getDate())
                                                        patientAppointmentLists.add(o);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            extracted(recyclerViewAppointmentPatientHome, patientAppointmentLists);
                                        }
                                    });
                        }
                    }
                });
        return view;
    }

    private void extracted(RecyclerView recyclerViewAppointmentPatientHome, ArrayList<Order> patientAppointmentLists) {
        OrderPatientHomeAdapter orderPatientHomeAdapter;
        orderPatientHomeAdapter = new OrderPatientHomeAdapter(patientAppointmentLists, this);
        recyclerViewAppointmentPatientHome.setAdapter(orderPatientHomeAdapter);
    }

    @Override
    public void onItemClick(Order patientAppointmentList) {
    }
}
