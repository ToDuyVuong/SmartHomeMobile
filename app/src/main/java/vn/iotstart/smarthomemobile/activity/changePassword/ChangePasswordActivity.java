package vn.iotstart.smarthomemobile.activity.changePassword;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstart.smarthomemobile.PreManager;
import vn.iotstart.smarthomemobile.R;
import vn.iotstart.smarthomemobile.api.ApiService;
import vn.iotstart.smarthomemobile.model.User;

public class ChangePasswordActivity extends AppCompatActivity {
    EditText textPass, textNewPass, textConfirmPass;
    Button btnSave;
    PreManager preManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        textPass = findViewById(R.id.editTextCPPassword);
        textNewPass = findViewById(R.id.editTextCPNewPassword);
        textConfirmPass = findViewById(R.id.editTextCPConfirmPassword);
        preManager = new PreManager(ChangePasswordActivity.this);

        btnSave = findViewById(R.id.buttonCPChangePassword);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = preManager.getUser();

                String password = textPass.getText().toString();
                String newPassword = textNewPass.getText().toString();
                String confirmPassword = textConfirmPass.getText().toString();

                if (!TextUtils.equals(password , user.getPassword())){
                    textPass.setError("Mật khẩu không đúng");
                    textPass.requestFocus();
                    return;
                }
                if (!TextUtils.equals(newPassword, confirmPassword)){
                    textNewPass.setError("Mật khẩu mới không khớp");
                    textNewPass.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(newPassword)){
                    textNewPass.setError("Mật khẩu mới không hợp lệ");
                    textNewPass.requestFocus();
                    return;
                }
                user.setPassword(newPassword);
                ApiService.apiService.update(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Toast.makeText(ChangePasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        user.setPassword(newPassword);
                        preManager.saveUserDetail(user);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(ChangePasswordActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

            }
        });

    }
}
