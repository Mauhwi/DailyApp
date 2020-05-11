package com.example.android.try2.DB.MedDB;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;


import java.util.List;

public class MedRepository {
    private MedDAO medDao;
    private LiveData<List<MedData>> allMeds;
    private LiveData<List<MedData>> doneMeds;
    private MedData medById;
    private int activeCount;
    private int inActiveCount;

    public MedRepository(Application application) {
        MedDB db = MedDB.getInstance(application);
        medDao = db.medDao();
        allMeds = medDao.getAllMeds();
        doneMeds = medDao.getInactiveMeds();
    }

    //для ViewModel
    public void insert(MedData medData) {
        new InsertMedAsyncTask(medDao).execute(medData);
    }

    public void update(MedData medData) {
        new UpdateMedAsyncTask(medDao).execute(medData);
    }

    public void delete(MedData medData) {
        new DeleteMedAsyncTask(medDao).execute(medData);
    }

    public void changeState(){
        new ChangeStateAsyncTask(medDao).execute();
    }

    public LiveData<List<MedData>> getAllMeds() {
        return allMeds;
    }

    public MedData findMedById(int id) {
        medById = medDao.findMedById(id);
        return medById;
    }

    public int getActiveCount() {
        activeCount = medDao.getActiveCount();
        return  activeCount;
    }

    public int getInactiveCount() {
        inActiveCount = medDao.getInactiveCount();
        return  inActiveCount;
    }

    //Асинхронные задания
    private static class InsertMedAsyncTask extends AsyncTask<MedData, Void, Void> {
        private MedDAO medDao;


        private InsertMedAsyncTask(MedDAO medDao) {
            this.medDao = medDao;
        }

        @Override
        protected  Void doInBackground(MedData... medData) {
            medDao.insert(medData[0]);
            return null;
        }
    }

    private static class UpdateMedAsyncTask extends AsyncTask<MedData, Void, Void> {
        private MedDAO medDao;


        private UpdateMedAsyncTask(MedDAO medDao) {
            this.medDao = medDao;
        }

        @Override
        protected  Void doInBackground(MedData... medData) {
            medDao.update(medData[0]);
            return null;
        }
    }

    private static class DeleteMedAsyncTask extends AsyncTask<MedData, Void, Void> {
        private MedDAO medDao;


        private DeleteMedAsyncTask(MedDAO medDao) {
            this.medDao = medDao;
        }

        @Override
        protected  Void doInBackground(MedData... medData) {
            medDao.delete(medData[0]);
            return null;
        }
    }

    private static class ChangeStateAsyncTask extends AsyncTask<MedData, Void, Void> {
        private MedDAO medDao;


        private ChangeStateAsyncTask(MedDAO medDao) {
            this.medDao = medDao;
        }

        @Override
        protected  Void doInBackground(MedData... medData) {
            medDao.changestate();
            return null;
        }
    }
}
