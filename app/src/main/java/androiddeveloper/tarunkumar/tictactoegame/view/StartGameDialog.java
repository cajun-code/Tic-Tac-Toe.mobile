package androiddeveloper.tarunkumar.tictactoegame.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androiddeveloper.tarunkumar.tictactoegame.R;

public class StartGameDialog extends DialogFragment {

    private TextInputLayout playerLayout;
    private TextInputEditText playerEditText;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private String player;

    private View rootView;
    private TicTacToeActivity activity;

    public static StartGameDialog newInstance(TicTacToeActivity activity) {
        StartGameDialog dialog = new StartGameDialog();
        dialog.activity = activity;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initViews();
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .setTitle(R.string.game_dialog_title)
                .setCancelable(false)
                .setPositiveButton(R.string.done, null)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.setOnShowListener(dialog -> onDialogShow(alertDialog));
        return alertDialog;
    }

    private void initViews() {
        final ViewGroup nullParent = null;

        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.dialog_game_start, nullParent, false);

        playerLayout = rootView.findViewById(R.id.layout_player);

        playerEditText = rootView.findViewById(R.id.et_player);

        radioGroup = rootView.findViewById(R.id.symbol_radiogroup);

        addTextWatchers();
    }

    private void onDialogShow(AlertDialog dialog) {
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(v -> onDoneClicked());
    }

    private void onDoneClicked() {
        if (isAValidName(playerLayout, player)) {

            int selectedId = radioGroup.getCheckedRadioButtonId();
            radioButton = rootView.findViewById(selectedId);
            activity.onPlayersSet(player, radioButton.getText().toString());

            dismiss();
        }
    }

    private boolean isAValidName(TextInputLayout layout, String name) {
        if (TextUtils.isEmpty(name)) {
            layout.setErrorEnabled(true);
            layout.setError(getString(R.string.error_empty_name));
            return false;
        }

        if (player.equalsIgnoreCase("Robot")) {
            layout.setErrorEnabled(true);
            layout.setError(getString(R.string.error_same_names));
            return false;
        }

        layout.setErrorEnabled(false);
        layout.setError("");
        return true;
    }

    private void addTextWatchers() {
        playerEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                player = s.toString();
            }
        });
    }
}
