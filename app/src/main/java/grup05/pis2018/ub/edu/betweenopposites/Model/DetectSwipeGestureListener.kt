package com.dev2qa.gestureexample

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.GestureDetector
import android.view.MotionEvent
import grup05.pis2018.ub.edu.betweenopposites.Model.*
import grup05.pis2018.ub.edu.betweenopposites.R
import grup05.pis2018.ub.edu.betweenopposites.View.UnJugador

/**
 * Created by Jerry on 4/18/2018.
 */

class DetectSwipeGestureListener : GestureDetector.SimpleOnGestureListener() {

    var lobo: Lobo = Lobo.instance


    /* This method is invoked when a swipe gesture happened. */
    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {

        // Get swipe delta value in x axis.
        val deltaX = e1.x - e2.x

        // Get swipe delta value in y axis.
        val deltaY = e1.y - e2.y

        // Get absolute value.
        val deltaXAbs = Math.abs(deltaX)
        val deltaYAbs = Math.abs(deltaY)

        // Only when swipe distance between minimal and maximal distance value then we treat it as effective swipe
        if (deltaXAbs >= MIN_SWIPE_DISTANCE_X && deltaXAbs <= MAX_SWIPE_DISTANCE_X) {
            if (lobo.velocidad == 0f) {
                lobo.restarurarVelocidad()
            }
            if (deltaX > 0) {
                if(lobo.direccionIvalida!= Direccion.IZQUIERDA){
                    lobo.direccion = Direccion.IZQUIERDA
                }

            } else {
                if(lobo.direccionIvalida!= Direccion.DERECHA){
                    lobo.direccion = Direccion.DERECHA
                }
            }
        }

        if (deltaYAbs >= MIN_SWIPE_DISTANCE_Y && deltaYAbs <= MAX_SWIPE_DISTANCE_Y) {
            if (lobo.velocidad == 0f) {
                lobo.restarurarVelocidad()
            }
            if (deltaY > 0) {
                if(lobo.direccionIvalida!= Direccion.ARRIBA){
                    lobo.direccion = Direccion.ARRIBA
                }
            } else {
                if(lobo.direccionIvalida!= Direccion.ABAJO){
                    lobo.direccion = Direccion.ABAJO
                }
            }
        }


        return true
    }

    // Invoked when single tap screen.


    companion object {

        // Minimal x and y axis swipe distance.
        private val MIN_SWIPE_DISTANCE_X = 100
        private val MIN_SWIPE_DISTANCE_Y = 100

        // Maximal x and y axis swipe distance.
        private val MAX_SWIPE_DISTANCE_X = 1000
        private val MAX_SWIPE_DISTANCE_Y = 1000
    }
}