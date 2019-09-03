package com.hemoglobin.hemoglobin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {

    private EditText Login_Email_Address, Login_Password;
    private Button Login_Button;
    private TextView Login_Register_Text, Login_Forgot_Password;
    private ProgressDialog mProgresDialog;

    private FirebaseAuth mAuth;
    private DatabaseReference mUsersDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login_Email_Address = findViewById(R.id.login_email_address);
        Login_Password = findViewById(R.id.login_password);
        Login_Button = findViewById(R.id.login_button);
        Login_Register_Text = findViewById(R.id.login_register_text);
        Login_Forgot_Password = findViewById(R.id.login_forgot_text);

        mAuth = FirebaseAuth.getInstance();

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mUsersDatabase.keepSynced(true);

        mProgresDialog = new ProgressDialog(this);

        Login_Forgot_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resetIntent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(resetIntent);
            }
        });

        Login_Register_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();

            }
        });

        Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email_address = Login_Email_Address.getText().toString();
                String password = Login_Password.getText().toString();

                if (isEmpty(email_address, password)) {

                    mProgresDialog.setTitle("Logging In");
                    mProgresDialog.setMessage("Authenticating User..");
                    mProgresDialog.show();
                    mProgresDialog.setCanceledOnTouchOutside(false);

                    mAuth.signInWithEmailAndPassword(email_address, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                mProgresDialog.setMessage("Setting User..");

                                String deviceToken = FirebaseInstanceId.getInstance().getToken();
                                String mCurrent_User_Id = mAuth.getCurrentUser().getUid();

                                mUsersDatabase.child(mCurrent_User_Id).child("device_token").setValue(deviceToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        mProgresDialog.dismiss();

                                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        finish();

                                    }
                                });



                            } else {

                                mProgresDialog.hide();
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "Error : " + errorMessage, Toast.LENGTH_LONG).show();

                            }



                        }
                    });

                }
            }
        });
    }

    private boolean isEmpty(String mobile_number, String password) {
        if (mobile_number.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Complete All the Details", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser mCurrent_User = mAuth.getCurrentUser();

        if(mCurrent_User != null){

            Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainIntent);

        }

    }
}