package com.example.sketchpad

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.BlendMode
import android.graphics.Path

import android.view.MotionEvent
import android.view.View


class CanvasView(context: Context) : View(context) {
    private val paintBrush = Paint().apply { // Creates Paint object and sets its properties
        color = Color.BLACK
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 10f
    }

    private val eraserBrush = Paint().apply { // Create eraser Paint opbject
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 10f
        blendMode = BlendMode.CLEAR
    }
    private lateinit var drawCanvas: Canvas
    private lateinit var canvasBitmap: Bitmap

    private var currentPaint = paintBrush // Tracks which brush is active

    private val paths = mutableListOf<Pair<Path, Paint>>() // List of all the paths, meaning the path of the stroke and the paintbrush that was used
    private var currentPath: Path? = null // currentPath can hold a path object or be null when there is no path being actively made

    init {
        setLayerType(LAYER_TYPE_SOFTWARE, null) // Enables layer to use software acceleration, needed for eraser functionality
    }

    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) { // Runs when changes are detected in view size or screen orientation
        super.onSizeChanged(width, height, oldWidth, oldHeight)

        canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888) // sets canvas size to the size of the screen, and sets the pixels to hold 32 bit data (RGBA)
        drawCanvas = Canvas(canvasBitmap)
    }

    override fun onDraw(canvas: Canvas) { // adds the drawing to the view
        super.onDraw(canvas)

        canvas.drawBitmap(canvasBitmap, 0f, 0f, null)

        for ((path, paint) in paths) { // draws all the saved paths onto the canvas
            canvas.drawPath(path, paint) // takes the paint object, applies it to path object
        }

        currentPath?.let { path -> // if there is a ongoing path, it draws that too
            canvas.drawPath(path, currentPaint)
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean { // runs when changes in contact is detected on screen

        val touchX = event.x // gets x and y cordinate of touch
        val touchY = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> { // when finger first touches the screen
                currentPath = Path().apply { moveTo(touchX, touchY) } // starts new path
                paths.add(currentPath!! to Paint(currentPaint)) // adds the currentPath and paintbrush pair to list, !! prevents error if it is null,
            }

            MotionEvent.ACTION_MOVE -> { // when the finger is dragged across the screen
                currentPath?.lineTo(touchX, touchY) // adds more to that path object as it moves to new x and y coordinates
            }

            MotionEvent.ACTION_UP -> { // when finger is lifted from screen
                currentPath = null // resets currentPath to prepare for next path object to be made
            }
        }
        invalidate() // updates the view
        return true // tells function that touch event was handled
    }


    fun setToBrush() {
        currentPaint = paintBrush // changes brush to painting paint object
    }

    fun setToEraser() {
        currentPaint = eraserBrush // changes brush to eraser paint object
    }
}