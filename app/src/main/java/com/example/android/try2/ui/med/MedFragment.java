package com.example.android.try2.ui.med;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.android.try2.DB.MedDB.MedData;
import com.example.android.try2.R;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class MedFragment extends Fragment {
    private MedViewModel medViewModel;
    public static final int ADD_MED_REQUEST = 1;

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


        medAdapter.setOnItemClickListener(new MedAdapter.onItemClickListener() {
            @Override
            public void onItemClick(MedData medData) {

            }

            @Override
            public void checkboxViewOnClick(MedData medData) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton)this.getActivity().findViewById(R.id.floating_button);
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
        } else {
            Toast.makeText(getActivity(), "Лекарство отменено: ", Toast.LENGTH_LONG).show();
        }
    }
}

