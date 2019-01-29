package com.jin.mytictactoe;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class GameBoard {
    private GameBoardCell[][] gameBoardCells;
    private char  userPiece;
    private char AIpiece;
    private boolean gameIsActive;
   // private Move bestMove;

    public char getAIpiece() {
        return AIpiece;
    }

    public void setAIpiece(char AIpiece) {
        this.AIpiece = AIpiece;
    }

    public GameBoard()
    {
        setUserPiece(GameBoardCell.PIECE_X);
        setAIpiece(GameBoardCell.PIECE_O);
        gameIsActive = false;
        gameBoardCells = new GameBoardCell[3][];
        gameBoardCells[0] = new GameBoardCell[3];
        gameBoardCells[1] = new GameBoardCell[3];
        gameBoardCells[2] = new GameBoardCell[3];
      //  bestMove= new Move();
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
            if (gameBoardCells[row][0].getCellPeice()==gameBoardCells[row][1].getCellPeice() &&
                    gameBoardCells[row][1].getCellPeice()==gameBoardCells[row][2].getCellPeice())
            {
                if (gameBoardCells[row][0].getCellPeice()==AIpiece)
                    return +10;
                else if (gameBoardCells[row][0].getCellPeice()==userPiece)
                    return -10;
            }
        }

        // Checking for Columns for X or O victory.
        for (int col = 0; col<3; col++)
        {
            if (gameBoardCells[0][col].getCellPeice()==gameBoardCells[1][col].getCellPeice() &&
                    gameBoardCells[1][col].getCellPeice()==gameBoardCells[2][col].getCellPeice())
            {
                if (gameBoardCells[0][col].getCellPeice()==AIpiece)
                    return +10;

                else if (gameBoardCells[0][col].getCellPeice()==userPiece)
                    return -10;
            }
        }

        // Checking for Diagonals for X or O victory.
        if (gameBoardCells[0][0].getCellPeice()==gameBoardCells[1][1].getCellPeice()
                && gameBoardCells[1][1].getCellPeice()==gameBoardCells[2][2].getCellPeice())
        {
            if (gameBoardCells[0][0].getCellPeice()==AIpiece)
                return +10;
            else if (gameBoardCells[0][0].getCellPeice()==userPiece)
                return -10;
        }

        if (gameBoardCells[0][2].getCellPeice()==gameBoardCells[1][1].getCellPeice()
                && gameBoardCells[1][1].getCellPeice()==gameBoardCells[2][0].getCellPeice())
        {
            if (gameBoardCells[0][2].getCellPeice()==AIpiece)
                return +10;
            else if (gameBoardCells[0][2].getCellPeice()==userPiece)
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
                        gameBoardCells[i][j].setCellPeice(AIpiece);

                        // Call minimax recursively and choose
                        // the maximum value
                        best = max( best,
                                minimax(depth+1, !isMax) );

                        // Undo the move
                        gameBoardCells[i][j].setCellPeice(GameBoardCell.PIECE_BLANK);
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
                        gameBoardCells[i][j].setCellPeice(userPiece);

                        // Call minimax recursively and choose
                        // the minimum value
                        best = min(best,
                                minimax(depth+1, !isMax));

                        // Undo the move
                        gameBoardCells[i][j].setCellPeice(GameBoardCell.PIECE_BLANK);
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
                    gameBoardCells[i][j].setCellPeice(AIpiece);

                    // compute evaluation function for this
                    // move.
                    int moveVal = minimax( 0, false);

                    // Undo the move
                    gameBoardCells[i][j].setCellPeice(GameBoardCell.PIECE_BLANK);

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
