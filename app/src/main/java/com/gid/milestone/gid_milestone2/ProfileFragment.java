package com.gid.milestone.gid_milestone2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gid.milestone.gid_milestone2.ui.settings.ResetPasswordFragment;
import com.gid.milestone.gid_milestone2.ui.settings.SettingsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {
    SharedPreferences sharedPreferences;
    public FirebaseAuth firebaseAuth;
    public FirebaseUser firebaseUser;
    SharedPreferences sharedPass;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.profile_fragment,container,false);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("loginsp", Context.MODE_PRIVATE);

        EditText text =(EditText)v.findViewById(R.id.profile_name);
        String emailText = sharedPreferences.getString("email"," ");
        text.setText(emailText);
        ImageButton editEmail = (ImageButton)v.findViewById(R.id.editButton);
        editEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builderdelete = new AlertDialog.Builder(getActivity());
                EditText input = new EditText(getActivity().getApplicationContext());
                        builderdelete.setTitle("Enter your new email: ")
                        .setView(input)
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String value = input.getText().toString().trim();
                                firebaseUser.updateEmail(value).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Toast.makeText(getActivity().getApplicationContext(), "Email Updated Successfully", Toast.LENGTH_SHORT).show();
                                        text.setText(value);

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getActivity().getApplicationContext(), "Email Could Not Be Updated", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });

                AlertDialog alert = builderdelete.create();
                alert.show();



            }
        });

        Button resetPassword =(Button)v.findViewById(R.id.resetpass);
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPasswordFragment newFragment = new ResetPasswordFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.container, newFragment, newFragment.getTag())
                        .commit();
            }
        });

        Button delete = (Button)v.findViewById(R.id.button_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builderdelete = new AlertDialog.Builder(getActivity())

                        .setTitle("REALLY?! ARE YOU SURE?")
                        .setMessage("This action will permanently delete all of your tasks. You cannot undo this.")
                        .setPositiveButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                firebaseUser.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(getActivity().getApplicationContext(),"Account Deleted",Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getContext().getApplicationContext(),MainActivity.class));
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putString("email","");
                                                    editor.putString("password","");
                                                    editor.commit();


                                                }
                                                else{
                                                    Toast.makeText(getActivity().getApplicationContext(),"Account Not Deleted",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        });

                AlertDialog alert = builderdelete.create();
                alert.show();


            }
        });

        Button signOut = (Button)v.findViewById(R.id.button_signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())

                        .setTitle("SIGN OUT")
                        .setMessage("Are you sure you want to exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();


            }
        });
        return v;
    }
}
