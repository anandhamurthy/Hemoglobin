package com.hemoglobin.hemoglobin;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText Register_Name, Register_Email_Address;
    private FloatingActionButton Register_Next;
    private TextView Register_Login_Text;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Register_Name = findViewById(R.id.register_name);
        Register_Email_Address = findViewById(R.id.register_email_address);
        Register_Login_Text = findViewById(R.id.register_login_text);
        Register_Next = findViewById(R.id.register_next);
        mAuth = FirebaseAuth.getInstance();

        Register_Login_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(registerIntent);
                finish();

            }
        });

        Register_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = Register_Name.getText().toString();
                String email_address = Register_Email_Address.getText().toString();

                if (isEmpty(name, email_address)){

                    Intent registerIndent = new Intent(RegisterActivity.this, SetupActivity.class);
                    registerIndent.putExtra("name", name);
                    registerIndent.putExtra("email_address", email_address);
                    startActivity(registerIndent);

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

            Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();

        }

    }

    private boolean isEmpty(String mobile_number, String password) {
        if (mobile_number.isEmpty() || password.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "Complete All the Details", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
