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
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.sketchpad.ui.theme.SketchpadTheme
import android.util.Log

import com.example.sketchpad.CanvasView


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
        enableEdgeToEdge()
        setContent {

            SketchpadTheme {
                MainUI()
            }


        }
    }
}

@Composable
fun MainUI() {
    val context = LocalContext.current
    val canvasView = remember {CanvasView(context)}
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        AndroidView(
            factory = {canvasView},
            modifier = Modifier.fillMaxSize()
        )
        Row(
            modifier = Modifier
                .wrapContentSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Button(onClick = {
                canvasView.toggleEraser()
                Log.d("Button", "Button Pressed")
            } ) {
                Text("Eraser")
            }
            Button(onClick = { test() }) {
                Text("Button")

            }
            Button(onClick = { test() }) {
                Text("Button")

            }
            Button(onClick = { test() }) {
                Text("Button")

            }
        }
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



fun test () {
    println("")
}