package com.example.swd_final;
//register project manager
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

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    TextView alreadyregistered;
    Button btnsignup;
    FirebaseAuth mfirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        alreadyregistered = findViewById(R.id.textView);
        btnsignup = findViewById(R.id.button);
        mfirebaseAuth = FirebaseAuth.getInstance();

        //buttonregister
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
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
                else if (!(pwd.isEmpty() && emailID.isEmpty())){
                    mfirebaseAuth.createUserWithEmailAndPassword(emailID,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this.getApplicationContext(),
                                        "SignUp unsuccessful: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            else{
                                startActivity(new Intent(MainActivity.this, DisplayActivity.class));
                            }
                        }
                    });

                }
                else {
                    Toast.makeText(MainActivity.this,"Error Occurred", Toast.LENGTH_SHORT).show();

                }
            }
        });

        //if PM already has account
        alreadyregistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

    }
}