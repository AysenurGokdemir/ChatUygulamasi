package com.example.aysenur.quiz;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth fAuth;
    private FirebaseUser fUser;

    Button btn_login_login;
    EditText et_login_email,et_login_password;
    TextView tv_login_notRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        if (fUser != null)
        {
            if(fUser.getEmail().equalsIgnoreCase("bthnorhan@a.com"))
            {
                Intent intent = new Intent(LoginActivity.this,QuestionActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        btn_login_login = (Button) findViewById(R.id.btn_login_login);
        et_login_email = (EditText) findViewById(R.id.et_login_email);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        tv_login_notRegistered = (TextView) findViewById(R.id.tv_login_notRegistered);

        tv_login_notRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email, pass;
                email = et_login_email.getText().toString().trim();
                pass = et_login_password.getText().toString().trim();

                fAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            if (email.equalsIgnoreCase("bthnorhan@a.com"))
                            {
                                Intent intent = new Intent(LoginActivity.this,QuestionActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else
                        {
                            Log.d("LOGIN",task.getException().getMessage());
                        }
                    }
                });

            }
        });
     }


}
