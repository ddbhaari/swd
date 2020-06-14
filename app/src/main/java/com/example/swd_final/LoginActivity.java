package com.example.swd_final;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    TextView notregistered;
    Button btnsignin;
    FirebaseAuth mfirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mfirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        notregistered = findViewById(R.id.textView);
        btnsignin = findViewById(R.id.button);

        //check current user
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            FirebaseUser mFirebaseUser = mfirebaseAuth.getCurrentUser();
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(mFirebaseUser != null){
                    Toast.makeText(LoginActivity.this, "You are Login",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this,DisplayActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Please Login",Toast.LENGTH_SHORT).show();
                }
            }
        };

        //button to login
        btnsignin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String emailID = email.getText().toString();
                String pwd = password.getText().toString();

                if(emailID.isEmpty()){
                    email.setError("Please enter your Email");
                    email.requestFocus();
                }
                else if(pwd.isEmpty()){
                    password.setError("Please enter your Password");
                    password.requestFocus();
                }
                //tak jadi
                else if(pwd.isEmpty() && emailID.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Field are empty", Toast.LENGTH_SHORT).show();
                }
                else if (!(pwd.isEmpty() && emailID.isEmpty())){
                    mfirebaseAuth.signInWithEmailAndPassword(emailID,pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this.getApplicationContext(),
                                        "Login unsuccessful: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent intToHome = new Intent(LoginActivity.this,DisplayActivity.class);
                                startActivity(intToHome);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(LoginActivity.this,"Error Occurred", Toast.LENGTH_SHORT).show();

                }

            }
        });

        //if pm still dont have account
        notregistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intToSignUp = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intToSignUp);
            }
        });

    }
}