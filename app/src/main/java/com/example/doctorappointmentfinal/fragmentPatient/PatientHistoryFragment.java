package com.example.doctorappointmentfinal.fragmentPatient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorappointmentfinal.R;
import com.example.doctorappointmentfinal.adapter.HistoryPatientAdapter;
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
/*
*
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
*/

public class PatientHistoryFragment extends Fragment implements HistoryPatientAdapter.ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientHistoryFragment() {
        // Required empty public constructor
    }

/**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientHistoryFragment.
*/

    // TODO: Rename and change types and number of parameters
    public static PatientHistoryFragment newInstance(String param1, String param2) {
        PatientHistoryFragment fragment = new PatientHistoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_patient_history, container, false);
        //////////////////////////////////////////////////

        return view;
    }

    private void extracted(RecyclerView recyclerHistoryPatient, ArrayList<Order>
            historyPatientAppointmentLists) {
        HistoryPatientAdapter historyPatientAdapter;
        historyPatientAdapter  = new HistoryPatientAdapter(
                historyPatientAppointmentLists, (HistoryPatientAdapter.ItemClickListener) this);
        recyclerHistoryPatient.setAdapter(historyPatientAdapter);
    }

    public void onItemClick(Order historyPatientAppointmentLists) {

    }
    public void onViewCreated(View v, Bundle b){
        HistoryPatientAdapter historyPatientAdapter;
        RecyclerView recyclerHistoryPatient;
        recyclerHistoryPatient = (RecyclerView) getView().findViewById(R.id.recyclerViewHistory);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerHistoryPatient.setLayoutManager(linearLayoutManager);
        // Xu ly backend
        ArrayList<Order> historyPatientAppointmentLists = new ArrayList<>();
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
                                                    if(o.DateAppoint.before(currTime)||o.DateAppoint.getDate()==currTime.getDate())
                                                        historyPatientAppointmentLists.add(o);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                            extracted(recyclerHistoryPatient, historyPatientAppointmentLists);
                                        }
                                    });
                        }
                    }
                });
    }
}
