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



GameBoard gameBoard = new GameBoard();
    TextView textViewInfo;;
    RadioGroup radioGroup;
    RadioButton radioButtonX;
    RadioButton radioButtonO;
    private char userPerice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // TextView textViewTitle = findViewById(R.id.textViewTitle);
       // TextView textViewSub   = findViewById(R.id.textViewSub);


        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);


        // Set x as default

        radioGroup.clearCheck();
        radioButtonX = (RadioButton)radioGroup.findViewById(R.id.radioButtonX);
        radioButtonO = (RadioButton)radioGroup.findViewById(R.id.radioButtonO);

        radioButtonX.setChecked(true);
        radioButtonX.setTextColor(getResources().getColor(R.color.colorAccent));
        gameBoard.setUserPiece(GameBoardCell.PIECE_X);
        gameBoard.setAIpiece(GameBoardCell.PIECE_O);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                RadioButton checkedRB = (RadioButton) group.findViewById(checkedId);
                if(checkedRB == radioButtonX)
                {
                    gameBoard.setUserPiece(GameBoardCell.PIECE_X);
                 //   gameBoard.setAIpiece(GameBoardCell.PIECE_O);
                    radioButtonO.setTextColor(getResources().getColor(R.color.colorBlack));

                }
                else {
                    gameBoard.setUserPiece(GameBoardCell.PIECE_O);
               //     gameBoard.setAIpiece(GameBoardCell.PIECE_X);
                    radioButtonX.setTextColor(getResources().getColor(R.color.colorBlack));
                }

                checkedRB.setTextColor(getResources().getColor(R.color.colorAccent));
                setTextViewInfo("Your piece is "+ gameBoard.getUserPiece());
            }
        });

        textViewInfo = (TextView) findViewById(R.id.textViewInfo);

        //init the info
        setTextViewInfo("Your piece is "+ gameBoard.getUserPiece());

        Button   buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameBoard.resetGameBoard();
                gameBoard.setGameActive(true);
//                radioGroup.setEnabled(false);
                radioButtonO.setEnabled(false);
                radioButtonX.setEnabled(false);
            }
        });

        Button buttonReset = (Button)findViewById(R.id.buttonReset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameBoard.resetGameBoard();
               // radioGroup.setEnabled(true);
                radioButtonO.setEnabled(true);
                radioButtonX.setEnabled(true);
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
                textView.setText((" "));
                gameBoard.getGameBoardCells()[i][j].setCellView(textView);
               // GameBoardCell tmpCell = gameBoard.getGameBoardCells()[i][j];
                gameBoard.getGameBoardCells()[i][j].getCellView().setTag(i*3+j);
                gameBoard.getGameBoardCells()[i][j].setCellPiece(gameBoard.getUserPiece());
                gameBoard.getGameBoardCells()[i][j].clearGameCell();

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer index = (Integer) view.getTag();

                        if(gameBoard.isGameActive())
                        {
                            GameBoardCell clickedCell = gameBoard.getGameBoardCells()[index/3][index%3];
                            if (clickedCell.isCellFree()){
//                                textView.setText(gameBoard.getUserPiece());
//                               clickedCell.setCellPiece(gameBoard.getUserPiece());
//                                clickedCell.getCellView().setText(gameBoard.getUserPiece());
                                clickedCell.writeToCell(gameBoard.getUserPiece());

                               switch( gameBoard.evaluate()) {
                                   case 10:
                                       //Ai win
                                       gameBoard.setGameActive(false);
                                       gameBoard.setGameSuspended(true);
                                       setTextViewInfo("Your piece is "+ gameBoard.getUserPiece() + ". The Machine Has Win");
                                       Toast toast = Toast.makeText(getApplicationContext(),
                                               "The Machine has win!",
                                               Toast.LENGTH_LONG);

                                       toast.show();
                                       break;
                                   case -10:
                                       //Human Win
                                       gameBoard.setGameActive(false);
                                       gameBoard.setGameSuspended(true);
                                       setTextViewInfo("Your piece is "+ gameBoard.getUserPiece() + ". You Win");
                                       Toast toastLose = Toast.makeText(getApplicationContext(),
                                               "The user has win!",
                                               Toast.LENGTH_LONG);

                                       toastLose.show();
                                       break;

                                   case 0:
                                       //if there is no move left there is a tie, call AI

                                       if (!gameBoard.isMovesLeft()) {
                                           gameBoard.setGameActive(false);
                                           gameBoard.setGameSuspended(true);
                                           setTextViewInfo("Your piece is "+ gameBoard.getUserPiece() + ". The Game Has Tied");

                                           Toast toastTie = Toast.makeText(getApplicationContext(),
                                                   "The Game is Tied!",
                                                   Toast.LENGTH_LONG);

                                           toastTie.show();
                                       } else {
                                           AIMove(textViewInfo);
                                       }
                                       break;
                                   default:
                                       System.out.println("default game eval, you are not suppose to be here");
                                       break;

                               }
                            }
                        }
                    }
                });
            }
        }
    }
@Override
    protected void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isGameActive", gameBoard.isGameActive());
        outState.putBoolean("isGameSuspended", gameBoard.isGameSuspended());
        outState.putCharSequence("userPiece", gameBoard.getUserPiece());
        outState.putCharSequence("boardState", gameBoard.getBoardState());
        outState.putCharSequence("info",getTextViewInfo());
    }
@Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        gameBoard.setGameActive(savedInstanceState.getBoolean("isGameActive"));
        gameBoard.setGameSuspended(savedInstanceState.getBoolean("isGameSuspended"));
        gameBoard.setUserPiece(savedInstanceState.getString("userPerice"));
        setTextViewInfo(savedInstanceState.getString("info"));

        if(savedInstanceState.getBoolean("isGameActive")||savedInstanceState.getBoolean("isGameSuspended")) {
            radioButtonO.setEnabled(false);
            radioButtonX.setEnabled(false);
            String temp = savedInstanceState.getString("boardState");


            gameBoard.restoreBoard(savedInstanceState.getString("boardState"));
        }
        else
        {
            radioButtonO.setEnabled(true);
            radioButtonX.setEnabled(true);
        }
    }

    public void enableRadioGroup(boolean bool)
    {
        radioGroup.setEnabled(bool);
    }

    public String getTextViewInfo()
    {
       return textViewInfo.getText().toString();
    }

    public void setTextViewInfo(String str)
    {
        textViewInfo.setText(str);
    }

    public void AIMove( TextView view)
    {
        // calcualte the move,
        Move bestMove = gameBoard.findBestMove();
        // write to cell
        gameBoard.getGameBoardCells()[bestMove.getX()][bestMove.getY()].writeToCell(gameBoard.getAIpiece());
    //    gameBoard.getGameBoardCells()[bestMove.getX()][bestMove.getY()].setCellPiece(gameBoard.getAIpiece());
    //    gameBoard.getGameBoardCells()[bestMove.getX()][bestMove.getY()].getCellView().setText(gameBoard.getAIpiece());

        // eval the board
        switch( gameBoard.evaluate())
        {
            case 10:
                //Ai win
                gameBoard.setGameActive(false);
                gameBoard.setGameSuspended(true);
                setTextViewInfo("Your piece is "+ gameBoard.getUserPiece() + ". The Machine Has Win!");

                Toast toast = Toast.makeText(getApplicationContext(),
                        "The Machine Has win!",
                        Toast.LENGTH_LONG);

                toast.show();
                break;
            case -10:
                //Human Win
                gameBoard.setGameActive(false);
                gameBoard.setGameSuspended(true);
                setTextViewInfo("Your piece is "+ gameBoard.getUserPiece() + ". You Win!");

                Toast toastLose = Toast.makeText(getApplicationContext(),
                        "The user Has win!",
                        Toast.LENGTH_LONG);

                toastLose.show();
                break;

            case 0:
                //if there is no move left there is a tie,
                if(!gameBoard.isMovesLeft()) {
                    gameBoard.setGameActive(false);
                    gameBoard.setGameSuspended(true);
                    setTextViewInfo("Your piece is "+ gameBoard.getUserPiece() + ". The Game is Tied!");

                    Toast toastTie = Toast.makeText(getApplicationContext(),
                            "It is a tie!",
                            Toast.LENGTH_LONG);

                    toastTie.show();
                }
                else
                {
                    // Tell user to move
                    view.setText("Your Move! your piece is " + gameBoard.getUserPiece());
                }
                break;
            default:
                System.out.println("default game eval, you are not suppose to be here after AI move");
                break;

        }
    }
}
