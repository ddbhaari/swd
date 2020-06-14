package com.example.swd_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    EditText name,employee_id;
    Button btnadd;
    TextView success;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String TAG = "AddActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        InputFilter filter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int start, int end,
                                       Spanned dest, int etStart, int etEnd) {
                if (charSequence.equals("")) {
                    return charSequence;
                }
                if (charSequence.toString().matches("[a-zA-Z ]+")) {
                    return charSequence;
                }
                return "";
            }
        };

        name = findViewById(R.id.editText);
        employee_id = findViewById(R.id.editText2);
        btnadd = findViewById(R.id.button1);
        name.setFilters(new InputFilter[] { filter });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uname = name.getText().toString();
                final String pemployee = employee_id.getText().toString();

                Map<String, String> Userdata = new HashMap<>();
                Userdata.put("Username", uname);
                Userdata.put("ID", pemployee);

                db.collection("Userdata").add(Userdata).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "onSuccess: Add to list");
                        Toast.makeText(AddActivity.this, "Successfully add ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddActivity.this, DisplayActivity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Not added");
                        Toast.makeText(AddActivity.this, "Fail to add " + e, Toast.LENGTH_SHORT).show();
                    }
                });

//                if (uname.matches("")) {
//                    Toast.makeText(AddActivity.this, "Please enter employee name ", Toast.LENGTH_SHORT).show();
//                    return;
//                } else if (pemployee.matches("")) {
//                    Toast.makeText(AddActivity.this, "Please enter employee id ", Toast.LENGTH_SHORT).show();
//                } else {
//
//
//                }
            }
        });
    }
}