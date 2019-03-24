package com.itamecodes.vivek.tictactoe.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itamecodes.vivek.tictactoe.models.Dummy


class SharedViewModel: ViewModel() {
    val inputNumber = MutableLiveData<Int>()
}