package com.example.uas_firebase;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ViewHolder> {

    private List<Complaints> complaintsList;
    private Context context;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    SharedPreferences sharedPreferences;

    public static final String userpref = "userpref";
    public static final String userName = "username";
    public static final String userPass = "password";
    public static final String userKey = "key";

    public ComplaintAdapter(List<Complaints> complaintsList, Context context) {
        this.complaintsList = complaintsList;
        this.context = context;
        sharedPreferences = context.getSharedPreferences(userpref, Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ComplaintAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewItem = inflater.inflate(R.layout.mini_complaint, parent, false);
        return new ViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintAdapter.ViewHolder holder, int position) {
        final Complaints complaint = complaintsList.get(position);

        holder.complaint_title.setText(complaint.getTitle());
        holder.user_complaint.setText(complaint.getDisplayName());

        holder.card_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent details = new Intent(context, DetailComplaint.class);
                details.putExtra("complaintKey", complaint.getKey());
                details.putExtra("complaintUserName", complaint.getDisplayName());
                details.putExtra("complaintUserNameKey", complaint.getCreatedBy());
                details.putExtra("complaintTitle", complaint.getTitle());
                details.putExtra("complaintContents", complaint.getContent());
                details.putExtra("complaintDate", complaint.getDate());
                details.putExtra("complaintLocation", complaint.getLocation());
                details.putExtra("complaintCreatedBy", complaint.getCreatedBy());
                details.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(details);
            }
        });

        holder.see_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String loggedInUser = sharedPreferences.getString(userKey, "");
                String complaintCreatedBy = complaint.getCreatedBy();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        database.child("Complaints").child(complaint.getKey()).removeValue()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(context, "Complaint successfully erased!", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Complaint failed to erase, please try again!", Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setMessage("Are you sure to erase this complaint, " + complaint.getDisplayName() + "?");

                if (loggedInUser.equals(complaintCreatedBy)) {
                    builder.show();
                }
                else {
                    Toast.makeText(context, "You didn't make the post, so you're unable to delete it", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return complaintsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView complaint_title, user_complaint;
        ImageView see_more;
        CardView card_complaint;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            complaint_title = itemView.findViewById(R.id.complaint_title);
            user_complaint = itemView.findViewById(R.id.user_complaint);
            see_more = itemView.findViewById(R.id.see_more);
            card_complaint = itemView.findViewById(R.id.card_complaint);
        }
    }
}
