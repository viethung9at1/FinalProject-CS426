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
import com.example.doctorappointmentfinal.adapter.ChooseSpecialtyAdapter;
import com.example.doctorappointmentfinal.appclass.Faculty;
import com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientChooseSpecialtyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientChooseSpecialtyFragment extends Fragment implements ChooseSpecialtyAdapter.ItemClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PatientChooseSpecialtyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientChooseSpecialtyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientChooseSpecialtyFragment newInstance(String param1, String param2) {
        PatientChooseSpecialtyFragment fragment = new PatientChooseSpecialtyFragment();
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
        View view = inflater.inflate(R.layout.fragment_patient_choose_specialty, container, false);

        ChooseSpecialtyAdapter chooseSpecialtyAdapter;
        RecyclerView recyclerViewChooseSpecialty;
        recyclerViewChooseSpecialty=(RecyclerView) view.findViewById(R.id.recyclerViewChooseSpecialty);

        /*try {
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
            recyclerViewChooseProfileList.setLayoutManager(linearLayoutManager);
        }
        catch (Exception e){
            e.printStackTrace();
        }*/

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerViewChooseSpecialty.setLayoutManager(linearLayoutManager);
        ArrayList<Faculty> specialtyModels=new ArrayList<>();
        /*specialtyModels.add(new SpecialtyModel("Cardiology", "s1heart"));
        specialtyModels.add(new SpecialtyModel("Neurology", "s2brain"));
        specialtyModels.add(new SpecialtyModel("Gastroenterology", "s3gastroenterology"));
        specialtyModels.add(new SpecialtyModel("Otolaryngology", "s4otolaryngology"));
        specialtyModels.add(new SpecialtyModel("Urology", "s5urology"));
        specialtyModels.add(new SpecialtyModel("Ophthalmology", "s6eye"));*/
        FirebaseNumberAndDateTimeProcess.db.collection("Faculty").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for(QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()){
                            Faculty f=Faculty.covertFromQuery(queryDocumentSnapshot);
                            specialtyModels.add(f);
                        }
                        extracted(recyclerViewChooseSpecialty, specialtyModels);
                    }
                });

        return view;
    }

    private void extracted(RecyclerView recyclerViewChooseSpecialty, ArrayList<Faculty> specialtyModels) {
        ChooseSpecialtyAdapter chooseSpecialtyAdapter;
        chooseSpecialtyAdapter=new ChooseSpecialtyAdapter(specialtyModels, this);
        recyclerViewChooseSpecialty.setAdapter(chooseSpecialtyAdapter);
    }

    public int itemChoose=0;
    @Override
    public void onItemClick(Faculty specialtyModel) {
        Faculty.CurrentFaculty=specialtyModel;
        /*String value = getArguments().getString("messageToSpecialty");
        Bundle bundle=new Bundle();
        bundle.putString("messageToListDoctor", value);*/
        Fragment fragment =new PatientChooseDoctorFragment();
        //fragment.setArguments(bundle);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.patientChooseSpecialtyContainer,fragment, "step4ChooseDoctor");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}