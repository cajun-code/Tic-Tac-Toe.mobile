package androiddeveloper.tarunkumar.tictactoegame.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androiddeveloper.tarunkumar.tictactoegame.R;

public class EndGameDialog extends DialogFragment {

    private View rootView;
    private TicTacToeActivity activity;
    private String winnerName;

    public static EndGameDialog newInstance(TicTacToeActivity activity, String winnerName) {
        EndGameDialog dialog = new EndGameDialog();
        dialog.activity = activity;
        dialog.winnerName = winnerName;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initViews();
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setView(rootView)
                .setCancelable(false)
                .setPositiveButton(R.string.done, ((dialog, which) -> onNewGame()))
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        return alertDialog;
    }

    private void initViews() {
        final ViewGroup nullParent = null;
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_game_end, nullParent, false);

        ((TextView) rootView.findViewById(R.id.tv_winner)).setText(winnerName);

    }

    private void onNewGame() {
        dismiss();
        activity.showStartGameDialog();

    }
}
