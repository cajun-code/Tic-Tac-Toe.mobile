package androiddeveloper.tarunkumar.tictactoegame.view;


import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import androiddeveloper.tarunkumar.tictactoegame.R;
import androiddeveloper.tarunkumar.tictactoegame.databinding.ActivityMainBinding;
import androiddeveloper.tarunkumar.tictactoegame.model.Player;
import androiddeveloper.tarunkumar.tictactoegame.viewmodel.TicTacToeViewModel;
import static androiddeveloper.tarunkumar.tictactoegame.utilities.StringUtility.isNullOrEmpty;

public class TicTacToeActivity extends AppCompatActivity {

    private static final String START_GAME_DIALOG_TAG = "start_game_dialog_tag";
    private static final String GAME_END_DIALOG_TAG = "game_end_dialog_tag";
    private static final String NO_WINNER = "No one";
    private TicTacToeViewModel ticTacToeViewModel;

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

    public void onPlayersSet(String player1, String playerValue) {
        initDataBinding(player1, playerValue);
    }

    private void initDataBinding(String player1, String playerValue) {
        ActivityMainBinding activityGameBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);
        ticTacToeViewModel = ViewModelProviders.of(this).get(TicTacToeViewModel.class);
        ticTacToeViewModel.init(player1, playerValue);
        activityGameBinding.setViewModel(ticTacToeViewModel);
        setUpOnGameEndListener();
    }

    private void setUpOnGameEndListener() {
        ticTacToeViewModel.getWinner().observe(this, this::onGameWinnerChanged);
    }

    public void onGameWinnerChanged(Player winner) {
        String winnerName = winner == null || isNullOrEmpty(winner.name) ? NO_WINNER : winner.name;
        EndGameDialog dialog = EndGameDialog.newInstance(this, winnerName);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), GAME_END_DIALOG_TAG);
    }
}
