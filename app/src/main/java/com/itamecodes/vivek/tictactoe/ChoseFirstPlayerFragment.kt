package com.itamecodes.vivek.tictactoe

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


class ChoseFirstPlayerFragment: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val context = activity?.applicationContext

        return context?.let {
            AlertDialog.Builder(context)
                    .setTitle("Who Starts First?")
                    .setMessage("Do you want to go first")
                    .setPositiveButton("Yes"){ dialog,which->
                        Toast.makeText(it,"Yes",Toast.LENGTH_LONG).show()
                    }.setNegativeButton("No"){dialog,which->
                        Toast.makeText(it,"No",Toast.LENGTH_LONG).show()
                    }.create()
        }?:run {
            super.onCreateDialog(savedInstanceState)
        }

    }
}