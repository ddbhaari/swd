package com.example.swd_final;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class DisplayActivity extends AppCompatActivity {
    Button btnlogout;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mFireBaseAuthListener;

    TextView employee_list;
    Button btnadd, btnedit, btndelete;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    CollectionReference dbref = db.collection("Userdata");

    private EmployeesAdapter eAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        btnlogout=(findViewById(R.id.logout));
        employee_list = findViewById(R.id.employee_list);
        btnadd = findViewById(R.id.button);

        //button logut
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(DisplayActivity.this,LoginActivity.class);
                startActivity(intToMain);
            }
        });

        GenerateRecyclerView();

        //panggil addactivity untuk add employee
        btnadd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intToAdd = new Intent(DisplayActivity.this, AddActivity.class);
                startActivity(intToAdd);
            }
        });

    }

    private void GenerateRecyclerView() {
        Query data = dbref.orderBy("Username");
        FirestoreRecyclerOptions<Employees> options = new FirestoreRecyclerOptions.Builder<Employees>().setQuery(data, Employees.class).build();

        eAdapter = new EmployeesAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(eAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        eAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        eAdapter.stopListening();
    }

}