package com.gid.milestone.gid_milestone2.ui.alerts;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.gid.milestone.gid_milestone2.AirQualityFragment;
import com.gid.milestone.gid_milestone2.MotionDetectionFragment;
import com.gid.milestone.gid_milestone2.R;
import com.gid.milestone.gid_milestone2.TphFragment;

public class AlertsFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.alerts_fragment,container,false);

        ImageButton b1= (ImageButton) v.findViewById(R.id.air_quality_button);
        b1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AirQualityFragment newFragment= new AirQualityFragment();
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.container,newFragment,newFragment.getTag())
                        .commit();
            }
        });

        ImageButton b2= (ImageButton) v.findViewById(R.id.motion_detection_button);
        b2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MotionDetectionFragment newFragment= new MotionDetectionFragment();
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.container,newFragment,newFragment.getTag())
                        .commit();
            }
        });

        ImageButton b3= (ImageButton) v.findViewById(R.id.tph_button);
        b3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TphFragment newFragment= new TphFragment();
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.container,newFragment,newFragment.getTag())
                        .commit();
            }
        });
        return v;
    }

    }
