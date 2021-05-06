package com.gid.milestone.gid_milestone2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    EditText email_l,pass_l;
    Button login,createAccount;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mAuth = FirebaseAuth.getInstance();

        email_l = (EditText) findViewById(R.id.login_email);
        pass_l = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.login_button);
        createAccount = (Button) findViewById(R.id.login_createbutton);
        SharedPreferences datafromcreateaccount=getApplicationContext().getSharedPreferences("loginsp", Context.MODE_PRIVATE);
        String email=datafromcreateaccount.getString("email","");
        String password=datafromcreateaccount.getString("password","");
        email_l.setText(email);
        pass_l.setText(password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String textEmail = email_l.getText().toString().trim();
                String textPassword = pass_l.getText().toString().trim();

                if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(login.this, "Email is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(login.this, "Password is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (textPassword.length() < 6) {
                    Toast.makeText(login.this, "Password length should be atleast 6", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(textEmail, textPassword)
                        .addOnCompleteListener(login.this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), Menu.class));


                                } else {

                                    Toast.makeText(login.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                                }


                            }
                        });

            }


        });
    }
        public void login_to_createacc(View v) {
            Intent i = new Intent(getApplicationContext(), createacc.class);
            startActivity(i);
        }
}
