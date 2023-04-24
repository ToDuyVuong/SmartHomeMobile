package vn.iotstart.smarthomemobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.iotstart.smarthomemobile.MainActivity;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.ApiService;
import vn.iotstart.smarthomemobile.api.ObjectCall;
import vn.iotstart.smarthomemobile.model.User;

public class RegisterActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        anhXa();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("TAG", "Username: " + username.getText().toString());
                Log.d("TAG", "Password: " + password.getText().toString());

                registerUser();
//                createUserAccount();
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


        User user1 = new User("123ad", user, pass, "https://haycafe.vn/wp-content/uploads/2022/02/Avatar-trang-den.png", true, "email", "0", "DiaChi");


        ApiService.apiSevice.register(user1).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user != null) {


                        Log.d("TAG", "User: " + user.toString());

                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userA", user);
                        intent.putExtras(bundle);
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "User is null", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // Handle a network error
            }
        });


    }

    private void anhXa() {
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        btnRegister = findViewById(R.id.buttonRegister);
    }
}