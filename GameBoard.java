package com.jin.mytictactoe;

public class GameBoard {
    GameBoardCell[][] gameBoardCells;
    char  userPiece;
    boolean gameIsActive;

    public GameBoard()
    {
        setUserPiece('X');
        gameIsActive = false;
        gameBoardCells = new GameBoardCell[3][];
    }
   public GameBoardCell[][] getGameBoardCells() {
        return gameBoardCells;
   }

    public char getUserPiece() {
        return userPiece;
    }

    public boolean isGameActive() {
        return gameIsActive;
    }

//    public void setGameBoardCell(GameBoardCell[][] gameBoard) {
//        this.gameBoardC = gameBoard;
//    }

    public void setUserPiece(char userPiece) {
        this.userPiece = userPiece;
    }

    public void setGameActive(boolean gameIsActive) {
        this.gameIsActive = gameIsActive;
    }

    public void resetGameBoard()
    {
        setUserPiece('X');
        setGameActive(false);
        for(GameBoardCell[] cells: gameBoardCells) {
            for (GameBoardCell cell : cells) {
                if (cell != null) {
                    cell.clearGameCell();
                }
            }
        }
    }
}
