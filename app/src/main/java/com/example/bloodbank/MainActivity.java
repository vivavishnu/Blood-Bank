package com.example.bloodbank;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    EditText ETMail, ETPass;
    Button login,register;
    FirebaseAuth mAuth;
    String Mail,Pass;
    TextView reset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ETMail =findViewById(R.id.editTextTextEmailAddress);
        ETPass =findViewById(R.id.editTextTextPassword);
        login=findViewById(R.id.button);
        reset=findViewById(R.id.textView7);
        register=findViewById(R.id.button2);
        mAuth=FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mail = ETMail.getText().toString();
                Pass = ETPass.getText().toString();
                if(Mail.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter Mail", Toast.LENGTH_SHORT).show();
                } else if (Pass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else if(Mail.equals("admin") && Pass.equals("admin123"))
                {
                   Intent it=new Intent(MainActivity.this,AdminActivity.class);
                   it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(it);
                   finish();
                }
                else {
                    mAuth.signInWithEmailAndPassword(Mail, Pass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                if (mAuth.getCurrentUser().isEmailVerified()) {
                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Open your mail and verify your mail address", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                String msg = task.getException().getMessage();
                                Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });
        FirebaseUser user=mAuth.getCurrentUser();
        if(user!=null)
        {
            Intent intent=new Intent(MainActivity.this,HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        }
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(MainActivity.this, ResetPassActivity.class);
                startActivity(it);
            }
        });
    }
}