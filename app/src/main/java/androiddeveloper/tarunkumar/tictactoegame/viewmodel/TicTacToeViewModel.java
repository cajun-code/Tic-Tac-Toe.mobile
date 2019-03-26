package androiddeveloper.tarunkumar.tictactoegame.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableArrayMap;
import androiddeveloper.tarunkumar.tictactoegame.model.Cell;
import androiddeveloper.tarunkumar.tictactoegame.model.Player;
import androiddeveloper.tarunkumar.tictactoegame.model.TicTacToeMinMax;
import androiddeveloper.tarunkumar.tictactoegame.utilities.StringUtility;
import static androiddeveloper.tarunkumar.tictactoegame.utilities.StringUtility.stringFromNumbers;

public class TicTacToeViewModel extends ViewModel {

    public ObservableArrayMap<String, String> cells;
    private TicTacToeMinMax ticTacToeMinMax;

    private int PLAYER = 1;
    private int COMPUTER = 2;

    public void init(String player, String playerValue) {
        ticTacToeMinMax = new TicTacToeMinMax(player, playerValue);
        cells = new ObservableArrayMap<>();
    }

    public void onClickedCellAt(int row, int column) {
        int winner;
        if (ticTacToeMinMax.cells[row][column] == null) {
            ticTacToeMinMax.cells[row][column] = new Cell(ticTacToeMinMax.currentPlayer);
            cells.put(stringFromNumbers(row, column), ticTacToeMinMax.currentPlayer.value);

            setMove(row, column);
            winner = ticTacToeMinMax.CheckGameState();

            if (winner == ticTacToeMinMax.PLAYING) {
                int[] result = ticTacToeMinMax.move();
                setMove(result[0], result[1]);
            }
        }
    }

    public LiveData<Player> getWinner() {
        return ticTacToeMinMax.winner;
    }

    private void setMove(int x, int y) {

        if(!ticTacToeMinMax.currentPlayer.name.equals(StringUtility.COMPUTER_NAME))
        {
            ticTacToeMinMax.placeAMove(x, y, PLAYER );
        }else{
            ticTacToeMinMax.placeAMove(x, y, COMPUTER );
        }

        cells.put(stringFromNumbers(x, y), ticTacToeMinMax.currentPlayer.value);

        if(ticTacToeMinMax.CheckGameState() == ticTacToeMinMax.PLAYING){
            ticTacToeMinMax.switchPlayer();
        }else if (ticTacToeMinMax.CheckGameState() == ticTacToeMinMax.DRAW) {

            ticTacToeMinMax.winner.setValue(null);
            ticTacToeMinMax.resetBoard();

        } else if (ticTacToeMinMax.CheckGameState() == ticTacToeMinMax.CROSS_WON) {
            ticTacToeMinMax.winner.setValue(ticTacToeMinMax.currentPlayer);
            ticTacToeMinMax.resetBoard();
        } else if (ticTacToeMinMax.CheckGameState() == ticTacToeMinMax.NOUGHT_WON) {
            ticTacToeMinMax.winner.setValue(ticTacToeMinMax.currentPlayer);
            ticTacToeMinMax.resetBoard();
        }
    }
}
