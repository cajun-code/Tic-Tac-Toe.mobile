package com.example.tictactoe;

import android.support.annotation.IntDef;
import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.reactivex.Completable;
import io.reactivex.functions.Action;

public final class TicTacToeGame {

    public interface OnGameOverListener {
        void onGameOver(@GameStateTracker int stateTracker, int[] winningIndices);
    }

    public static final int CONTINUE = 0;
    public static final int DRAW = 1;
    public static final int PLAYER_ONE_WINS = 2;
    public static final int PLAYER_TWO_WINS = 3;

    @IntDef({CONTINUE, DRAW, PLAYER_ONE_WINS, PLAYER_TWO_WINS})
    public @interface GameStateTracker {
    }

    public static final char NONE = '-';
    public static  char PLAYER_ONE;
    public static  char PLAYER_TWO;

    private static final int BOARD_GRID_SIZE = 9;

    private char[] gridChars = {NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE, NONE};
    private int[] intsWinIndicies = {-1, -1, -1};
    private char currentPlayer;
    private boolean isGameOver = false;
    private int computerMove;
    @GameStateTracker
    private int gameState = CONTINUE;
    private OnGameOverListener onGameOverListener;
    private final Random random = new Random();

    public static final ArrayMap<String, Integer> SAVE_SCORE = new ArrayMap<>();

    // Mapping of gridChars indices to their x,y coordinates
    private static final int[][] COORDINATES_MAPPING = {
            new int[]{0, 0},
            new int[]{1, 0},
            new int[]{2, 0},
            new int[]{0, 1},
            new int[]{1, 1},
            new int[]{2, 1},
            new int[]{0, 2},
            new int[]{1, 2},
            new int[]{2, 2}
    };

    // Mapping of coordinates to indices
    private static final int[][] INDEX_MAPPING = {
            new int[]{0, 1, 2},
            new int[]{3, 4, 5},
            new int[]{6, 7, 8}
    };

    private static final boolean[] DIAGONALS = {
            true, false, true,
            false, true, false,
            true, false, true
    };

    public TicTacToeGame(char playerOne,char playerTwo ) {
        this.PLAYER_ONE=playerOne;
        this.PLAYER_TWO = playerTwo;
        currentPlayer = PLAYER_ONE;
    }

    public char currentPlayer() {
        return this.currentPlayer;
    }

    public void setOnGameOverListener(OnGameOverListener listener) {
        this.onGameOverListener = listener;
    }

    public void makeMove(int position) {
        gridChars[position] = currentPlayer;

        currentPlayer = currentPlayer == PLAYER_ONE ? PLAYER_TWO : PLAYER_ONE;

        if (checkForWinner(position) != CONTINUE) {
            endGame();
        }
    }

    private void makeDummyMove(int position, char player) {
        gridChars[position] = player;
    }

    @GameStateTracker
    private int checkForWinner(int newIndex) {
        boolean winnerFound = true;
        int[] coordinates = COORDINATES_MAPPING[newIndex];
        char player = gridChars[newIndex];
        int x = coordinates[0];
        int y = coordinates[1];

        // Check columns
        for (int i = 0; i < 3; ++i) {
            int index = INDEX_MAPPING[i][x];
            if (gridChars[index] != player) {
                winnerFound = false;
                break;
            }
            intsWinIndicies[i] = index;
        }

        // Check rows
        if (!winnerFound) {
            winnerFound = true;
            for (int i = 0; i < 3; ++i) {
                int index = INDEX_MAPPING[y][i];
                if (gridChars[index] != player) {
                    winnerFound = false;
                    break;
                }
                intsWinIndicies[i] = index;
            }
        }

        // Check diagonals
        if (!winnerFound && DIAGONALS[newIndex]) {
            winnerFound = true;
            boolean checkBoth = newIndex == 4;
            if (checkBoth || newIndex == 0 || newIndex == 8) {
                // Left to right
                for (int i = 0; i < 3; ++i) {
                    int index = INDEX_MAPPING[i][i];
                    if (gridChars[index] != player) {
                        winnerFound = false;
                        break;
                    }
                    intsWinIndicies[i] = index;
                }
            } else {
                winnerFound = false;
            }
            if (!winnerFound && (checkBoth || newIndex == 2 || newIndex == 6)) {
                winnerFound = true;
                // Right to left
                for (int i = 2; i < 7; i += 2) {
                    if (gridChars[i] != player) {
                        winnerFound = false;
                        break;
                    }
                    intsWinIndicies[(i / 2) - 1] = i;
                }
            }
        }


        @GameStateTracker int result = DRAW;
        if (!winnerFound) {
            // No winner yet, continue if there are any open spaces left
            // Clear winning indices
            intsWinIndicies[0] = -1;
            intsWinIndicies[1] = -1;
            intsWinIndicies[2] = -1;
            if (!getAvailableTiles().isEmpty()) {
                result = CONTINUE;
            }
        } else {
            result = player == PLAYER_ONE ? PLAYER_ONE_WINS : PLAYER_TWO_WINS;
        }

        gameState = result;
        return gameState;
    }

    public void endGame() {
        isGameOver = true;
        if (onGameOverListener != null) {
            onGameOverListener.onGameOver(gameState, intsWinIndicies[0] == -1 ? null : intsWinIndicies);
        }
    }

    public Completable getCpuMove() {
        return Completable.complete().
                doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        if (getAvailableTiles().size() == gridChars.length) {
                            // miniMax will spend a lot of time calculating every permutation of this, but always ends on 0. Let's spice it up
                            computerMove = new Random().nextInt(gridChars.length);
                        } else {
                            miniMax(0, PLAYER_TWO, -1);
                        }
                    }
                });
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void restart() {
        isGameOver = false;
        for (int i = 0; i < BOARD_GRID_SIZE; ++i) {
            gridChars[i] = NONE;
        }
//        boolean playerOneGoesFirst =false;
        currentPlayer = PLAYER_ONE;
    }

    @Override
    public String toString() {
        return "TicTacToeGame{currentPlayer="
                + currentPlayer
                + ", current gridChars="
                + pprintGrid()
                + "\n}";
    }

    private String pprintGrid() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 9; ++i) {
            if (i % 3 == 0) {
                builder.append(" | ");
            }
            builder.append(gridChars[i]);
        }
        return builder.toString();
    }

    public char[] getGridState() {
        return this.gridChars;
    }

    public void setGridState(char[] gridState) {
        this.gridChars = gridState;
    }

    public int[] getIntsWinIndicies() {
        return this.intsWinIndicies;
    }

    public void setIntsWinIndicies(int[] indices) {
        this.intsWinIndicies = indices;
    }

    @GameStateTracker
    public int getGameStateTracker() {
        return gameState;
    }

    public void setGameStateTracker(int gameState) {
        this.gameState = gameState;
    }

    public int getComputerMove() {
        return computerMove;
    }

    public void setIsOver(boolean isOver) {
        this.isGameOver = isOver;
    }

    public void setCurrentPlayer(char currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    private List<Integer> getAvailableTiles() {
        List<Integer> availableTiles = new ArrayList<>();
        for (int i = 0; i < gridChars.length; i++) {
            char c = gridChars[i];
            if (c == NONE) {
                availableTiles.add(i);
            }
        }

        return availableTiles;
    }

    /**
     * Implementation of the miniMax algorithm for this game implementation
     *
     * @param depth  current depth of the miniMax recursion
     * @param player player to check
     * @return the maximized score if this is the computer (PLAYER_TWO)
     * or minimized score if human (PLAYER_ONE)
     */
    private int miniMax(int depth, char player, int newIndex) {
        String stateKey = Arrays.toString(gridChars) + player + depth;

        if (SAVE_SCORE.containsKey(stateKey)) {
            return SAVE_SCORE.get(stateKey);
        }
        List<Integer> pointsAvailable = getAvailableTiles();
        if (depth != 0) {
            @GameStateTracker int currentResult = checkForWinner(newIndex);
            if (currentResult == PLAYER_TWO_WINS) {
                return 10 - depth;
            } else if (currentResult == PLAYER_ONE_WINS) {
                return depth - 10;
            } else if (pointsAvailable.isEmpty()) {
                return 0;   // Ties help no one
            }
        }

        ++depth;
        boolean isPlayerTwo = player == PLAYER_TWO;
        char otherPlayer = isPlayerTwo ? PLAYER_ONE : PLAYER_TWO;
        int runningScore = isPlayerTwo ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int chosenIndex = 0;

        for (int i = 0; i < pointsAvailable.size(); ++i) {
            int index = pointsAvailable.get(i);
            makeDummyMove(index, player);
            int score = miniMax(depth, otherPlayer, index);
            boolean bool = random.nextBoolean();
            if ((isPlayerTwo && score > runningScore)
                    || (!isPlayerTwo && score < runningScore)
                    || (score == runningScore && bool)) {   // Equally good/bad options, so randomly choose one for added flavor
                runningScore = score;
                chosenIndex = index;
            }
            gridChars[index] = NONE; // Clean up when we're done
        }

        computerMove = chosenIndex;
        SAVE_SCORE.put(stateKey, runningScore);
        return runningScore;
    }
}
