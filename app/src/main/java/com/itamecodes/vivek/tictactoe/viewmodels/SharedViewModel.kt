package com.itamecodes.vivek.tictactoe.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class SharedViewModel: ViewModel() {
    val inputNumber = MutableLiveData<Int>()
}