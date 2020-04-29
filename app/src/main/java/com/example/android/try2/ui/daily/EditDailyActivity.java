package com.example.android.try2.ui.daily;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.android.try2.R;

public class EditDailyActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.example.android.try2.EXTRA_ID";
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
        setContentView(R.layout.activity_edit_daily);

        editTextTitle = findViewById(R.id.editTitleText);
        editTextTime = findViewById(R.id.editTimeText);
        editTextDetails = findViewById(R.id.editDetailsText);

        Intent intent = getIntent();
        editTextTitle.setText(intent.getStringExtra(EXTRA_TEXT));
        editTextTime.setText(intent.getStringExtra(EXTRA_TIME));
        editTextDetails.setText(intent.getStringExtra(EXTRA_DETAILS));

        final ImageButton backButton = findViewById(R.id.editBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDaily();
            }
        });

        final ImageButton deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDailyActivity.this, R.style.AlertDialogCustom);
                builder.setMessage("Удалить задание?")
                        .setCancelable(false)
                        .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteDaily();
                                EditDailyActivity.this.finish();
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


    }

    private void deleteDaily() {
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        Intent data = new Intent();
        data.putExtra(EXTRA_ID, id);

        setResult(12, data);
        finish();
    }


    private void updateDaily() {
        String title =  editTextTitle.getText().toString();
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

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }
}
