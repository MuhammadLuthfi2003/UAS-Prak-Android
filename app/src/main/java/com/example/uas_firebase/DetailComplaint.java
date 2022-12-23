package com.example.uas_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailComplaint extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    // key values to access shared pref
    public static final String userpref = "userpref";
    public static final String userName = "username";
    public static final String userPass = "password";
    public static final String userKey = "key";

    // key values to aceess intent
    public static final String complaintKey = "complaintKey";
    public static final String complaintUserName = "complaintUserName";
    public static final String complaintUserNameKey = "complaintUserNameKey";
    public static final String complaintTitle = "complaintTitle";
    public static final String complaintContents = "complaintContents";
    public static final String complaintDate = "complaintDate";
    public static final String complaintLocation = "complaintLocation";
    public static final String complaintCreatedBy= "complaintCreatedBy";

    TextView display_title, display_content, display_date, display_location, display_username;
    Button edit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_complaint);

        sharedPreferences = getSharedPreferences(userpref, Context.MODE_PRIVATE);

        Intent intent = getIntent();

        display_title = findViewById(R.id.display_title);
        display_content = findViewById(R.id.display_content);
        display_date = findViewById(R.id.display_date);
        display_location = findViewById(R.id.display_location);
        display_username = findViewById(R.id.display_username);

        display_title.setText(intent.getStringExtra(complaintTitle));
        display_content.setText(intent.getStringExtra(complaintContents));
        display_date.setText(intent.getStringExtra(complaintDate));
        display_location.setText(intent.getStringExtra(complaintLocation));
        display_username.setText(intent.getStringExtra(complaintUserName));

        edit_btn = findViewById(R.id.edit_btn);


        // disable button if user key does not match
        String tmpUserkey = sharedPreferences.getString(userKey, "");
        String compUserkey = intent.getStringExtra(complaintUserNameKey);

        if (!tmpUserkey.equals(compUserkey)) {
            edit_btn.setEnabled(false);
        }

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editCredentials = new Intent(DetailComplaint.this, EditComplaint.class);
                editCredentials.putExtra(complaintKey, intent.getStringExtra(complaintKey));
                editCredentials.putExtra(complaintUserName, intent.getStringExtra(complaintUserName));
                editCredentials.putExtra(complaintUserNameKey, intent.getStringExtra(complaintUserNameKey));
                editCredentials.putExtra(complaintTitle, intent.getStringExtra(complaintTitle));
                editCredentials.putExtra(complaintContents, intent.getStringExtra(complaintContents));
                editCredentials.putExtra(complaintDate, intent.getStringExtra(complaintDate));
                editCredentials.putExtra(complaintLocation, intent.getStringExtra(complaintLocation));
                editCredentials.putExtra(complaintCreatedBy, intent.getStringExtra(complaintCreatedBy));
                startActivity(editCredentials);

            }
        });




    }
}