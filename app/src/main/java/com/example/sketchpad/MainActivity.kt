package com.example.sketchpad

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.sketchpad.ui.theme.SketchpadTheme
import android.util.Log
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = ( // Sets app in fullscreen and hides status bar and navigation menu bar
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )

        enableEdgeToEdge() // fills up whole screen, including the space the status and navigation bar would take up

        setContent {
            SketchpadTheme { // Applies theme set in Theme.kt to contents
                MainUI()
            }
        }
    }
}

@Composable
fun MainUI() {
    val context = LocalContext.current
    val canvasView = remember {CanvasView(context)} // Creates a CanvasView object, passes context UI can interact with it
    Box( // makes box to contain all the UI elements
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        AndroidView( // makes view for the CanvasView object
            factory = {canvasView},
            modifier = Modifier
                .wrapContentSize()
                .background(Color.White)
                .align(Alignment.Center)

        )

        Row( // Sets row to contain buttons at top of screen
            modifier = Modifier
                .wrapContentSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Button(onClick = {
                canvasView.setToEraser() // Turns on Eraser
            } ) {
                Text("Eraser")
            }
            Button(onClick = {
                canvasView.setToBrush() // Turns on Paint brush
            } ) {
                Text("Brush")
            }
        }

        // Column element for sidebar buttons in the future
//        Column(
//            modifier = Modifier
//                .wrapContentSize()
//                .background(MaterialTheme.colorScheme.surface)
//                .align(AbsoluteAlignment.CenterLeft)
//        ) {
//            Button(onClick = { test() }) {
//                Text("Button")
//
//            }
//            Button(onClick = { test() }) {
//                Text("Button")
//
//            }
//            Button(onClick = { test() }) {
//                Text("Button")
//
//            }
//            Button(onClick = { test() }) {
//                Text("Button")
//
//            }
//        }
    }
}