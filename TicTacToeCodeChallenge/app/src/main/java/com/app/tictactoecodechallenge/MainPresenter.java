package com.app.tictactoecodechallenge;

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
    }
}
