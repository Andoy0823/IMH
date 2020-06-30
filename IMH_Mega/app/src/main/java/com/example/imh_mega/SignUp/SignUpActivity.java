package com.example.imh_mega.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.imh_mega.R;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        final TextInputEditText editvipName, edituserName, editpassword, editconfirmPassword, editPhone, editaddress;
        Button signUpBtn, clrFieldBtn;


        editvipName = (TextInputEditText) findViewById(R.id.fullnameSignupFieldID);
        edituserName = (TextInputEditText) findViewById(R.id.userSignupFieldID);
        editpassword = (TextInputEditText) findViewById(R.id.passSignupFieldID);
        editconfirmPassword = (TextInputEditText) findViewById(R.id.confirmpassSignupFieldID);
        editPhone = (TextInputEditText) findViewById(R.id.phoneSignupFieldID);
        editaddress = (TextInputEditText) findViewById(R.id.addressSignupFieldID);

        signUpBtn = findViewById(R.id.signUpDbBtnID);
        clrFieldBtn = findViewById(R.id.clearfieldsignupBtnID);

    }
}