package vn.iotstart.smarthomemobile.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import vn.iotstart.smarthomemobile.R;

public class OtpCodeForgotActivity extends AppCompatActivity {

    EditText editTextOtpCode;
    Button buttonOtpCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);





        buttonOtpCode = findViewById(R.id.buttonOtpCode);
        editTextOtpCode = findViewById(R.id.editTextOtpCode);

        buttonOtpCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optCode();
            }
        });

    }

    private void optCode() {
        String otp = String.valueOf(editTextOtpCode.getText());
//
//        //first we will do the validations
        if (TextUtils.isEmpty(otp)) {
            editTextOtpCode.setError("Please enter account");
            editTextOtpCode.requestFocus();
            return;
        }

        Intent intent = getIntent();
        String id = intent.getSerializableExtra("id").toString();
        String code = intent.getSerializableExtra("code").toString();

        Log.d("code: ", code);
        Log.d("id: ", id);

        if(code.equals(otp)){
            intent = new Intent(OtpCodeForgotActivity.this, NewPassword.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }
        else{
            String message = "Sai OTP";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }



    }
}
