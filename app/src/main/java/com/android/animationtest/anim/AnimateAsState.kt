package com.android.animationtest.anim

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AnimateFloatAsState() {
    var editable by remember { mutableStateOf(true) }
    val alpha: Float by animateFloatAsState(if (editable) 0f else 90f)

    Box(
        Modifier
            .fillMaxSize()
    ) {
        Box(
            Modifier
                .width(200.dp)
                .height(200.dp)
                .graphicsLayer(rotationZ = alpha)
                .background(Color.Red)
        ) {

        }
        Button(modifier = Modifier.align(Alignment.Center),
            onClick = { editable = !editable }) {
            Text(text = "Degistir")
        }
    }
}

@Composable
fun AnimateColorAsState() {
    var editable by remember { mutableStateOf(true) }
    val color: Color by animateColorAsState(if (editable) Color.Red else Color.Blue)

    Box(
        Modifier
            .fillMaxSize()
    ) {
        Box(
            Modifier
                .width(200.dp)
                .height(200.dp)
                .background(color)
        ) {

        }
        Button(modifier = Modifier.align(Alignment.Center),
            onClick = { editable = !editable }) {
            Text(text = "Degistir")
        }
    }
}

@Composable
fun AnimateDpAsState() {
    var editable by remember { mutableStateOf(true) }
    val size: Dp by animateDpAsState(if (editable) 50.dp else 200.dp)

    Box(
        Modifier
            .fillMaxSize()
    ) {
        Box(
            Modifier
                .width(size)
                .height(size)
                .background(Color.Red)
        ) {

        }
        Button(modifier = Modifier.align(Alignment.Center),
            onClick = { editable = !editable }) {
            Text(text = "Degistir")
        }
    }
}