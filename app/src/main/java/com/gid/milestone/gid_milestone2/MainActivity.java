package com.gid.milestone.gid_milestone2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    public void callIntent(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.welcome_button:
                intent = new Intent(this, login.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}