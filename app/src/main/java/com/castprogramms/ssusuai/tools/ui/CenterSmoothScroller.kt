package com.castprogramms.ssusuai.tools.ui

import android.content.Context
import androidx.recyclerview.widget.LinearSmoothScroller
import kotlin.math.max


class CenterSmoothScroller(context: Context) : LinearSmoothScroller(context) {
    override fun calculateDtToFit(
        viewStart: Int,
        viewEnd: Int,
        boxStart: Int,
        boxEnd: Int,
        snapPreference: Int
    ): Int {
        return boxStart + (boxEnd - boxStart) / 2 - (viewStart + (viewEnd - viewStart) / 2)
    }

    override fun calculateTimeForScrolling(dx: Int): Int {
        return max(50, dx/10)
    }

}