package com.app.tictactoecodechallenge.presenter;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;

public class MainPresenter  {
    private View view;


    public MainPresenter(View view) {
        this.view = view;
    }


    public interface View {
        boolean checkBoardStatus();
        void statusWinner();
        void draw();
        void newGame();
        void showPlayerChoiceMark();
        void computerView();

    }
}
