package vn.iotstart.smarthomemobile.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.widget.NestedScrollView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.PreManager;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.User;
import androidx.appcompat.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditProfileActivity extends AppCompatActivity {
    PreManager preManager;
    RadioButton radioMale, radioFemale;
    RadioGroup radioGroupGender;
    ImageView avatar;
    TextView btnSave, btnEdit, btnUpload, btnLogout, btnBack;
    RelativeLayout nameRelative, phoneRelative, emailRelative, addressRelative;
    TextView nameTitle, nameContent, phoneTitle, phoneContent, emailTitle, emailContent, addressTitle, addressContent;
    EditText nameText, phoneText, emailText, addressText;
    boolean isEditting = false;
    CoordinatorLayout rootLayout;
    NestedScrollView rootLayout2;
    TextView userIdTitle, btnListOrder, emailProfileTile;
    ProgressDialog progressDialog;
    Uri imageUri;
    StorageReference storageReference;
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

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEditting){
                    startEditing();
                }
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditting){
                    updateUserInfo();
                    endEditing();
                }
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
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
        btnEdit = findViewById(R.id.btnEdit);
        btnSave = findViewById(R.id.btnSave);
        btnUpload = findViewById(R.id.btnUpload);
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

        btnSave.setVisibility(View.GONE);
        btnUpload.setVisibility(View.GONE);
    }
    private void startEditing(){
        isEditting = true;
        btnSave.setText("Save");

        radioMale.setEnabled(true);
        radioFemale.setEnabled(true);

        btnSave.setVisibility(View.VISIBLE);
        btnUpload.setVisibility(View.VISIBLE);
        btnEdit.setVisibility(View.GONE);

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


        btnSave.setVisibility(View.GONE);
        btnUpload.setVisibility(View.GONE);
        btnEdit.setVisibility(View.VISIBLE);

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
    private void uploadImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }
    private void updateUserInfo(){
        progressDialog = new ProgressDialog(EditProfileActivity.this);
        progressDialog.setTitle("Updating...");
        progressDialog.show();

        User user = getUserInfo();
        final String[] uploadedImageUrl = {""};
        if (imageUri != null)
        {
            Log.d("hello", "updateUserInfo: ");
            saveImageToFirebase(new ImageUploadCallback() {
                @Override
                public void onSuccess(String downloadUrl) {
                    // Handle the successful upload and obtain the download URL
                    // The downloadUrl variable contains the URL of the uploaded image
                    if (TextUtils.isEmpty(downloadUrl)) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(EditProfileActivity.this, "Failed to Save", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    user.setAvatar(downloadUrl);
                    saveUser(user);
                }

                @Override
                public void onFailure(Exception e) {
                    // Handle the upload failure
                    Log.d("saveImage", "onFailure: " + e.getMessage());
                    saveUser(user);
                }
            });
        }


    }
    private void saveUser(User user){
        ApiService.apiService.update(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()){
                    User user = response.body();
                    preManager.saveUserDetail(user);
                    bindingData();
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Log.d("updateUserInfo", "onFailure: " + t.getMessage());
                Toast.makeText(getApplicationContext(), "Failed to Save", Toast.LENGTH_SHORT).show();
            }
        });    }


    private void saveImageToFirebase(final ImageUploadCallback callback) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String filename = format.format(now);
        FirebaseApp.initializeApp(EditProfileActivity.this);
        storageReference = FirebaseStorage.getInstance().getReference("images/" + filename);
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageReference.getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                       String newUri = uri.toString();
                                        callback.onSuccess(newUri);
                                     }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFailure(e);
                    }
                });
    }
    interface ImageUploadCallback{
        void onSuccess(String newUri);
        void onFailure(Exception e);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){
            imageUri = data.getData();
            Glide.with(EditProfileActivity.this)
                    .load(imageUri)
                    .into(avatar);
        }
    }

}
