package vn.iotstart.smarthomemobile.activity.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.ApiService;
import vn.iotstart.smarthomemobile.MainActivity;
import vn.iotstart.smarthomemobile.R;

public class AccountForgotActivity extends AppCompatActivity {


    EditText account;
    Button accountForgotpassword;

    TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_forgotpassword);
        anhXa();
        accountForgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
//                Log.d("b", "b");
//                startActivity(new Intent(ForgotPasswordActivity.this, MainActivity.class));
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on textview that already register open LoginActivity
                finish();
                startActivity(new Intent(AccountForgotActivity.this, MainActivity.class));
            }
        });
//
//        buttonRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checkEmpty();
////                registerUser();
//            }
//        });

    }


    private void forgotPassword() {

        String Account = String.valueOf(account.getText());
//
//        //first we will do the validations
        if (TextUtils.isEmpty(Account)) {
            account.setError("Please enter account");
            account.requestFocus();
            return;
        }


//        ApiService.apiSevice.forgotPassword(Account).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (response.isSuccessful()) {
//                    String result = response.body();
//                    Log.d("code: ", result);
//                    String message = "Please check your email for the code";
//                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//
//                    Intent intent = new Intent(AccountForgotActivity.this, OtpCodeForgotActivity.class);
//
//                    startActivity(intent);
//
//                } else {
//                    // Xử lý lỗi
//                    Log.d("code: ", "An error occurred.");
//                    String message = "An error occurred. Please try again later.";
//                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                // Xử lý lỗi
//                Log.d("code: ", "An error occurred. Please try again later.");
//                String message = "An error occurred. Please try again later.";
//                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//            }
//        });

        ApiService.apiSevice.forgotPassword(Account).enqueue(new Callback<List<String>>() {


            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    List result = response.body();

                    Log.e("code: ", result.get(0).toString());
                    Log.e("code: ", result.get(1).toString());
                    String message = "Please check your email for the code";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AccountForgotActivity.this, OtpCodeForgotActivity.class);

                    intent.putExtra("code", result.get(0).toString());
                    intent.putExtra("id", result.get(1).toString());


                    startActivity(intent);

                } else {
                    // Xử lý lỗi
                    Log.d("code: ", "An error occurred.");
                    String message = "An error occurred. Please try again later.";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }

    private void anhXa() {
        account = findViewById(R.id.editTextAccountForgotPassword);
        accountForgotpassword = findViewById(R.id.buttonAccountForgotPassword);
        textViewLogin = findViewById(R.id.textViewLogin);
    }


}






