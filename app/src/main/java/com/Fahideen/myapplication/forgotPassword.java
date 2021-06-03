package com.Fahideen.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class forgotPassword extends AppCompatActivity {

    private EditText mForgotPassword;
    private TextView mGoBackToLogin;
    private Button mPasswordRecoverButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        getSupportActionBar().hide();
        firebaseAuth=FirebaseAuth.getInstance();
        mForgotPassword=findViewById(R.id.forgotPassword);
        mGoBackToLogin=findViewById(R.id.gobacktologin);
        mPasswordRecoverButton=findViewById(R.id.passwordRecoverButton);

        mGoBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(forgotPassword.this,MainActivity.class);
                startActivity(intent);
            }
        });


        mPasswordRecoverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=mForgotPassword.getText().toString().trim();
                if(mail.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Enter your mail first",Toast.LENGTH_SHORT).show();
                }
                else
                {
                  firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull @NotNull Task<Void> task)
                      {
                       if(task.isSuccessful())
                       {
                           Toast.makeText(getApplicationContext(),"Password mail sent successfully",Toast.LENGTH_SHORT).show();
                           finish();
                           startActivity(new Intent(forgotPassword.this,MainActivity.class));
                       }
                       else
                           Toast.makeText(getApplicationContext(),"email doesn't exist or wrong email Entered",Toast.LENGTH_SHORT).show();
                      }
                  });

                }
                }
        });




    }
}