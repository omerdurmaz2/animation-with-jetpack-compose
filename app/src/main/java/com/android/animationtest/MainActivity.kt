package com.android.animationtest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.android.animationtest.ui.theme.AnimationTestTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationTestTheme {

                val coroutineScope = rememberCoroutineScope()

                val offsetY by remember { mutableStateOf(Animatable(0f)) }

                Box(contentAlignment = Alignment.TopCenter, modifier = Modifier.fillMaxSize()) {

                    Button(modifier = Modifier.align(Alignment.Center),
                        onClick = {
                            coroutineScope.launch {
                                offsetY.snapTo(offsetY.value + 150)
                                offsetY.animateTo(
                                    targetValue = 0f, spring(
                                        dampingRatio = Spring.DampingRatioHighBouncy,
                                        stiffness = StiffnessLow

                                    )
                                )
                            }
                        }) {}
                    Surface(
                        color = Color(0xFF34AB52),
                        modifier = Modifier
                            .size(100.dp)
                            .offset {
                                IntOffset(0, offsetY.value.roundToInt())
                            }

                    ) {
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AnimationTestTheme {
        Greeting("Android")
    }
}