package com.gid.milestone.gid_milestone2;
import com.gid.milestone.gid_milestone2.login;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class createacc extends AppCompatActivity {

    EditText email,pass,repass;
    SharedPreferences datatologin;

    Button createac;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createacc_activity);

        email=(EditText)findViewById(R.id.emailText);
        pass=(EditText)findViewById(R.id.passText);
        repass=(EditText)findViewById(R.id.confirmPassText);
        createac=(Button)findViewById(R.id.createbutton);
        datatologin=getSharedPreferences("loginsp", Context.MODE_PRIVATE);

        firebaseAuth = FirebaseAuth.getInstance();
        createac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailtext = email.getText().toString().trim();
                String passtext = pass.getText().toString().trim();
                String repasstext = repass.getText().toString().trim();
                if(TextUtils.isEmpty(emailtext)){
                    Toast.makeText(createacc.this,"Email is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(passtext)){
                    Toast.makeText(createacc.this,"Password is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(repasstext)){
                    Toast.makeText(createacc.this,"Confirm Password is Empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(passtext.length()<6){
                    Toast.makeText(createacc.this,"Password length should be atleast 6", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(passtext.equals(repasstext)){
                    firebaseAuth.createUserWithEmailAndPassword(emailtext, passtext)
                            .addOnCompleteListener(createacc.this, task -> {
                                if(task.isSuccessful()) {
                                    SharedPreferences.Editor editor=datatologin.edit();
                                    editor.putString("email",emailtext);
                                    editor.putString("password",passtext);
                                    editor.commit();
                                    Toast.makeText(createacc.this,"Authentication Successful", Toast.LENGTH_SHORT).show();
                                 startActivity(new Intent(getApplicationContext(),login.class));

                                } else {
                                    Toast.makeText(createacc.this,"Authentication Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }


}