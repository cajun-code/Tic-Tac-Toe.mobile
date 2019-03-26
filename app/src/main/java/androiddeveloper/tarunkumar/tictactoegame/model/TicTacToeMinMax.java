package androiddeveloper.tarunkumar.tictactoegame.model;

import android.arch.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.List;
import androiddeveloper.tarunkumar.tictactoegame.utilities.StringUtility;

public class TicTacToeMinMax {

    private final int EMPTY = 0;
    private final int CROSS = 1;
    private final int NOUGHT = 2;
    public final int PLAYING = 0;
    public final int CROSS_WON = 1;
    public final int NOUGHT_WON = 2;
    public final int DRAW = 3;
    private static final int ROWS = 3, COLS = 3; // number of rows and columns
    private static int[][] board = new int[ROWS][COLS];
    private static final String TAG = TicTacToeMinMax.class.getSimpleName();
    private static final int BOARD_SIZE = 3;
    private Player player;
    private Player computer;
    public Player currentPlayer;
    public Cell[][] cells;
    public MutableLiveData<Player> winner = new MutableLiveData<>();
    public TicTacToeMinMax(String playerOne, String playerValue) {

        cells = new Cell[BOARD_SIZE][BOARD_SIZE];
        player = new Player(playerOne, playerValue);
        if(playerValue.equalsIgnoreCase("X")){
            computer = new Player(StringUtility.COMPUTER_NAME, "O" );
        }else{
            computer = new Player(StringUtility.COMPUTER_NAME, "X");
        }

        currentPlayer = player;
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer == player ? computer : player;
    }

    public void resetBoard() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                board[i][j] = 0;
            }
        }
    }

    public int[] move() {
        int[] result = minimax(2, NOUGHT);
        return new int[] {result[1], result[2]};
    }

    private int[] minimax(int depth, int player){
        List<int[]> nextMoves = generateMoves();

        int bestScore = (player == NOUGHT) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestRow = -1;
        int bestCol = -1;

        if (nextMoves.isEmpty() || depth == 0){
            bestScore = evaluate();
        } else {
            for (int[] move : nextMoves){
                board[move[0]][move[1]] = player;
                if (player == NOUGHT) {
                    currentScore = minimax(depth - 1, CROSS)[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                } else {
                    currentScore = minimax(depth - 1, NOUGHT)[0];
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }
                board[move[0]][move[1]] = EMPTY;
            }
        }
        return new int[] {bestScore, bestRow, bestCol};
    }

    private int evaluate() {
        int score = 0;
        score += evaluateLine(0, 0, 0, 1, 0, 2);
        score += evaluateLine(1, 0, 1, 1, 1, 2);
        score += evaluateLine(2, 0, 2, 1, 2, 2);
        score += evaluateLine(0, 0, 1, 0, 2, 0);
        score += evaluateLine(0, 1, 1, 1, 2, 1);
        score += evaluateLine(0, 2, 1, 2, 2, 2);
        score += evaluateLine(0, 0, 1, 1, 2, 2);
        score += evaluateLine(0, 2, 1, 1, 2, 0);
        return score;
    }


    private int evaluateLine(int row1, int col1, int row2, int col2, int row3, int col3) {
        int score = 0;

        if (board[row1][col1] == NOUGHT) {
            score = 1;
        } else if (board[row1][col1] == CROSS) {
            score = -1;
        }

        if (board[row2][col2] == NOUGHT) {
            if (score == 1) {
                score = 10;
            } else if (score == -1) {
                return 0;
            } else {
                score = 1;
            }
        } else if (board[row2][col2] == CROSS) {
            if (score == -1) {
                score = -10;
            } else if (score == 1) {
                return 0;
            } else {
                score = -1;
            }
        }

        if (board[row3][col3] == NOUGHT) {
            if (score > 0) {
                score *= 10;
            } else if (score < 0) {
                return 0;
            } else {
                score = 1;
            }
        } else if (board[row3][col3] == CROSS) {
            if (score < 0) {
                score *= 10;
            } else if (score > 1) {
                return 0;
            } else {
                score = -1;
            }
        }
        return score;
    }

    private List<int[]> generateMoves() {
        List<int[]> nextMoves = new ArrayList<>();

        int State = CheckGameState();

        if (State == 1 ||
                State == 2 ||
                State == 3)
        {
            return nextMoves;
        }

        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                if (board[row][col] == EMPTY) {
                    nextMoves.add(new int[] {row, col});
                }
            }
        }
        return nextMoves;
    }

    public void placeAMove(int x, int y, int player) {
        board[x][y] = player;
    }

    public int CheckGameState() {

        // Check Rows - Horizontal Lines
        for (int i = 0; i< ROWS; i++){
            if (board[i][0] == CROSS &&
                    board[i][1] == CROSS &&
                    board[i][2] == CROSS){
                return CROSS_WON;
            }
            if (board[i][0] == NOUGHT &&
                    board[i][1] == NOUGHT &&
                    board[i][2] == NOUGHT){
                return NOUGHT_WON;
            }
        }

        // Check Columns - Vertical Lines
        for (int i = 0; i< COLS; i++){
            if (board[0][i] == CROSS &&
                    board[1][i] == CROSS &&
                    board[2][i] == CROSS){
                return CROSS_WON;
            }
            if (board[0][i] == NOUGHT &&
                    board[1][i] == NOUGHT &&
                    board[2][i] == NOUGHT){
                return NOUGHT_WON;
            }
        }

        // Check Diagonal
        if (board[0][0] == CROSS &&
                board[1][1] == CROSS &&
                board[2][2] == CROSS){
            return CROSS_WON;
        }
        if (board[0][0] == NOUGHT &&
                board[1][1] == NOUGHT &&
                board[2][2] == NOUGHT){
            return NOUGHT_WON;
        }


        // Check Reverse-Diagonal
        if (board[0][2] == CROSS &&
                board[1][1] == CROSS &&
                board[2][0] == CROSS){
            return CROSS_WON;
        }
        if (board[0][2] == NOUGHT &&
                board[1][1] == NOUGHT &&
                board[2][0] == NOUGHT){
            return NOUGHT_WON;
        }

        // Check for Tie
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] != CROSS && board[i][j] != NOUGHT){
                    return PLAYING;
                }
            }
        }

        return DRAW;
    }
}
