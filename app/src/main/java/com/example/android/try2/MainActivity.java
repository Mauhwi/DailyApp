package com.example.android.try2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.try2.ui.home.DailyFragment;
import com.example.android.try2.ui.home.DailyViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //код запроса
    private DailyViewModel dailyViewModel;
    public static final int ADD_DAILY_REQUEST = 1;
    //------

    public void onClickExpand(View b) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //------------
        dailyViewModel = ViewModelProviders.of(this).get(DailyViewModel.class);
        FloatingActionButton buttonAddDaily = findViewById(R.id.floating_button);
        buttonAddDaily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddDailyActivity.class);
                startActivityForResult(intent, ADD_DAILY_REQUEST);
            }
        });

        //BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
               //R.id.navigation_home, R.id.navigation_dashboard)
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
            int state = data.getIntExtra(AddDailyActivity.EXTRA_STATE, 1);

            DailyData dailyData = new DailyData(title, details, time, state);
            dailyViewModel.insert(dailyData);

            Toast.makeText(this, "Задание добавлено", Toast.LENGTH_LONG).show();
        }
        //если закрыто с помощью кнопки назад
        else {
            Toast.makeText(this, "Задание отменено", Toast.LENGTH_LONG).show();
        }
    }
}
