package com.itamecodes.vivek.tictactoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import kotlinx.android.synthetic.main.splash_fragment.*

class SplashScreenFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        play_game_button.setOnClickListener {
            val action = SplashScreenFragmentDirections.actionSplashFragmentToGameFragment()
            findNavController(it).navigate(action)
        }
    }
}



