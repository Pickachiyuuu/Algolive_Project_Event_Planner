package com.example.event_planner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.event_planner.utils.SessionManager;

public class DashboardActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private Button btnLogout, btnCreateEvent, btnViewEvents;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sessionManager = new SessionManager(this);
        btnLogout = findViewById(R.id.btnLogout);
        btnCreateEvent = findViewById(R.id.btnCreateEvent);
        btnViewEvents = findViewById(R.id.btnViewEvents);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.setLogin(false);
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                finish();
            }
        });

        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, CreateEventActivity.class));
            }
        });

        btnViewEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, MyEventsActivity.class));
            }
        });
    }
}
