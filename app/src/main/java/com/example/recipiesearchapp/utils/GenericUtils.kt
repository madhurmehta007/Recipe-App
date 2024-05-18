package com.example.recipiesearchapp.utils

import android.app.Activity
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.recipiesearchapp.R

class GenericUtils {
    companion object {
        fun <T> removeDuplicates(list: ArrayList<T>): ArrayList<T> {
            val distinctItems = ArrayList<T>()
            distinctItems.addAll(list.distinct())
            return distinctItems
        }

        fun setPeekHeight(height: Double, bottomSheet: ConstraintLayout, activity: Activity) {
            val metrics = DisplayMetrics()
            activity.windowManager?.defaultDisplay?.getMetrics(metrics)
            bottomSheet.layoutParams.height = (metrics.heightPixels * height).toInt()
        }

        fun View.hide() {
            visibility = View.GONE
        }

        fun View.show() {
            visibility = View.VISIBLE
        }

        fun Fragment.replaceChildFragmentWithAnimation(
            fragment: Fragment, @IdRes frameId: Int,
            addBackStack: String? = null
        ) {
            childFragmentManager.beginTransaction().apply {
                setCustomAnimations(
                    R.anim.enter_animation,
                    R.anim.exit_animation,
                    R.anim.enter_animation,
                    R.anim.exit_animation
                )
                replace(frameId, fragment)
                if (addBackStack != null) {
                    addToBackStack(addBackStack)
                }
                commitAllowingStateLoss()
            }
        }
    }
}