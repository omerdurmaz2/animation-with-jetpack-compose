package com.android.animationtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.android.animationtest.anim.*
import com.android.animationtest.ui.theme.AnimationTestTheme
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationTestTheme {
                ACSizeTransform()
            }
        }
    }
}

@Composable
fun BounceAnimation() {
    val coroutineScope = rememberCoroutineScope()
    val offsetY by remember { mutableStateOf(Animatable(0f)) }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
    ) {
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
