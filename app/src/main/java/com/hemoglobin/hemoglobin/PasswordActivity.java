package com.hemoglobin.hemoglobin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

import id.zelory.compressor.Compressor;

public class PasswordActivity extends AppCompatActivity {

    private EditText Password_Password, Password_Confirm_Password;
    private FloatingActionButton Password_Register;

    private ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;
    private DatabaseReference mUsersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Password_Password = findViewById(R.id.password_password);
        Password_Confirm_Password = findViewById(R.id.password_comfirm_password);
        Password_Register = findViewById(R.id.register_register);

        mProgressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);

        Password_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password = Password_Password.getText().toString();
                String confirm_password = Password_Confirm_Password.getText().toString();
                final String name = getIntent().getStringExtra("name");
                final String email_address = getIntent().getStringExtra("email");
                final String gender = getIntent().getStringExtra("gender");
                final String age = getIntent().getStringExtra("age");
                final String weight = getIntent().getStringExtra("weight");
                final String day = getIntent().getStringExtra("day");
                final String month = getIntent().getStringExtra("month");
                final String year = getIntent().getStringExtra("year");
                final String state = getIntent().getStringExtra("state");
                final String district = getIntent().getStringExtra("district");
                final String city = getIntent().getStringExtra("city");

                if (isEmpty(name, email_address, gender, age, weight, day, month, year, state, district, city)) {

                    if (isEqual(confirm_password, password)){

                        mProgressDialog.setTitle("Registering");
                        mProgressDialog.setMessage("Creating User..");
                        mProgressDialog.show();
                        mProgressDialog.setCanceledOnTouchOutside(false);

                        mAuth.createUserWithEmailAndPassword(email_address, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    if (task.isSuccessful()) {

                                        mProgressDialog.setMessage("Creating The Profile..");

                                        String device_token = FirebaseInstanceId.getInstance().getToken();
                                        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                                        String uid = current_user.getUid();
                                        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                                        HashMap userMap = new HashMap<>();
                                        userMap.put("email_id", email_address);
                                        userMap.put("name", name);
                                        userMap.put("state", state);
                                        userMap.put("district",district);
                                        userMap.put("day", day);
                                        userMap.put("month", month);
                                        userMap.put("year", year);
                                        userMap.put("age", age);
                                        userMap.put("gender", gender);
                                        userMap.put("weight", weight);
                                        userMap.put("city", city);
                                        userMap.put("created_date", date);
                                        userMap.put("user_id", uid);
                                        userMap.put("device_token", device_token);

                                        mUsersDatabase.child(uid).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {

                                                    mProgressDialog.dismiss();

                                                    Toast.makeText(PasswordActivity.this, "Account Created & Logging in Sucessfully", Toast.LENGTH_LONG).show();
                                                    Intent setupIntent = new Intent(PasswordActivity.this, MainActivity.class);
                                                    setupIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(setupIntent);
                                                    finish();

                                                }

                                            }
                                        });

                                    } else {

                                        mProgressDialog.dismiss();
                                        String error = task.getException().getMessage();
                                        Toast.makeText(PasswordActivity.this, "Error : " + error, Toast.LENGTH_LONG).show();


                                    }

                                } else {
                                    mProgressDialog.dismiss();
                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(PasswordActivity.this, "Error : " + errorMessage, Toast.LENGTH_LONG).show();

                                }

                            }
                        });


                    }

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

            Intent mainIntent = new Intent(PasswordActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();

        }

    }

    private boolean isEqual(String confirm_password, String password) {
        if (!confirm_password.equals(password)) {
            Toast.makeText(PasswordActivity.this, "Password Does Not Match", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isEmpty(String name, String email, String gender, String age, String weight, String day, String month, String year, String state, String district, String city) {
        if (name.isEmpty() || email.isEmpty() || gender.isEmpty() || age.isEmpty() || weight.isEmpty() || day.isEmpty() || month.isEmpty() || year.isEmpty() || state.isEmpty() || district.isEmpty() || city.isEmpty()) {
            Toast.makeText(PasswordActivity.this, "Complete All the Details", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}