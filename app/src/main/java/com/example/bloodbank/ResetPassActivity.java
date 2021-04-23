package com.example.bloodbank;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassActivity extends AppCompatActivity {
    Button reset;
    EditText mail;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpass);
        reset=findViewById(R.id.button3);
        mAuth=FirebaseAuth.getInstance();
        mail=findViewById(R.id.editTextTextEmailAddress2);
        final String DMail=mail.getText().toString();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.sendPasswordResetEmail(DMail).addOnCompleteListener(ResetPassActivity.this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Check your Mail",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String msg=task.getException().getMessage();
                            Toast.makeText(getApplicationContext(),""+msg,Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }
}