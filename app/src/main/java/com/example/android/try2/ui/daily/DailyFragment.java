package com.example.android.try2.ui.daily;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

import com.example.android.try2.DB.DailyDB.DailyData;
import com.example.android.try2.R;
import com.example.android.try2.ReminderManager;

import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import static android.app.Activity.RESULT_OK;

public class DailyFragment extends Fragment {
    private DailyViewModel dailyViewModel;
    public static final int EDIT_DAILY_REQUEST = 2;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        //активные
        final RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_daily);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final DailyAdapter adapter = new DailyAdapter();
        recyclerView.setAdapter(adapter);
        //неактивные
        final RecyclerView recyclerView2 = rootView.findViewById(R.id.recycler_view_dailyDone);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView2.setHasFixedSize(true);

        final DailyAdapter adapter2 = new DailyAdapter();
        recyclerView2.setAdapter(adapter2);

        dailyViewModel = ViewModelProviders.of(this).get(DailyViewModel.class);
        dailyViewModel.getAllDailies().observe(this, new Observer<List<DailyData>>() {
            @Override
            public void onChanged(List<DailyData> dailyData) {
                adapter.setDailies(dailyData);
            }
        });
        dailyViewModel.getInactiveDailies().observe(this, new Observer<List<DailyData>>() {
            @Override
            public void onChanged(List<DailyData> dailyData) {
                adapter2.setDailies(dailyData);
            }
        });

        adapter.setOnItemClickListener(new DailyAdapter.onItemClickListener() {
            @Override
            public void onItemClick(DailyData dailyData) {
                Intent intent = new Intent(getActivity(), EditDailyActivity.class);
                intent.putExtra(EditDailyActivity.EXTRA_ID, dailyData.getId());
                intent.putExtra(EditDailyActivity.EXTRA_TEXT, dailyData.getTitle());
                intent.putExtra(EditDailyActivity.EXTRA_TIME, dailyData.getTime());
                intent.putExtra(EditDailyActivity.EXTRA_DETAILS, dailyData.getDescription());
                intent.putExtra(EditDailyActivity.EXTRA_STATE, dailyData.getState());
                startActivityForResult(intent, EDIT_DAILY_REQUEST);
            }

            @Override
            public void checkboxViewOnClick(DailyData dailyData) {
                dailyData.setState(2);
                dailyViewModel.update(dailyData);
            }

            @Override
            public void notificationOnClick(DailyData dailyData, boolean state) {
                if (state == true) {
                    int id = dailyData.getId();
                    String time = dailyData.getTime();
                    String title = dailyData.getTitle();
                    StringTokenizer tokens = new StringTokenizer(time, ":");
                    int hour = Integer.valueOf(tokens.nextToken());
                    int minute = Integer.valueOf(tokens.nextToken());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);

                    ReminderManager.setReminder(getActivity().getApplicationContext(), id, title, calendar, 1);

                    Toast.makeText(getActivity(), "Уведомление включено: ", Toast.LENGTH_LONG).show();
                } else {
                    int id = dailyData.getId();
                    String time = dailyData.getTime();
                    String title = dailyData.getTitle();
                    StringTokenizer tokens = new StringTokenizer(time, ":");
                    int hour = Integer.valueOf(tokens.nextToken());
                    int minute = Integer.valueOf(tokens.nextToken());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);

                    ReminderManager.setReminder(getActivity().getApplicationContext(), id, title, calendar, 2);

                    Toast.makeText(getActivity(), "Уведомление отключено: " + hour + ":" + minute, Toast.LENGTH_LONG).show();
                }
            }
        });

        adapter2.setOnItemClickListener(new DailyAdapter.onItemClickListener() {
            @Override
            public void onItemClick(DailyData dailyData) {
                Intent intent = new Intent(getActivity(), EditDailyActivity.class);
                intent.putExtra(EditDailyActivity.EXTRA_ID, dailyData.getId());
                intent.putExtra(EditDailyActivity.EXTRA_TEXT, dailyData.getTitle());
                intent.putExtra(EditDailyActivity.EXTRA_TIME, dailyData.getTime());
                intent.putExtra(EditDailyActivity.EXTRA_DETAILS, dailyData.getDescription());
                intent.putExtra(EditDailyActivity.EXTRA_STATE, dailyData.getState());
                startActivityForResult(intent, EDIT_DAILY_REQUEST);
            }

            @Override
            public void checkboxViewOnClick(DailyData dailyData) {
                dailyData.setState(1);
                dailyViewModel.update(dailyData);
            }

            @Override
            public void notificationOnClick(DailyData dailyData, boolean state) {
            }


        });

        TextView show = rootView.findViewById(R.id.showCompletedTextView);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerView2.getVisibility() == View.INVISIBLE) {
                    recyclerView2.setVisibility(View.VISIBLE);
                } else {
                    recyclerView2.setVisibility(View.INVISIBLE);
                }
            }
        });


        //TODO: must be a better way :(
        rootView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int activeTasks = adapter.getItemCount();
                        int completedTasks = adapter2.getItemCount();
                        ProgressBar progressBar = rootView.findViewById(R.id.progressBar);
                        TextView textView = rootView.findViewById(R.id.progressText);
                        progressBar.setMax(activeTasks + completedTasks);
                        progressBar.setProgress(completedTasks);
                        textView.setText(completedTasks + "/" + (activeTasks + completedTasks));
                    }
                });


        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == EDIT_DAILY_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(EditDailyActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(getActivity(), "Нет задания", Toast.LENGTH_LONG).show();
                return;
            }
            String title = data.getStringExtra(EditDailyActivity.EXTRA_TEXT);
            String time = data.getStringExtra(EditDailyActivity.EXTRA_TIME);
            String details = data.getStringExtra(EditDailyActivity.EXTRA_DETAILS);
            int state = data.getIntExtra(EditDailyActivity.EXTRA_STATE, 1);


            DailyData dailyData = new DailyData(title, details, time, state);
            dailyData.setId(id);
            dailyViewModel.update(dailyData);

            Toast.makeText(getActivity(), "Задание обновлено", Toast.LENGTH_LONG).show();
        } else if (requestCode == EDIT_DAILY_REQUEST && resultCode == 12) {
            final int id = data.getIntExtra(EditDailyActivity.EXTRA_ID, -1);
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    DailyData dailyData = dailyViewModel.findDailyById(id);
                    dailyViewModel.delete(dailyData);
                }
            });
        } else if (requestCode == EDIT_DAILY_REQUEST && resultCode == 345) {
            final int id = data.getIntExtra(EditDailyActivity.EXTRA_ID, -1);
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    DailyData dailyData = dailyViewModel.findDailyById(id);
                    String title = dailyData.getTitle();
                    String time = dailyData.getTime();
                    StringTokenizer tokens = new StringTokenizer(time, ":");
                    int hour = Integer.valueOf(tokens.nextToken());
                    int minute = Integer.valueOf(tokens.nextToken());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    if (dailyData.getState() == 1) {
                        dailyData.setState(2);
                        dailyViewModel.update(dailyData);
                        ReminderManager.setReminder(getActivity().getApplicationContext(), id, title, calendar, 2);
                    } else {
                        dailyData.setState(1);
                        dailyViewModel.update(dailyData);
                    }
                }
            });
        }

        //если закрыто с помощью кнопки назад
        else {
            Toast.makeText(getActivity(), "Задание отменено: ", Toast.LENGTH_LONG).show();
        }

    }
}