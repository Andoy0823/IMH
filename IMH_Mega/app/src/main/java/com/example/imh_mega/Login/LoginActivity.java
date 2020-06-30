package com.example.imh_mega.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.imh_mega.Login.Models.VipModel;
import com.example.imh_mega.MainActivity;
import com.example.imh_mega.R;
import com.example.imh_mega.Retrofit.ApiClient;
import com.example.imh_mega.Retrofit.APIInterface;
import com.example.imh_mega.SignUp.SignUpActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.userInputFieldID) TextInputEditText editUserLogin;
    @BindView(R.id.passInputFieldID) TextInputEditText editPassLogin;

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        apiInterface = ApiClient.getAPIClient().create(APIInterface.class);

        ButterKnife.bind(this);


        Button loginBtn, signupBtn;

        signupBtn = findViewById(R.id.signUpBtnID);
        loginBtn = findViewById(R.id.loginBtnID);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<VipModel> VipModelCall = apiInterface.loginUser(editUserLogin.getText().toString().trim(),
                                                                        editPassLogin.getText().toString().trim());

                VipModelCall.enqueue(new Callback<VipModel>() {
                    @Override
                    public void onResponse(Call<VipModel> call, Response<VipModel> response) {

                        if (response.isSuccessful() && response.body() != null){

                            VipModel vipMowdel = response.body();

                            if (vipMowdel.isSuccess()){

                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent mainAct = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(mainAct);

                            }

                            else{

                                Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                            }

                        }

                    }

                    @Override
                    public void onFailure(Call<VipModel> call, Throwable t) {

                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signUpintent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signUpintent);

            }
        });

    }

}