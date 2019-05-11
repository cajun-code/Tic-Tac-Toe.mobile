package com.example.tic_tac_toe_test_suresh.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayMap;

import com.example.tic_tac_toe_test_suresh.Utility;
import com.example.tic_tac_toe_test_suresh.model.Board;
import com.example.tic_tac_toe_test_suresh.model.MinMaxAlogirtham;
import com.example.tic_tac_toe_test_suresh.model.Player;

import static com.example.tic_tac_toe_test_suresh.Utility.stringFromNumbers;

public class MainViewModel extends ViewModel {

    //data binding observer
    public ObservableArrayMap<String, String> cells;

    private MinMaxAlogirtham minMaxAlogirtham;
    private int PLAYER = 1;
    private int ANDROID = 2;

    public void init(String playerName, String playerValue) {
        minMaxAlogirtham = new MinMaxAlogirtham(playerName, playerValue);
        cells = new ObservableArrayMap<>();
    }

    //setting the data to button cells
    public void onButtonClickedAt(int row, int column) {
        int winner;
        if (minMaxAlogirtham.board[row][column] == null) {
            minMaxAlogirtham.board[row][column] = new Board(minMaxAlogirtham.currentPlayer);

            cells.put(stringFromNumbers(row, column), minMaxAlogirtham.currentPlayer.value);

            setMove(row, column);
            winner = minMaxAlogirtham.checkForWinner();

            if (winner == MinMaxAlogirtham.TicTacGameState.PLAYING.value) {
                int[] result = minMaxAlogirtham.move();
                setMove(result[0], result[1]);
            }
        }
    }

    //calling winner
    public LiveData<Player> getWinner() {
        return minMaxAlogirtham.winner;
    }

    private void setMove(int x, int y) {
        if (!minMaxAlogirtham.currentPlayer.name.equals(Utility.ANDROID)) {
            minMaxAlogirtham.placeAMove(x, y, PLAYER);
        } else {
            minMaxAlogirtham.placeAMove(x, y, ANDROID);
        }
        cells.put(stringFromNumbers(x, y), minMaxAlogirtham.currentPlayer.value);
        minMaxAlogirtham.checkStatus();
    }
}

