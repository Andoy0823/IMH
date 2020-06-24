package com.example.imh_mega;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText userLogin, passLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Button loginBtn, signupBtn;

        signupBtn = findViewById(R.id.signUpBtnID);
        loginBtn = findViewById(R.id.loginBtnID);

        userLogin = findViewById(R.id.userInputFieldID);
        passLogin = findViewById(R.id.passInputFieldID);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signupIntent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUser();
            }
        });


    }

    public void getUser(){

        final String usernameInput, passwordInput;

        usernameInput = userLogin.getText().toString().trim();
        passwordInput = passLogin.getText().toString().trim();

        DatabaseReference dbreference = FirebaseDatabase.getInstance().getReference("VipUsers");

        Query checkUser = dbreference.orderByChild("username").equalTo(usernameInput);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    String passwordfromDB = snapshot.child(usernameInput).child("password").getValue(String.class);

                    if (passwordfromDB.equals(passwordInput)){

                        String userfromDB = snapshot.child(usernameInput).child("username").getValue(String.class);
                        String passfromDB = snapshot.child(usernameInput).child("password").getValue(String.class);
                        String addressfromDB = snapshot.child(usernameInput).child("address").getValue(String.class);
                        String phonefromDB = snapshot.child(usernameInput).child("phone").getValue(String.class);
                        String vipNamefromDB = snapshot.child(usernameInput).child("vipName").getValue(String.class);

                        Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);

                        intentMain.putExtra("username",userfromDB);
                        intentMain.putExtra("password",passfromDB);
                        intentMain.putExtra("address",addressfromDB);
                        intentMain.putExtra("phone",phonefromDB);
                        intentMain.putExtra("vipName",vipNamefromDB);

                        startActivity(intentMain);
                    }
                    Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                    userLogin.setText("");
                    passLogin.setText("");
                }
                else{
                    Toast.makeText(LoginActivity.this, "Invalid Login", Toast.LENGTH_SHORT).show();
                    userLogin.setText("");
                    passLogin.setText("");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}