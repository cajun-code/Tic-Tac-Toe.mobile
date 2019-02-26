package com.lasley.adam.codingtest.Fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.lasley.adam.codingtest.Objects.GamePrefs
import com.lasley.adam.codingtest.R
import kotlinx.android.synthetic.main.fragment_main_menu.*


class MainMenu : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadPrefs()
        setControlActions()
    }

    private fun loadPrefs() {
        if (GamePrefs().playerX)
            radio_x.isChecked = true
        else
            radio_o.isChecked = true

        seek_BSize.progress = GamePrefs().boardSize
        txt_bSize.text = (seek_BSize.progress + 3).let { p -> "$p x $p" }

        seek_WinSize.max = seek_BSize.progress + 1
        seek_WinSize.progress = GamePrefs().winSize
        txt_winSize.text = (seek_WinSize.progress + 2).toString()
    }

    private fun setControlActions() {
        radio_x.setOnClickListener { GamePrefs().playerX = true }
        radio_o.setOnClickListener { GamePrefs().playerX = false }

        seek_BSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                txt_bSize.text = (progress + 3).let { p -> "$p x $p" }
                seek_WinSize.max = progress + 1
                seek_WinSize.progress = Math.max(1, Math.min(seek_WinSize.max, seek_WinSize.progress))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                GamePrefs().boardSize = seekBar.progress
            }
        })

        seek_WinSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (progress == 0)
                    seekBar.progress = 1
                else
                    txt_winSize.text = (progress + 2).toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                GamePrefs().winSize = seekBar.progress
            }
        })

        btnStart.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, Gameplay())
                ?.addToBackStack("Game")?.commit()
        }
    }


}
