package vn.iotstart.smarthomemobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.PreManager;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.User;
import vn.iotstart.smarthomemobile.response.LoginResponse;

public class LoginActivity extends AppCompatActivity {
    EditText editTextAccount;
    EditText editTextPassword;
    Button buttonLogin;
    TextView textViewSignup;
    TextView textViewForgotPassword;
    ProgressBar progressBarLogin;
    PreManager preManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mapping();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTextAccount.getText().toString();
                String password = editTextPassword.getText().toString();

                if (TextUtils.isEmpty(id)){
                    editTextAccount.setError("Please enter your account");
                    editTextAccount.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    editTextPassword.setError("Please enter your password");
                    editTextPassword.requestFocus();
                    return;
                }
                progressBarLogin.setVisibility(View.VISIBLE);

                User user = new User();
                user.setId(id);
                user.setPassword(password);

                loginUser(user);
            }
        });


        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, AccountForgotActivity.class);
                startActivity(intent);
            }
        });

    }

    private void mapping(){
        editTextAccount = (EditText) findViewById(R.id.editTextLoginAccount);
        editTextPassword = (EditText) findViewById(R.id.editTextLoginPassword);
        textViewSignup = (TextView) findViewById(R.id.textViewSignup);
        textViewForgotPassword = (TextView) findViewById(R.id.textViewForgotPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        progressBarLogin = (ProgressBar) findViewById(R.id.progressBarLogin);
        preManager = new PreManager(getApplicationContext());

        if (!preManager.isUserLogout()){
            Intent intent = new Intent(getApplicationContext(), IndexActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return;
        }

        progressBarLogin.setVisibility(View.GONE);
    }

    private void loginUser(User user){
        ApiService.apiService.login(user).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call,@NonNull Response<LoginResponse> response) {
                progressBarLogin.setVisibility(View.GONE);
                if (response.isSuccessful()){
                    LoginResponse loginResponse = response.body();
                    if (TextUtils.equals(loginResponse.getMessage(),"Not Found")){
                        Toast.makeText(LoginActivity.this, "Your account doesn't exist", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.equals(loginResponse.getMessage(), "Incorrect")){
                        Toast.makeText(LoginActivity.this, "Incorrect Password, Please try again", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (TextUtils.equals(loginResponse.getMessage(), "Success")){
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                        preManager.saveUserDetail(loginResponse.getUser());
                        Intent intent = new Intent(getApplicationContext(), IndexActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call,@NonNull Throwable t) {
                progressBarLogin.setVisibility(View.GONE);
                Log.d("login", "onFailure: " + t.getMessage());
                Toast.makeText(LoginActivity.this,"An error occur! Please try again", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
