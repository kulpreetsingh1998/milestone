package com.gid.milestone.gid_milestone2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import com.gid.milestone.gid_milestone2.ui.alerts.AlertsFragment;
import com.gid.milestone.gid_milestone2.ui.createnewtask.CreateNewTaskFragment;
import com.gid.milestone.gid_milestone2.ui.home.HomeFragment;
import com.gid.milestone.gid_milestone2.ui.settings.SettingsFragment;
import com.gid.milestone.gid_milestone2.ui.viewtask.ViewTaskFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle resumeinstancestate) {

        super.onCreate(resumeinstancestate);
        setContentView(R.layout.menu_activity);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
    }
    Fragment selectedFragment = new HomeFragment();
    BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch(item.getItemId()){
                        case R.id.navigation_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.navigation_alerts:
                            selectedFragment = new AlertsFragment();
                            break;
                        case R.id.navigation_view_task:
                            selectedFragment = new ViewTaskFragment();
                            break;
                        case R.id.navigation_create_task:
                            selectedFragment = new CreateNewTaskFragment();
                            break;
                        case R.id.navigation_settings:
                            selectedFragment = new SettingsFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.container,selectedFragment).commit();
                    return true;
                }
            };

    public void onBackPressed(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this)
       .setTitle("SIGN OUT")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = alertBuilder.create();
        alert.show();
        return;
    }
}