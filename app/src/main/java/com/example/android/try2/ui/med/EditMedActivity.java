package com.example.android.try2.ui.med;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.try2.R;
import com.example.android.try2.ReminderManager;
import com.example.android.try2.ui.daily.EditDailyActivity;

public class EditMedActivity extends AppCompatActivity {
    public static final String MED_ID = "com.example.android.try2.MED_ID";
    public static final String MED_TEXT = "com.example.android.try2.MED_TEXT";
    public static final String MED_TIME = "com.example.android.try2.MED_TIME";
    public static final String MED_IMAGE = "com.example.android.try2.MED_IMAGE";
    public static final String MED_STATE = "com.example.android.try2.MED_STATE";

    private EditText editMedTitle;
    private TextView editMedTime;
    private ImageView image1, image2, image3, image4, image5;
    private String imageResId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_med);

        editMedTitle = findViewById(R.id.editMedText);
        editMedTime = findViewById(R.id.editMedTime);

        Intent intent = getIntent();
        editMedTitle.setText(intent.getStringExtra(MED_TEXT));
        editMedTime.setText(intent.getStringExtra(MED_TIME));
        imageResId = intent.getStringExtra(MED_IMAGE);

        int state = getIntent().getIntExtra(MED_STATE, 1);
        Button completeMedButton = findViewById(R.id.completeMed);
        if (state == 2) {
            completeMedButton.setText("Вернуть");
        }
        completeMedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeStateMed();
            }
        });

        final ImageButton backButton = findViewById(R.id.editMedBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMed();
            }
        });

        final ImageButton deleteButton = findViewById(R.id.deleteMedButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditMedActivity.this, R.style.AlertDialogCustom);
                builder.setMessage("Удалить лекарство?")
                        .setCancelable(false)
                        .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteMed();
                                EditMedActivity.this.finish();
                            }
                        })
                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        image1 = findViewById(R.id.image1);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackground(image1);
                imageResId = "pill";
            }
        });

        image2 = findViewById(R.id.image2);
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageResId = "pill2";
                setBackground(image2);
            }
        });

        image3 = findViewById(R.id.image3);
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackground(image3);
                imageResId = "syringe";
            }
        });

        image4 = findViewById(R.id.image4);
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackground(image4);
                imageResId = "medicine1";
            }
        });

        image5 = findViewById(R.id.image5);
        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBackground(image5);
                imageResId = "medicine2";
            }
        });

    }

    private void deleteMed() {
        int id = getIntent().getIntExtra(MED_ID, -1);
        String title =  editMedTitle.getText().toString();
        String time = editMedTime.getText().toString();
        Intent data = new Intent();
        data.putExtra(MED_ID, id);
        data.putExtra(MED_TEXT, title);
        data.putExtra(MED_TIME, time);
        setResult(12, data);
        finish();
    }


    private void updateMed() {
        String title =  editMedTitle.getText().toString();
        String time = editMedTime.getText().toString();
        int state = getIntent().getIntExtra(MED_STATE, 1);

        if (title.trim().isEmpty() || time.trim().isEmpty() || imageResId == null) {
            Toast.makeText(this, "Пожалуйста введите задание, время и детали", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra(MED_TEXT, title);
        data.putExtra(MED_TIME, time);
        data.putExtra(MED_IMAGE, imageResId);
        data.putExtra(MED_STATE, state);

        int id = getIntent().getIntExtra(MED_ID, -1);
        if (id != -1) {
            data.putExtra(MED_ID, id);
        }



        setResult(RESULT_OK, data);
        finish();
    }

    private void changeStateMed() {
        int id = getIntent().getIntExtra(MED_ID, -1);
        Intent data = new Intent();
        data.putExtra(MED_ID, id);
        setResult(345, data);
        finish();
    }

    private void setBackground(View view) {
        view.setBackgroundResource(R.drawable.border);
        if (view == image1) {
            image2.setBackground(null);
            image3.setBackground(null);
            image4.setBackground(null);
            image5.setBackground(null);
        }
        if (view == image2) {
            image1.setBackground(null);
            image3.setBackground(null);
            image4.setBackground(null);
            image5.setBackground(null);
        }
        if (view == image3) {
            image1.setBackground(null);
            image2.setBackground(null);
            image4.setBackground(null);
            image5.setBackground(null);
        }
        if (view == image4) {
            image1.setBackground(null);
            image2.setBackground(null);
            image3.setBackground(null);
            image5.setBackground(null);
        }
        if (view == image5) {
            image1.setBackground(null);
            image2.setBackground(null);
            image3.setBackground(null);
            image4.setBackground(null);
        }
    }
}
