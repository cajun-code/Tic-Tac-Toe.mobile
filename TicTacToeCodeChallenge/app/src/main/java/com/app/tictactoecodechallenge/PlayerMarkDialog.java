package com.app.tictactoecodechallenge;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import java.util.Objects;

@TargetApi(Build.VERSION_CODES.KITKAT)
public class PlayerMarkDialog extends DialogFragment {

    private Context context;

//    SharedPreferences sharedPref = Objects.requireNonNull(getActivity()).getSharedPreferences(
//            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
//    SharedPreferences.Editor editor = sharedPref.edit();


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
//                editor.putString("playerChoice", "X");
//                editor.putString("computerChoice", "O");
//                editor.commit();
                dismiss();
            }
        });

        oMarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                editor.putString("playerChoice", "O");
//                editor.putString("computerChoice", "X");
//                editor.commit();
                dismiss();
            }
        });

        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);


    }
}
