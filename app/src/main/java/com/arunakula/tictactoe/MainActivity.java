package com.arunakula.tictactoe;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btnRestart;
    private int turn = 1;
    private int winnerIndex = 0;
    private int gamov = 0;
    private int flag;
    private String displayTurn;
    private GridLayout gridLayout;
    private Button playBoard[][] = new Button[3][3];
    private int boardMatrix[][] = new int[3][3];
    private double probMatrix[][] = new double[3][3];
    private TextView playerTurn;
    private int moveNumber=1;
    private int flipValue=0;
    private AlertDialog.Builder dialogBuilder;
    private final String player1Name = "Your", player2Name = "Computer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerTurn = findViewById(R.id.playerIndicator);
        btnRestart = findViewById(R.id.btnRestart);
        dialogBuilder = new AlertDialog.Builder(this);

        gridLayout = findViewById(R.id.gridLayout);
        displayTurn=player1Name + "'s turn (X)";
        playerTurn.setText(displayTurn);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                playBoard[i][j] = (Button) gridLayout.getChildAt(3 * i + j);
                boardMatrix[i][j]=0;
            }
        }
        if(flipValue==1){
            computerPlay();
            turn=2;
        }
    }

    public void playNextMove(View view) {
        if(btnRestart.getVisibility()==View.INVISIBLE) {
            btnRestart.setVisibility(View.VISIBLE);
        }
        int index = gridLayout.indexOfChild(view);
        int i = index / 3;
        int j = index % 3;
        flag = 0;
        if (turn == 1 && gamov == 0 && !(playBoard[i][j].getText().toString().equals("X")) && !(playBoard[i][j].getText().toString().equals("O"))) {
            if(flipValue==0){
                displayTurn=player2Name + "'s turn (O)";
                playBoard[i][j].setText("X");
                boardMatrix[i][j]=1;
                turn = 2;
                moveNumber++;
                computerPlay();
                turn = 1;
                displayTurn=player1Name + "'s turn (X)";
                moveNumber++;
            }
        } else if (turn == 2 && gamov == 0 && !(playBoard[i][j].getText().toString().equals("X")) && !(playBoard[i][j].getText().toString().equals("O"))) {
            if(flipValue==1){
                displayTurn=player2Name + "'s turn (X)";
                playBoard[i][j].setText("O");
                boardMatrix[i][j]=1;
                turn = 1;
                moveNumber++;
                computerPlay();
                displayTurn=player1Name + "'s turn (O)";
                turn = 2;
                moveNumber++;
            }
        }

        checkWin();
        if (gamov == 1) {
            if (winnerIndex == 1) {
                dialogBuilder.setMessage("You won! Do you want to play again?").setTitle("Game over");
            } else if (winnerIndex == 2) {
                dialogBuilder.setMessage("Computer won! Do you want to play again?").setTitle("Game over");
            }
            dialogBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id){
                    btnRestart.performClick();
                }

            });
            dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.this.finish();
                }
            });
            AlertDialog dialog = dialogBuilder.create();
            dialog.show();
        }
        if (gamov == 0) {
            for (i = 0; i < 3; i++) {
                for (j = 0; j < 3; j++) {
                    if (!playBoard[i][j].getText().toString().equals("X") && !playBoard[i][j].getText().toString().equals("O")) {
                        flag = 1;
                        break;
                    }
                }
            }
            if (flag == 0) {
                dialogBuilder.setMessage("It's a draw. Do you want to play again?").setTitle("Game over.");
                dialogBuilder.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                            btnRestart.performClick();
                    }

                });
                dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                    }
                });
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        }
    }
    int level=0;
    public void randomPlay(){
        int random = (int)(Math.random()*9);
        int i=random/3;
        int j=random%3;
        playBoard[i][j].setText("X");
        boardMatrix[i][j]=1;
    }

    private void makeToast(String message){
        Toast.makeText(this, message,Toast.LENGTH_LONG).show();
    }
    public void computerPlay(){
        int currentTurn = turn;
        int currentMove = moveNumber;
        int i=0,j=0;
        int moveChoice=0;
        int flag=0;
        int flagGameNotOver=0;
        if(turn==1){
            turn=2;
        }
        else{
            turn=1;
        }
        for(int c=0;c<9;c++){
            i=c/3;
            j=c%3;
            probMatrix[i][j]=0;
        }

        for(int c=0; c<9;c++) {
            i = c / 3;
            j = c % 3;
            if (boardMatrix[i][j] == 0) {
                flagGameNotOver=1;
                boardMatrix[i][j] = 1;
                if (flipValue == 1)
                    playBoard[i][j].setText("X");
                else
                    playBoard[i][j].setText("O");
                if (checkWinComp() == 2 && flipValue == 0) {
                    flag=1;
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j] = 0;
                    break;
                } else if (checkWinComp() == 2 && flipValue == 1) {
                    flag=1;
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j] = 0;
                    break;
                }
                if (checkWinComp() == 1 && flipValue == 1) {
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j] = 0;
                    continue;
                } else if (checkWinComp() == 1 && flipValue == 0) {
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j] = 0;
                    continue;

                } else {
                    level++;
                    probMatrix[i][j]=computerAnalyze();
                    level--;
                }
                playBoard[i][j].setText(" ");
                boardMatrix[i][j] = 0;
            }
        }
        if(flagGameNotOver==0){
            return;
        }
        double maxProb=0;
        if(flag==0){
            for(int p=0;p<3;p++){
                for(int q=0;q<3;q++){
                    if(maxProb<probMatrix[p][q]){
                        maxProb=probMatrix[p][q];
                    }
                }
            }
            for(int p=0;p<3;p++){
                for(int q=0;q<3;q++){
                    if(maxProb==probMatrix[p][q] && boardMatrix[p][q]==0){
                        moveChoice=3*p+q;
                        break;
                    }
                }
            }
        }
        else{
            moveChoice=3*i+j;
        }
        turn = currentTurn;
        moveNumber = currentMove;
        int xCoord=moveChoice/3;
        int yCoord=moveChoice%3;
        boardMatrix[xCoord][yCoord]=1;
        if(flipValue==0){
            playBoard[xCoord][yCoord].setText("O");
        }
        else{
            playBoard[xCoord][yCoord].setText("X");
        }
    }

    public double computerAnalyze() {
        double sum=0;
        int counter=0;
        int flagCheckGameNotOver=0;
        for(int c=0;c<9;c++){
            int i=c/3;
            int j=c%3;

            if(boardMatrix[i][j]==0){
                flagCheckGameNotOver=1;
                boardMatrix[i][j]=1;

                if(turn==1)
                    playBoard[i][j].setText("X");
                else
                    playBoard[i][j].setText("O");
                if(checkWinComp()==2 && flipValue==0){
                    sum=1;
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j]=0;

                    return sum;
                }
                else if(checkWinComp()==2 && flipValue==1){
                    sum=1;
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j]=0;

                    return sum;
                }
                else if(checkWinComp()==1 && flipValue==1){
                    sum=0;
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j]=0;

                    return sum;
                }
                else if(checkWinComp()==1 && flipValue==0){
                    sum=0;
                    playBoard[i][j].setText(" ");
                    boardMatrix[i][j]=0;

                    return sum;
                }
                else {
                    counter++;
                    if(turn==1){
                        turn=2;
                    }
                    else{
                        turn=1;
                    }
                    level++;
                    double value=computerAnalyze();
                    level--;
                    sum+=value;
                }
                playBoard[i][j].setText(" ");
                boardMatrix[i][j]=0;
                if(turn==1){
                    turn=2;
                }
                else{
                    turn=1;
                }
            }

        }
        if(flagCheckGameNotOver==0){
            return 0.5;
        }
        double average = (sum)/ ((double) counter);
        return average;
    }

    public void startNewGame(View view) {
        btnRestart.setVisibility(View.INVISIBLE);
        winnerIndex = 0;
        gamov = 0;
        turn=1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                playBoard[i][j].setText(" ");
                playBoard[i][j].setTextColor(Color.WHITE);
                boardMatrix[i][j]=0;
            }
        }

        if(flipValue==1){
            randomPlay();
            turn=2;
        }
    }

    public void checkWin() {
        for (int i = 0; i < 3; i++) {
            if (playBoard[i][0].getText().toString().equals(playBoard[i][1].getText().toString()) && playBoard[i][0].getText().toString().equals(playBoard[i][2].getText().toString())) {
                if (playBoard[i][0].getText().toString().equals("X")) {
                    gamov = 1;
                    if(flipValue==0)
                        winnerIndex = 1;
                    else if(flipValue==1)
                        winnerIndex =2;
                } else if (playBoard[i][0].getText().toString().equals("O")) {
                    gamov = 1;
                    if(flipValue==0)
                        winnerIndex = 2;
                    else if(flipValue==1)
                        winnerIndex =1;
                }
                if (!playBoard[i][0].getText().toString().equals(" ")) {
                    playBoard[i][0].setTextColor(Color.RED);
                    playBoard[i][1].setTextColor(Color.RED);
                    playBoard[i][2].setTextColor(Color.RED);
                }
            }
            if (playBoard[0][i].getText().toString().equals(playBoard[1][i].getText().toString()) && playBoard[0][i].getText().toString().equals(playBoard[2][i].getText().toString())) {
                if (playBoard[0][i].getText().toString().equals("X")) {
                    gamov = 1;
                    if(flipValue==0)
                        winnerIndex = 1;
                    else if(flipValue==1)
                        winnerIndex =2;
                } else if (playBoard[0][i].getText().toString().equals("O")) {
                    gamov = 1;
                    if(flipValue==0)
                        winnerIndex = 2;
                    else if(flipValue==1)
                        winnerIndex =1;
                }
                if (!playBoard[0][i].getText().toString().equals(" ")) {
                    playBoard[0][i].setTextColor(Color.RED);
                    playBoard[1][i].setTextColor(Color.RED);
                    playBoard[2][i].setTextColor(Color.RED);
                }
            }
        }
        if (playBoard[0][0].getText().toString().equals(playBoard[1][1].getText().toString()) && playBoard[0][0].getText().toString().equals(playBoard[2][2].getText().toString())) {
            if (playBoard[0][0].getText().toString().equals("X")) {
                gamov = 1;
                if(flipValue==0)
                    winnerIndex = 1;
                else if(flipValue==1)
                    winnerIndex =2;
            } else if (playBoard[0][0].getText().toString().equals("O")) {
                gamov = 1;
                if(flipValue==0)
                    winnerIndex = 2;
                else if(flipValue==1)
                    winnerIndex =1;
            }
            if (!playBoard[0][0].getText().toString().equals(" ")) {
                playBoard[0][0].setTextColor(Color.RED);
                playBoard[1][1].setTextColor(Color.RED);
                playBoard[2][2].setTextColor(Color.RED);
            }
        }
        if (playBoard[0][2].getText().toString().equals(playBoard[1][1].getText().toString()) && playBoard[0][2].getText().toString().equals(playBoard[2][0].getText().toString())) {
            if (playBoard[0][2].getText().toString().equals("X")) {
                gamov = 1;
                if(flipValue==0)
                    winnerIndex = 1;
                else if(flipValue==1)
                    winnerIndex =2;
            } else if (playBoard[0][2].getText().toString().equals("O")) {
                gamov = 1;
                if(flipValue==0)
                    winnerIndex = 2;
                else if(flipValue==1)
                    winnerIndex =1;
            }
            if (!playBoard[2][0].getText().toString().equals(" ")) {
                playBoard[2][0].setTextColor(Color.RED);
                playBoard[1][1].setTextColor(Color.RED);
                playBoard[0][2].setTextColor(Color.RED);
            }
        }
    }

    public int checkWinComp() {
        for (int i = 0; i < 3; i++) {
            if (playBoard[i][0].getText().toString().equals(playBoard[i][1].getText().toString()) && playBoard[i][0].getText().toString().equals(playBoard[i][2].getText().toString())) {
                if (playBoard[i][0].getText().toString().equals("X")) {

                    if(flipValue==0)
                        return 1;
                    else if(flipValue==1)
                        return 2;
                } else if (playBoard[i][0].getText().toString().equals("O")) {
                    if(flipValue==0)
                        return 2;
                    else if(flipValue==1)
                        return 1;
                }

            }
            if (playBoard[0][i].getText().toString().equals(playBoard[1][i].getText().toString()) && playBoard[0][i].getText().toString().equals(playBoard[2][i].getText().toString())) {
                if (playBoard[0][i].getText().toString().equals("X")) {
                    if(flipValue==0)
                        return 1;
                    else if(flipValue==1)
                        return 2;
                } else if (playBoard[0][i].getText().toString().equals("O")) {

                    if(flipValue==0)
                        return 2;
                    else if(flipValue==1)
                        return 1;
                }
            }
        }
        if (playBoard[0][0].getText().toString().equals(playBoard[1][1].getText().toString()) && playBoard[0][0].getText().toString().equals(playBoard[2][2].getText().toString())) {
            if (playBoard[0][0].getText().toString().equals("X")) {

                if(flipValue==0)
                    return 1;
                else if(flipValue==1)
                    return 2;
            } else if (playBoard[0][0].getText().toString().equals("O")) {

                if(flipValue==0)
                    return 2;
                else if(flipValue==1)
                    return 1;
            }
        }
        if (playBoard[0][2].getText().toString().equals(playBoard[1][1].getText().toString()) && playBoard[0][2].getText().toString().equals(playBoard[2][0].getText().toString())) {
            if (playBoard[0][2].getText().toString().equals("X")) {

                if(flipValue==0)
                    return 1;
                else if(flipValue==1)
                    return 2;
            } else if (playBoard[0][2].getText().toString().equals("O")) {
                if(flipValue==0)
                    return 2;
                else if(flipValue==1)
                    return 1;
            }
        }
        return 0;
    }
}