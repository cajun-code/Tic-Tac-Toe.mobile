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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    private char userPerice;

    public GameBoard gameBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // TextView textViewTitle = findViewById(R.id.textViewTitle);
       // TextView textViewSub   = findViewById(R.id.textViewSub);


        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);


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
                    gameBoard.setUserPiece(GameBoardCell.PIECE_X);
                }
                else
                    gameBoard.setUserPiece(GameBoardCell.PIECE_O);
            }
        });

        final TextView textViewInfo = (TextView) findViewById(R.id.textViewInfo);

        Button   buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameBoard.setGameActive(true);
                radioGroup.setEnabled(false);
            }
        });

        Button buttonReset = (Button)findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameBoard.resetGameBoard();
                radioGroup.setEnabled(true);
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
                gameBoard.getGameBoardCells()[i][j].setCellView(textView);
                gameBoard.getGameBoardCells()[i][j].getCellView().setTag(i*3+j);
                gameBoard.getGameBoardCells()[i][j].setCellPeice(gameBoard.getUserPiece());

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer index = (Integer) view.getTag();

                        if(gameBoard.isGameActive())
                        {
                            GameBoardCell clickedCell = gameBoard.getGameBoardCells()[index/3][index%3];
                            if (clickedCell.isCellFree()){
                                textView.setText(gameBoard.getUserPiece());
                                clickedCell.setCellPeice(gameBoard.getUserPiece());

                               switch( gameBoard.evaluate()) {
                                   case 10:
                                       //Ai win
                                       Toast toast = Toast.makeText(getApplicationContext(),
                                               "The Machine has win!",
                                               Toast.LENGTH_LONG);

                                       toast.show();
                                   case -10:
                                       //Human Win
                                       Toast toastLose = Toast.makeText(getApplicationContext(),
                                               "The Machine has win!",
                                               Toast.LENGTH_LONG);

                                       toastLose.show();

                                   case 0:
                                       //if there is no move left there is a tie, call AI
                                       if (!gameBoard.isMovesLeft()) {
                                           Toast toastTie = Toast.makeText(getApplicationContext(),
                                                   "The Machine has win!",
                                                   Toast.LENGTH_LONG);

                                           toastTie.show();
                                       } else {
                                           AIMove(textViewInfo);
                                       }
                                   default:
                                       System.out.println("default game eval, you are not suppose to be here");

                               }
                            }
                        }
                    }
                });
            }
        }


        //set click listener


    }


    public void AIMove(TextView view)
    {
        // calcualte the move,
        Move bestMove = gameBoard.findBestMove();
        // set text
        gameBoard.getGameBoardCells()[bestMove.getX()][bestMove.getY()].setCellPeice(gameBoard.getAIpiece());
        gameBoard.getGameBoardCells()[bestMove.getX()][bestMove.getY()].getCellView().setText(gameBoard.getAIpiece());

        // eval the board
        switch( gameBoard.evaluate())
        {
            case 10:
                //Ai win
                Toast toast = Toast.makeText(getApplicationContext(),
                        "The Machine has win!",
                        Toast.LENGTH_LONG);

                toast.show();
            case -10:
                //Human Win
                Toast toastLose = Toast.makeText(getApplicationContext(),
                        "The Machine has win!",
                        Toast.LENGTH_LONG);

                toastLose.show();

            case 0:
                //if there is no move left there is a tie,
                if(!gameBoard.isMovesLeft()) {
                    Toast toastTie = Toast.makeText(getApplicationContext(),
                            "The Machine has win!",
                            Toast.LENGTH_LONG);

                    toastTie.show();
                }
                else
                {
                    // Tell user to move
                    view.setText("Your Move! your peices is " + gameBoard.getUserPiece());
                }
            default:
                System.out.println("default game eval, you are not suppose to be here after AI move");

        }
        // call to inform user's turn

    }
}
