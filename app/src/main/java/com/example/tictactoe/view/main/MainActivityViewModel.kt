package com.example.tictactoe.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject


class MainActivityViewModel @Inject constructor() : ViewModel() {

    private val _player: MutableLiveData<String> = MutableLiveData()
    val startGame: LiveData<String>
        get() = _player


    fun startGame(player: String) {
        _player.value = player
    }

}