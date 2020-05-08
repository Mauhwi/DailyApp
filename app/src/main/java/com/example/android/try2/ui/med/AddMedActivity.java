package com.example.android.try2.ui.med;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.try2.R;
import com.example.android.try2.ui.daily.AddDailyActivity;

import java.util.Calendar;

public class AddMedActivity extends AppCompatActivity {

    public static final String MED_TEXT = "com.example.android.try2.MED_TEXT";
    public static final String MED_TIME = "com.example.android.try2.MED_TIME";
    public static final String MED_IMAGE = "com.example.android.try2.MED_IMAGE";
    public static final String MED_STATE = "com.example.android.try2.MED_STATE";

    private EditText addMedTitle;
    private TextView addMedTime;
    private ImageView image1, image2, image3, image4, image5;
    private String imageResId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_med);

        addMedTitle=findViewById(R.id.addMedText);
        addMedTime=findViewById(R.id.addMedTime);

        image1 = findViewById(R.id.image1);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image1.setBackgroundResource(R.drawable.border);
                imageResId = "R.drawable.pill";
            }
        });

        image2 = findViewById(R.id.image2);
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image2.setBackgroundResource(R.drawable.border);
                imageResId = "R.drawable.image2";
            }
        });

        image3 = findViewById(R.id.image3);
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image3.setBackgroundResource(R.drawable.border);
                imageResId = "R.drawable.image3";
            }
        });

        image4 = findViewById(R.id.image4);
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image4.setBackgroundResource(R.drawable.border);
                imageResId = "R.drawable.image4";
            }
        });

        image5 = findViewById(R.id.image5);
        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image5.setBackgroundResource(R.drawable.border);
                imageResId = "R.drawable.image5";
            }
        });

        final ImageButton button = findViewById(R.id.add_med);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageResId == null) {
                    Toast.makeText(getApplicationContext(),"Выберите вид", Toast.LENGTH_SHORT).show();
                } else {
                    saveMed();
                }
            }
        });

        final ImageButton backbutton = findViewById(R.id.medBackButton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addMedTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddMedActivity.this, R.style.TimePickerDark, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedMinute < 10) {
                            addMedTime.setText(selectedHour + ":0" + selectedMinute);
                        } else {
                            addMedTime.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, true);
                mTimePicker.show();
            }
        });

    }

    private void saveMed() {
        String title = addMedTitle.getText().toString();
        String time = addMedTime.getText().toString();
        int state = 1;

        if (title.trim().isEmpty() || time.trim().isEmpty()) {
            Toast.makeText(this, "Пожалуйста введите задание, время и вид", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(MED_TEXT, title);
        data.putExtra(MED_TIME, time);
        data.putExtra(MED_IMAGE, imageResId);
        data.putExtra(MED_STATE, state);
        setResult(RESULT_OK, data);

        finish();

    }

}
