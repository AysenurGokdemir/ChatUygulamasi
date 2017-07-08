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

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth fAuth;
    private FirebaseUser fUser;

    EditText et_register_email,et_register_password,et_register_repassword;
    Button btn_register_register;
    TextView tv_registered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        if(fUser == null)
        {
            Intent intent = new Intent(RegisterActivity.this,RegisterActivity.class);
            startActivity(intent);
            finish();
        }

        tv_registered = (TextView) findViewById(R.id.tv_register_registered);
        et_register_email = (EditText) findViewById(R.id.et_register_email);
        et_register_password = (EditText) findViewById(R.id.et_register_password);
        et_register_repassword = (EditText) findViewById(R.id.et_register_repassword);
        btn_register_register = (Button) findViewById(R.id.btn_register_register);

        tv_registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_register_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,pass,repass;
                email = et_register_email.getText().toString().trim();
                pass = et_register_password.getText().toString().trim();
                repass = et_register_repassword.getText().toString().trim();

                if (pass.equalsIgnoreCase(repass))
                {
                    fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                if(fUser.getEmail().equalsIgnoreCase("bthnorhan@a.com"))
                                {
                                    Intent intent = new Intent(RegisterActivity.this,QuestionActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            else
                            {
                                Log.d("REGISTER",task.getException().getMessage());
                            }
                        }
                    });
                }

            }
        });
    }
}
