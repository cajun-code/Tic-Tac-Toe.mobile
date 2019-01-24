package com.app.tictactoecodechallenge;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements MainPresenter.View, View.OnClickListener {

    private Button buttonTiles [][] = new Button[3][3];
    private boolean playerTurn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonLayoutId = "button_layout_" + i + j;
                int resID = getResources().getIdentifier(buttonLayoutId, "id", getPackageName());
                buttonTiles[i][j] = findViewById(resID);
                buttonTiles[i][j].setOnClickListener(this);
            }
        }

        showPlayerChoiceMark();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_game:
                newGame();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean checkBoardStatus() {
        return false;
    }

    @Override
    public void statusWinner() {

    }

    @Override
    public void draw() {

    }

    public void newGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttonTiles[i][j].setText("");
            }
        }
        showPlayerChoiceMark();
    }

    public void showPlayerChoiceMark() {
            FragmentManager fm = getSupportFragmentManager();
            PlayerMarkDialog playerMarkDialog = PlayerMarkDialog.newInstance("Choose");
            playerMarkDialog.show(fm, "dialog_playet_mark_choice");
    }

    @Override
    public void onClick(View v) {

    }
}
