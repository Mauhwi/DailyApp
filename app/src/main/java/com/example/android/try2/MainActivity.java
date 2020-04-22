package com.example.android.try2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.try2.ui.home.DailyViewModel;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //код запроса
    public static final int ADD_DAILY_REQUEST = 1;
    //------
    private DailyViewModel dailyViewModel;
    //------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //should be activity_main
        setContentView(R.layout.fragment_home);
        //------------

        Button buttonAddDaily = findViewById(R.id.addDailyButton);
        buttonAddDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddDailyActivity.class);
                startActivityForResult(intent, ADD_DAILY_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view_daily);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final DailyAdapter adapter = new DailyAdapter();
        recyclerView.setAdapter(adapter);

        dailyViewModel = ViewModelProviders.of(this).get(DailyViewModel.class);
        dailyViewModel.getAllDailies().observe(this, new Observer<List<DailyData>>() {
            @Override
            public void onChanged(List<DailyData> dailyData) {
                adapter.setDailies(dailyData);
            }
        });

        //------------
        //BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
               //R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                //.build();
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        //NavigationUI.setupWithNavController(navView, navController);
    //}

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_DAILY_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddDailyActivity.EXTRA_TEXT);
            String time = data.getStringExtra(AddDailyActivity.EXTRA_TIME);
            String details = data.getStringExtra(AddDailyActivity.EXTRA_DETAILS);

            DailyData dailyData = new DailyData(title, details, time);
            dailyViewModel.insert(dailyData);

            Toast.makeText(this, "Задание добавлено", Toast.LENGTH_LONG).show();
        }
        //если закрыто с помощью кнопки назад
        else {
            Toast.makeText(this, "Задание отменено", Toast.LENGTH_LONG).show();
        }
    }
}
