package com.hemoglobin.hemoglobin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ProfileEditGeneralActivity extends AppCompatActivity {

    private EditText HL, PR, BP, BT, year;
    private Spinner day, month;
    private Spinner BG;
    private FloatingActionButton Profile_Next_General;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit_general);


        BG = findViewById(R.id.profile_edit_bg);
        HL = findViewById(R.id.profile_edit_hl);
        PR = findViewById(R.id.profile_edit_pr);
        BP = findViewById(R.id.profile_edit_bd);
        BT = findViewById(R.id.profile_edit_bt);
        day = findViewById(R.id.profile_edit_day);
        month = findViewById(R.id.profile_edit_month);
        year = findViewById(R.id.profile_edit_year);
        Profile_Next_General = findViewById(R.id.profile_edit_general);

        Profile_Next_General.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bg = BG.getSelectedItem().toString();
                String hl = HL.getText().toString();
                String pr = PR.getText().toString();
                String bp = BP.getText().toString();
                String bt = BT.getText().toString();
                String rd = day.getSelectedItem().toString()+"-"+month.getSelectedItem().toString()+"-"+year.getText().toString();
                String name = getIntent().getStringExtra("name");
                String email = getIntent().getStringExtra("email");
                String gender = getIntent().getStringExtra("gender");
                String age = getIntent().getStringExtra("age");
                String weight = getIntent().getStringExtra("weight");
                String day = getIntent().getStringExtra("day");
                String month = getIntent().getStringExtra("month");
                String year = getIntent().getStringExtra("year");
                String state = getIntent().getStringExtra("state");
                String district = getIntent().getStringExtra("district");
                String city = getIntent().getStringExtra("city");

                if (isEmpty(bg, hl, pr, bp, bt, rd)){

                    Intent registerIndent = new Intent(ProfileEditGeneralActivity.this, ProfileEditDiseasesActivity.class);
                    registerIndent.putExtra("bg", bg);
                    registerIndent.putExtra("hl", hl);
                    registerIndent.putExtra("pr", pr);
                    registerIndent.putExtra("bp", bp);
                    registerIndent.putExtra("bt", bt);
                    registerIndent.putExtra("rd", rd);
                    registerIndent.putExtra("name", name);
                    registerIndent.putExtra("email", email);
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

    private boolean isEmpty(String name, String email, String gender, String age, String weight, String day) {
        if (name.isEmpty() || email.isEmpty() || gender.isEmpty() || age.isEmpty() || weight.isEmpty() || day.isEmpty()) {
            Toast.makeText(ProfileEditGeneralActivity.this, "Complete All the Details", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}

