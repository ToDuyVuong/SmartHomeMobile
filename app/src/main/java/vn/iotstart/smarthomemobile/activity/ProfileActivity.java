package vn.iotstart.smarthomemobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import vn.iotstart.smarthomemobile.PreManager;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.model.User;

public class ProfileActivity extends AppCompatActivity {

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



        anhXa();
        bindingData();


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
    }

    private void bindingData() {
        textAccount.setText("Account: " + preManager.getUser().getId());
        textUsername.setText("Name: " + preManager.getUser().getUsername());
        textEmail.setText("Email: " + preManager.getUser().getEmail());
        textPhoneNumber.setText("Phone: " + preManager.getUser().getPhoneNumber());
        textAddress.setText("Address: " + preManager.getUser().getAddress());
        String urlImage = preManager.getUser().getAvatar();
        Glide.with(getApplicationContext()).load(urlImage).into(avatar);




    }

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


}
