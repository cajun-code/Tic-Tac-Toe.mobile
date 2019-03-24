package com.itamecodes.vivek.tictactoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import kotlinx.android.synthetic.main.splash_fragment.*
class SplashScreenFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v = inflater.inflate(R.layout.splash_fragment,container,false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view_score_button.setOnClickListener{
            val action = SplashScreenFragmentDirections.actionSplashFragmentToGameFragment()
            action.setScore(10)
            findNavController(it).navigate(action)
        }
            play_game_button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.scoreFragment,null))
        }
    }



