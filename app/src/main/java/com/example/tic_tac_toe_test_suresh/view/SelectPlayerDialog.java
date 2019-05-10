package com.example.tic_tac_toe_test_suresh.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.tic_tac_toe_test_suresh.R;

public class SelectPlayerDialog extends DialogFragment {

    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private String player;

    private MainActivity activity;
    public View rootView;

    public static SelectPlayerDialog newInstance(MainActivity activity) {
        SelectPlayerDialog dialog = new SelectPlayerDialog();
        dialog.activity = activity;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.select_player_dialog, null, false);

        radioGroup = rootView.findViewById(R.id.symbol_radiogroup);

        android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(getContext())
                .setView(rootView)
                .setTitle("Start Game")
                .setCancelable(false)
                .setPositiveButton("Done", null)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.setOnShowListener(dialog -> onDialogShow(alertDialog));
        return alertDialog;
    }

    private void onDialogShow(android.app.AlertDialog dialog) {
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(v -> onDoneClicked());
    }

    private void onDoneClicked() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = rootView.findViewById(selectedId);
        dismiss();
    }
}


