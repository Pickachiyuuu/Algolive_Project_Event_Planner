package com.example.event_planner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.example.event_planner.R;
import com.example.event_planner.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToNextActivity();
            }
        }, SPLASH_DELAY);
    }

    private void navigateToNextActivity() {
        Intent intent;

        if (sessionManager.isLoggedIn()) {
            intent = new Intent(MainActivity.this, DashboardActivity.class);
        } else {
            intent = new Intent(MainActivity.this, LoginActivity.class);
        }

        startActivity(intent);
        finish();
    }

}