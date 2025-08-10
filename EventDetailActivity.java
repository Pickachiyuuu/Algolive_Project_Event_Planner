package com.example.event_planner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.event_planner.models.Event;

import java.io.Serializable;

public class EventDetailActivity extends AppCompatActivity {
    private TextView title, description, date, time, location, priority;
    private Button editButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);

        title = findViewById(R.id.eventTitle);
        description = findViewById(R.id.eventDescription);
        date = findViewById(R.id.eventDate);
        time = findViewById(R.id.eventTime);
        location = findViewById(R.id.eventLocation);
        priority = findViewById(R.id.eventPriority);
        editButton = findViewById(R.id.editEventButton);

        Event event = (Event) getIntent().getSerializableExtra("event");
        if (event != null) {
            title.setText(event.getTitle());
            description.setText(event.getDescription());
            date.setText(event.getDate());
            time.setText(event.getTime());
            location.setText(event.getLocation());
            priority.setText(event.getPriority());
        }

        editButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailActivity.this, EditEventActivity.class);
            intent.putExtra("event", (Serializable) event);
            startActivity(intent);
        });
    }
}
