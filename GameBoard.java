package com.jin.mytictactoe;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class GameBoard {
    private GameBoardCell[][] gameBoardCells;
    private String  userPiece;
    private String AIpiece;
    private boolean gameIsActive;
   // private Move bestMove;

    public String getAIpiece() {
        return AIpiece;
    }

    public void setAIpiece(String AIpiece) {
        this.AIpiece = AIpiece;
    }

    public GameBoard()
    {
        setUserPiece(GameBoardCell.PIECE_X);
        setAIpiece(GameBoardCell.PIECE_O);
        gameIsActive = false;
//        gameBoardCells{new GameBoardCell(), new GameBoardCell(), new GameBoardCell(),
//                          new GameBoardCell(), new GameBoardCell(), new GameBoardCell(),
//                          new GameBoardCell(), new GameBoardCell(), new GameBoardCell()};
//        for(GameBoardCell[] cells: gameBoardCells)
//        {
//            for(GameBoardCell cell: cells)
//            {
//                cell = new GameBoardCell();
//            }
//        }
//       gameBoardCells[0] = {new GameBoardCell(), new GameBoardCell(), new GameBoardCell()};
        gameBoardCells = new GameBoardCell[3][];
gameBoardCells[0] = new GameBoardCell[3];
       gameBoardCells[0][0] =new GameBoardCell();
       gameBoardCells[0][1] = new GameBoardCell();
       gameBoardCells[0][2] = new GameBoardCell();

       gameBoardCells[1] = new GameBoardCell[3];
        gameBoardCells[1][0] =new GameBoardCell();
        gameBoardCells[1][1] = new GameBoardCell();
        gameBoardCells[1][2] = new GameBoardCell();

       gameBoardCells[2] = new GameBoardCell[3];
        gameBoardCells[2][0] =new GameBoardCell();
        gameBoardCells[2][1] = new GameBoardCell();
        gameBoardCells[2][2] = new GameBoardCell();

      //  bestMove= new Move();
    }
   public GameBoardCell[][] getGameBoardCells() {
        return gameBoardCells;
   }

    public String getUserPiece() {
        return userPiece;
    }

    public boolean isGameActive() {
        return gameIsActive;
    }

//    public void setGameBoardCell(GameBoardCell[][] gameBoard) {
//        this.gameBoardC = gameBoard;
//    }

    public void setUserPiece(String userPiece) {
        this.userPiece = userPiece;
    }

    public void setGameActive(boolean gameIsActive) {
        this.gameIsActive = gameIsActive;
    }

    public void resetGameBoard()
    {
        setUserPiece(GameBoardCell.PIECE_X);
        setGameActive(false);
        for(GameBoardCell[] cells: gameBoardCells) {
            for (GameBoardCell cell : cells) {
                if (cell != null) {
                    cell.clearGameCell();
                }
            }
        }
    }

    // This function returns true if there are moves
// remaining on the board. It returns false if
// there are no moves left to play.
    boolean isMovesLeft()
    {
        for (GameBoardCell[] cells: gameBoardCells)
        {
            for (GameBoardCell cell: cells)
            {
                if(cell.isCellFree())
                {
                    return true;
                }
            }
        }
        return false;
    }

    int evaluate()
    {
        // Checking for Rows for X or O victory.
        for (int row = 0; row<3; row++)
        {
            if (gameBoardCells[row][0].getCellPiece()==gameBoardCells[row][1].getCellPiece() &&
                    gameBoardCells[row][1].getCellPiece()==gameBoardCells[row][2].getCellPiece())
            {
                if (gameBoardCells[row][0].getCellPiece()==AIpiece)
                    return +10;
                else if (gameBoardCells[row][0].getCellPiece()==userPiece)
                    return -10;
            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col<3; col++)
        {
            if (gameBoardCells[0][col].getCellPiece()==gameBoardCells[1][col].getCellPiece() &&
                    gameBoardCells[1][col].getCellPiece()==gameBoardCells[2][col].getCellPiece())
            {
                if (gameBoardCells[0][col].getCellPiece()==AIpiece)
                    return +10;

                else if (gameBoardCells[0][col].getCellPiece()==userPiece)
                    return -10;
            }
        }

        // Checking for Diagonals for X or O victory.
        if (gameBoardCells[0][0].getCellPiece()==gameBoardCells[1][1].getCellPiece()
                && gameBoardCells[1][1].getCellPiece()==gameBoardCells[2][2].getCellPiece())
        {
            if (gameBoardCells[0][0].getCellPiece()==AIpiece)
                return +10;
            else if (gameBoardCells[0][0].getCellPiece()==userPiece)
                return -10;
        }

        if (gameBoardCells[0][2].getCellPiece()==gameBoardCells[1][1].getCellPiece()
                && gameBoardCells[1][1].getCellPiece()==gameBoardCells[2][0].getCellPiece())
        {
            if (gameBoardCells[0][2].getCellPiece()==AIpiece)
                return +10;
            else if (gameBoardCells[0][2].getCellPiece()==userPiece)
                return -10;
        }

        // Else if none of them have won then return 0
        return 0;
    }

    // This is the minimax function. It considers all
// the possible ways the game can go and returns
// the value of the board
    int minimax(int depth, boolean isMax)
    {
        int score = evaluate();

        // If Maximizer has won the game return his/her
        // evaluated score
        if (score == 10)
            return score;

        // If Minimizer has won the game return his/her
        // evaluated score
        if (score == -10)
            return score;

        // If there are no more moves and no winner then
        // it is a tie
        if (isMovesLeft()==false)
            return 0;

        // If this maximizer's move
        if (isMax)
        {
            int best = -1000;

            // Traverse all cells
            for (int i = 0; i<3; i++)
            {
                for (int j = 0; j<3; j++)
                {
                    // Check if cell is empty
                    if (gameBoardCells[i][j].isCellFree())
                    {
                        // Make the move
                        gameBoardCells[i][j].setCellPiece(AIpiece);

                        // Call minimax recursively and choose
                        // the maximum value
                        best = max( best,
                                minimax(depth+1, !isMax) );

                        // Undo the move
                        gameBoardCells[i][j].setCellPiece(GameBoardCell.PIECE_BLANK);
                    }
                }
            }
            return best;
        }

        // If this minimizer's move
        else
        {
            int best = 1000;

            // Traverse all cells
            for (int i = 0; i<3; i++)
            {
                for (int j = 0; j<3; j++)
                {
                    // Check if cell is empty
                    if (gameBoardCells[i][j].isCellFree())
                    {
                        // Make the move
                        gameBoardCells[i][j].setCellPiece(userPiece);

                        // Call minimax recursively and choose
                        // the minimum value
                        best = min(best,
                                minimax(depth+1, !isMax));

                        // Undo the move
                        gameBoardCells[i][j].setCellPiece(GameBoardCell.PIECE_BLANK);
                    }
                }
            }
            return best;
        }
    }

    // This will return the best possible move for the player
    Move findBestMove()
    {
        int bestVal = -1000;
        Move bestMove = new Move();
        bestMove.setX(-1);
        bestMove.setY(-1);

        // Traverse all cells, evaluate minimax function for
        // all empty cells. And return the cell with optimal
        // value.
        for (int i = 0; i<3; i++)
        {
            for (int j = 0; j<3; j++)
            {
                // Check if cell is empty
                if (gameBoardCells[i][j].isCellFree())
                {
                    // Make the move
                    gameBoardCells[i][j].setCellPiece(AIpiece);

                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax( 0, false);

                    // Undo the move
                    gameBoardCells[i][j].setCellPiece(GameBoardCell.PIECE_BLANK);

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal > bestVal)
                    {
                        bestMove.setX(i);
                        bestMove.setY(j);
                        bestVal = moveVal;
                    }
                }
            }
        }

        System.out.println("The value of the best Move is : "+
                bestVal);

        return bestMove;
    }

}
