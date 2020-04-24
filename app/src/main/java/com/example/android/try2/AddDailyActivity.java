package com.example.android.try2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AddDailyActivity extends AppCompatActivity {
    //Intent ключи
    public static final String EXTRA_TEXT = "com.example.android.try2.EXTRA_TEXT";
    public static final String EXTRA_TIME = "com.example.android.try2.EXTRA_TIME";
    public static final String EXTRA_DETAILS = "com.example.android.try2.EXTRA_DETAILS";
    public static final String EXTRA_STATE = "com.example.android.try2.EXTRA_STATE";

    private EditText editTextTitle;
    private EditText editTextTime;
    private EditText editTextDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_daily);

        editTextTitle = findViewById(R.id.editDailyText);
        editTextTime = findViewById(R.id.editDailyTime);
        editTextDetails = findViewById(R.id.editDailyDetails);

        final Button button = findViewById(R.id.add_daily);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDaily();
            }
        });

        final ImageButton backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void saveDaily() {
        String title = editTextTitle.getText().toString();
        String time = editTextTime.getText().toString();
        String details = editTextDetails.getText().toString();
        int state = 1;

        if (title.trim().isEmpty() || time.trim().isEmpty() || details.trim().isEmpty()) {
            Toast.makeText(this, "Пожалуйста введите задание, время и детали", Toast.LENGTH_SHORT).show();
            return;
        }
        //передача в main activity через intent
        Intent data = new Intent();
        data.putExtra(EXTRA_TEXT, title);
        data.putExtra(EXTRA_TIME, time);
        data.putExtra(EXTRA_DETAILS, details);
        data.putExtra(EXTRA_STATE, state);

        setResult(RESULT_OK, data);
        finish();
    }

}
