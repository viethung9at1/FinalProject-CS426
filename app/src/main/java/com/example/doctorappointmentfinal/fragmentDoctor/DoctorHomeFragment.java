package com.example.doctorappointmentfinal.fragmentDoctor;

import static com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess.db;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doctorappointmentfinal.R;
import com.example.doctorappointmentfinal.adapter.DoctorHomescreenAppointmentAdapter;
import com.example.doctorappointmentfinal.adapter.OrderPatientHomeAdapter;
import com.example.doctorappointmentfinal.appclass.Doctor;
import com.example.doctorappointmentfinal.appclass.Order;
import com.example.doctorappointmentfinal.appclass.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorHomeFragment extends Fragment implements DoctorHomescreenAppointmentAdapter.ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoctorHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorHomeFragment newInstance(String param1, String param2) {
        DoctorHomeFragment fragment = new DoctorHomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_doctor_home, container, false);


///////////////////////
        return view;
    }

    private void extracted(RecyclerView recyclerViewAppointmentPatientHome, ArrayList<Order> doctorAppointmentLists) {
        DoctorHomescreenAppointmentAdapter doctorHomescreenAppointmentAdapter;
        doctorHomescreenAppointmentAdapter = new DoctorHomescreenAppointmentAdapter(
                doctorAppointmentLists, this);
        recyclerViewAppointmentPatientHome.setAdapter(doctorHomescreenAppointmentAdapter);
    }

    public void onItemClick(Order doctorAppointmentList) {

    }
    public void onViewCreated(View v, Bundle b){

        OrderPatientHomeAdapter orderPatientHomeAdapter;
        RecyclerView recyclerViewAppointmentPatientHome;
        recyclerViewAppointmentPatientHome = (RecyclerView) getView().findViewById(R.id.recyclerview_doctorNextAppointment);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewAppointmentPatientHome.setLayoutManager(linearLayoutManager);
// Xu ly backend
        ArrayList<Order> doctorAppointmentLists = new ArrayList<>();
        db.collection("Orders").whereEqualTo("DoctorID", Doctor.CurrentDoctor.ID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot q: task.getResult()){
                            try {
                                Order d=Order.convertFromQuery(q);
                                Date currTime=new Date(System.currentTimeMillis());
                                if(d.DateAppoint.after(currTime)||d.DateAppoint.getDate()==currTime.getDate())
                                    doctorAppointmentLists.add(d);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        extracted(recyclerViewAppointmentPatientHome, doctorAppointmentLists);
                    }
                });
    }
}