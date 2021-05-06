package com.gid.milestone.gid_milestone2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gid.milestone.gid_milestone2.ui.alerts.AlertsFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.Objects;

public class AirQualityFragment extends Fragment {

        public TextView mq2_result;
    public TextView mq2_co_data;
    public TextView mq2_lpg_data;
    public TextView mq2_smoke_data;
        public double co;
        public double lpg;
        public double smoke;
        DatabaseReference databaseReference;
    DecimalFormat numberFormat = new DecimalFormat("#.00000000");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.air_quality_fragment,container,false);

        mq2_result= v.findViewById(R.id.mq2_result);
        mq2_co_data=v.findViewById(R.id.mq2_co_data);
        mq2_lpg_data=v.findViewById(R.id.mq2_lpg_data);
        mq2_smoke_data=v.findViewById(R.id.mq2_smoke_data);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        Query query = databaseReference.child("mq2").limitToLast(1);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()) co = (double)Objects.requireNonNull(snapshot.child("co").getValue());
                if(snapshot.exists()) lpg = (double)Objects.requireNonNull(snapshot.child("lpg").getValue());
                if(snapshot.exists()) smoke = (double)Objects.requireNonNull(snapshot.child("smoke").getValue());
                calculate(co, lpg, smoke);
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
    public void calculate(double co, double lpg, double smoke){
        mq2_smoke_data.setText("Smoke: "+Double.toString(Double.parseDouble(numberFormat.format(smoke)))+" ppm");
        mq2_lpg_data.setText("LPG: "+Double.toString(Double.parseDouble(numberFormat.format(lpg)))+" ppm");
        mq2_co_data.setText("CO: "+Double.toString(Double.parseDouble(numberFormat.format(co)))+" ppm");
        if(co<50.0 && lpg<2100.0 && smoke<100)
        mq2_result.setText("Within Ideal Range");
        else
            mq2_result.setText("Air Not Safe");
    }
}