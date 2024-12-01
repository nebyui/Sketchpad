package com.example.sketchpad

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BlendMode
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.view.MotionEvent
import android.view.View
import android.util.Log
import androidx.core.graphics.setBlendMode

class CanvasView(context: Context) : View(context) {
    private lateinit var canvas: Canvas
    private lateinit var bitmap: Bitmap
    private var lastX: Float = 0.0f
    private var lastY: Float = 0.0f
    private var tap: Boolean = false
    private val path = Path()
    private var eraserOn: Boolean = false


    private val paintBrush = Paint().apply {
        color = Color.BLACK
        strokeWidth = 50f
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        blendMode = BlendMode.CLEAR
    }


    init {
        val screenWidth = context.resources.displayMetrics.widthPixels
        val screenHeight = context.resources.displayMetrics.heightPixels

        bitmap = Bitmap.createBitmap(
            screenWidth - 50,
            screenHeight - 100,
            Bitmap.Config.ARGB_8888
        )

        canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC)


    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        canvas.drawPath(path, paintBrush)

//
    }

    override fun onTouchEvent(event: MotionEvent): Boolean { // provided by View class, runs when the contact between the finger and screen change

        if (eraserOn) {
            paintBrush.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) // Eraser mode
            Log.d("EraserFunction", "ERASER ON")
        } else {
            paintBrush.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER) // Drawing mode
            Log.d("EraserFunction", "ERASER OFF")
        }





//        if (eraserOn == true) {
//            paintBrush.setBlendMode(BlendMode.CLEAR)
//            Log.d("EraserFunction", "ERASER ON")
//            currentMode = PorterDuff.Mode.CLEAR
//            Log.d("Brush Mode", "Current Brush Mode: $currentMode")
//        } else if (eraserOn == false) {
//            paintBrush.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)
//            Log.d("EraserFunction", "ERASER OFF")
//            paintBrush.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)
//            currentMode = PorterDuff.Mode.SRC_OVER
//            Log.d("Brush Mode", "Current Brush Mode: $currentMode")
//        }

        Log.d("Eraser Bool", eraserOn.toString())
        val screenXCordinate = event.x
        val screenYCordinate = event.y

        when (event.getActionMasked()) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(
                    screenXCordinate,
                    screenYCordinate
                ) // stores the coordinates of the initial touch to be used later
                lastX = screenXCordinate
                lastY = screenYCordinate
            }

            MotionEvent.ACTION_POINTER_DOWN -> { // when additional fingers are placed
                tap =
                    false // tap is used to detect when the user whats to simply tap a dot. If more than one finger is detected, that tap event does not occur
            }

            MotionEvent.ACTION_MOVE -> { // whenever the finger is dragged across the screen
                if (event.pointerCount < 1 && tap) { // when only one finger is detected and tap is true

                } else {
                    val midX = (lastX + screenXCordinate) / 2
                    val midY = (lastY + screenYCordinate) / 2
                    path.quadTo(lastX, lastY, midX, midY)
                    lastX = screenXCordinate
                    lastY = screenYCordinate
                    invalidate()
                }
            }


        }
        return true
    }

    fun toggleEraser() {
        Log.d("EraserFunction", "Function Called")
        if (eraserOn == true) {
            eraserOn = false
//            Log.d("EraserFunction", "SET TO FALSE")
//            Log.d("Eraser Bool", eraserOn.toString())
        } else if (eraserOn == false) {
            eraserOn = true
//            Log.d("EraserFunction", "SET TO TRUE")
//            Log.d("Eraser Bool", eraserOn.toString())
        }



    }

}

