package com.hemoglobin.hemoglobin;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ProfileEditDiseasesActivity extends AppCompatActivity {

    private RadioGroup Alcohol, HIV, CA, HyperTension, Asth, Cancer, Tuber;
    private FloatingActionButton Profile_Edit_Diseases;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit_diseases);

        Alcohol = findViewById(R.id.alcohol);
        HIV = findViewById(R.id.hiv);
        CA = findViewById(R.id.ca);
        HyperTension = findViewById(R.id.hp);
        Asth = findViewById(R.id.profile_edit_as);
        Tuber = findViewById(R.id.profile_edit_tub);
        Cancer = findViewById(R.id.profile_edit_can);
        Profile_Edit_Diseases = findViewById(R.id.profile_edit_diseases);

        Profile_Edit_Diseases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int alcohol_id = Alcohol.getCheckedRadioButtonId();
                RadioButton alcohol_button = findViewById(alcohol_id);
                int hiv_id = HIV.getCheckedRadioButtonId();
                RadioButton hiv_button = findViewById(hiv_id);
                int ca_id = CA.getCheckedRadioButtonId();
                RadioButton ca_button = findViewById(ca_id);
                int hyper_id = HyperTension.getCheckedRadioButtonId();
                RadioButton hyper_button = findViewById(hyper_id);
                int asth_id = Asth.getCheckedRadioButtonId();
                RadioButton asth_button = findViewById(asth_id);
                int tub_id = Tuber.getCheckedRadioButtonId();
                RadioButton tub_button = findViewById(tub_id);
                int can_id = Cancer.getCheckedRadioButtonId();
                RadioButton can_button = findViewById(can_id);

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
                String bg = getIntent().getStringExtra("bg");
                String hl = getIntent().getStringExtra("hl");
                String pr = getIntent().getStringExtra("pr");
                String bp = getIntent().getStringExtra("bp");
                String bt = getIntent().getStringExtra("bt");
                String rd = getIntent().getStringExtra("rd");
                String alcohol = alcohol_button.getText().toString();
                String hiv = hiv_button.getText().toString();
                String ca = ca_button.getText().toString();
                String hypertension = hyper_button.getText().toString();
                String asth = asth_button.getText().toString();
                String tuber = tub_button.getText().toString();
                String cancer = can_button.getText().toString();

                if (isEmpty(alcohol, hiv, ca, hypertension, asth, tuber, cancer)){

                    Intent registerIndent = new Intent(ProfileEditDiseasesActivity.this, ProfileEditOtherDiseasesActivity.class);
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
                    registerIndent.putExtra("bg", bg);
                    registerIndent.putExtra("hl", hl);
                    registerIndent.putExtra("pr", pr);
                    registerIndent.putExtra("bp", bp);
                    registerIndent.putExtra("bt", bt);
                    registerIndent.putExtra("rd", rd);
                    registerIndent.putExtra("alcohol", alcohol);
                    registerIndent.putExtra("hiv", hiv);
                    registerIndent.putExtra("ca", ca);
                    registerIndent.putExtra("hypertension", hypertension);
                    registerIndent.putExtra("asth", asth);
                    registerIndent.putExtra("tuber", tuber);
                    registerIndent.putExtra("cancer", cancer);
                    startActivity(registerIndent);
                }
            }

        });
    }

    private boolean isEmpty(String name, String email, String gender, String age, String asth, String tuber, String cancer) {
        if (name.isEmpty() || email.isEmpty() || gender.isEmpty() || age.isEmpty() || asth.isEmpty() || tuber.isEmpty() || cancer.isEmpty() ) {
            Toast.makeText(ProfileEditDiseasesActivity.this, "Complete All the Details", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}