package com.example.doctorappointmentfinal.appclass;

import static com.example.doctorappointmentfinal.appclass.FirebaseNumberAndDateTimeProcess.db;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class User {
    public String ID;
    public String Username;
    public String Password;
    public static User CurrentUser;
    public static ArrayList<User> users;
    public User(String id,String username, String password){
        ID=id;
        Username=username;
        Password=password;
    }
    public User(){}
    public User(boolean load){
        if(load==true) users=User.loadUser();
    }
    public static boolean loadComplete=false;
    public static ArrayList<User> loadUser() {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        ArrayList<User> userList = new ArrayList<>();// the array that store data
        db.collection("Users").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        loadComplete=true;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User u = document.toObject(User.class);
                                userList.add(u);
                            }
                        } else {
                            Log.d("123", "Error");
                        }
                    }
                });
        return userList;
    }
    private int userIndex;
    public static boolean checkUserExist(String username, String password){
        for(int i=0;i<users.size();i++){
            if(users.get(i).Username.equals(username) && users.get(i).Password.equals(password)) return true;
        }
        return false;
    }
    public static User getUser(String username, String password){
        for(int i=0;i<users.size();i++) {
            if (users.get(i).Username.equals(username) && users.get(i).Password.equals(password))
                return users.get(i);
        }
        return null;
    }
    public static User covertFromQuery(QueryDocumentSnapshot documentSnapshot){
        User u = documentSnapshot.toObject(User.class);
        return u;
    }
}
