package com.hemoglobin.hemoglobin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class ProfileEditOtherDiseasesActivity extends AppCompatActivity {

    private RadioGroup KA, DIA, E, Jan, Tatt, TM, BBP, lab, lp;
    private FloatingActionButton Profile_Edit_Diseases_Others;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private String mCurrentUserId;
    private DatabaseReference mUsersDatabase, mProfileDatabase, mDoctorsDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit_other_diseases);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mCurrentUserId = mCurrentUser.getUid();

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUserId);
        mProfileDatabase = FirebaseDatabase.getInstance().getReference().child("Profile").child(mCurrentUserId);
        mDoctorsDatabase = FirebaseDatabase.getInstance().getReference().child("Blood Bank");
        mUsersDatabase.keepSynced(true);
        mProfileDatabase.keepSynced(true);
        mDoctorsDatabase.keepSynced(true);

        KA = findViewById(R.id.ka);
        DIA = findViewById(R.id.dia);
        E = findViewById(R.id.ep);
        Jan = findViewById(R.id.profile_edit_jan);
        Tatt = findViewById(R.id.profile_edit_tatt);
        TM = findViewById(R.id.profile_edit_tm);
        BBP = findViewById(R.id.profile_edit_bbp);
        lp = findViewById(R.id.lp);
        lab = findViewById(R.id.lab);
        Profile_Edit_Diseases_Others = findViewById(R.id.profile_edit_other_diseases_submit);

        Profile_Edit_Diseases_Others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int ka_id = KA.getCheckedRadioButtonId();
                RadioButton ka_button = findViewById(ka_id);
                int dia_id = DIA.getCheckedRadioButtonId();
                RadioButton dia_button = findViewById(dia_id);
                int e_id = E.getCheckedRadioButtonId();
                RadioButton e_button = findViewById(e_id);
                int jan_id = Jan.getCheckedRadioButtonId();
                RadioButton jan_button = findViewById(jan_id);
                int tatt_id = Tatt.getCheckedRadioButtonId();
                RadioButton tatt_button = findViewById(tatt_id);
                int tm_id = TM.getCheckedRadioButtonId();
                RadioButton tm_button = findViewById(tm_id);
                int bbp_id = BBP.getCheckedRadioButtonId();
                RadioButton bbp_button = findViewById(bbp_id);

                int lp_id = lp.getCheckedRadioButtonId();
                RadioButton lp_button = findViewById(lp_id);
                int lap_id = lab.getCheckedRadioButtonId();
                RadioButton lap_button = findViewById(lap_id);

//                int np_id = NP.getCheckedRadioButtonId();
//                RadioButton np_button = findViewById(np_id);

                final String name = getIntent().getStringExtra("name");
                final String email = getIntent().getStringExtra("email");
                final String gender = getIntent().getStringExtra("gender");
                final String age = getIntent().getStringExtra("age");
                final String weight = getIntent().getStringExtra("weight");
                final String day = getIntent().getStringExtra("day");
                final String month = getIntent().getStringExtra("month");
                final String year = getIntent().getStringExtra("year");
                final String state = getIntent().getStringExtra("state");
                final String district = getIntent().getStringExtra("district");
                final String city = getIntent().getStringExtra("city");
                final String bg = getIntent().getStringExtra("bg");
                final String hl = getIntent().getStringExtra("hl");
                final String pr = getIntent().getStringExtra("pr");
                final String bp = getIntent().getStringExtra("bp");
                final String bt = getIntent().getStringExtra("bt");
                final String rd = getIntent().getStringExtra("rd");
                final String alcohol = getIntent().getStringExtra("alcohol");
                final String hiv = getIntent().getStringExtra("hiv");
                final String ca = getIntent().getStringExtra("ca");
                final String hypertension = getIntent().getStringExtra("hypertension");
                final String asth = getIntent().getStringExtra("asth");
                final String tuber = getIntent().getStringExtra("tuber");
                final String cancer = getIntent().getStringExtra("cancer");
                final String ka = ka_button.getText().toString();
                final String dia = dia_button.getText().toString();
                final String e = e_button.getText().toString();
                final String jan = jan_button.getText().toString();
                final String tm = tm_button.getText().toString();
                final String tatt = tatt_button.getText().toString();
                final String bbp = bbp_button.getText().toString();
                final String lady_abrt = lap_button.getText().toString();
                final String lady_period = lp_button.getText().toString();
                //final String np = np_button.getText().toString();

                if (isEmpty(ka, dia, e, jan, tm , tatt, bbp)) {

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(ProfileEditOtherDiseasesActivity.this);
                    alertDialog.setTitle("Blood Bank Key");
                    alertDialog.setMessage("Enter Blood Bank Key");
                    final EditText input = new EditText(ProfileEditOtherDiseasesActivity.this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);
                    alertDialog.setView(input);

                    alertDialog.setPositiveButton("Authorize",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    final String password = input.getText().toString();
                                    if (!password.isEmpty()) {
                                        mDoctorsDatabase.child(password).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {

                                                if (dataSnapshot.exists()){
                                                    String doctor_key = dataSnapshot.child("user_id").getValue().toString();
                                                    if (doctor_key.equals(password)) {
                                                        Toast.makeText(getApplicationContext(), "Key Matched", Toast.LENGTH_SHORT).show();
                                                        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                                                        final HashMap profileMap = new HashMap<>();
                                                        profileMap.put("email_id", email);
                                                        profileMap.put("name", name);
                                                        profileMap.put("state", state);
                                                        profileMap.put("district",district);
                                                        profileMap.put("city",city);
                                                        profileMap.put("date_of_birth", day+" : "+month+" : "+year);
                                                        profileMap.put("age", age);
                                                        profileMap.put("gender", gender);
                                                        profileMap.put("weight", weight);
                                                        profileMap.put("blood_group", bg);
                                                        profileMap.put("hemoglobin_level", hl);
                                                        profileMap.put("pulse_rate", pr);
                                                        profileMap.put("body_temperature", bt);
                                                        profileMap.put("bp_diastolic", bp);
                                                        profileMap.put("recent_donation", rd);
                                                        profileMap.put("alcohol", alcohol);
                                                        profileMap.put("hiv", hiv);
                                                        profileMap.put("cardiac_arrest", ca);
                                                        profileMap.put("hyper_tension", hypertension);
                                                        profileMap.put("kidney_alignments", ka);
                                                        profileMap.put("diabetics", dia);
                                                        profileMap.put("epilepsy", e);
                                                        profileMap.put("date", date);
                                                        profileMap.put("jaundice", jan);
                                                        profileMap.put("typhoid_or_malaria", jan);
                                                        profileMap.put("tattoo", jan);
                                                        profileMap.put("asthmatic", asth);
                                                        profileMap.put("tuberculosis", tuber);
                                                        profileMap.put("cancer", cancer);
                                                        profileMap.put("bbp", bbp);
                                                        profileMap.put("periods", lady_period);
                                                        profileMap.put("abortion", lady_abrt);
                                                        profileMap.put("user_id", mCurrentUserId);

                                                        mProfileDatabase.setValue(profileMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                if (task.isSuccessful()) {

                                                                    Toast.makeText(ProfileEditOtherDiseasesActivity.this, "Sucessfully Updated", Toast.LENGTH_LONG).show();
                                                                    Intent setupIntent = new Intent(ProfileEditOtherDiseasesActivity.this, MainActivity.class);
                                                                    setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                    startActivity(setupIntent);
                                                                    finish();

                                                                }

                                                            }
                                                        });
                                                    } else {
                                                        Toast.makeText(getApplicationContext(), "Key is Invalid", Toast.LENGTH_SHORT).show();
                                                    }
                                                }else{
                                                    Toast.makeText(ProfileEditOtherDiseasesActivity.this, "Key did not Match", Toast.LENGTH_SHORT).show();
                                                }

                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });
                                    }else{

                                        Toast.makeText(ProfileEditOtherDiseasesActivity.this, "Ask Doctor to enter the key", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    alertDialog.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });

                    alertDialog.show();
                }
            }

        });
    }

    private boolean isEmpty(String name, String email, String gender, String np, String tm, String tatt, String bbp) {
        if (name.isEmpty() || email.isEmpty() || gender.isEmpty() || np.isEmpty() || tm.isEmpty() || tatt.isEmpty()) {
            Toast.makeText(ProfileEditOtherDiseasesActivity.this, "Complete All the Details", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}