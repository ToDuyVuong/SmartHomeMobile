package vn.iotstart.smarthomemobile;

import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import vn.iotstart.smarthomemobile.activity.LoginActivity;
import vn.iotstart.smarthomemobile.model.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundleReceive = getIntent().getExtras();
        if (bundleReceive != null) {
            User user = (User) bundleReceive.getSerializable("userA");
            if(user != null) {
                Log.d("TAG", "Username: " + user.toString());

            }else {
                Log.d("TAG", "User is null");
            }
        }else {
            Log.d("TAG", "Bundle is null");
        }
    }
}