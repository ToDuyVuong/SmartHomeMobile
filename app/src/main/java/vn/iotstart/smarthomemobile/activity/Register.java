package vn.iotstart.smarthomemobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.MainActivity;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.api.APIService;
import vn.iotstart.smarthomemobile.api.ObjectCall;
import vn.iotstart.smarthomemobile.model.User;

public class Register extends AppCompatActivity {

    TextInputEditText username;
    TextInputEditText password;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        anhXa();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registerUser();
            }
        });


    }

    private void registerUser() {
        String user = String.valueOf(username.getText());
        String pass = String.valueOf(password.getText());

        //first we will do the validations
        if (TextUtils.isEmpty(user)) {
            username.setError("Please enter username");
            username.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            password.setError("Please enter password");
            password.requestFocus();
            return;
        }

        APIService.apiSevice.register(user, pass).enqueue(new Callback<ObjectCall>() {
            @Override
            public void onResponse(Call<ObjectCall> call, Response<ObjectCall> response) {
                User user = response.body().getUser();
                Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                if (user!= null){
                    startActivity(new Intent(Register.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<ObjectCall> call, Throwable t) {
                Log.e("+++++", t.toString());
            }
        });


        }

    private void anhXa() {
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        btnRegister = findViewById(R.id.buttonRegister);
    }
}