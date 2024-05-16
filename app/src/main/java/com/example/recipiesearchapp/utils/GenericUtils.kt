package com.example.recipiesearchapp.utils

import android.app.Activity
import android.util.DisplayMetrics
import androidx.constraintlayout.widget.ConstraintLayout

class GenericUtils {
    companion object{
        fun <T> removeDuplicates(list: ArrayList<T>): ArrayList<T> {
            val distinctItems = ArrayList<T>()
            distinctItems.addAll(list.distinct())
            return distinctItems
        }
         fun setPeekHeight(height:Double, bottomSheet: ConstraintLayout, activity: Activity){
            val metrics = DisplayMetrics()
            activity.windowManager?.defaultDisplay?.getMetrics(metrics)
            bottomSheet.layoutParams.height = (metrics.heightPixels * height).toInt()
        }
    }
}