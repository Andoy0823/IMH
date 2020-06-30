package com.example.imh_mega;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.imh_mega.BackgroundTasks.SignUpHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class SignUpActivity extends AppCompatActivity {

    FirebaseDatabase rootNode, rootSchema;
    DatabaseReference dbreference, tblReference2;

    Integer randOutput;
    Random r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        final TextInputEditText editvipName, edituserName, editpassword, editconfirmPassword, editPhone, editaddress;
        Button signUpBtn, clrFieldBtn;


        r = new Random();

        editvipName = (TextInputEditText) findViewById(R.id.fullnameSignupFieldID);
        edituserName = (TextInputEditText) findViewById(R.id.userSignupFieldID);
        editpassword = (TextInputEditText) findViewById(R.id.passSignupFieldID);
        editconfirmPassword = (TextInputEditText) findViewById(R.id.confirmpassSignupFieldID);
        editPhone = (TextInputEditText) findViewById(R.id.phoneSignupFieldID);
        editaddress = (TextInputEditText) findViewById(R.id.addressSignupFieldID);

        signUpBtn = findViewById(R.id.signUpDbBtnID);
        clrFieldBtn = findViewById(R.id.clearfieldsignupBtnID);

        String Smin = "100000";
        String Smax = "999999";

        Integer dmin = Integer.parseInt(Smin);
        Integer dmax = Integer.parseInt(Smax);

        randOutput = r.nextInt((dmax - dmin) + 1) + dmin;

        clrFieldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editvipName.setText("");
                edituserName.setText("");
                editpassword.setText("");
                editconfirmPassword.setText("");
                editPhone.setText("");
                editaddress.setText("");
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String usernameInput = edituserName.getText().toString();
                final String passwordInput = editpassword.getText().toString();
                final String confirmInput = editconfirmPassword.getText().toString();
                final String vipName = editvipName.getText().toString();
                final String address = editaddress.getText().toString();
                final String phone = editPhone.getText().toString();
                final String vipID = String.valueOf(randOutput);

                if (usernameInput.equals("") || passwordInput.equals("") || confirmInput.equals("")

                        || vipName.equals("") || address.equals("") || phone.equals(""))
                {
                    Toast.makeText(SignUpActivity.this, "Fill Up All Fields", Toast.LENGTH_SHORT).show();
                }

                else
                    {
                    if (checkPassword(passwordInput, confirmInput, usernameInput) == 1){

                        tblReference2 = FirebaseDatabase.getInstance().getReference().child("VipUsers");
                            tblReference2.child(usernameInput).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        Toast.makeText(SignUpActivity.this, "Username already taken", Toast.LENGTH_SHORT).show();
                                    }

                                    else
                                        {
                                            rootNode = FirebaseDatabase.getInstance();
                                            dbreference = rootNode.getReference("VipUsers");

                                            SignUpHelper signUpHelper = new SignUpHelper(vipID, vipName, usernameInput, passwordInput, phone, address);
                                            dbreference.child(vipID).setValue(signUpHelper);

                                            Toast.makeText(SignUpActivity.this, "Sign Up Successfull", Toast.LENGTH_SHORT).show();

                                            Intent loginIntent = new Intent(SignUpActivity.this, LoginActivity.class);

                                            startActivity(loginIntent);

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });

                        SignUpHelper signUpHelper = new SignUpHelper();

                    }

                    else if (checkPassword(passwordInput, confirmInput, usernameInput) == 2)
                    {

                        Toast.makeText(SignUpActivity.this, "Password does not match", Toast.LENGTH_SHORT).show();

                    }

                    else
                        {
                            Toast.makeText(SignUpActivity.this, "Username or Password cannot contain spaces!", Toast.LENGTH_SHORT).show();
                            editpassword.setText("");
                            editconfirmPassword.setText("");
                    }
                }
            }

            public int checkPassword (String passwordInput, String confirmInput, String usernameInput){

                if (passwordInput.equals(confirmInput)){
                    return 1;
                }

                if (usernameInput.contains(" ") || passwordInput.contains(" ")){
                    return 2;
                }

                else {
                    return 3;
                }
            }
        });
    }
}