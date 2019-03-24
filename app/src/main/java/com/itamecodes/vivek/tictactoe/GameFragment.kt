package com.itamecodes.vivek.tictactoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.itamecodes.vivek.tictactoe.viewmodels.GameViewModel
import com.itamecodes.vivek.tictactoe.viewmodels.SharedViewModel
import kotlinx.android.synthetic.main.game_fragment.*
import java.lang.Exception

class GameFragment: Fragment() {
    private lateinit var model: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.game_fragment,container,false);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val score =GameFragmentArgs.fromBundle(arguments!!).score
        Toast.makeText(activity,"the score is $score",Toast.LENGTH_LONG).show()
        activity?.let{
            model = ViewModelProviders.of(it).get(GameViewModel::class.java)
        }
        populateData()
    }

    fun populateData(){

    }
}