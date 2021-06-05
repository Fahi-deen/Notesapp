package com.Fahideen.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class createnote extends AppCompatActivity {
     private EditText mcreatetitleofnote,mtoolbarofcreatenote;
     private FloatingActionButton mSavenote;
     private FirebaseAuth firebaseAuth;
     private FirebaseUser firebaseUser;
     private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnote);
        mcreatetitleofnote=findViewById(R.id.createtitleofnote);
        mtoolbarofcreatenote=findViewById(R.id.createcontentofnote);
        mSavenote=findViewById(R.id.savenote);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarofcreatenote);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSavenote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=mcreatetitleofnote.getText().toString();
                String content=mtoolbarofcreatenote.getText().toString();
                if(title.isEmpty() || content.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Both fields are Required",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Toast.makeText(getApplicationContext(),content,Toast.LENGTH_SHORT).show();
                    DocumentReference documentReference=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("My_notes").document();
                    Map<String,Object> notes=new HashMap<>();
                    notes.put("title",title);
                    notes.put("content",content);
                    documentReference.set(notes).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(),"Notes added Sucessfully..",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(createnote.this, notesActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Oops Notes creation Failed..",Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

       if(item.getItemId()==android.R.id.home)
       {
        onBackPressed();
       }
        return super.onOptionsItemSelected(item);
    }
}