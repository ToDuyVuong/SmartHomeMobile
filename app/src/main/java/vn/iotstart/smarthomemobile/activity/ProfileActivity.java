package vn.iotstart.smarthomemobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.PreManager;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.User;

public class ProfileActivity extends AppCompatActivity {
    Button buttonBackHome;
    Button buttonSave;
    Button buttonChangePassword;
    ImageView avatar;
    EditText textAccount;
    EditText textUsername;
    EditText textEmail;
    EditText textPhoneNumber;
    EditText textAddress;
    RadioGroup radioGender;
    RadioButton radioMale;
    RadioButton radioFemale;
    PreManager preManager;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);

        mapping();
        bindingData();
        buttonBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, IndexActivity.class));
            }
        });
        buttonChangePassword.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "Unavailable Currently", Toast.LENGTH_SHORT).show();
            }
        }));
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo();
            }
        });
    }

    private void mapping(){
        buttonBackHome = (Button) findViewById(R.id.buttonProfileBackHome);
        buttonSave = findViewById(R.id.buttonSaveProfile);
        buttonChangePassword = findViewById(R.id.buttonChangePassword);
        textAccount = findViewById(R.id.editTextProfileAccount);
        textUsername = findViewById(R.id.editTextProfileUsername);
        textEmail = findViewById(R.id.editTextProfileEmail);
        textPhoneNumber = findViewById(R.id.editTextProfilePhone);
        textAddress = findViewById(R.id.editTextProfileAddress);
        radioGender = findViewById(R.id.radioProfileGender);
        radioMale = findViewById(R.id.radioButtonProfileMale);
        radioFemale = findViewById(R.id.radioButtonProfileFemale);
        preManager = new PreManager(getApplicationContext());

        textAccount.setFocusable(false);
    }
    private User getUserInfo(){
        User prefUser = preManager.getUser();
        String textGender;
        if (radioMale.isChecked()){
            textGender = "Nam";
        }
        else{
            textGender = "Nữ";
        }

        User user = new User();
        user.setId(textAccount.getText().toString());
        user.setPassword(prefUser.getPassword());
        user.setUsername(textUsername.getText().toString());
        user.setEmail(textEmail.getText().toString());
        user.setPhoneNumber(textPhoneNumber.getText().toString());
        user.setAddress(textAddress.getText().toString());
        user.setGender(textGender);
        //Change later
        user.setAvatar(prefUser.getAvatar());

        return user;
    }

    private void bindingData(){
        User user = preManager.getUser();
        textAccount.setText(user.getId());
        textUsername.setText(user.getUsername());
        textEmail.setText(user.getEmail());
        textAddress.setText(user.getAddress());
        textPhoneNumber.setText(user.getPhoneNumber());
        if (TextUtils.equals(user.isGender(), "Nam")){
            radioGender.check(R.id.radioButtonProfileMale);
        }
        else{
            radioGender.check(R.id.radioButtonProfileFemale);
        }
    }

    private void updateUserInfo(){
        User user = getUserInfo();
        ApiService.apiService.update(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    User user = response.body();
                    preManager.saveUserDetail(user);
                    Toast.makeText(ProfileActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Failed to Save", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
