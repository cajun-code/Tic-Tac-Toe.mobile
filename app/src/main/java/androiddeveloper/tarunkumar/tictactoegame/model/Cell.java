package androiddeveloper.tarunkumar.tictactoegame.model;

import androiddeveloper.tarunkumar.tictactoegame.utilities.StringUtility;

public class Cell {

    private Player player;

    public Cell(Player player) {
        this.player = player;
    }

    public boolean isEmpty() {
        return player == null || StringUtility.isNullOrEmpty(player.value);
    }
}
