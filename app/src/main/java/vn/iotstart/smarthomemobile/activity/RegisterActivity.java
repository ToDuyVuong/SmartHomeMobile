package vn.iotstart.smarthomemobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.MainActivity;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.model.User;

public class RegisterActivity extends AppCompatActivity {

    EditText account;
    EditText username;
    EditText password;
    EditText email;
    EditText phone;
    EditText address;
    RadioGroup gender;
    TextView textViewLogin;
    Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        anhXa();

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on textview that already register open LoginActivity
                finish();
//                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmpty();
//                registerUser();
            }
        });

    }

    private void checkEmpty() {

        String Account = String.valueOf(account.getText());
        String Username = String.valueOf(username.getText());
        String Password = String.valueOf(password.getText());
        String Email = String.valueOf(email.getText());
        String Phone = String.valueOf(phone.getText());
        String Address = String.valueOf(address.getText());

        //first we will do the validations
        if (TextUtils.isEmpty(Account)) {
            account.setError("Please enter account");
            account.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Username)) {
            username.setError("Please enter username");
            username.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Password)) {
            password.setError("Please enter password");
            password.requestFocus();
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Please enter email");
            email.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(Phone)) {
            phone.setError("Please enter phone");
            phone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(Address)) {
            address.setError("Please enter address");
            address.requestFocus();
            return;
        }

        registerUser();

    }


    private void registerUser() {
        String Account = String.valueOf(account.getText());
        String Username = String.valueOf(username.getText());
        String Password = String.valueOf(password.getText());
        String Email = String.valueOf(email.getText());
        String Phone = String.valueOf(phone.getText());
        String Address = String.valueOf(address.getText());
        String Gender = ((RadioButton) findViewById(gender.getCheckedRadioButtonId())).getText().toString();
        String Avatar = "https://haycafe.vn/wp-content/uploads/2022/02/Avatar-trang-den.png";

        User newUser = new User();
        newUser.setId(Account);
        newUser.setUsername(Username);
        newUser.setPassword(Password);
        newUser.setEmail(Email);
        newUser.setPhoneNumber(Phone);
        newUser.setAddress(Address);
        newUser.setAvatar(Avatar);
        newUser.setGender(Gender);


        ApiService.apiService.register(newUser).enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user != null) {
                        Log.d("TAG", "User: " + user.toString());
                        Toast.makeText(getApplicationContext(), "Register Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userA", user);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(), "Account already exists", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Account already exists", Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void anhXa() {
        account = findViewById(R.id.editTextAccount);
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextLoginPassword);
        email = findViewById(R.id.editTextLoginAccount);
        phone = findViewById(R.id.editTextPhone);
        address = findViewById(R.id.editTextAddress);
        gender = findViewById(R.id.radioGender);
        textViewLogin = findViewById(R.id.textViewLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
    }


}
