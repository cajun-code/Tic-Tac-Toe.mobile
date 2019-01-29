package com.jin.mytictactoe;

import android.widget.TextView;

public class GameBoardCell {
    static final String PIECE_X = "X";
    static final String PIECE_O = "O";
    static final String PIECE_BLANK=" ";

    private TextView cellView;
    private String     cellPiece;


    public GameBoardCell()
    {
        cellView = null;
        cellPiece=PIECE_BLANK;
    }

    public GameBoardCell(TextView cellView, String cellPiece) {
        this.cellView = cellView;
        this.cellPiece = cellPiece;
    }



    public TextView getCellView() {
        return cellView;
    }

    public String getCellPiece() {
        return cellPiece;
    }

    public boolean isCellFree() {
        if(getCellPiece()==PIECE_BLANK)
            return true;
        else
            return false;
    }

    public void setCellView(TextView cellView) {
        this.cellView = cellView;
    }

    public void setCellPiece(String cellPiece) {
        this.cellPiece = cellPiece;
    }


    public void clearGameCell()
    {
        setCellPiece(PIECE_BLANK);
        getCellView().setText(" ");
    }
}
