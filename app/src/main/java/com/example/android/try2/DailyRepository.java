package com.example.android.try2;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DailyRepository {
    private DailyDao dailyDao;
    private LiveData<List<DailyData>> allDailies;
    private LiveData<List<DailyData>> doneDailies;
    private DailyData dailyById;

    //application используется как контекст для создания singleton экземпляра ДБ
    public DailyRepository(Application application) {
        DailyDB db = DailyDB.getInstance(application);
        //хотя сам dailyDao() - абстрактный метод и не имеет тела, instance создается
        // с помощью специального метода databaseBuilder, поэтому Room генерирует весь код
        //и создает подкласс абстрактного класса
        dailyDao = db.dailyDao();
        allDailies = dailyDao.getAllDailies();
        doneDailies = dailyDao.getInactiveDailies();
    }

    //методы для ViewModel
    public void insert(DailyData dailyData) {
        new InsertDailyAsyncTask(dailyDao).execute(dailyData);
    }
    public void update(DailyData dailyData) {
        new UpdateDailyAsyncTask(dailyDao).execute(dailyData);
    }
    public void delete(DailyData dailyData) {
        new DeleteDailyAsyncTask(dailyDao).execute(dailyData);
    }

    public LiveData<List<DailyData>> getAllDailies() {
        return allDailies;
    }
    public LiveData<List<DailyData>> getInactiveDailies() {
        return doneDailies;
    }

    public DailyData findDailyById(int id) {
        dailyById = dailyDao.findDailyById(id);
        return dailyById;
    }

    private static class FindDailyByIdAsyncTask extends AsyncTask<DailyData, Void, Void> {
        private DailyDao dailyDao;

        private FindDailyByIdAsyncTask(DailyDao dailyDao) {
            this.dailyDao = dailyDao;
        }

        @Override
        protected Void doInBackground(DailyData... dailyData) {
            dailyDao.update(dailyData[0]);
            return null;
        }
    }


    //Room не позволяет совершать запросы к ДБ с основного треда,
    //поэтому создаются AsyncTasks для всех операций
    private static class InsertDailyAsyncTask extends AsyncTask<DailyData, Void, Void> {
        private DailyDao dailyDao;

        //static поэтому конструктор
        private InsertDailyAsyncTask(DailyDao dailyDao) {
            this.dailyDao = dailyDao;
        }

        @Override
        protected Void doInBackground(DailyData... dailyData) {
            dailyDao.insert(dailyData[0]);
            return null;
        }
    }

    private static class UpdateDailyAsyncTask extends AsyncTask<DailyData, Void, Void> {
        private DailyDao dailyDao;

        private UpdateDailyAsyncTask(DailyDao dailyDao) {
            this.dailyDao = dailyDao;
        }

        @Override
        protected Void doInBackground(DailyData... dailyData) {
            dailyDao.update(dailyData[0]);
            return null;
        }
    }

    private static class DeleteDailyAsyncTask extends AsyncTask<DailyData, Void, Void> {
        private DailyDao dailyDao;

        private DeleteDailyAsyncTask(DailyDao dailyDao) {
            this.dailyDao = dailyDao;
        }

        @Override
        protected Void doInBackground(DailyData... dailyData) {
            dailyDao.delete(dailyData[0]);
            return null;
        }
    }

}
