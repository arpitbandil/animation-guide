package com.absoft.rotateanimation.extensions

import android.content.res.Resources
import android.view.View
import com.absoft.rotateanimation.listeners.MultiFunctionalTouchListener

fun View.setMultiFunctionalTouchListener(eventListener: MultiFunctionalTouchListener.EventListener) {
    setOnTouchListener(MultiFunctionalTouchListener(this, eventListener))
}

val Float.dp: Float
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f)