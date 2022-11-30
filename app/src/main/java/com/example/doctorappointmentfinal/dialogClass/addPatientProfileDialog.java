package com.example.doctorappointmentfinal.dialogClass;

import static com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess.db;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.doctorappointmentfinal.R;
import com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess;
import com.example.doctorappointmentfinal.appclass.Patient;
import com.example.doctorappointmentfinal.fragmentPatient.PatientProfileFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class addPatientProfileDialog extends AppCompatDialogFragment {
    private EditText edtUserfullname;
    private Spinner edtGender;
    private EditText edtDob;
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_profile_patient, null);
        Spinner s=view.findViewById(R.id.spinner_dialog_add_profile_patient_gender);
        ArrayAdapter<CharSequence> arrayAdapter=ArrayAdapter.createFromResource(this.getContext(),R.array.typeGender, android.R.layout.simple_spinner_item);
        s.setAdapter(arrayAdapter);
        builder.setView(view).setTitle("Add Patient Profile").setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("Accept", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String userFullName = edtUserfullname.getText().toString();
                String dob = edtDob.getText().toString();
                db.collection("Patients").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                QuerySnapshot q=task.getResult();
                                int numberOfPatients=q.size();
                                Map<String, Object> user = new HashMap<>();
                                user.put("ID", "P"+Integer.toString(numberOfPatients+1));
                                user.put("Name", userFullName);
                                user.put("Gender", s.getSelectedItemId()==0?"Male":"Female");
                                user.put("DateOfBirth", dob);
                                user.put("PhoneNumber", "");
                                user.put("Email", "");
                                user.put("Address", "");
                                user.put("BelongToID",Patient.CurrentPatient.ID);
                                user.put("PicImage","person");
                                Patient p=new Patient("P"+Integer.toString(numberOfPatients+1),userFullName, s.getSelectedItemId()==0?false:true, FirebaseNumberAndDateTimeProcess.stringToDate(dob,"dd/MM/yyyy"),"","","",Patient.CurrentPatient.ID,"person");
                                db.collection("Patients").add(user);
                                Bundle b=getArguments();
                                PatientProfileFragment fragment=(PatientProfileFragment) b.getSerializable("PatientProfileFragment");
                                fragment.ItemUpdate(p);
                            }
                        });

            }});

        edtUserfullname = view.findViewById(R.id.editText_dialog_add_profile_patient_fullname);
        edtGender = view.findViewById(R.id.spinner_dialog_add_profile_patient_gender);
        edtDob = view.findViewById(R.id.editText_dialog_add_profile_patient_dob);
        return builder.create();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
