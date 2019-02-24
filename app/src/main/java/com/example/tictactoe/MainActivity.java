package com.example.tictactoe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.tictactoe.TicTacToeGame.GameStateTracker;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String EXTRA_GRID_STATE = "gridState";
    private static final String EXTRA_IS_GAME_OVER = "isGameOver";
    private static final String EXTRA_CURRENT_PLAYER = "currentPlayer";
    private static final String EXTRA_GAME_STATE = "gameState";
    private static final String EXTRA_WINNING_INDICES = "winningIndices";
    private static final String PREF_HUMAN_WINS = "humanWins";
    private static final String PREF_COMPUTER_WINS = "computer_wins";
    private static final String PREF_TIES = "ties";
    public static final String GET_INTENT_PLAYER_CHOICE = "player";

    @BindView(R.id.ttt_view)
    protected TicTacToeView ticTacToeView;

    TicTacToeGame game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        char playerChoice = getIntent().getCharExtra(GET_INTENT_PLAYER_CHOICE, '_');
        if (playerChoice == 'X')
            game = new TicTacToeGame('X', 'O');
        else
            game = new TicTacToeGame('O', 'X');

        ticTacToeView.setOnTileClickedListener(new TicTacToeView.OnTileClickListener() {
            @Override
            public void onTileClick(int position) {
                handleMove(position);
            }
        });

        game.setOnGameOverListener(new TicTacToeGame.OnGameOverListener() {
            @Override
            public void onGameOver(@TicTacToeGame.GameStateTracker  int stateTracker, int[] winningIndices) {
                endGame(stateTracker, winningIndices);
            }
        });

        if (savedInstanceState != null) {
            ticTacToeView.setEnabled(false);    // So no sneaky quick taps can't happen while restoring...
            game.setGridState(savedInstanceState.getCharArray(EXTRA_GRID_STATE));
            game.setIsOver(savedInstanceState.getBoolean(EXTRA_IS_GAME_OVER));
            game.setCurrentPlayer(savedInstanceState.getChar(EXTRA_CURRENT_PLAYER));
            game.setGameStateTracker(savedInstanceState.getInt(EXTRA_GAME_STATE));
            ticTacToeView.restoreBoard(game.getGridState());

            if (game.isGameOver()) {
                game.setIntsWinIndicies(savedInstanceState.getIntArray(EXTRA_WINNING_INDICES));
                game.endGame();
            } else {
                ticTacToeView.setNextPlayer(game.currentPlayer());
            }
        } else {
            ticTacToeView.setNextPlayer(game.currentPlayer());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!game.isGameOver()) {
            startGame();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // If the game got any more complex, I'd probably switch to some form of game serialization
        outState.putCharArray(EXTRA_GRID_STATE, game.getGridState());
        outState.putBoolean(EXTRA_IS_GAME_OVER, game.isGameOver());
        outState.putChar(EXTRA_CURRENT_PLAYER, game.currentPlayer());
        outState.putInt(EXTRA_GAME_STATE, game.getGameStateTracker());
        outState.putIntArray(EXTRA_WINNING_INDICES, game.getIntsWinIndicies());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.restart:
                restart();
                return true;
            case R.id.scores:
                showScoresDialog(TicTacToeGame.CONTINUE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startGame() {
        ticTacToeView.setNextPlayer(game.currentPlayer());
        if (game.currentPlayer() == TicTacToeGame.PLAYER_TWO) {
            simulateCpuMove();
        } else {
            ticTacToeView.setEnabled(true);
            Snackbar.make(ticTacToeView, "Your turn!", Snackbar.LENGTH_LONG).show();
        }
    }

    private void handleMove(int position) {
        game.makeMove(position);
        char nextPlayer = game.currentPlayer();
        ticTacToeView.setNextPlayer(nextPlayer);

        if (!game.isGameOver()) {
            if (nextPlayer == TicTacToeGame.PLAYER_TWO) {
                simulateCpuMove();
            }
        }
    }

    /**
     * Simulate a CPU move. This can sometimes take awhile, and we also want it to happen after a
     * delay. To do this without blocking the UI thread, RxJava is my go-to tool for threading.
     * Used in tandem with RxLifecycle, we can do this in a non-leaky and responsive way.
     */
    private void simulateCpuMove() {
        final Snackbar snackbar = Snackbar.make(ticTacToeView, "Thinking...", Snackbar.LENGTH_INDEFINITE);
        game.getCpuMove()
                .subscribeOn(Schedulers.computation())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        ticTacToeView.setEnabled(false);
                        snackbar.show();
                    }
                })
                .delay(1, TimeUnit.SECONDS)                 // Make it look like the computer is "thinking"
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action() {
                    @Override
                    public void run() throws Exception {
                        ticTacToeView.setEnabled(true);
                        ticTacToeView.setTile(game.getComputerMove(), TicTacToeGame.PLAYER_TWO);
                        handleMove(game.getComputerMove());
                        snackbar.dismiss();
                    }
                });
    }

    private void endGame(@GameStateTracker int result, @Nullable int[] winningIndices) {
        ticTacToeView.endGame(winningIndices);
        if (getSupportFragmentManager().findFragmentByTag("scores") == null) {
            showScoresDialog(result);
        }
    }

    private void restart() {
        ticTacToeView.reset();
        game.restart();
        startGame();
    }

    @SuppressLint("CommitPrefEdits")
    private void showScoresDialog(@GameStateTracker final int result) {
        final boolean isDone = result != TicTacToeGame.CONTINUE;

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String title;
        String scoreToUpdate = null;
        switch (result) {
            case TicTacToeGame.PLAYER_ONE_WINS:
                title = "You win!";
                scoreToUpdate = PREF_HUMAN_WINS;
                break;
            case TicTacToeGame.PLAYER_TWO_WINS:
                title = "Computer wins!";
                scoreToUpdate = PREF_COMPUTER_WINS;
                break;
            case TicTacToeGame.DRAW:
                title = "It's a tie!";
                scoreToUpdate = PREF_TIES;
                break;
            default:
                title = "History";
        }

        if (scoreToUpdate != null) {
            prefs.edit().putInt(scoreToUpdate, prefs.getInt(scoreToUpdate, 0) + 1).commit();    // Commit synchronously so we're sure they're up to date in the next line
        }

        String scores = prefs.getInt(PREF_HUMAN_WINS, 0) + " - Human"
                + "\n" + prefs.getInt(PREF_COMPUTER_WINS, 0) + " - Computer"
                + "\n" + prefs.getInt(PREF_TIES, 0) + " - Ties";

        ScoresDialogFragment dialog = new ScoresDialogFragment();
        dialog.isDone = isDone;
        dialog.title = title;
        dialog.message = scores;
        dialog.prefs = prefs;
        dialog.show(getSupportFragmentManager(), "scores");

    }

    /**
     * These are annoyingly tedious to maintain, but necessary to avoid leaky windows and
     * maintaining the dialog across rotations.
     */
    public static class ScoresDialogFragment extends DialogFragment {

        boolean isDone;
        String message;
        String title;
        SharedPreferences prefs;

        public static ScoresDialogFragment newInstance(String title) {
            ScoresDialogFragment frag = new ScoresDialogFragment();
            Bundle args = new Bundle();
            args.putString("title", title);
            frag.setArguments(args);
            return frag;
        }

        public ScoresDialogFragment() {
            setRetainInstance(true);
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            if (!isDone) {
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Restart",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (isDone) {
                                    ((MainActivity) getContext()).restart();
                                }
                            }
                        });
            } else {
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Done",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                prefs.edit()
                                        .putInt(PREF_HUMAN_WINS, 0)
                                        .putInt(PREF_COMPUTER_WINS, 0)
                                        .putInt(PREF_TIES, 0)
                                        .apply();
                            }
                        });
            }
            alertDialog.show();


            return alertDialog;
        }

        @Override
        public void onDestroyView() {
            // workaround for a bug causing the dialog to dismissed on rotation
            if (getDialog() != null && getRetainInstance()) {
                getDialog().setDismissMessage(null);
            }
            super.onDestroyView();
        }


        @Override
        public void show(FragmentManager manager, String tag) {
            try {
                FragmentTransaction ft = manager.beginTransaction();
                ft.add(this, tag);
                ft.commitAllowingStateLoss();
            } catch (IllegalStateException e) {
                Log.d("ABSDIALOGFRAG", "Exception", e);
            }
        }
    }
}
