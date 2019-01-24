package com.app.tictactoecodechallenge.model;

import android.content.Context;
import android.content.SharedPreferences;

public class PlayerChoiceModel {
    public static final String PREFERENCE_NAME = "PREFERENCE_DATA";
    private final SharedPreferences sharedpreferences;

    public PlayerChoiceModel(Context context) {
        sharedpreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    public String getPlayerChoice() {
        return sharedpreferences.getString("playerChoice", "?");
    }

    public String getComputerChoice(){
        return sharedpreferences.getString("computerChoice","?");
    }

    // These method should be on the PlayerMArkDialog. Retuning NullPointerException even declaring SharedPreferences
    public void setXChoice() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("playerChoice", "X");
        editor.putString("computerChoice", "O");
        editor.apply();
    }

    public void setOChoice(){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("playerChoice", "O");
        editor.putString("computerChoice", "X");
        editor.apply();
    }

    public void clearChoice() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove("count");
        editor.apply();
    }
}
