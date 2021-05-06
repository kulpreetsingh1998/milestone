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

import java.text.DecimalFormat;
import java.util.Objects;


public class TphFragment extends Fragment {

    public TextView tph_result;
    public TextView tph_data_temperature;
    public TextView tph_data_humidity;
    public TextView tph_data_pressure;

    public double temperature;
    public double humidity;
    public double pressure;

    DatabaseReference databaseReference;
    DecimalFormat numberFormat = new DecimalFormat("####.00");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.tph_fragment,container,false);

        tph_result= v.findViewById(R.id.tph_result);
        tph_data_temperature= v.findViewById(R.id.tph_data_temperature);
        tph_data_humidity= v.findViewById(R.id.tph_data_humidity);
        tph_data_pressure= v.findViewById(R.id.tph_data_pressure);
        databaseReference= FirebaseDatabase.getInstance().getReference();

        Query query = databaseReference.child("bme280").limitToLast(1);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()) temperature = (double) Objects.requireNonNull(snapshot.child("temp").getValue());
                if(snapshot.exists()) humidity = (double)Objects.requireNonNull(snapshot.child("humidity").getValue());
                if(snapshot.exists()) pressure = (double)Objects.requireNonNull(snapshot.child("pressure").getValue());
                calculate(temperature, humidity, pressure);
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
    public void calculate(double temperature, double humidity, double pressure){
        String tph_res="";
        tph_data_temperature.setText(Double.toString(Double.parseDouble(numberFormat.format(temperature)))+" C");
        tph_data_humidity.setText(Double.toString(Double.parseDouble(numberFormat.format(humidity)))+" %");
        tph_data_pressure.setText(Double.toString(Double.parseDouble(numberFormat.format(pressure)))+" hPa");

        if(temperature<18.0) tph_res=tph_res.concat(" Cold");
        if(temperature>28.0) tph_res=tph_res.concat(" Hot");
        if(humidity<30.0) tph_res=tph_res.concat(" Dry");
        if(humidity>50.0) tph_res=tph_res.concat(" Humid");
        else tph_res=" Ideal";

        tph_result.setText(tph_res);
    }
}