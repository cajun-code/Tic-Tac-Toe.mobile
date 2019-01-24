package com.app.tictactoecodechallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements MainPresenter.View {
    private Button buttonTiles [][] = new Button[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_new_game:
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean checkBoardStatus() {
        return false;
    }

    @Override
    public void statusWinner() {

    }

    @Override
    public void draw() {

    }
}
