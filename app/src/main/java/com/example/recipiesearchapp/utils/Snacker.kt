package com.example.recipiesearchapp.utils

import android.view.View
import androidx.core.content.ContextCompat
import com.example.recipiesearchapp.R
import com.google.android.material.snackbar.Snackbar

class Snacker (val view : View, val text : String) {
    fun success(){
        return Snackbar.make(view,text, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(view.context, R.color.colorSuccess))
            .setTextColor(ContextCompat.getColor(view.context,R.color.white))
            .show()
    }

    fun error(){
        return Snackbar.make(view,text, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(view.context,R.color.colorError))
            .setTextColor(ContextCompat.getColor(view.context,R.color.white))
            .show()
    }

    fun warning(){
        return Snackbar.make(view,text, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(view.context,R.color.colorWarning))
            .setTextColor(ContextCompat.getColor(view.context,R.color.white))
            .show()
    }

}