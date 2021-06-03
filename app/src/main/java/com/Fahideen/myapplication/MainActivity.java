package com.Fahideen.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {
    private TextView mgotoForgotPassword;
    private EditText mLoginEmail,mLoginPassword;
    private RelativeLayout mLogin,mGotoSignup;
    private Button mNewSignupBtn,mLoginBtn;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        mLoginEmail=findViewById(R.id.loginEmail);
        mLoginPassword=findViewById(R.id.loginPassword);
        mLogin=findViewById(R.id.login);
        mLoginBtn=findViewById(R.id.loginButton);
        mNewSignupBtn=findViewById(R.id.newSignUpBtn);
        mgotoForgotPassword=findViewById(R.id.gotoForgotPassword);
        firebaseUser=firebaseAuth.getCurrentUser();

        if(firebaseUser!=null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,NotesActivity.class));
        }


        mgotoForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,forgotPassword.class);
                startActivity(intent);
            }
        });
        mNewSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),signup.class));
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=mLoginEmail.getText().toString().trim();
                String password=mLoginPassword.getText().toString().trim();

                if(mail.isEmpty()|| password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"All Field Are Required",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    firebaseAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                          if(task.isSuccessful())
                          {
                              checkmailVerfication();
                          }
                          else
                          {
                              Toast.makeText(getApplicationContext(),"Please verify your email and Try Again",Toast.LENGTH_SHORT).show();
                          }
                        }
                    });
                }
            }
        });
        }
        private void checkmailVerfication()
        {
            FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
            if(firebaseUser.isEmailVerified())
            {
                Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(MainActivity.this,NotesActivity.class));

            }

            else
            {
                Toast.makeText(getApplicationContext(),"please Verify Your email First",Toast.LENGTH_SHORT).show();

            }
        }

    }
