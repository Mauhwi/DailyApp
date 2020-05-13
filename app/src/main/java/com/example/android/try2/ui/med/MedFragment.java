package com.example.android.try2.ui.med;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.try2.DB.MedDB.MedData;
import com.example.android.try2.R;
import com.example.android.try2.ReminderManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import static android.app.Activity.RESULT_OK;

public class MedFragment extends Fragment {
    private MedViewModel medViewModel;
    int completedTasks;
    public static final int ADD_MED_REQUEST = 1;
    public static final int EDIT_MED_REQUEST = 2;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_med, container, false);
        final RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_med);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final MedAdapter medAdapter = new MedAdapter();
        recyclerView.setAdapter(medAdapter);

        medViewModel = ViewModelProviders.of(this).get(MedViewModel.class);
        medViewModel.getAllMeds().observe(this, new Observer<List<MedData>>() {
            @Override
            public void onChanged(List<MedData> medData) {
                medAdapter.setMeds(medData);
            }
        });


        medAdapter.setOnItemClickListener(new MedAdapter.onItemClickListener() {
            @Override
            public void onItemClick(MedData medData) {
                Intent intent = new Intent(getActivity(), EditMedActivity.class);
                intent.putExtra(EditMedActivity.MED_ID, medData.getId());
                intent.putExtra(EditMedActivity.MED_TEXT, medData.getTitle());
                intent.putExtra(EditMedActivity.MED_TIME, medData.getTime());
                intent.putExtra(EditMedActivity.MED_IMAGE, medData.getImage());
                intent.putExtra(EditMedActivity.MED_STATE, medData.getState());
                startActivityForResult(intent, EDIT_MED_REQUEST);
            }

            @Override
            public void checkboxViewOnClick(MedData medData, boolean state) {
                if (state == true) {
                    medData.setState(2);
                    medViewModel.update(medData);
                } else {
                    medData.setState(1);
                    medViewModel.update(medData);
                }
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        completedTasks = medViewModel.getInactiveCount();
                    }
                });
            }
        });

        rootView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        AsyncTask.execute(new Runnable() {
                            @Override
                            public void run() {
                                completedTasks = medViewModel.getInactiveCount();
                            }
                        });
                        ProgressBar progressBar = rootView.findViewById(R.id.progressBar);
                        TextView textView = rootView.findViewById(R.id.progressText);
                        progressBar.setMax(medAdapter.getItemCount());
                        progressBar.setProgress(completedTasks);
                        textView.setText(completedTasks + "/" + medAdapter.getItemCount());
                    }
                });

        FloatingActionButton fab = this.getActivity().findViewById(R.id.floating_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddMedActivity.class);
                startActivityForResult(intent, ADD_MED_REQUEST);
            }
        });

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_MED_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddMedActivity.MED_TEXT);
            String time = data.getStringExtra(AddMedActivity.MED_TIME);
            String image = data.getStringExtra(AddMedActivity.MED_IMAGE);
            int state = data.getIntExtra(AddMedActivity.MED_STATE, 1);
            MedData medData = new MedData(title, image, time, state);
            medViewModel.insert(medData);
            StringTokenizer tokens = new StringTokenizer(time, ":");
            int hour = Integer.valueOf(tokens.nextToken());
            int minute = Integer.valueOf(tokens.nextToken());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            int id = medData.getId();
            ReminderManager.setReminder(getActivity().getApplicationContext(), id, title, calendar, 1);
        } else if (requestCode == EDIT_MED_REQUEST && resultCode == RESULT_OK){
            String title = data.getStringExtra(EditMedActivity.MED_TEXT);
            String time = data.getStringExtra(EditMedActivity.MED_TIME);
            String image = data.getStringExtra(EditMedActivity.MED_IMAGE);
            int state = data.getIntExtra(EditMedActivity.MED_STATE, 1);
            MedData medData = new MedData(title, image, time, state);
            medViewModel.update(medData);
            StringTokenizer tokens = new StringTokenizer(time, ":");
            int hour = Integer.valueOf(tokens.nextToken());
            int minute = Integer.valueOf(tokens.nextToken());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            int id = medData.getId();
            ReminderManager.setReminder(getActivity().getApplicationContext(), id, title, calendar, 1);

        } else if (requestCode == EDIT_MED_REQUEST && resultCode == 12) {
            final String title = data.getStringExtra(EditMedActivity.MED_TEXT);
            String time = data.getStringExtra(EditMedActivity.MED_TIME);
            final int id = data.getIntExtra(EditMedActivity.MED_ID, -1);
            StringTokenizer tokens = new StringTokenizer(time, ":");
            int hour = Integer.valueOf(tokens.nextToken());
            int minute = Integer.valueOf(tokens.nextToken());
            final Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    MedData medData = medViewModel.findMedById(id);
                    medViewModel.delete(medData);
                    ReminderManager.setReminder(getActivity().getApplicationContext(), id, title, calendar, 2);
                }
            });
        } else {
            Toast.makeText(getActivity(), "Лекарство отредактировано", Toast.LENGTH_LONG).show();
        }
    }
}

