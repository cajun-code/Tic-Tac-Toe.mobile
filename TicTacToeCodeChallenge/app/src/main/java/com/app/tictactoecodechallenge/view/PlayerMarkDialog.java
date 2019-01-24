package com.app.tictactoecodechallenge.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.tictactoecodechallenge.R;
import com.app.tictactoecodechallenge.model.PlayerChoiceModel;

public class PlayerMarkDialog extends DialogFragment {

    private PlayerChoiceModel playerChoiceModel;
    private Button xMarkButton;
    private Button oMarkButton;

    public PlayerMarkDialog() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static PlayerMarkDialog newInstance(String title) {
        PlayerMarkDialog frag = new PlayerMarkDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_player_mark_choice, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        xMarkButton = view.findViewById(R.id.btn_x);
        oMarkButton = view.findViewById(R.id.btn_o);

        xMarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // playerChoiceModel.setXChoice();
                dismiss();
            }
        });

        oMarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // playerChoiceModel.setOChoice();
                dismiss();
            }
        });

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);


    }
}
