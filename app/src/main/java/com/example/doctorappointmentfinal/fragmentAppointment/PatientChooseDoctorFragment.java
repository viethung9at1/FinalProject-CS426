package com.example.doctorappointmentfinal.fragmentAppointment;

import static com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess.db;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doctorappointmentfinal.R;
import com.example.doctorappointmentfinal.adapter.ChooseDoctorAdapter;
import com.example.doctorappointmentfinal.appclass.Doctor;
import com.example.doctorappointmentfinal.appclass.Faculty;
import com.example.doctorappointmentfinal.appclass.People;
import com.example.doctorappointmentfinal.model.TypeAppointment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientChooseDoctorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientChooseDoctorFragment extends Fragment implements ChooseDoctorAdapter.ItemClickListener{
    public String value="";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    //private String mParam2;

    public PatientChooseDoctorFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientChooseDoctorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientChooseDoctorFragment newInstance(String param1, String param2) {
        PatientChooseDoctorFragment fragment = new PatientChooseDoctorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_patient_choose_doctor, container, false);
        ChooseDoctorAdapter chooseDoctorAdapter;
        RecyclerView recyclerViewChooseDoctor;
        recyclerViewChooseDoctor=(RecyclerView) view.findViewById(R.id.recyclerViewChooseDoctor);

        /*try {
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
            recyclerViewChooseProfileList.setLayoutManager(linearLayoutManager);
        }
        catch (Exception e){
            e.printStackTrace();
        }*/

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        ArrayList<Doctor> doctorModels=new ArrayList<>();
        db.collection("Doctors").whereEqualTo("Specialize",Faculty.CurrentFaculty.ID).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        recyclerViewChooseDoctor.setLayoutManager(linearLayoutManager);
                        for(QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()){
                            Doctor d=(Doctor) People.convertFromQuery(queryDocumentSnapshot);
                            //if(!PatientAppointmentActivity.WithCallNow) doctorModels.add(d);
                            //else if(d.IsFree) doctorModels.add(d);

                            Bundle b=getArguments();
                            value= TypeAppointment.CurrentType.getTypeAppointment();
                            if (value!="Video call now") doctorModels.add(d);
                            else if(d.IsFree) doctorModels.add(d);
                        }
                        extracted(recyclerViewChooseDoctor, doctorModels);
                        if(doctorModels.isEmpty()) makeToast("No doctor here, choose another specialize");
                    }
                });
        return view;
    }
    private void makeToast(String message){
        Toast.makeText(this.getActivity(),message,Toast.LENGTH_LONG).show();
    }
    private void extracted(RecyclerView recyclerViewChooseDoctor, ArrayList<Doctor> doctorModels) {
        ChooseDoctorAdapter chooseDoctorAdapter;
        chooseDoctorAdapter=new ChooseDoctorAdapter(doctorModels, this);
        recyclerViewChooseDoctor.setAdapter(chooseDoctorAdapter);
    }

    @Override
    public void onItemClick(Doctor doctors) {
        Bundle bundle=new Bundle();
        bundle.putString("messageToDetailDoctor", value);
        Fragment fragment=null;
        Doctor.CurrentDoctor=doctors;
        if (value=="Video call now")
        {
            fragment=new PatientDetailDoctorCallFragment();
            fragment.setArguments(bundle);
            /*try {
                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setServerURL(new URL("https://meet.jit.si"))
                        .setRoom("test123")
                        .setAudioMuted(false)
                        .setVideoMuted(false)
                        .setAudioOnly(false)
                        .setConfigOverride("requireDisplayName", true)
                        .build();
                JitsiMeetActivity.launch(this.getContext(),options);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }*/
        }
        else if (value!="Video call now") {
            fragment = new PatientDetailDoctorBookFragment();
            fragment.setArguments(bundle);
        }

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.patientChooseDoctorContainer,fragment, "step5DetailDoctorCall");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}