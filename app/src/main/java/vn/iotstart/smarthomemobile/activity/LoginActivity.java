package vn.iotstart.smarthomemobile.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import org.w3c.dom.Text;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.User;
import vn.iotstart.smarthomemobile.response.LoginResponse;

public class LoginActivity extends AppCompatActivity {
    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonLogin;
    TextView textViewSignup;
    TextView textViewForgotPassword;
    ProgressBar progressBarLogin;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mapping();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (TextUtils.isEmpty(email)){
                    editTextEmail.setError("Please enter your email");
                    editTextEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    editTextPassword.setError("Please enter your password");
                    editTextPassword.requestFocus();
                    return;
                }
                progressBarLogin.setVisibility(View.VISIBLE);

                User user = new User();
                user.setEmail(email);
                user.setPassword(password);

                loginUser(user);
                progressBarLogin.setVisibility(View.GONE);
            }
        });

    }

    private void mapping(){
        editTextEmail = (EditText) findViewById(R.id.editTextLoginEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextLoginPassword);
        textViewSignup = (TextView) findViewById(R.id.textViewSignup);
        textViewForgotPassword = (TextView) findViewById(R.id.textViewForgotPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        progressBarLogin = (ProgressBar) findViewById(R.id.progressBarLogin);

        progressBarLogin.setVisibility(View.GONE);
    }

        private void loginUser(User user){
            ApiService.apiService.login(user).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

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
                            Toast.makeText(LoginActivity.this, loginResponse.getUser().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this,"An error occur! Please try again", Toast.LENGTH_SHORT).show();
                    Log.d("hello", "onFailure: " + t.getMessage());
                }
            });

        }

}
