package com.itamecodes.vivek.tictactoe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import com.itamecodes.vivek.tictactoe.viewmodels.SharedViewModel
import kotlinx.android.synthetic.main.score_fragment.*
class ScoreFragment: Fragment() {
    private lateinit var model:SharedViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v = inflater.inflate(R.layout.score_fragment,container,false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val options = NavOptions.Builder()
                .setPopUpTo(R.id.splashFragment,
                        true).build()
        goback.setOnClickListener{
            findNavController(it).navigate(R.id.action_scoreFragment_to_splashFragment,null,options)
        }

        model = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

       
    }

}