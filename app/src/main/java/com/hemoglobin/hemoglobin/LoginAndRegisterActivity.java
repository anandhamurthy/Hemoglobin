package com.hemoglobin.hemoglobin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginAndRegisterActivity extends AppCompatActivity {

    private Button Login_And_Register_Login_Button, Login_And_Register_Register_Button;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_and_register);

        mAuth = FirebaseAuth.getInstance();

        Login_And_Register_Login_Button = findViewById(R.id.login_and_register_login_button);

        Login_And_Register_Register_Button = findViewById(R.id.login_and_register_register_button);

        Login_And_Register_Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(LoginAndRegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });


        Login_And_Register_Register_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginAndRegisterActivity.this, RegisterActivity.class);
                registerIntent.putExtra("selection", getIntent().getStringExtra("selection"));
                startActivity(registerIntent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser mCurrent_User = mAuth.getCurrentUser();

        if(mCurrent_User != null){

            Intent mainIntent = new Intent(LoginAndRegisterActivity.this, MainActivity.class);
            startActivity(mainIntent);

        }
    }
}
