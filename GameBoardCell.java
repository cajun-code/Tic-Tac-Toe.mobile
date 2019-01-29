package com.jin.mytictactoe;

import android.widget.TextView;

public class GameBoardCell {
    static final char PIECE_X = 'X';
    static final char PIECE_O = 'O';
    static final char PIECE_BLANK=' ';

    private TextView cellView;
    private char     cellPeice;


    public GameBoardCell()
    {
        cellView = null;
        cellPeice=PIECE_BLANK;
    }

    public GameBoardCell(TextView cellView, char cellPeice) {
        this.cellView = cellView;
        this.cellPeice = cellPeice;
    }

    public TextView getCellView() {
        return cellView;
    }

    public char getCellPeice() {
        return cellPeice;
    }

    public boolean isCellFree() {
        if(getCellPeice()==PIECE_BLANK)
            return true;
        else
            return false;
    }

    public void setCellView(TextView cellView) {
        this.cellView = cellView;
    }

    public void setCellPeice(char cellPeice) {
        this.cellPeice = cellPeice;
    }


    public void clearGameCell()
    {
        setCellPeice(PIECE_BLANK);
    }
}
