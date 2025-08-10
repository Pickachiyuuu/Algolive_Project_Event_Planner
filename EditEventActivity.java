package com.example.event_planner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.event_planner.models.Event;

public class EditEventActivity extends AppCompatActivity {
    private EditText title, description, date, time, location, priority;
    private Button saveButton;
    private Event event;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_event);

        title = findViewById(R.id.editTitle);
        description = findViewById(R.id.editDescription);
        date = findViewById(R.id.editDate);
        time = findViewById(R.id.editTime);
        location = findViewById(R.id.editLocation);
        priority = findViewById(R.id.editPriority);
        saveButton = findViewById(R.id.saveEventButton);

        event = (Event) getIntent().getSerializableExtra("event");
        if (event != null) {
            title.setText(event.getTitle());
            description.setText(event.getDescription());
            date.setText(event.getDate());
            time.setText(event.getTime());
            location.setText(event.getLocation());
            priority.setText(event.getPriority());
        }

        saveButton.setOnClickListener(v -> finish());
    }
}
