package com.example.uas_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditComplaint extends AppCompatActivity {

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

    TextView add_date;
    EditText add_title, add_content, add_location;
    Button btn_datepicker, add_submit;
    String tmpDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_complaint);

        sharedPreferences = getSharedPreferences(userpref, Context.MODE_PRIVATE);
        Intent intent = getIntent();

        add_date = findViewById(R.id.add_date);
        add_title = findViewById(R.id.add_title);
        add_content = findViewById(R.id.add_content);
        add_location = findViewById(R.id.add_location);

        add_date.setText(intent.getStringExtra(complaintDate));
        add_title.setText(intent.getStringExtra(complaintTitle));
        add_content.setText(intent.getStringExtra(complaintContents));
        add_location.setText(intent.getStringExtra(complaintLocation));
        tmpDate = intent.getStringExtra(complaintDate);

        btn_datepicker = findViewById(R.id.btn_datepicker);
        btn_datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePicker2();
                newFragment.show(getSupportFragmentManager(), "datePicker2");
            }
        });

        add_submit = findViewById(R.id.add_submit);
        add_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String complaintTitle = add_title.getText().toString();
                String complaintContent = add_content.getText().toString();
                String complaintDate = tmpDate;
                String complaintLocation = add_location.getText().toString();
                String complaintBy = sharedPreferences.getString(userKey, "");
                String displayName = sharedPreferences.getString(userName, "");

                if (complaintBy.isEmpty() || displayName.isEmpty()) {
                    startActivity(new Intent(EditComplaint.this, MainActivity.class));
                }
                else if (complaintTitle.isEmpty()){
                    add_title.setError("Judul Harus Diisi!");
                }
                else if (complaintContent.isEmpty()){
                    add_content.setError("Isi Harus Diisi!");
                }
                else if (complaintDate.isEmpty()){
                    Toast.makeText(EditComplaint.this, "Belum menginputkan tanggal!", Toast.LENGTH_SHORT).show();
                }
                else if (complaintLocation.isEmpty()){
                    add_location.setError("Lokasi Harus Diisi!");
                }
                else {
                    database.child("Complaints").child(intent.getStringExtra(complaintKey)).setValue(new Complaints(complaintTitle, complaintContent, complaintDate, complaintLocation, complaintBy, displayName))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(EditComplaint.this, "Data Successfully Added!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(EditComplaint.this, Dashboard.class));
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EditComplaint.this, "Data Failed To Add, Please Try Again!", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }

    public void processDatePicker(int year, int month, int day) {
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);

        String dateMessage = (month_string + "/" + day_string + "/" + year_string);
        tmpDate = dateMessage;
        add_date.setText(dateMessage);
    }
}