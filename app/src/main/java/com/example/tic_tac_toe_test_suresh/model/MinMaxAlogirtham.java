package com.example.tic_tac_toe_test_suresh.model;

import android.arch.lifecycle.MutableLiveData;

import com.example.tic_tac_toe_test_suresh.Utility;

import java.util.ArrayList;
import java.util.List;


public class MinMaxAlogirtham {

    // Name-enum to represent the seeds and cell contents
    public enum TicTacCellState {
        EMPTY(0),
        CROSS(1),
        NOUGHT(2);

        private final int value;

        TicTacCellState(int val) {
            this.value = val;
        }
    }

    // Name-enum to represent the various states of the game
    public enum TicTacGameState {
        PLAYING(0),
        CROSS_WON(1),
        NOUGHT_WON(2),
        DRAW(3);
        public final int value;

        TicTacGameState(int val) {
            this.value = val;
        }
    }

    private static final int CELL_SIZE = 3;
    private static int[][] cell = new int[CELL_SIZE][CELL_SIZE];

    private Player player, android;
    public Player currentPlayer;
    public Board[][] board;

    public MutableLiveData<Player> winner = new MutableLiveData<>();

    public MinMaxAlogirtham(String playerName, String playerValue) {

        board = new Board[CELL_SIZE][CELL_SIZE];
        player = new Player(playerName, playerValue);
        if (playerValue.equalsIgnoreCase("X")) {
            android = new Player(Utility.ANDROID, "O");
        } else {
            android = new Player(Utility.ANDROID, "X");
        }
        currentPlayer = player;
    }

    // Switching the player after a move
    public void flipPlayer() {
        currentPlayer = currentPlayer == player ? android : player;
    }

    //clearing the board
    public void clearBoard() {
        for (int i = 0; i < CELL_SIZE; ++i) {
            for (int j = 0; j < CELL_SIZE; ++j) {
                cell[i][j] = 0;
            }
        }
    }

    /**
     * Get next best move for android. Return int[2] of {row, col}
     */
    public int[] move() {
        int[] result = minmax(2, TicTacCellState.NOUGHT.value);
        return new int[]{result[1], result[2]};
    }

    //comptuter AI not to loos algoritham
    private int[] minmax(int depth, int player) {
        List<int[]> nextMoves = generateMoves();

        //NOUGHT is maximizing; CROSS is minimizing
        int bestScore = (player == TicTacCellState.NOUGHT.value) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestRow = -1;
        int bestCol = -1;

        if (nextMoves.isEmpty() || depth == 0) {
            bestScore = evaluate();
        } else {
            for (int[] move : nextMoves) {
                cell[move[0]][move[1]] = player;
                if (player == TicTacCellState.NOUGHT.value) {
                    currentScore = minmax(depth - 1, TicTacCellState.CROSS.value)[0];
                    if (currentScore > bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                } else {
                    currentScore = minmax(depth - 1, TicTacCellState.NOUGHT.value)[0];
                    if (currentScore < bestScore) {
                        bestScore = currentScore;
                        bestRow = move[0];
                        bestCol = move[1];
                    }
                }
                cell[move[0]][move[1]] = TicTacCellState.EMPTY.value;
            }
        }
        return new int[]{bestScore, bestRow, bestCol};
    }

    // Evaluate score for each of the 8 lines (3 rows, 3 columns, 2 diagonals)
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

        if (cell[row1][col1] == TicTacCellState.NOUGHT.value) {
            score = 1;
        } else if (cell[row1][col1] == TicTacCellState.CROSS.value) {
            score = -1;
        }

        if (cell[row2][col2] == TicTacCellState.NOUGHT.value) {
            if (score == 1) {
                score = 10;
            } else if (score == -1) {
                return 0;
            } else {
                score = 1;
            }
        } else if (cell[row2][col2] == TicTacCellState.CROSS.value) {
            if (score == -1) {
                score = -10;
            } else if (score == 1) {
                return 0;
            } else {
                score = -1;
            }
        }

        if (cell[row3][col3] == TicTacCellState.NOUGHT.value) {
            if (score > 0) {
                score *= 10;
            } else if (score < 0) {
                return 0;
            } else {
                score = 1;
            }
        } else if (cell[row3][col3] == TicTacCellState.CROSS.value) {
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

        int State = checkForWinner();

        // If gameover, i.e., no next move
        if (State == 1 ||
                State == 2 ||
                State == 3) {
            return nextMoves;
        }

        for (int row = 0; row < CELL_SIZE; ++row) {
            for (int col = 0; col < CELL_SIZE; ++col) {
                if (cell[row][col] == TicTacCellState.EMPTY.value) {
                    nextMoves.add(new int[]{row, col});
                }
            }
        }
        return nextMoves;
    }

    // set the given player at the given location on the game board.
    // the location must be available, or the board will not be changed.
    public void placeAMove(int x, int y, int player) {
        cell[x][y] = player;
    }

    //checking game status
    public void checkStatus() {
        if (checkForWinner() == TicTacGameState.PLAYING.value) {
            flipPlayer();

        } else if (checkForWinner() == TicTacGameState.DRAW.value) {

            winner.setValue(null);
            clearBoard();

        } else if (checkForWinner() == TicTacGameState.CROSS_WON.value) {
            winner.setValue(currentPlayer);
            clearBoard();
        } else if (checkForWinner() == TicTacGameState.NOUGHT_WON.value) {
            winner.setValue(currentPlayer);
            clearBoard();
        }
    }

    // Check for a winner and return a status value indicating who has won.
    // Return 0 if no winner or tie yet, 1 if it's a tie, 2 if X won, or 3
    // if O won.
    public int checkForWinner() {

        // Checking cells for Horizontal views
        for (int i = 0; i < CELL_SIZE; i++) {
            if (cell[i][0] == TicTacCellState.CROSS.value &&
                    cell[i][1] == TicTacCellState.CROSS.value &&
                    cell[i][2] == TicTacCellState.CROSS.value) {
                return TicTacGameState.CROSS_WON.value;
            }
            if (cell[i][0] == TicTacCellState.NOUGHT.value &&
                    cell[i][1] == TicTacCellState.NOUGHT.value &&
                    cell[i][2] == TicTacCellState.NOUGHT.value) {
                return TicTacGameState.NOUGHT_WON.value;
            }
        }

        // Checking cells for Vertical views
        for (int i = 0; i < CELL_SIZE; i++) {
            if (cell[0][i] == TicTacCellState.CROSS.value &&
                    cell[1][i] == TicTacCellState.CROSS.value &&
                    cell[2][i] == TicTacCellState.CROSS.value) {
                return TicTacGameState.CROSS_WON.value;
            }
            if (cell[0][i] == TicTacCellState.NOUGHT.value &&
                    cell[1][i] == TicTacCellState.NOUGHT.value &&
                    cell[2][i] == TicTacCellState.NOUGHT.value) {
                return TicTacGameState.NOUGHT_WON.value;
            }
        }

        // Checking cells for Diagonal
        if (cell[0][0] == TicTacCellState.CROSS.value &&
                cell[1][1] == TicTacCellState.CROSS.value &&
                cell[2][2] == TicTacCellState.CROSS.value) {
            return TicTacGameState.CROSS_WON.value;
        }
        if (cell[0][0] == TicTacCellState.NOUGHT.value &&
                cell[1][1] == TicTacCellState.NOUGHT.value &&
                cell[2][2] == TicTacCellState.NOUGHT.value) {
            return TicTacGameState.NOUGHT_WON.value;
        }


        // Checking cells for Diagonal
        if (cell[0][2] == TicTacCellState.CROSS.value &&
                cell[1][1] == TicTacCellState.CROSS.value &&
                cell[2][0] == TicTacCellState.CROSS.value) {
            return TicTacGameState.CROSS_WON.value;
        }
        if (cell[0][2] == TicTacCellState.NOUGHT.value &&
                cell[1][1] == TicTacCellState.NOUGHT.value &&
                cell[2][0] == TicTacCellState.NOUGHT.value) {
            return TicTacGameState.NOUGHT_WON.value;
        }

        // Checking cells for draw
        for (int i = 0; i < CELL_SIZE; i++) {
            for (int j = 0; j < CELL_SIZE; j++) {
                if (cell[i][j] != TicTacCellState.CROSS.value && cell[i][j] != TicTacCellState.NOUGHT.value) {
                    return TicTacGameState.PLAYING.value;
                }
            }
        }
        return TicTacGameState.DRAW.value;
    }
}

