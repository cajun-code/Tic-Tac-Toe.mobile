package com.app.tictactoecodechallenge.view;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.app.tictactoecodechallenge.R;
import com.app.tictactoecodechallenge.presenter.MainPresenterView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements MainPresenterView.View, View.OnClickListener {

    private Button buttonTiles [][] = new Button[3][3];
    private boolean playerTurn = true; //Player Turn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Set the button layout and to get the Button layout id by corresponding array
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

    // OnClickListener for the ButtonTitles.
    @Override
    public void onClick(View v) {
        //SharedPreferences preferences = getSharedPreferences("",MODE_PRIVATE);// get your preferences

        if (playerTurn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        // Computer will make move after click
        computerView();


        if (checkBoardStatus()){
            if (playerTurn) {
                Toast.makeText(getApplicationContext(),"You Win!",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),"You Lose!",Toast.LENGTH_LONG).show();
            }
        }

    }

    // Creates the Menu Layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    // Get to Listeners for the Menu Selected
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

    // Checks the board if the row and column has matched to determine spots filled
    @Override
    public boolean checkBoardStatus() {

        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttonTiles[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    @Override
    public void statusWinner() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttonTiles[i][j].setText("");
            }
        }
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

    // Dialog to show the user (via FragmentManager) the choice between X and O
    public void showPlayerChoiceMark() {
            FragmentManager fm = getSupportFragmentManager();
            PlayerMarkDialog playerMarkDialog = PlayerMarkDialog.newInstance("Choose");
            playerMarkDialog.show(fm, "dialog_playet_mark_choice");
    }

    // This is the AI Component. After the onClickListener from the player, the computer will
    // automatically pick a square
    @Override
    public void computerView() {
        Random r = new Random();
        int i = r.nextInt(3); // Array bound for row
        int j = r.nextInt(3); // Array bound for column

        if (!buttonTiles[i][j].getText().equals("X")){
            buttonTiles[i][j].setText("O");
        }

    }




}
