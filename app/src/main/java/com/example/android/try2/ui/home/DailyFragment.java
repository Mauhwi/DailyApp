package com.example.android.try2.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.try2.AddDailyActivity;
import com.example.android.try2.DailyAdapter;
import com.example.android.try2.DailyData;
import com.example.android.try2.R;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class DailyFragment extends Fragment {
    private DailyViewModel dailyViewModel;

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

        return rootView;
    }



}