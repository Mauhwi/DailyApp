package com.example.android.try2.ui.daily;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.try2.R;

import java.util.Calendar;

public class AddDailyActivity extends AppCompatActivity {
    //Intent ключи
    public static final String EXTRA_TEXT = "com.example.android.try2.EXTRA_TEXT";
    public static final String EXTRA_TIME = "com.example.android.try2.EXTRA_TIME";
    public static final String EXTRA_DETAILS = "com.example.android.try2.EXTRA_DETAILS";
    public static final String EXTRA_STATE = "com.example.android.try2.EXTRA_STATE";

    private EditText addTextTitle;
    private TextView addTextTime;
    private EditText addTextDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_daily);

        addTextTitle = findViewById(R.id.addDailyText);
        addTextTime = findViewById(R.id.addDailyTime);
        addTextDetails = findViewById(R.id.addDailyDetails);

        final ImageButton button = findViewById(R.id.add_daily);
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

        addTextTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddDailyActivity.this, R.style.TimePickerDark, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedMinute < 10) {
                            addTextTime.setText(selectedHour + ":0" + selectedMinute);
                        } else {
                            addTextTime.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, true);
                mTimePicker.show();

            }
        });


    }

    private void saveDaily() {
        String title = addTextTitle.getText().toString();
        String time = addTextTime.getText().toString();
        String details = addTextDetails.getText().toString();
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
