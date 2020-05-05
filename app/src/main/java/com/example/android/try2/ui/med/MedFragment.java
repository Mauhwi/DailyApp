package com.example.android.try2.ui.med;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.try2.DB.MedDB.MedData;
import com.example.android.try2.R;

import java.util.List;

public class MedFragment extends Fragment {

    private MedViewModel medViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
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

        return rootView;
    }
}