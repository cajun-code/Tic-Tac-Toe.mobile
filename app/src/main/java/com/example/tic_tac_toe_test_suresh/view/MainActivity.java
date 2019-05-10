package com.example.tic_tac_toe_test_suresh.view;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.tic_tac_toe_test_suresh.R;
import com.example.tic_tac_toe_test_suresh.databinding.ActivityMainBinding;
import com.example.tic_tac_toe_test_suresh.viewModel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String START_GAME_DIALOG_TAG = "start_game_dialog_tag";
    private static final String GAME_END_DIALOG_TAG = "game_end_dialog_tag";
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
        dialog.show(getSupportFragmentManager(), START_GAME_DIALOG_TAG);
    }

    private void initDataBinding() {
        ActivityMainBinding activityGameBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        activityGameBinding.setViewModel(mainViewModel);
    }

}
