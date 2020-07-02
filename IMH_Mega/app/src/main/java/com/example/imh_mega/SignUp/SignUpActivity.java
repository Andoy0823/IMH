package com.example.imh_mega.SignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.imh_mega.Login.LoginActivity;
import com.example.imh_mega.Login.Models.VipModel;
import com.example.imh_mega.MainActivity;
import com.example.imh_mega.R;
import com.example.imh_mega.Retrofit.APIInterface;
import com.example.imh_mega.Retrofit.ApiClient;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.fullnameSignupFieldID) TextInputEditText editvipName;
    @BindView(R.id.userSignupFieldID) TextInputEditText edituserName;
    @BindView(R.id.passSignupFieldID) TextInputEditText editpassword;
    @BindView(R.id.confirmPassSignupFieldID) TextInputEditText editconfirmPassword;
    @BindView(R.id.phoneSignupFieldID) TextInputEditText editPhone;
    @BindView(R.id.addressSignupFieldID) TextInputEditText editAddress;

    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        apiInterface = ApiClient.getAPIClient().create(APIInterface.class);

        ButterKnife.bind(this);

        Button signUpBtn, clrFieldBtn;

        signUpBtn = findViewById(R.id.signUpDbBtnID);
        clrFieldBtn = findViewById(R.id.clearfieldsignupBtnID);

            signUpBtn.setEnabled(true);

            signUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Call<VipModel> registerCall = apiInterface.registerUser(
                            editvipName.getText().toString().trim(),
                            edituserName.getText().toString().trim(),
                            editpassword.getText().toString().trim(),
                            editconfirmPassword.getText().toString().trim(),
                            editPhone.getText().toString().trim(),
                            editAddress.getText().toString().trim());

                    registerCall.enqueue(new Callback<VipModel>() {
                        @Override
                        public void onResponse(Call<VipModel> call, Response<VipModel> response) {

                            if (response.body() != null){

                                VipModel vipMowdel = response.body();

                                if (vipMowdel.isSuccess()){

                                    Toast.makeText(SignUpActivity.this, vipMowdel.getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent loginAct = new Intent(SignUpActivity.this, LoginActivity.class);
                                    startActivity(loginAct);

                                }

                                else{

                                    Toast.makeText(SignUpActivity.this, vipMowdel.getMessage(), Toast.LENGTH_SHORT).show();

                                }

                            }

                        }

                        @Override
                        public void onFailure(Call<VipModel> call, Throwable t) {

                            Toast.makeText(SignUpActivity.this, "Error", Toast.LENGTH_SHORT).show();

                        }
                    });

                }
            });





        clrFieldBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editvipName.setText("");
                editAddress.setText("");
                editPhone.setText("");
                editpassword.setText("");
                edituserName.setText("");

            }
        });

    }
}