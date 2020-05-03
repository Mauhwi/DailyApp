package com.example.android.try2.ui.daily;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.try2.DailyData;
import com.example.android.try2.DailyRepository;

import java.util.List;

public class DailyViewModel extends AndroidViewModel {
    private DailyRepository dailyRepository;
    private LiveData<List<DailyData>> allDailies;
    private LiveData<List<DailyData>> doneDailies;

    public DailyViewModel(@NonNull Application application) {
        super(application);
        dailyRepository = new DailyRepository(application);
        allDailies = dailyRepository.getAllDailies();
        doneDailies = dailyRepository.getInactiveDailies();
    }

    public void insert(DailyData dailyData) {
        dailyRepository.insert(dailyData);
    }

    public void update(DailyData dailyData) {
        dailyRepository.update(dailyData);
    }

    public void delete(DailyData dailyData) {
        dailyRepository.delete(dailyData);
    }

    public LiveData<List<DailyData>> getAllDailies() {
        return allDailies;
    }

    public LiveData<List<DailyData>> getInactiveDailies() {
        return doneDailies;
    }

    public DailyData findDailyById(int id) {
        DailyData dailyById = dailyRepository.findDailyById(id);
        return dailyById;
    }

}
