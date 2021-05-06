package com.gid.milestone.gid_milestone2.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gid.milestone.gid_milestone2.ProfileFragment;
import com.gid.milestone.gid_milestone2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ResetPasswordFragment extends Fragment {
    public FirebaseAuth firebaseAuth;
    public FirebaseUser firebaseUser;
    SharedPreferences sharedPass;
    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_reset_password, container, false);
    firebaseAuth = FirebaseAuth.getInstance();
    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    EditText currentP = (EditText)v.findViewById(R.id.current_pass);
    EditText newP = (EditText)v.findViewById(R.id.new_pass);
    EditText confirmP = (EditText)v.findViewById(R.id.confirm_pass);


    Button save = (Button)v.findViewById(R.id.applychanges);
        save.setOnClickListener(new View.OnClickListener() {


        @Override
        public void onClick(View v) {

            String currentText = currentP.getText().toString().trim();
            String newText = newP.getText().toString().trim();
            String confirmText = confirmP.getText().toString().trim();
            if (TextUtils.isEmpty(currentText)) {
                Toast.makeText(getActivity().getApplicationContext(), "Enter Current Password Again", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(newText)) {
                Toast.makeText(getActivity().getApplicationContext(), "Enter New Password Again", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(confirmText)) {
                Toast.makeText(getActivity().getApplicationContext(), "Enter New Password Again", Toast.LENGTH_SHORT).show();
                return;
            }
            if (newText.length() < 6) {
                Toast.makeText(getActivity().getApplicationContext(), "Password length should be atleast 6", Toast.LENGTH_SHORT).show();
                return;
            }

            if (newText.equals(confirmText)) {
                firebaseUser.updatePassword(newText).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity().getApplicationContext(), "Password Updated Successfully", Toast.LENGTH_SHORT).show();
                        ProfileFragment newFragment= new ProfileFragment();
                        FragmentManager manager= getFragmentManager();
                        manager.beginTransaction()
                                .replace(R.id.container,newFragment,newFragment.getTag())
                                .commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Password Could Not Be Updated", Toast.LENGTH_SHORT).show();
                    }
                });



            }

        }
    });

    Button cancel_button = (Button)v.findViewById(R.id.cancel);
        cancel_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ProfileFragment newFragment= new ProfileFragment();
            FragmentManager manager= getFragmentManager();
            manager.beginTransaction()
                    .replace(R.id.container,newFragment,newFragment.getTag())
                    .commit();
        }
    });



        return v;
}
}

