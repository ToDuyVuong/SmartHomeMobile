package vn.iotstart.smarthomemobile.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import com.bumptech.glide.Glide;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.PreManager;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.User;
import androidx.appcompat.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;

public class EditProfileActivity extends AppCompatActivity {
    PreManager preManager;
    RadioButton radioMale;
    RadioButton radioFemale;
    RadioGroup radioGroupGender;
    ImageView avatar;
    TextView btnBack;
    TextView btnLogout;
    TextView btnSave;
    RelativeLayout nameRelative;
    TextView nameTitle;
    TextView nameContent;
    EditText nameText;
    RelativeLayout phoneRelative;
    TextView phoneTitle;
    TextView phoneContent;
    EditText phoneText;
    RelativeLayout emailRelative;
    TextView emailTitle;
    TextView emailContent;
    EditText emailText;
    RelativeLayout addressRelative;
    TextView addressTitle;
    TextView addressContent;
    EditText addressText;
    boolean isEditting = false;
    CoordinatorLayout rootLayout;
    NestedScrollView rootLayout2;
    TextView userIdTitle;
    TextView emailProfileTile;
    TextView btnListOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mapping();
        bindingData();

        rootLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Hide the keyboard when the user touches outside of the EditText
                hideKeyboard();
                return false;
            }
        });
        rootLayout2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                return false;
            }
        });
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preManager.logout();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Toast.makeText(EditProfileActivity.this, "Loged out", Toast.LENGTH_SHORT).show();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEditting){
                    startEditing();
                }
                else{
                    isEditting = false;
                    btnSave.setText("Edit");
                    //
                    updateUserInfo();
                    endEditing();
                }
            }
        });

        btnListOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListOrderActivity.class));
            }
        });
    }


    private void mapping(){
        preManager = new PreManager(getApplicationContext());
        avatar = findViewById(R.id.imageProfileAvatar);
        btnBack = findViewById(R.id.btnBackToHome);
        btnSave = findViewById(R.id.btnSave);
        btnLogout = findViewById(R.id.btnLoggout);
        radioMale = findViewById(R.id.radioButtonProfileMale);
        radioFemale = findViewById(R.id.radioButtonProfileFemale);
        radioGroupGender = findViewById(R.id.radioProfileGender);

        nameRelative = findViewById(R.id.relativetUserName);
        nameTitle = findViewById(R.id.nameTextView);
        nameContent = findViewById(R.id.nameTextViewContent);
        nameText = findViewById(R.id.textUsername);

        phoneRelative = findViewById(R.id.relativeMobileInfo);
        phoneTitle = findViewById(R.id.phoneTextView);
        phoneContent = findViewById(R.id.phoneTextViewContent);
        phoneText = findViewById(R.id.textPhoneProfile);

        emailRelative = findViewById(R.id.relativeEmailInfo);
        emailTitle = findViewById(R.id.emailTextView);
        emailContent = findViewById(R.id.emailTextViewContent);
        emailText = findViewById(R.id.textEmailProfile);

        addressRelative = findViewById(R.id.relativeAddressInfo);
        addressTitle = findViewById(R.id.addressTextView);
        addressContent = findViewById(R.id.addressTextViewContent);
        addressText = findViewById(R.id.textAddressProfile);

        rootLayout = findViewById(R.id.rootLayout);
        rootLayout2 = findViewById(R.id.rootLayout2);

        radioMale.setEnabled(false);
        radioFemale.setEnabled(false);

        userIdTitle = findViewById(R.id.textViewUserId);
        emailProfileTile = findViewById(R.id.textViewEmailProfileTitle);
        btnListOrder = findViewById(R.id.btnListOrder);
    }
    private void startEditing(){
        isEditting = true;
        btnSave.setText("Save");

        radioMale.setEnabled(true);
        radioFemale.setEnabled(true);

        nameText.setText(nameContent.getText());
        phoneText.setText(phoneContent.getText());
        emailText.setText(emailContent.getText());
        addressText.setText(addressContent.getText());

        nameContent.setVisibility(View.GONE);
        nameTitle.setVisibility(View.GONE);
        nameText.setVisibility(View.VISIBLE);
        phoneContent.setVisibility(View.GONE);
        phoneTitle.setVisibility(View.GONE);
        phoneText.setVisibility(View.VISIBLE);
        emailContent.setVisibility(View.GONE);
        emailTitle.setVisibility(View.GONE);
        emailText.setVisibility(View.VISIBLE);
        addressContent.setVisibility(View.GONE);
        addressTitle.setVisibility(View.GONE);
        addressText.setVisibility(View.VISIBLE);
    }
    private void endEditing(){
        isEditting = false;
        btnSave.setText("Edit");

        radioMale.setEnabled(false);
        radioFemale.setEnabled(false);

        nameContent.setText(nameText.getText());
        emailContent.setText(emailText.getText());
        phoneContent.setText(phoneContent.getText());
        addressContent.setText(addressText.getText());

        nameContent.setVisibility(View.VISIBLE);
        nameTitle.setVisibility(View.VISIBLE);
        nameText.setVisibility(View.GONE);
        phoneContent.setVisibility(View.VISIBLE);
        phoneTitle.setVisibility(View.VISIBLE);
        phoneText.setVisibility(View.GONE);
        emailContent.setVisibility(View.VISIBLE);
        emailTitle.setVisibility(View.VISIBLE);
        emailText.setVisibility(View.GONE);
        addressContent.setVisibility(View.VISIBLE);
        addressTitle.setVisibility(View.VISIBLE);
        addressText.setVisibility(View.GONE);
    }
    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(nameText.getWindowToken(), 0);
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
        user.setId(prefUser.getId());
        user.setPassword(prefUser.getPassword());
        user.setUsername(nameText.getText().toString());
        user.setEmail(emailText.getText().toString());
        user.setPhoneNumber(phoneText.getText().toString());
        user.setAddress(addressText.getText().toString());
        user.setGender(textGender);
        //Change later
        user.setAvatar(prefUser.getAvatar());

        Log.d("hello", "getUserInfo: " + user.toString());

        return user;
    }

    private void bindingData(){
        User user = preManager.getUser();
        nameContent.setText(user.getUsername());
        nameText.setText(user.getUsername());
        emailContent.setText(user.getEmail());
        emailText.setText(user.getEmail());
        addressContent.setText(user.getAddress());
        addressText.setText(user.getAddress());
        phoneContent.setText(user.getPhoneNumber());
        phoneText.setText(user.getPhoneNumber());
        if (TextUtils.equals(user.isGender(), "Nam")){
            radioGroupGender.check(R.id.radioButtonProfileMale);
        }
        else{
            radioGroupGender.check(R.id.radioButtonProfileFemale);
        }
        userIdTitle.setText("@" + user.getId());
        emailProfileTile.setText(user.getEmail());

        Glide.with(getApplicationContext())
                .load(user.getAvatar())
                .fitCenter()
                .into(avatar);
    }

    private void updateUserInfo(){
        User user = getUserInfo();
        ApiService.apiService.update(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    User user = response.body();
                    preManager.saveUserDetail(user);
                    bindingData();
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
            }
        });
    }



}
