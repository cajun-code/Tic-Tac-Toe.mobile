package com.example.tic_tac_toe_test_suresh.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.tic_tac_toe_test_suresh.R;
import com.example.tic_tac_toe_test_suresh.databinding.ActivityMainBinding;
import com.example.tic_tac_toe_test_suresh.model.Player;
import com.example.tic_tac_toe_test_suresh.viewModel.MainViewModel;

import static com.example.tic_tac_toe_test_suresh.Utility.isNullOrEmpty;

public class GameActivity extends AppCompatActivity {

    private static final String SELECT_PLAYER_DIALOG_TAG = "select_player_dialog_tag";
    private static final String RESULT_DIALOG_TAG = "result_dialog_tag";
    private static final String NO_WINNER = "No one";
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showSelectPlayerDialog();

    }

    public void showSelectPlayerDialog() {
        SelectPlayerDialog dialog = SelectPlayerDialog.newInstance(this);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), SELECT_PLAYER_DIALOG_TAG);
    }

    public void onPlayersSelected(String playerName, String playerValue) {
        initDataBinding(playerName, playerValue);
    }

    //binding the views
    private void initDataBinding(String playerName, String playerValue) {
        ActivityMainBinding activityGameBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.init(playerName, playerValue);
        activityGameBinding.setViewModel(mainViewModel);
        setUpOnGameEndListener();
    }

    private void setUpOnGameEndListener() {
        mainViewModel.getWinner().observe(this, this::onGameWinnerChanged);
    }

    public void onGameWinnerChanged(Player winner) {
        String winnerName = winner == null || isNullOrEmpty(winner.name) ? NO_WINNER : winner.name;
        ResultDialog dialog = ResultDialog.newInstance(this, winnerName);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), RESULT_DIALOG_TAG);
    }
}
