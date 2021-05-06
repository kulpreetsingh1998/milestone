package com.gid.milestone.gid_milestone2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

public class MotionDetectionFragment extends Fragment {
    public TextView pir_data_motion;
    public TextView pir_data_buzzer;
    public String pir_data_motion_detected;
    public String pir_data_buzzer_state;
    DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.motion_detection_fragment,container,false);
        pir_data_motion= v.findViewById(R.id.pir_data_motion);
        pir_data_buzzer= v.findViewById(R.id.pir_data_buzzer);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        Query query = databaseReference.child("pir").limitToLast(1);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()) pir_data_motion_detected = Objects.requireNonNull(snapshot.child("motion_detected").getValue()).toString();
                if(snapshot.exists()) pir_data_buzzer_state = Objects.requireNonNull(snapshot.child("buzzer_state").getValue()).toString();
                calculate(pir_data_motion_detected,pir_data_buzzer_state);
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) { }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        return v;
    }
    public void calculate(String pir_data_motion_detected, String pir_data_buzzer_state){
        if(Objects.equals(pir_data_motion_detected, "No Motion Detected")){
            pir_data_motion.setText("No Motion Detected in area");
        }
        else{
            pir_data_motion.setText("Motion Detected in area");
        }

        if(Objects.equals(pir_data_buzzer_state,"Buzzer is Off")){
            pir_data_buzzer.setText("Alarm System is Off");
        }
        else{
            pir_data_buzzer.setText("Alarm System is On");
        }

    }
}