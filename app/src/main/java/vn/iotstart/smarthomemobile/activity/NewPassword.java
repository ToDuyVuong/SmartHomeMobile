package vn.iotstart.smarthomemobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.R;


public class NewPassword extends AppCompatActivity {

    EditText editTextNewPassword;
    EditText editTextConfirmPassword;
    Button buttonNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        editTextNewPassword = findViewById(R.id.editTextFGNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextFGConfirmPassword);
        buttonNewPassword = findViewById(R.id.buttonFGChangePassword);

        buttonNewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword();
                Log.e("TAG", "onClick: " + "123");

            }
        });
    }


    private void newPassword() {
        String newPassword = String.valueOf(editTextNewPassword.getText());
        String confirmPassword = String.valueOf(editTextConfirmPassword.getText());

        if (TextUtils.isEmpty(newPassword)) {
            editTextNewPassword.setError("Please enter new password");
            editTextNewPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            editTextConfirmPassword.setError("Please enter confirm password");
            editTextConfirmPassword.requestFocus();
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            editTextConfirmPassword.setError("Confirm password not match");
            editTextConfirmPassword.requestFocus();
            return;
        } else {
            Intent intent = getIntent();
            String id = intent.getSerializableExtra("id").toString();

            ApiService.apiService.newForgotPassword(id, newPassword).enqueue(new retrofit2.Callback<String>() {
                @Override
                public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
                    if (response.isSuccessful()) {
                        Log.e("TAG", "onResponse: " + response.body());
                        Toast.makeText(getApplicationContext(), "Change password success", Toast.LENGTH_SHORT).show();

                        Intent intent1 = new Intent(NewPassword.this, LoginActivity.class);
                        startActivity(intent1);
                    } else {
                        Toast.makeText(getApplicationContext(), "Change password fail", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<String> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Change password fail12121", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



//    private void newPassword() {
//
//        String newPassword = String.valueOf(editTextNewPassword.getText());
//        String confirmPassword = String.valueOf(editTextConfirmPassword.getText());
//
//        if (TextUtils.isEmpty(newPassword)) {
//            editTextNewPassword.setError("Please enter new password");
//            editTextNewPassword.requestFocus();
//            return;
//        }
//
//        if (TextUtils.isEmpty(confirmPassword)) {
//            editTextConfirmPassword.setError("Please enter confirm password");
//            editTextConfirmPassword.requestFocus();
//            return;
//        }
//
//        if (!newPassword.equals(confirmPassword)) {
//            editTextConfirmPassword.setError("Confirm password not match");
//            editTextConfirmPassword.requestFocus();
//            return;
//        } else {
//            Intent intent = getIntent();
//            String id = intent.getSerializableExtra("id").toString();
//
//
////            ApiService.apiSevice.newForgotPassword(id, newPassword).enqueue(new retrofit2.Callback<String>() {
////                @Override
////                public void onResponse(retrofit2.Call<String> call, retrofit2.Response<String> response) {
////                    if (response.isSuccessful()) {
////                        Log.e("TAG", "onResponse: " + response.body());
////                        Toast.makeText(getApplicationContext(), "Change password success", Toast.LENGTH_SHORT).show();
////
////
////                        Intent intent1 = new Intent(NewPassword.this, MainActivity.class);
////                        startActivity(intent1);
////                    } else {
////                        Toast.makeText(getApplicationContext(), "Change password fail", Toast.LENGTH_SHORT).show();
////                    }
////                }
////
////                @Override
////                public void onFailure(retrofit2.Call<String> call, Throwable t) {
////                    Toast.makeText(getApplicationContext(), "Change password fail12121", Toast.LENGTH_SHORT).show();
////                }
////            });
//
//
//        }
//
//    }
}
