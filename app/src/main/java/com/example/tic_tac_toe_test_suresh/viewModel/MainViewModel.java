package com.example.tic_tac_toe_test_suresh.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayMap;

import com.example.tic_tac_toe_test_suresh.model.Player;

public class MainViewModel extends ViewModel {
    public ObservableArrayMap<String, String> cells;
    public LiveData<Player> liveData;

    public void onButtonClickedAt(int row, int column) {

    }


}

