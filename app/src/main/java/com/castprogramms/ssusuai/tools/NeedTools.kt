package com.castprogramms.ssusuai.tools

import android.content.Context
import android.content.ContextWrapper
import android.view.View
import androidx.lifecycle.LifecycleOwner
import java.lang.Exception

object NeedTools {

    fun Context.lifecycleOwner(): LifecycleOwner? {
        var curContext = this
        var maxDepth = 20
        while (maxDepth-- > 0 && curContext !is LifecycleOwner) {
            curContext = try {
                (curContext as ContextWrapper).baseContext
            }catch (e:Exception){
                (curContext)
            }
        }
        return if (curContext is LifecycleOwner) {
            curContext
        } else {
            null
        }
    }

    fun View.toTransitionGroup() = this to transitionName


}