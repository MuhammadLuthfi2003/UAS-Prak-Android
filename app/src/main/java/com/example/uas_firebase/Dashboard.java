package com.example.uas_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    ArrayList<Complaints> listComplaints;
    SharedPreferences sharedPreferences;

    public static final String userpref = "userpref";
    public static final String userName = "username";
    public static final String userPass = "password";
    public static final String userKey = "key";

    TextView text_user;
    FloatingActionButton addcomplaint_btn;
    RecyclerView complaints_list;
    ComplaintAdapter complaintAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sharedPreferences = getSharedPreferences(userpref, Context.MODE_PRIVATE);
        String loggedInUsername = sharedPreferences.getString(userName, "");

        if (loggedInUsername.isEmpty()) {
            startActivity(new Intent(Dashboard.this, MainActivity.class));
        }

        text_user = findViewById(R.id.text_user);
        text_user.setText("Hello! " + loggedInUsername);

        addcomplaint_btn = findViewById(R.id.addcomplaint_btn);
        addcomplaint_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this, AddComplaint.class));
            }
        });

        complaints_list = findViewById(R.id.complaints_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        complaints_list.setLayoutManager(layoutManager);
        complaints_list.setItemAnimator(new DefaultItemAnimator());

        showData();
    }

    private void showData() {
        database.child("Complaints").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listComplaints = new ArrayList<>();

                for (DataSnapshot item : snapshot.getChildren()) {
                    Complaints complaint = item.getValue(Complaints.class);
                    complaint.setKey(item.getKey());
                    listComplaints.add(complaint);
                }

                complaintAdapter = new ComplaintAdapter(listComplaints, Dashboard.this);
                complaints_list.setAdapter(complaintAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

