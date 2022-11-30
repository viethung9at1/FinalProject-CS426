package com.example.doctorappointmentfinal.fragmentAppointment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doctorappointmentfinal.R;
import com.example.doctorappointmentfinal.adapter.ChooseProfileAdapter;
import com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess;
import com.example.doctorappointmentfinal.appclass.Patient;
import com.example.doctorappointmentfinal.appclass.People;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientChooseProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientChooseProfileFragment extends Fragment implements ChooseProfileAdapter.ItemClickListener{



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public PatientChooseProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
    //* @param param2 Parameter 2.
     * @return A new instance of fragment PatientChooseProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientChooseProfileFragment newInstance(String param1, String param2) {
        PatientChooseProfileFragment fragment = new PatientChooseProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private boolean loadState=false;
    private ArrayList<Patient> choosePatientProfileList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private RecyclerView recyclerViewChooseProfileList;
    private final ChooseProfileAdapter[] chooseProfileAdapter = new ChooseProfileAdapter[1];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_patient_choose_profile, container, false);
        recyclerViewChooseProfileList=view.findViewById(R.id.recyclerViewChooseProfileList);

        /*try {
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
            recyclerViewChooseProfileList.setLayoutManager(linearLayoutManager);
        }
        catch (Exception e){
            e.printStackTrace();
        }*/

        choosePatientProfileList=new ArrayList<>();
        FirebaseNumberAndDateTimeProcess.db.collection("Patients").whereEqualTo("BelongToID",Patient.CurrentPatient.BelongToID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
                            recyclerViewChooseProfileList.setLayoutManager(linearLayoutManager);
                            for(QueryDocumentSnapshot q:task.getResult()){
                                if(q.get("ID").toString().equals(Patient.CurrentPatient.BelongToID)){
                                    Patient p=(Patient) People.convertFromQuery(q);
                                    choosePatientProfileList.add(p);
                                    break;
                                }
                            }
                            for(QueryDocumentSnapshot q: task.getResult()){
                                if(!q.get("ID").toString().equals(Patient.CurrentPatient.BelongToID)) {
                                    Patient p = (Patient) People.convertFromQuery(q);
                                    choosePatientProfileList.add(p);
                                }
                            }
                        }
                        extracted();
                    }
                });
        //choosePatientProfileList.add(new PatientProfile("Linda Brown", "Female", "12/08/2000","person"));
        //choosePatientProfileList.add(new PatientProfile("George Lee", "Male", "01/10/1960","person"));
        return view;
    }
    private void extracted() {
        chooseProfileAdapter[0] =new ChooseProfileAdapter(choosePatientProfileList, this);
        recyclerViewChooseProfileList.setAdapter(chooseProfileAdapter[0]);
    }

    @Override
    public void onItemClick(Patient patientProfile) {

        /*String value = getArguments().getString("messageToProfile");
        Bundle bundle=new Bundle();
        bundle.putString("messageToSpecialty", value);*/
        Fragment fragment =new PatientChooseSpecialtyFragment();
        //fragment.setArguments(bundle);
        FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack
        transaction.replace(R.id.patientChooseProfileContainer,fragment, "step3ChooseSpecialty");
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();
    }
}