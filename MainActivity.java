/*Hi Allan,
*
* I am Joe, thank you very much for giving me second opportunity. Maybe I am not the best skilled developer, but I will be the work hardest machine!
*Really expect to work for you, hope you could give me a chance. I really appreciate it.
*
* Joe
*/

package universe02.example.zhouyu.tictactoe;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import universe02.example.zhouyu.samecolorsudoku.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] btns = new Button[3][3];
    private Button newGame;
    private Button select_O, select_X;
    private String user_Icon, pc_Icon;
    private List<Button> btnList = new ArrayList<>();//store 9 buttons

    private static final int TURNS = 9;
    private int mTurnsLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        mTurnsLeft = TURNS;

        //Mat buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String btnID = "btn_" + i + j;
                int resID = getResources().getIdentifier(btnID, "id", getPackageName());
                btns[i][j] = findViewById(resID);
                btns[i][j].setOnClickListener(this);
                btns[i][j].setBackgroundResource(R.color.myYellow);
                btnList.add(btns[i][j]);
            }
        }
        disable_btns();

        select_O = findViewById(R.id.select_O);
        select_X = findViewById(R.id.select_X);
        //User select O
        select_O.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_O.setText("You select\n O");
                select_X.setVisibility(View.INVISIBLE);
                user_Icon = "O";
                pc_Icon = "X";
                enable_btns();
            }
        });
        //User select X
        select_X.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_X.setText("You select\n X");
                select_O.setVisibility(View.INVISIBLE);
                user_Icon = "X";
                pc_Icon = "O";
                enable_btns();
            }
        });

        //Start a new game
        newGame = findViewById(R.id.newGame);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        //user could only click blank button
        if (!((Button) v).getText().toString().equals(""))
            return;

            ((Button) v).setTextColor(Color.RED);
            ((Button) v).setTextSize(20);
            ((Button) v).setText(user_Icon);
            --mTurnsLeft;

        if(mTurnsLeft>0) {
            Button button = findViewById(getBestMove());  //find the PC click button
            button.setText(pc_Icon);
            button.setTextColor(Color.RED);
            button.setTextSize(20);
            --mTurnsLeft;
        }
        isEndGame();  //check if the game is end
    }

    //Prevent user to click the button before user select the XO or the game is finished
    private void disable_btns(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                btns[i][j].setEnabled(false);
            }
        }
    }

    //Enable Mat buttons
    private void enable_btns(){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                btns[i][j].setEnabled(true);
            }
        }
    }

    // checks if X or O won, or there is a tie. return false if none
    private boolean isEndGame() {
        String result = checkWin(new String[3][3], false);
        if (result.equals(user_Icon)) {
            Toast.makeText(this, "Congradulations!!! You win!!!Please click New Game", Toast.LENGTH_SHORT).show();
            disable_btns();
            return true;
        }
        else if (result.equals(pc_Icon)) {
            Toast.makeText(this, "Sorry, you lose.Please click New Game", Toast.LENGTH_SHORT).show();
            disable_btns();
            return true;
        }
        else if (mTurnsLeft == 0) {
            Toast.makeText(this, "It is Draw.Please click New Game", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


    private int getBestMove() {
        int bestMove = -1;
        int bestValue = Integer.MIN_VALUE;


        String[][] values = new String[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                values[i][j] = btns[i][j].getText().toString();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3 ; j++) {
                if (values[i][j].equals("")){
                    values[i][j] = pc_Icon; // PC turn
                    --mTurnsLeft;
                    int currentValue = miniMax(values, false);
                    ++mTurnsLeft;
                    values[i][j] = "";        //reset the button
                    if (currentValue > bestValue) {
                        bestValue = currentValue;
                        String btnID = "btn_" + i + j;
                        bestMove = getResources().getIdentifier(btnID, "id", getPackageName());
                    }
                }
            }
        }
        return bestMove;  //button ID
    }

    private String checkWin(String[][] values, boolean minimax) {

        if (!minimax) {
            values = new String[3][3];
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    values[i][j] = btns[i][j].getText().toString();
        }

        // check rows
        for (int i = 0; i < 3; i++)
            if (!values[i][0].equals("") && values[i][0].equals(values[i][1]) && values[i][1].equals(values[i][2]))
                return values[i][0];

        // check cols
        for (int i = 0; i < 3; i++)
            if (!values[0][i].equals("") && values[0][i].equals(values[1][i]) && values[1][i].equals(values[2][i]))
                return values[0][i];

        // check backslash diagonal
        if (!values[0][0].equals("") && values[0][0].equals(values[1][1]) && values[1][1].equals(values[2][2]))
            return values[0][0];

        // check slash diagonal
        if (!values[0][2].equals("") && values[0][2].equals(values[1][1]) && values[1][1].equals(values[2][0]))
            return values[0][2];

        // after checking every row, col, diagonals
        return "";
    }
    private int miniMax(String[][] values, boolean isPC) {
        String result = checkWin(values, true);
        if (result.equals(user_Icon))    //user win
            return -100;
        else if (result.equals(pc_Icon))  //PC win
            return 100;
        else if (mTurnsLeft == 0)         //Draw
            return 0;

        if (isPC) {
            int bestValue = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (values[i][j].equals("")) {
                        values[i][j] = pc_Icon;
                        --mTurnsLeft;
                        bestValue = Math.max(bestValue, miniMax(values, !isPC));  //compare and get the biggest value. after set the values[i][j], isPC turn to !isPC
                        ++mTurnsLeft;
                        values[i][j] = "";              //reset the button, this is just for calculating the value
                    }
                }
            }
            return bestValue;
        }
        else {
            int bestValue = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (values[i][j].equals("")) {
                        values[i][j] = user_Icon;
                        --mTurnsLeft;
                        bestValue = Math.min(bestValue, miniMax(values, !isPC));   //compare and get the smallest value. after set the values[i][j], isPC turn to !isPC
                        ++mTurnsLeft;
                        values[i][j] = "";                //reset the button, this is just for calculating the value
                    }
                }
            }
            return bestValue;
        }
    }

}