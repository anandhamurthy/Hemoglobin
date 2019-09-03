package com.hemoglobin.hemoglobin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
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

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {

    private TextView Name, Email;
    private EditText City, Year, Age, Gender, Weight;
    private AutoCompleteTextView State, District, Day, Month;
    private FloatingActionButton Save;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private String mCurrentUserId;
    private DatabaseReference mUsersDatabase;

    private String name, email, gender,district, month,year,city,state,day,age, weight;

    private HashMap userMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mCurrentUserId = mCurrentUser.getUid();

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUserId);
        mUsersDatabase.keepSynced(true);

        Name = findViewById(R.id.settings_name);
        Email = findViewById(R.id.settings_email);
        Gender = findViewById(R.id.settings_gender);
        Age = findViewById(R.id.settings_age);
        City = findViewById(R.id.settings_city);
        Year = findViewById(R.id.settings_year);
        Month = findViewById(R.id.settings_month);
        Day = findViewById(R.id.settings_day);
        State = findViewById(R.id.settings_state);
        District = findViewById(R.id.settings_district);
        Weight = findViewById(R.id.settings_weight);
        Save = findViewById(R.id.settings_save);

        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                name = dataSnapshot.child("name").getValue().toString();
                email = dataSnapshot.child("email_id").getValue().toString();
                gender = dataSnapshot.child("gender").getValue().toString();
                state = dataSnapshot.child("state").getValue().toString();
                district = dataSnapshot.child("district").getValue().toString();
                city = dataSnapshot.child("city").getValue().toString();
                day = dataSnapshot.child("day").getValue().toString();
                month = dataSnapshot.child("month").getValue().toString();
                year = dataSnapshot.child("year").getValue().toString();
                age = dataSnapshot.child("age").getValue().toString();
                weight = dataSnapshot.child("weight").getValue().toString();
                Name.setText(name);
                Email.setText(email);
                Gender.setText(gender);
                Day.setText(day);
                Month.setText(month);
                Year.setText(year);
                City.setText(city);
                District.setText(district);
                State.setText(state);
                Age.setText(age);
                Weight.setText(weight);
                userMap = new HashMap<>();
                userMap.put("state", state);
                userMap.put("district",district);
                userMap.put("city",city);
                userMap.put("day", day);
                userMap.put("month", month);
                userMap.put("year", day);
                userMap.put("age", age);
                userMap.put("weight", weight);
                userMap.put("gender", gender);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isEmpty(gender, age, weight, day, month, year, state, district, city)){

                    mUsersDatabase.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            if (task.isSuccessful()){

                                Toast.makeText(SettingsActivity.this, "Sucessfully Updated", Toast.LENGTH_LONG).show();
                                Intent setupIntent = new Intent(SettingsActivity.this, MainActivity.class);
                                setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(setupIntent);
                                finish();
                            }

                        }
                    });

                }


            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseUser mCurrentUser = mAuth.getCurrentUser();

        if (mCurrentUser == null) {

            sendToLogin();

        }

    }

    private void sendToLogin() {

        Intent loginIntent = new Intent(SettingsActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();

    }
    private boolean isEmpty(String gender, String age, String weight, String day, String month, String year, String state, String district, String city) {
        if (gender.isEmpty() || age.isEmpty() || weight.isEmpty() || day.isEmpty() || month.isEmpty()|| year.isEmpty() || state.isEmpty() || district.isEmpty() || city.isEmpty()) {
            Toast.makeText(SettingsActivity.this, "Complete All the Details", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
