package com.itamecodes.vivek.tictactoe.extensions

import android.widget.Button
import androidx.fragment.app.Fragment

fun Fragment.getButtonDynamically(viewName:String):Button{
    return view?.findViewById(getResources().getIdentifier(viewName, "id", activity?.getPackageName())) as Button
}