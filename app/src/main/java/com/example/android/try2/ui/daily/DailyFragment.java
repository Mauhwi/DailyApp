package com.example.android.try2.ui.daily;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.try2.DailyAdapter;
import com.example.android.try2.DailyData;
import com.example.android.try2.MainActivity;
import com.example.android.try2.R;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class DailyFragment extends Fragment {
    private DailyViewModel dailyViewModel;
    public static final int EDIT_DAILY_REQUEST = 2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        //активные
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_daily);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final DailyAdapter adapter = new DailyAdapter();
        recyclerView.setAdapter(adapter);

        //неактивные
        RecyclerView recyclerView2 = rootView.findViewById(R.id.recycler_view_dailyDone);
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
        }
        //если закрыто с помощью кнопки назад
        else {
            Toast.makeText(getActivity(), "Задание отменено", Toast.LENGTH_LONG).show();
        }
    }
}