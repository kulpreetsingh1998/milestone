package com.gid.milestone.gid_milestone2.ui.createnewtask;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.gid.milestone.gid_milestone2.R;
import com.gid.milestone.gid_milestone2.ui.viewtask.ViewTaskFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateNewTaskFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.create_task_fragment,container,false);
        EditText title_t= (EditText) v.findViewById(R.id.title_text);
        EditText content_t= (EditText) v.findViewById(R.id.content_title);
        DatePicker datePicker = (DatePicker) v.findViewById(R.id.time_picker);

        final String[] time_t = new String[1];
        FloatingActionButton b= (FloatingActionButton) v.findViewById(R.id.add_task);

        datePicker.init(
                datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                      time_t[0] =" "+year+"-"+monthOfYear+"-"+dayOfMonth+" ";
                    }
                });
       b.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("tasks").push();
                HashMap<String , Object> map = new HashMap<>();
                map.put("title",title_t.getText().toString());
                map.put("content",content_t.getText().toString());
                map.put("time",time_t[0]);
                map.put("key",databaseReference.getKey());
                databaseReference.setValue(map);

                ViewTaskFragment newFragment= new ViewTaskFragment();
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.container,newFragment,newFragment.getTag())
                        .commit();
            }
        });
        return v;
    }
}