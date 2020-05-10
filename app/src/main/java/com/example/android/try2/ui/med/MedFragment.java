package com.example.android.try2.ui.med;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.try2.DB.MedDB.MedData;
import com.example.android.try2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class MedFragment extends Fragment {
    private MedViewModel medViewModel;
    public static final int ADD_MED_REQUEST = 1;
    public static final int EDIT_MED_REQUEST = 2;
    PendingIntent pi;
    BroadcastReceiver br;
    AlarmManager am;
    Context _context;

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
            }
        });
        Button button = rootView.findViewById(R.id.temp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medViewModel.changeState();
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

        setup();

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
        } else if (requestCode == EDIT_MED_REQUEST && resultCode == RESULT_OK){
            int id = data.getIntExtra(EditMedActivity.MED_ID, -1);
            if (id == -1) {
                Toast.makeText(getActivity(), "Нет задания", Toast.LENGTH_LONG).show();
                return;
            }
            String title = data.getStringExtra(EditMedActivity.MED_TEXT);
            String time = data.getStringExtra(EditMedActivity.MED_TIME);
            String image = data.getStringExtra(EditMedActivity.MED_IMAGE);
            int state = data.getIntExtra(EditMedActivity.MED_STATE, 1);


            MedData medData = new MedData(title, image, time, state);
            medData.setId(id);
            medViewModel.update(medData);

            Toast.makeText(getActivity(), "Лекарство обновлено", Toast.LENGTH_LONG).show();
        } else if (requestCode == EDIT_MED_REQUEST && resultCode == 12) {
            final int id = data.getIntExtra(EditMedActivity.MED_ID, -1);
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    MedData medData = medViewModel.findMedById(id);
                    medViewModel.delete(medData);
                }
            });
        } else {
            Toast.makeText(getActivity(), "Лекарство отменено: ", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        _context = context;
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        _context.unregisterReceiver(br);
    }


    private void setup()
    {
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent i)
            {
                medViewModel.changeState();
            }
        };
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        _context.registerReceiver(br, new IntentFilter("com.example.android.try2.setup"));
        pi = PendingIntent.getBroadcast(_context, 0, new Intent("com.example.android.try2.setup"), 0);
        am = (AlarmManager)(_context.getSystemService(Context.ALARM_SERVICE));
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);
    }
}

