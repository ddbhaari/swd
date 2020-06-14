package com.example.swd_final;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EmployeesAdapter extends FirestoreRecyclerAdapter<Employees, EmployeesAdapter.EmployeesHolder> {

    public  EmployeesAdapter (@NonNull FirestoreRecyclerOptions<Employees> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull EmployeesHolder employeesHolder, int i, @NonNull Employees employees) {
        employeesHolder.setEmployeeID(getSnapshots().getSnapshot(i).getId());
        employeesHolder.name_view.setText(employees.getUsername());
        employeesHolder.employeeid_view.setText(employees.getID());
    }

    @NonNull
    @Override
    public EmployeesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.employee_list, parent, false);
        return new EmployeesHolder(view);
    }

    class EmployeesHolder extends RecyclerView.ViewHolder{
        LinearLayout list_root;
        EditText name_view, employeeid_view;
        Button btndel,btnupdate,btnsave;

        public String employeeID;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference dbref = db.collection("Userdata");

        public EmployeesHolder(@NonNull View itemView) {
            super(itemView);
            list_root = itemView.findViewById(R.id.list_root);
            name_view = itemView.findViewById(R.id.name_view);
            employeeid_view = itemView.findViewById(R.id.employeeid_view);
            btndel = itemView.findViewById(R.id.btndel);
            btnupdate = itemView.findViewById(R.id.btnupdate);
            btnsave = itemView.findViewById(R.id.btncupdate);

            final Dialog dialog = new Dialog(itemView.getContext());

            btndel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbref.document(employeeID).delete();
                }
            });

            //function untuk update
            final Map<String,Object> updates = new HashMap<>();

            btnupdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnupdate.setVisibility(View.GONE);
                    btnsave.setVisibility(View.VISIBLE);
                    name_view.setEnabled(true);
                    employeeid_view.setEnabled(true);

                }
            });

            btnsave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updates.put("Username", name_view.getText());
                    updates.put("ID", employeeid_view.getText());
                    btnupdate.setVisibility(View.VISIBLE);
                    btnsave.setVisibility(View.GONE);

                    dbref.document(employeeID).update(updates);
                }
            });


        }

        public void setEmployeeID(String employeeID) {
            this.employeeID = employeeID;
        }

    }
}
