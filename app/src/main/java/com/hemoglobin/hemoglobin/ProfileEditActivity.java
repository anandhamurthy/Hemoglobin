package com.hemoglobin.hemoglobin;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileEditActivity extends AppCompatActivity {

    private TextView Age, Weight, Year, City;
    private TextView Name, Email;
    private CircleImageView Profile_Image;
    private FloatingActionButton Profile_Edit_Next;

    private String name, email, gender,district, month,year,city,state,day,age, weight;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private String mCurrentUserId;
    private DatabaseReference mUsersDatabase;

    private TextView Gender, State, District, Day, Month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mCurrentUserId = mCurrentUser.getUid();

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUserId);
        mUsersDatabase.keepSynced(true);

        Name = findViewById(R.id.profile_edit_name);
        Email = findViewById(R.id.profile_edit_email);
        Gender = findViewById(R.id.profile_edit_gender);
        Age = findViewById(R.id.profile_edit_age);
        Weight = findViewById(R.id.profile_edit_weight);
        Day = findViewById(R.id.profile_edit_day);
        Month = findViewById(R.id.profile_edit_month);
        Year = findViewById(R.id.profile_edit_year);
        State = findViewById(R.id.profile_edit_state);
        District = findViewById(R.id.profile_edit_district);
        City = findViewById(R.id.profile_edit_city);
        Profile_Image = findViewById(R.id.profile_edit_image);
        Profile_Edit_Next = findViewById(R.id.profile_edit_next);

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
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Profile_Edit_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = Name.getText().toString();
                String email = Email.getText().toString();
                String gender = Gender.getText().toString();
                String age = Age.getText().toString();
                String weight = Weight.getText().toString();
                String day = Day.getText().toString();
                String month = Month.getText().toString();
                String year = Year.getText().toString();
                String state = State.getText().toString();
                String district = District.getText().toString();
                String city = City.getText().toString();

                if (isEmpty(name, email, gender, age, weight, day, month, year, state, district, city)){

                    Intent registerIndent = new Intent(ProfileEditActivity.this, ProfileEditGeneralActivity.class);
                    registerIndent.putExtra("name",  name);
                    registerIndent.putExtra("email",  email);
                    registerIndent.putExtra("gender", gender);
                    registerIndent.putExtra("age", age);
                    registerIndent.putExtra("weight", weight);
                    registerIndent.putExtra("day", day);
                    registerIndent.putExtra("month", month);
                    registerIndent.putExtra("year", year);
                    registerIndent.putExtra("state", state);
                    registerIndent.putExtra("district", district);
                    registerIndent.putExtra("city", city);
                    startActivity(registerIndent);
                }
            }

        });
    }

    private boolean isEmpty(String name, String email, String gender, String age, String weight, String day, String month, String year, String state, String district, String city) {
        if (name.isEmpty() || email.isEmpty() || gender.isEmpty() || age.isEmpty() || weight.isEmpty() || day.isEmpty() || month.isEmpty()|| year.isEmpty() || state.isEmpty() || district.isEmpty() || city.isEmpty()) {
            Toast.makeText(ProfileEditActivity.this, "Complete All the Details", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
