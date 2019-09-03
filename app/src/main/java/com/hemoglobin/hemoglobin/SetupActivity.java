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

import de.hdodenhof.circleimageview.CircleImageView;

public class SetupActivity extends AppCompatActivity {

    private EditText Age, Weight, Year, City;
    private TextView Name, Email;
    private CircleImageView Profile_Image;
    private FloatingActionButton Profile_Edit_Next;

    private AutoCompleteTextView Gender, State, District, Day, Month;

    private String gender_spinner[], state_spinner[], tamil_spinner[], day_spinner[], month_spinner[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        gender_spinner = getResources().getStringArray(R.array.gender);
        state_spinner = getResources().getStringArray(R.array.gender);
        tamil_spinner = getResources().getStringArray(R.array.tamilnadu);
        day_spinner = getResources().getStringArray(R.array.day);
        month_spinner = getResources().getStringArray(R.array.month);

        ArrayAdapter arrayAdapter= new ArrayAdapter(SetupActivity.this, android.R.layout.simple_list_item_1, gender_spinner);
        ArrayAdapter arrayAdapter1= new ArrayAdapter(SetupActivity.this, android.R.layout.simple_list_item_1, tamil_spinner);
        ArrayAdapter arrayAdapter2= new ArrayAdapter(SetupActivity.this, android.R.layout.simple_list_item_1, state_spinner);
        ArrayAdapter arrayAdapter3= new ArrayAdapter(SetupActivity.this, android.R.layout.simple_list_item_1, day_spinner);
        ArrayAdapter arrayAdapter4= new ArrayAdapter(SetupActivity.this, android.R.layout.simple_list_item_1, month_spinner);

        Name = findViewById(R.id.setup_name);
        Email = findViewById(R.id.setup_email);
        Gender = findViewById(R.id.setup_gender);
        Age = findViewById(R.id.setup_age);
        Weight = findViewById(R.id.setup_weight);
        Day = findViewById(R.id.setup_day);
        Month = findViewById(R.id.setup_month);
        Year = findViewById(R.id.setup_year);
        State = findViewById(R.id.setup_state);
        District = findViewById(R.id.setup_district);
        City = findViewById(R.id.setup_city);
        Profile_Image = findViewById(R.id.setup_image);
        Profile_Edit_Next = findViewById(R.id.setup_next);

        Name.setText(getIntent().getStringExtra("name"));
        Email.setText(getIntent().getStringExtra("email_address"));
        Gender.setAdapter(arrayAdapter);
        State.setAdapter(arrayAdapter2);
        District.setAdapter(arrayAdapter1);
        Day.setAdapter(arrayAdapter3);
        Month.setAdapter(arrayAdapter4);

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

                    Intent registerIndent = new Intent(SetupActivity.this, PasswordActivity.class);
                    registerIndent.putExtra("name",  getIntent().getStringExtra("name"));
                    registerIndent.putExtra("email",  getIntent().getStringExtra("email_address"));
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
            Toast.makeText(SetupActivity.this, "Complete All the Details", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
