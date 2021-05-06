package com.gid.milestone.gid_milestone2.ui.settings;

import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gid.milestone.gid_milestone2.AboutFragment;
import com.gid.milestone.gid_milestone2.ProfileFragment;
import com.gid.milestone.gid_milestone2.R;

import java.util.Locale;

public class SettingsFragment extends Fragment {
    String selectedLanguage="English";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.settings_fragment,container,false);

        TextView profile_text= (TextView) v.findViewById(R.id.profile);
        profile_text.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ProfileFragment newFragment= new ProfileFragment();
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.container,newFragment,newFragment.getTag())
                        .commit();
            }
        });




        TextView language_text= (TextView) v.findViewById(R.id.language);
        language_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsDialog();
            }

            private void showOptionsDialog() {
                String[] languages = {"English","French"};
                AlertDialog.Builder abuilder = new AlertDialog.Builder(getActivity());
                abuilder.setTitle("Choose a language");
                abuilder.setSingleChoiceItems(languages, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                    setLocale("en");
                                break;
                            case 1:
                                    setLocale("fr");
                                break;

                        }
                    }
                });
                abuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                abuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert2 =abuilder.create();
                alert2.show();
            }
            public void setLocale(String lang){
                Locale myLocale = new Locale(lang);
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = myLocale;
                res.updateConfiguration(conf,dm);
                SettingsFragment newFragment= new SettingsFragment();
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.container,newFragment,newFragment.getTag())
                        .commit();

            }
        });





        TextView about_text= (TextView) v.findViewById(R.id.about);
        about_text.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AboutFragment newFragment= new AboutFragment();
                FragmentManager manager= getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.container,newFragment,newFragment.getTag())
                        .commit();
            }
        });

        return v;
    }
}