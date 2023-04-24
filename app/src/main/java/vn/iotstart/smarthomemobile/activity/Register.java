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
import vn.iotstart.smarthomemobile.api.APIService;
import vn.iotstart.smarthomemobile.api.ObjectCall;
import vn.iotstart.smarthomemobile.model.User;

public class Register extends AppCompatActivity {

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

//                registerUser();
                createUserAccount();
            }

        });


    }




    private void createUserAccount() {

        String usera = String.valueOf(username.getText());
        String pass = String.valueOf(password.getText());

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.8:8085/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        APIService apiService = retrofit.create(APIService.class);

        User user = new User("123ad", usera, pass, "https://haycafe.vn/wp-content/uploads/2022/02/Avatar-trang-den.png", "true", "email", "0", "DiaChi");

        Log.d("TAG", "User name: " + user.getId());
        Log.d("TAG", "User pass: " + user.getUsername());

        try {
            Call<User> call = apiService.createUser(user);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(@Nullable Call<User> call, @Nullable Response<User> response) {
                    if (response != null && response.isSuccessful()) {
                        User userCall = response.body();
                        if (userCall != null) {
                            Log.d("TAG", "User created: " + userCall.getId());
                        }
                        else {
                            Log.d("TAG", "User created: null");
                        }
                    }
                }

                @Override
                public void onFailure(@Nullable Call<User> call, @Nullable Throwable t) {
                    Log.e("TAG", "Failed to create user", t);

                }
            });
        } catch (Exception e) {
            Log.e("TAG", "Exception occurred when creating user", e);
        }
    }




//    private void createUser() {
//
//        String usera = String.valueOf(username.getText());
//        String pass = String.valueOf(password.getText());
//
//        // Tạo OkHttpClient để sử dụng trong Retrofit
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//
//// Tạo đối tượng Retrofit
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.1.8:8085/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(httpClient.build())
//                .build();
//
//// Tạo đối tượng ApiService từ Retrofit
//        APIService apiService = retrofit.create(APIService.class);
//
//// Tạo đối tượng User
//        User user = new User("123ad", usera, pass, "https://haycafe.vn/wp-content/uploads/2022/02/Avatar-trang-den.png", true, "email", "0", "DiaChi");
//
//
//        Log.d("TAG", "User created: " + user.getId());
//        Log.d("TAG", "User created: " + user.getUsername());
//
//
//// Gọi API để tạo user mới
//        Call<User> call = apiService.createUser(user);
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
//                // Xử lý kết quả thành công
//                User user = response.body();
//                assert user != null;
//                Log.d("TAG", "User created: " + user.getId());
//
////                User user = response.body().getUser();
////                Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
////                if (user!= null){
////                    startActivity(new Intent(Register.this, MainActivity.class));
////                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                // Xử lý lỗi khi gửi request hoặc nhận kết quả không thành công
//                Log.e("TAG", "Failed to create user", t);
//            }
//        });
//    }


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

//        APIService.apiSevice.register(username.getText().toString().trim(),
//                password.getText().toString().trim())
//                .enqueue(new Callback<ObjectCall>() {
//
//
//            @Override
//            public void onResponse(Call<ObjectCall> call, Response<ObjectCall> response) {
//                assert response.body() != null;
//                User user = response.body().getUser();
//                Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
//                if (user!= null){
//                    startActivity(new Intent(Register.this, MainActivity.class));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ObjectCall> call, Throwable t) {
//                Log.e("+++++", t.toString());
//            }
//        });


        User user1 = new User();

        APIService.apiSevice.registerUser(user1).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user != null) {
                        startActivity(new Intent(Register.this, MainActivity.class));
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