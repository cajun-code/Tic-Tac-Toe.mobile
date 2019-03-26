package androiddeveloper.tarunkumar.tictactoegame.view;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import androiddeveloper.tarunkumar.tictactoegame.R;

public class TicTacToeActivity extends AppCompatActivity {

    private static final String START_GAME_DIALOG_TAG = "start_game_dialog_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Show Start Game Dialog
        showStartGameDialog();
    }

    public void showStartGameDialog() {
        StartGameDialog dialog = StartGameDialog.newInstance(this);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), START_GAME_DIALOG_TAG);
    }
}
