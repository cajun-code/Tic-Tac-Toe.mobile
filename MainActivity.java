package com.jin.mytictactoe;

import android.app.PictureInPictureParams;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private boolean    buttonXIsSelected;
    private boolean    gameStarted;
    private final char UserPericeX = 'X';
    private final char UserPericeO = 'O';
    private char userPerice;

    public GameBoard gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // TextView textViewTitle = findViewById(R.id.textViewTitle);
       // TextView textViewSub   = findViewById(R.id.textViewSub);


        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);


        // Set x as default

        radioGroup.clearCheck();
        final RadioButton radioButtonX = (RadioButton)radioGroup.findViewById(R.id.radioButtonX);
        radioButtonX.setChecked(true);
        gameBoard = new GameBoard();


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                RadioButton checkedRB = (RadioButton) group.findViewById(checkedId);
                if(checkedRB == radioButtonX)
                {
                    gameBoard.setUserPiece(UserPericeX);
                }
                else
                    gameBoard.setUserPiece(UserPericeO);
            }
        });

        TextView textViewInfo = (TextView) findViewById(R.id.textViewInfo);

        Button   buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameBoard.setGameActive(true);
            }
        });

        Button buttonReset = (Button)findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameBoard.resetGameBoard();
            }
        });


        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        int numOfRow = tableLayout.getChildCount();

        for (int i = 0; i< numOfRow; i++)
        {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            int numOfColumn = tableRow.getChildCount();
            for (int j=0; j< numOfColumn; j++)
            {
                final TextView textView = (TextView) tableRow.getChildAt(j);
                gameBoard.gameBoardCells[i][j].setCellView(textView);
                gameBoard.gameBoardCells[i][j].getCellView().setTag(i*3+j);
                gameBoard.gameBoardCells[i][j].setCellPeice(gameBoard.userPiece);
                gameBoard.gameBoardCells[i][j].setCellFree(false);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer index = (Integer) view.getTag();

                        if(gameBoard.isGameActive())
                        {
                            GameBoardCell clickedCell = gameBoard.gameBoardCells[index/3][index%3];
                            if (clickedCell.isCellFree()){
                                textView.setText(gameBoard.getUserPiece());
                                clickedCell.setCellFree(false);
                                AIMove();
                            }
                        }
                    }
                });
            }
        }


        //set click listener


    }

    public void setXOButtonToX(boolean bool)
    {
        buttonXIsSelected = bool;
    }

    public boolean getXOButtonState()
    {
        return buttonXIsSelected;
    }

    public void setGameState(boolean bool)
    {
        gameStarted = bool;
    }

    public boolean getGameState()
    {
        return gameStarted;
    }

    public char getUserPerice()
    {
        return userPerice;
    }

    public void setUserPeice(char piece)
    {
        userPerice = piece;
    }
    public void AIMove()
    {
        // calcualte the move,

        // set text

        // call to inform user's turn

    }
}
