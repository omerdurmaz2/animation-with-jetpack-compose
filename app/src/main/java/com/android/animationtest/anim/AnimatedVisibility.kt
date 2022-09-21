package com.android.animationtest.anim

import androidx.compose.animation.*
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun AVWithCustomAnim() {
    var editable by remember { mutableStateOf(true) }
    val density = LocalDensity.current

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
    ) {
        Button(modifier = Modifier.align(Alignment.Center),
            onClick = { editable = !editable }) {
            Text(text = if (editable) "Gizle" else "Goster")
        }
        AnimatedVisibility(
            visible = editable,
            enter = slideInVertically {
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                expandFrom = Alignment.CenterVertically
            ) + fadeIn(
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            Text(text = "Hello World\nI'm Omer Durmaz")
        }

    }
}

@Composable
fun AVWithStateListening() {
    var editable by remember { mutableStateOf(true) }
    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
    ) {
        Button(modifier = Modifier.align(Alignment.Center),
            onClick = { editable = !editable }) {
            Text(text = if (editable) "Gizle" else "Goster")
        }
        AnimatedVisibility(visible = editable) {
            Text(
                text = when {
                    state.isIdle && state.currentState -> "Visible Long"
                    !state.isIdle && state.currentState -> "Disappearing Long"
                    state.isIdle && !state.currentState -> "Invisible Long"
                    else -> "Appearing Long"
                }
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AVAnimateChildVisibility(

) {
    var editable by remember { mutableStateOf(true) }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
    ) {

        AnimatedVisibility(
            visible = editable,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            // Fade in/out the background and the foreground.
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
            ) {
                Box(
                    Modifier
                        .align(Alignment.Center)
                        .animateEnterExit(
                            enter = expandIn(),
                            exit = shrinkOut()
                        )
                        .sizeIn(minWidth = 256.dp, minHeight = 64.dp)
                        .background(Color.Red)
                ) {
                    // Content of the notification…
                }
            }
        }

        Button(modifier = Modifier.align(Alignment.Center),
            onClick = { editable = !editable }) {
            Text(text = if (editable) "Gizle" else "Goster")
        }
    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AVAddCustomAnimation() {
    var editable by remember { mutableStateOf(true) }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column() {
            var background by remember {
                mutableStateOf(Color.Blue)
            }
            Box(
                modifier = Modifier
                    .size(128.dp)
                    .background(background)
            )
            AnimatedVisibility(
                visible = editable,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                background = transition.animateColor(label = "") { state ->
                    if (state == EnterExitState.Visible) Color.Blue else Color.Gray
                }.value

                Box(
                    modifier = Modifier
                        .size(128.dp)
                        .background(background)
                )
            }

        }

        Button(modifier = Modifier.align(Alignment.Center),
            onClick = { editable = !editable }) {
            Text(text = if (editable) "Gizle" else "Goster")
        }
    }
}