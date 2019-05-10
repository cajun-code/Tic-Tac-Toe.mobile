package com.example.tic_tac_toe_test_suresh.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.tic_tac_toe_test_suresh.R;

public class ResultDialog extends DialogFragment {


    private MainActivity activity;
    private String winnerName;

    public static ResultDialog newInstance(MainActivity activity, String winnerName) {
        ResultDialog dialog = new ResultDialog();
        dialog.activity = activity;
        dialog.winnerName = winnerName;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.result_dialog, null, false);
        ((TextView) rootView.findViewById(R.id.tv_winner)).setText(winnerName);

        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setView(rootView)
                .setCancelable(false)
                .setPositiveButton("yes", ((dialog, which) -> onStartGame()))
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        return alertDialog;
    }


    private void onStartGame() {
        dismiss();
        activity.showSelectPlayerDialog();

    }
}