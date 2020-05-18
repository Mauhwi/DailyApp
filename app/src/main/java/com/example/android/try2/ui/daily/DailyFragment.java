package com.example.android.try2.ui.daily;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.try2.DB.DailyData.DailyData;
import com.example.android.try2.R;
import com.example.android.try2.ReminderManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import static android.app.Activity.RESULT_OK;

public class DailyFragment extends Fragment {
    private static final int EDIT_DAILY_REQUEST = 2;
    private static final int ADD_DAILY_REQUEST = 1;
    private DailyViewModel dailyViewModel;
    private AnimationDrawable catAnimation;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_daily,
                container, false);
        //активные
        final RecyclerView recyclerView = rootView
                .findViewById(R.id.recycler_view_daily);
        recyclerView.setLayoutManager(new LinearLayoutManager
                (getActivity()));
        recyclerView.setHasFixedSize(true);
        final DailyAdapter adapter = new DailyAdapter();
        recyclerView.setAdapter(adapter);
        //неактивные
        final RecyclerView recyclerView2 = rootView
                .findViewById(R.id.recycler_view_dailyDone);
        recyclerView2.setLayoutManager(new LinearLayoutManager
                (getActivity()));
        recyclerView2.setHasFixedSize(true);
        final DailyAdapter adapter2 = new DailyAdapter();
        recyclerView2.setAdapter(adapter2);
        //назначение адаптеров
        dailyViewModel = ViewModelProviders.of(this)
                .get(DailyViewModel.class);
        dailyViewModel.getAllDailies().observe(this,
                new Observer<List<DailyData>>() {
                    @Override
                    public void onChanged(List<DailyData> dailyData) {
                        adapter.setDailies(dailyData);
                    }
                });
        dailyViewModel.getInactiveDailies().observe(this,
                new Observer<List<DailyData>>() {
                    @Override
                    public void onChanged(List<DailyData> dailyData) {
                        adapter2.setDailies(dailyData);
                    }
                });
        //обработчики событий
        adapter.setOnItemClickListener(new DailyAdapter.onItemClickListener() {
            @Override
            public void onItemClick(DailyData dailyData) {
                Intent intent = new Intent(getActivity(), EditDailyActivity.class);
                intent.putExtra(EditDailyActivity.EXTRA_ID, dailyData.getId());
                intent.putExtra(EditDailyActivity.EXTRA_TEXT, dailyData.getTitle());
                intent.putExtra(EditDailyActivity.EXTRA_TIME, dailyData.getTime());
                intent.putExtra(EditDailyActivity.EXTRA_DETAILS,
                        dailyData.getDescription());
                intent.putExtra(EditDailyActivity.EXTRA_STATE, dailyData.getState());
                startActivityForResult(intent, EDIT_DAILY_REQUEST);
            }

            @Override
            public void checkboxViewOnClick(DailyData dailyData) {
                dailyData.setState(2);
                dailyViewModel.update(dailyData);
                if (catAnimation.isRunning()) {
                    catAnimation.stop();
                }
                catAnimation.start();
            }

            @Override
            public void notificationOnClick(DailyData dailyData, boolean state) {
                if (state == true) {
                    int id = dailyData.getId();
                    String time = dailyData.getTime();
                    String title = dailyData.getTitle();
                    Calendar calendar = timeConverter(time);
                    dailyData.setNotificationState(1);
                    dailyViewModel.update(dailyData);
                    ReminderManager.setReminder(getActivity().getApplicationContext(),
                            id, title, calendar, 1);
                    Toast.makeText(getActivity(), "Уведомление включено",
                            Toast.LENGTH_LONG).show();
                } else {
                    int id = dailyData.getId();
                    String time = dailyData.getTime();
                    String title = dailyData.getTitle();
                    Calendar calendar = timeConverter(time);
                    dailyData.setNotificationState(2);
                    dailyViewModel.update(dailyData);
                    ReminderManager.setReminder(getActivity().getApplicationContext(),
                            id, title, calendar, 2);
                    Toast.makeText(getActivity(), "Уведомление отключено",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        adapter2.setOnItemClickListener(new DailyAdapter.onItemClickListener() {
            @Override
            public void onItemClick(DailyData dailyData) {
                Intent intent = new Intent(getActivity(), EditDailyActivity.class);
                intent.putExtra(EditDailyActivity
                        .EXTRA_ID, dailyData.getId());
                intent.putExtra(EditDailyActivity
                        .EXTRA_TEXT, dailyData.getTitle());
                intent.putExtra(EditDailyActivity
                        .EXTRA_TIME, dailyData.getTime());
                intent.putExtra(EditDailyActivity
                        .EXTRA_DETAILS, dailyData
                        .getDescription());
                intent.putExtra(EditDailyActivity
                        .EXTRA_STATE, dailyData.getState());
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
        //обновление верхней части фрагмента
        final ImageView catView = rootView.findViewById(R.id.cat_image);
        catView.setBackgroundResource(R.drawable.animatorhappycat);
        catAnimation = (AnimationDrawable) catView.getBackground();
        catAnimation.start();

        rootView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        int activeTasks = adapter.getItemCount();
                        int completedTasks = adapter2.getItemCount();
                        ProgressBar progressBar = rootView
                                .findViewById(R.id.progressBar);
                        TextView textView = rootView
                                .findViewById(R.id.progressText);
                        progressBar.setMax(activeTasks + completedTasks);
                        progressBar.setProgress(completedTasks);
                        textView.setText(completedTasks + "/" +
                                (activeTasks + completedTasks));
                        TextView textView2 = rootView.findViewById(R.id
                                .showCompletedTextView);
                        textView2.setText("Выполненные задания: " + "(" +
                                (completedTasks) + ")");
                    }
                });
        //FAB из MainActivity: добавление задания
        FloatingActionButton fab = (FloatingActionButton) this.getActivity()
                .findViewById(R.id.floating_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),
                        AddDailyActivity.class);
                startActivityForResult(intent, ADD_DAILY_REQUEST);
            }
        });
        return rootView;
    }

    //обработка данных из AddDailyActivity и EditDailyActivity
    //редактирование данных
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_DAILY_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(EditDailyActivity.EXTRA_ID, -1);
            String title = data.getStringExtra(EditDailyActivity
                    .EXTRA_TEXT);
            String time = data.getStringExtra(EditDailyActivity
                    .EXTRA_TIME);
            String details = data.getStringExtra(EditDailyActivity
                    .EXTRA_DETAILS);
            int state = data.getIntExtra(EditDailyActivity
                    .EXTRA_STATE, 1);
            DailyData dailyData = new DailyData(title, details, time,
                    state, 2);
            dailyData.setId(id);
            dailyViewModel.update(dailyData);
            Calendar calendar = timeConverter(time);
            ReminderManager.setReminder(getActivity().getApplicationContext(),
                    id, title, calendar, 2);
            //удаление данных
        } else if (requestCode == EDIT_DAILY_REQUEST && resultCode == 12) {
            final int id = data.getIntExtra(EditDailyActivity
                    .EXTRA_ID, -1);
            String title = data.getStringExtra(EditDailyActivity
                    .EXTRA_TEXT);
            String time = data.getStringExtra(EditDailyActivity
                    .EXTRA_TIME);
            String details = data.getStringExtra(EditDailyActivity
                    .EXTRA_DETAILS);
            int state = data.getIntExtra(EditDailyActivity
                    .EXTRA_STATE, 1);
            DailyData dailyData = new DailyData(title, details, time, state, 2);
            dailyData.setId(id);
            Calendar calendar = timeConverter(time);
            ReminderManager.setReminder(getActivity().getApplicationContext(),
                    id, title, calendar, 2);
            dailyViewModel.delete(dailyData);
            //изменение статуса задания
        } else if (requestCode == EDIT_DAILY_REQUEST && resultCode == 345) {
            int id = data.getIntExtra(EditDailyActivity
                    .EXTRA_ID, -1);
            String title = data.getStringExtra(EditDailyActivity
                    .EXTRA_TEXT);
            String time = data.getStringExtra(EditDailyActivity
                    .EXTRA_TIME);
            String details = data.getStringExtra(EditDailyActivity
                    .EXTRA_DETAILS);
            int state = data.getIntExtra(EditDailyActivity
                    .EXTRA_STATE, 1);
            DailyData dailyData = new DailyData(title, details, time, state, 2);
            dailyData.setId(id);
            Calendar calendar = timeConverter(time);
            dailyData.getState();
            ReminderManager.setReminder(getActivity().getApplicationContext(),
                    id, title, calendar, 2);
            if (dailyData.getState() == 1) {
                dailyData.setState(2);
                dailyViewModel.update(dailyData);
            } else {
                dailyData.setState(1);
                dailyViewModel.update(dailyData);
            }
            //добавление задания
        } else if (requestCode == ADD_DAILY_REQUEST &&
                resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddDailyActivity
                    .EXTRA_TEXT);
            String time = data.getStringExtra(AddDailyActivity
                    .EXTRA_TIME);
            String details = data.getStringExtra(AddDailyActivity
                    .EXTRA_DETAILS);
            int state = data.getIntExtra(AddDailyActivity
                    .EXTRA_STATE, 1);

            DailyData dailyData = new DailyData(title, details, time,
                    state, 2);
            dailyViewModel.insert(dailyData);
        }
        //если закрыто с помощью кнопки назад
        else {
            Toast.makeText(getActivity(), "Задание отредактировано",
                    Toast.LENGTH_LONG).show();
        }
    }

    //конвертация времени из String в Calendar
    private Calendar timeConverter(String time) {
        StringTokenizer tokens = new StringTokenizer(time, ":");
        int hour = Integer.valueOf(tokens.nextToken());
        int minute = Integer.valueOf(tokens.nextToken());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar;
    }
}