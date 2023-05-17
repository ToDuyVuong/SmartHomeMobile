package vn.iotstart.smarthomemobile.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import vn.iotstart.smarthomemobile.R;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        new Thread(() -> {
            int n = 0;
            try {
                do {
                    if (n >= 3000) {
                        IntroActivity.this.finish();
                        Intent intent = new Intent(IntroActivity.this.getApplicationContext(), (Class) LoginActivity.class);
                        IntroActivity.this.startActivity(intent);
                        return;
                    }
                    Thread.sleep((long) 100);
                    n += 100;
                } while (true);
            } catch (InterruptedException interruptedException) {
                IntroActivity.this.finish();
                Intent intent = new Intent(IntroActivity.this.getApplicationContext(), (Class) LoginActivity.class);
                IntroActivity.this.startActivity(intent);
                return;
            } catch (Throwable throwable) {
                IntroActivity.this.finish();
                Intent intent = new Intent(IntroActivity.this.getApplicationContext(), (Class) LoginActivity.class);
                IntroActivity.this.startActivity(intent);
                return;
            }
        }).start();
    }}
