package com.hemoglobin.hemoglobin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ApproveActivity extends AppCompatActivity {

    private Button Approve;
    private DatabaseReference mDonationDatabase, mDoctorsDatabase;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private String mCurrentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve);

        Approve = findViewById(R.id.approve_button);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mCurrentUserId = mCurrentUser.getUid();

        mDonationDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(getIntent().getStringExtra("user_id"));
        mDonationDatabase.keepSynced(true);
        mDoctorsDatabase = FirebaseDatabase.getInstance().getReference().child("Doctors").child(mCurrentUserId);
        mDoctorsDatabase.keepSynced(true);

        Approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                final String uid = current_user.getUid();
                final String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                mDoctorsDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String doctor_name = dataSnapshot.child("name").getValue().toString();
                        String state = dataSnapshot.child("state").getValue().toString();
                        String district = dataSnapshot.child("district").getValue().toString();
                        String city = dataSnapshot.child("city").getValue().toString();

                        HashMap userMap = new HashMap<>();
                        userMap.put("blood_bank_name", doctor_name);
                        userMap.put("blood_bank_place", state+", "+district+", "+city);
                        userMap.put("doanted_date", date);
                        userMap.put("donated_time", date);
                        userMap.put("user_id", uid);

                        mDonationDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){

                                    Toast.makeText(ApproveActivity.this, "Donated Sucessfully", Toast.LENGTH_LONG).show();
                                    Intent setupIntent = new Intent(ApproveActivity.this, MainActivity.class);
                                    setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(setupIntent);
                                    finish();

                                }

                            }
                        });

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
    }
}
