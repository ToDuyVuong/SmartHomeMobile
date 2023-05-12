package vn.iotstart.smarthomemobile.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD
import android.text.TextUtils;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
=======
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

>>>>>>> 84e7f6740b470eac79e427a65d82538ea124b0af
import vn.iotstart.smarthomemobile.PreManager;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.model.User;
import androidx.appcompat.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;

public class ProfileActivity extends AppCompatActivity {
<<<<<<< HEAD
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
    int currentTextId = -1;
    int currentTitleId = -1;
    int currentContentId = -1;
    private CoordinatorLayout rootLayout;
    private NestedScrollView rootLayout2;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_edit_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
=======

    TextView textAccount, textUsername, textEmail, textPhoneNumber, textAddress;
    Button buttonEditProfile, buttonOrderHistory, buttonLogout;
    LinearLayout layoutInfo, layoutIndex;
    ImageView avatar;
    PreManager preManager;
    FloatingActionButton buttonCartProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
>>>>>>> 84e7f6740b470eac79e427a65d82538ea124b0af



        anhXa();
        bindingData();

<<<<<<< HEAD
        rootLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Hide the keyboard when the user touches outside of the EditText
                endEditing();
                return false;
            }
        });
        rootLayout2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                endEditing();
                return false;
=======

        layoutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        layoutIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        buttonOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, ListOrderActivity.class);
                startActivity(intent);
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                PreManager.logoutUser();
            }
        });

        buttonCartProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, CartActivity.class);
                startActivity(intent);
>>>>>>> 84e7f6740b470eac79e427a65d82538ea124b0af
            }
        });
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                endEditing();
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
                Toast.makeText(ProfileActivity.this, "Loged out", Toast.LENGTH_SHORT).show();
            }
        });
        
        nameRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditing(nameTitle, nameContent, nameText);
            }
        });
        phoneRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditing(phoneTitle, phoneContent, phoneText);
            }
        });
        emailRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditing(emailTitle, emailContent, emailText);
            }
        });
        addressRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditing(addressTitle, addressContent, addressText);
            }
        });
//        buttonBackHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ProfileActivity.this, IndexActivity.class));
//            }
//        });
//        buttonChangePassword.setOnClickListener((new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ProfileActivity.this, "Unavailable Currently", Toast.LENGTH_SHORT).show();
//            }
//        }));
//        buttonSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                updateUserInfo();
//            }
//        });
    }

<<<<<<< HEAD

    private void mapping(){
        rootLayout = findViewById(R.id.rootLayout);
        rootLayout2 = findViewById(R.id.rootLayout2);
        preManager = new PreManager(getApplicationContext());
        avatar = findViewById(R.id.imageProfileAvatar);
        btnBack = findViewById(R.id.btnBackToHome);
        btnSave = findViewById(R.id.btnSave);
        btnLogout = findViewById(R.id.btnLoggout);
        radioMale = findViewById(R.id.radioButtonProfileMale);
        radioFemale = findViewById(R.id.radioButtonProfileMale);
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
    }
    private void startEditing(TextView title, TextView content, EditText text){
        if (currentTextId != -1){
            endEditing();
        }

        text.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
        text.requestFocus();
        text.selectAll();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(text, InputMethodManager.SHOW_IMPLICIT);
        }
        currentTextId = text.getId();
        currentTitleId = title.getId();
        currentContentId = content.getId();
        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT){
                    endEditing();
                    return true;
                }
                return false;
            }
        });
=======
    private void bindingData() {
        textAccount.setText("Account: " + preManager.getUser().getId());
        textUsername.setText("Name: " + preManager.getUser().getUsername());
        textEmail.setText("Email: " + preManager.getUser().getEmail());
        textPhoneNumber.setText("Phone: " + preManager.getUser().getPhoneNumber());
        textAddress.setText("Address: " + preManager.getUser().getAddress());
        String urlImage = preManager.getUser().getAvatar();
        Glide.with(getApplicationContext()).load(urlImage).into(avatar);




>>>>>>> 84e7f6740b470eac79e427a65d82538ea124b0af
    }
    private void endEditing(){
        if (currentTextId == -1) return;
        EditText text = findViewById(currentTextId);
        TextView title = findViewById(currentTitleId);
        TextView content = findViewById(currentContentId);

        //hide keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(text.getWindowToken(), 0);
        //hide edittext
        text.setVisibility(View.GONE);
        title.setVisibility(View.VISIBLE);
        content.setVisibility(View.VISIBLE);

        if(TextUtils.isEmpty(text.getText())){
            text.setText(content.getText());
            return;
        }
        content.setText(text.getText());
        currentTextId = -1;
    }

//    private User getUserInfo(){
//        User prefUser = preManager.getUser();
//        String textGender;
//        if (radioMale.isChecked()){
//            textGender = "Nam";
//        }
//        else{
//            textGender = "Nữ";
//        }
//
//        User user = new User();
//        user.setId(textAccount.getText().toString());
//        user.setPassword(prefUser.getPassword());
//        user.setUsername(textUsername.getText().toString());
//        user.setEmail(textEmail.getText().toString());
//        user.setPhoneNumber(textPhoneNumber.getText().toString());
//        user.setAddress(textAddress.getText().toString());
//        user.setGender(textGender);
//        //Change later
//        user.setAvatar(prefUser.getAvatar());
//
//        return user;
//    }

<<<<<<< HEAD
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
    }

//    private void updateUserInfo(){
//        User user = getUserInfo();
//        ApiService.apiService.update(user).enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if (response.isSuccessful()){
//                    User user = response.body();
//                    preManager.saveUserDetail(user);
//                    Toast.makeText(ProfileActivity.this, "Saved", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                Toast.makeText(ProfileActivity.this, "Failed to Save", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
=======
    private void anhXa() {
        textAccount = findViewById(R.id.textViewAccountProfile);
        textUsername = findViewById(R.id.textViewUsernameProfile);
        textEmail = findViewById(R.id.textViewEmailProfile);
        textPhoneNumber = findViewById(R.id.textViewPhoneProfile);
        textAddress = findViewById(R.id.textViewAddressProfile);
        buttonEditProfile = findViewById(R.id.buttonEditProfile);
        buttonOrderHistory = findViewById(R.id.buttonOrderHistory);
        buttonLogout = findViewById(R.id.buttonLogout);
        layoutInfo = findViewById(R.id.profileBtnProfile);
        layoutIndex = findViewById(R.id.homeBtnProfile);
        avatar = findViewById(R.id.imageViewAvatarProfile);
        buttonCartProfile = findViewById(R.id.buttonCartProfile);

        preManager = new PreManager(this);

    }

>>>>>>> 84e7f6740b470eac79e427a65d82538ea124b0af

}
