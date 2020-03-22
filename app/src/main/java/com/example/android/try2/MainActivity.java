package com.example.android.try2;

import android.os.Bundle;
import android.view.View;

import com.example.android.try2.ui.home.DailyViewModel;
import com.example.android.try2.ui.home.dailyDialogFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //------
    private DailyViewModel dailyViewModel;
    //------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //------------

        dailyViewModel = ViewModelProviders.of(this).get(DailyViewModel.class);
        dailyViewModel.getAllDailies().observe(this, new Observer<List<DailyData>>() {
            @Override
            public void onChanged(List<DailyData> dailyData) {
                //update RecyclerView
            }
        });

        //------------
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void showDialog(View v) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        dailyDialogFragment dailyDialog = new dailyDialogFragment();
        dailyDialog.show(fragmentManager, "dailyDialog");
    }
}
