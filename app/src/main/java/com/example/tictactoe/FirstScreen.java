package com.example.tictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.buttonStart)
    void onButtonClick(View view) {
        Log.e("TESt", "BUttonClick");
        onCreateDialog();
    }

    public void onCreateDialog() {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Choose X or O");
        alertDialog.setMessage("Please choose x or o");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "O",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(FirstScreen.this, MainActivity.class).putExtra(MainActivity.GET_INTENT_PLAYER_CHOICE, 'O'));
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "X",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(FirstScreen.this, MainActivity.class).putExtra(MainActivity.GET_INTENT_PLAYER_CHOICE, 'X'));
                    }
                });
        alertDialog.show();
    }
}
