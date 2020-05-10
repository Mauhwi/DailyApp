package com.example.android.try2.ui.med;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.try2.DB.MedDB.MedData;
import com.example.android.try2.DB.MedDB.MedRepository;

import java.util.List;

public class MedViewModel extends AndroidViewModel {
    private MedRepository medRepository;
    private LiveData<List<MedData>> allMeds;


    public MedViewModel(@NonNull Application application) {
        super(application);
        medRepository = new MedRepository(application);
        allMeds = medRepository.getAllMeds();
    }

    public void insert(MedData medData) {
        medRepository.insert(medData);
    }
    public void update(MedData medData) {
        medRepository.update(medData);
    }
    public void delete(MedData medData) {
        medRepository.delete(medData);
    }

    public LiveData<List<MedData>> getAllMeds() {
        return allMeds;
    }

    public MedData findMedById(int id) {
        MedData medById = medRepository.findMedById(id);
        return medById;
    }

    public void changeState() {
        medRepository.changeState();
    }
}