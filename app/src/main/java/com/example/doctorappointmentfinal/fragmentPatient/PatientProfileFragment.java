package com.example.doctorappointmentfinal.fragmentPatient;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.doctorappointmentfinal.R;
import com.example.doctorappointmentfinal.adapter.AddPatientProfileAdapter;
import com.example.doctorappointmentfinal.dialogClass.addPatientProfileDialog;
import com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess;
import com.example.doctorappointmentfinal.appclass.Patient;
import com.example.doctorappointmentfinal.appclass.People;
import com.example.doctorappointmentfinal.appclass.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;


public class PatientProfileFragment extends Fragment implements AddPatientProfileAdapter.ItemClickListener, Serializable {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientProfileFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPatientProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static com.example.doctorappointmentfinal.fragmentPatient.PatientProfileFragment newInstance(String param1, String param2) {
        com.example.doctorappointmentfinal.fragmentPatient.PatientProfileFragment fragment = new com.example.doctorappointmentfinal.fragmentPatient.PatientProfileFragment();
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

        //Bi loi tu dong nay toi dong thu 79. Khi tach rieng ra ngoai thi chay khong co loi


    }

    RecyclerView recyclerViewAddPatientProfile;
    ArrayList<Patient> addPatientProfileArrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_profile, container, false);
        //ddPatientProfileAdapter addPatientProfileAdapter;
        recyclerViewAddPatientProfile = (RecyclerView) view.findViewById(R.id.recyclerView_patient_profile);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewAddPatientProfile.setLayoutManager(linearLayoutManager);
        addPatientProfileArrayList = new ArrayList<>();

        /*addPatientProfileArrayList.add(new AddPatientProfile("Lionel Messi", "Male", "24/06/1987"));
        addPatientProfileArrayList.add(new AddPatientProfile("Lionel Messi", "Male", "24/06/1987"));
        addPatientProfileArrayList.add(new AddPatientProfile("Lionel Messi", "Male", "24/06/1987"));
        addPatientProfileArrayList.add(new AddPatientProfile("Lionel Messi", "Male", "24/06/1987"));
        addPatientProfileArrayList.add(new AddPatientProfile("Lionel Messi", "Male", "24/06/1987"));
        addPatientProfileArrayList.add(new AddPatientProfile("Lionel Messi", "Male", "24/06/1987"));*/
        FirebaseNumberAndDateTimeProcess.db.collection("Patients").whereEqualTo("BelongToID",User.CurrentUser.ID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot d: task.getResult()){
                            Patient p=(Patient) People.convertFromQuery(d);
                            addPatientProfileArrayList.add(p);
                        }
                        extracted(recyclerViewAddPatientProfile, addPatientProfileArrayList);
                    }
                });
        return view;
    }
    AddPatientProfileAdapter addPatientProfileAdapter;
    private void extracted(RecyclerView recyclerViewAddPatientProfile, ArrayList<Patient> addPatientProfileArrayList) {
        addPatientProfileAdapter = new AddPatientProfileAdapter(addPatientProfileArrayList, this);
        recyclerViewAddPatientProfile.setAdapter(addPatientProfileAdapter);
        Toast.makeText(this.getContext(), "Please log out and log in again for profile update",Toast.LENGTH_LONG);
    }

    public void openDialog() {
        addPatientProfileDialog AddPatientProfileDialogs = new addPatientProfileDialog();
        Bundle b=new Bundle();
        b.putSerializable("PatientProfileFragment", this);
        AddPatientProfileDialogs.setArguments(b);
        AddPatientProfileDialogs.show(getActivity().getSupportFragmentManager(), "Patient dialog");
    }

    @Override
    public void onItemClick(Patient addPatientProfiles) {

    }
    public void onViewCreated(View v, Bundle b){

        Button button = getView().findViewById(R.id.button_patient_profile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }
    public void ItemUpdate(Patient p){
        addPatientProfileArrayList.add(p);
        addPatientProfileAdapter.notifyItemInserted(addPatientProfileArrayList.size()-1);
    }
}