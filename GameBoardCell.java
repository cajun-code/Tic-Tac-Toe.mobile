package com.jin.mytictactoe;

import android.widget.TextView;

public class GameBoardCell {
    TextView cellView;
    char     cellPeice;
    boolean  cellIsFree;

    public GameBoardCell()
    {

    }

    public GameBoardCell(TextView cellView, char cellPeice, boolean cellIsFree) {
        this.cellView = cellView;
        this.cellPeice = cellPeice;
        this.cellIsFree = cellIsFree;
    }

    public TextView getCellView() {
        return cellView;
    }

    public char getCellPeice() {
        return cellPeice;
    }

    public boolean isCellFree() {
        return cellIsFree;
    }

    public void setCellView(TextView cellView) {
        this.cellView = cellView;
    }

    public void setCellPeice(char cellPeice) {
        this.cellPeice = cellPeice;
    }

    public void setCellFree(boolean cellIsFree) {
        this.cellIsFree = cellIsFree;
    }
    public void clearGameCell()
    {
        setCellFree(true);
        setCellPeice('X');
    }
}
