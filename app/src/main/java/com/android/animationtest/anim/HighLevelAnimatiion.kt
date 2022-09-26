package com.android.animationtest.anim

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.ExperimentalTransitionApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.createChildTransition
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.android.animationtest.R


/**
 * Animated Visibility
 * */

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

/**
 * Animated * AsState
 * */

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

/**
 * Animated Content
 * */

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ACExample() {
    Row {
        var count by remember { mutableStateOf(0) }
        Button(onClick = { count++ }) {
            Text("Add")
        }
        AnimatedContent(targetState = count) { targetCount ->
            // Make sure to use `targetCount`, not `count`.
            Text(text = "Count: $targetCount")
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ACCustomTransition() {
    Row {
        var count by remember { mutableStateOf(0) }
        Button(onClick = { count++ }) {
            Text("Add")
        }
        AnimatedContent(
            targetState = count,
            transitionSpec = {
                // Compare the incoming number with the previous number.
                if (targetState > initialState) {
                    // If the target number is larger, it slides up and fades in
                    // while the initial (smaller) number slides up and fades out.
                    slideInVertically { height -> height } + fadeIn() with
                            slideOutVertically { height -> -height } + fadeOut()
                } else {
                    // If the target number is smaller, it slides down and fades in
                    // while the initial number slides down and fades out.
                    slideInVertically { height -> -height } + fadeIn() with
                            slideOutVertically { height -> height } + fadeOut()
                }.using(
                    // Disable clipping since the faded slide-in/out should
                    // be displayed out of bounds.
                    SizeTransform(clip = false)
                )
            }
        ) { targetCount ->
            Text(text = "$targetCount")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun ACSizeTransform() {
    var expanded by remember { mutableStateOf(false) }
    Surface(
        color = MaterialTheme.colors.primary,
        onClick = { expanded = !expanded }
    ) {
        AnimatedContent(
            targetState = expanded,
            transitionSpec = {
                fadeIn(animationSpec = tween(150, 150)) with
                        fadeOut(animationSpec = tween(150)) using
                        SizeTransform { initialSize, targetSize ->
                            if (targetState) {
                                keyframes {
                                    // Expand horizontally first.
                                    IntSize(targetSize.width, initialSize.height) at 150
                                    durationMillis = 300
                                }
                            } else {
                                keyframes {
                                    // Shrink vertically first.
                                    IntSize(initialSize.width, targetSize.height) at 150
                                    durationMillis = 300
                                }
                            }
                        }
            }
        ) { targetExpanded ->
            if (targetExpanded) {
                Expanded()
            } else {
                ContentIcon()
            }
        }
    }
}

@Composable
private fun Expanded() {
    Text(text = "sd;fsflsd;flsfs;lfksdfksdfjsldkfjsdlkfsdlfksdlfkjslfjslfksdjflskdjflsdkjflskjflsdkfjlskfsjdflskfjsldkfjsldkfjsldkfjsldkfjsldkfjsldkfjsldfjsldkfjlsdjflsdkjflskdf")
}

@Composable
private fun ContentIcon() {
    Icon(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "")
}


/**
 * Animate Content Size
 * */

@Composable
fun AnimateContentSize() {
    var size by remember {
        mutableStateOf(false)
    }
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column {
            Box(
                modifier = Modifier
                    .background(Color.Blue)
                    .animateContentSize()
                    .size(if (size) 120.dp else 240.dp)
            )
        }

        Button(modifier = Modifier.align(Alignment.Center),
            onClick = { size = !size }) {
            Text(text = "Degis")
        }
    }
}

/**
 * CrossFade
 * */

@Composable
fun CrossFade() {
    var size by remember {
        mutableStateOf(false)
    }
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()

    ) {
        Crossfade(targetState = size) { screen ->
            when (screen) {
                true -> Text("Page A")
                false -> Text("Page B")
            }
        }

        Button(modifier = Modifier.align(Alignment.Center),
            onClick = { size = !size }) {
            Text(text = "Degis")
        }
    }
}

/**
 * Update Transition
 * */

enum class BoxState {
    Collapsed,
    Expanded
}

@OptIn(ExperimentalTransitionApi::class)
@Composable
fun UpdateTransitionEasy() {
    var currentState by remember { mutableStateOf(BoxState.Collapsed) }
    val transition = updateTransition(currentState, label = "")
    val size by transition.animateDp(label = "") {
        when (it) {
            BoxState.Collapsed -> 100.dp
            else -> 50.dp
        }
    }

    val color by transition.animateColor(label = "") {
        when (it) {
            BoxState.Collapsed -> Color.Red
            else -> Color.Blue
        }
    }


    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()

    ) {
        Box(
            modifier = Modifier
                .size(size)
                .background(color)
        )

        ChildTransitionCompose(transition = transition.createChildTransition() {
            it == BoxState.Collapsed
        })

        Button(modifier = Modifier.align(Alignment.Center),
            onClick = {
                currentState =
                    if (currentState == BoxState.Collapsed) BoxState.Expanded else BoxState.Collapsed
            }) {
            Text(text = "Degis")
        }
    }
}

//--------------------------

@Composable
fun ChildTransitionCompose(transition: Transition<Boolean>) {
    val size by transition.animateDp(label = "") {
        if (it) 50.dp else 25.dp
    }

    val color by transition.animateColor(label = "") {
        if (it) Color.Green else Color.Magenta
    }
    Box(
        modifier = Modifier
            .size(size)
            .background(color)
    )
}

//--------------------------
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TransitionWithAnimatedVisibilityAndAnimatedContent() {
    var currentState by remember { mutableStateOf(true) }
    val transition = updateTransition(currentState, label = "")

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column() {
            transition.AnimatedVisibility(visible = { targetSelected -> targetSelected }) {
                Text(text = "Animated Visibility Wity Transition")
            }
            Spacer(modifier = Modifier.height(50.dp))
            transition.AnimatedContent() { targetState ->
                if (targetState) {
                    Text(text = "Phone Text")
                } else {
                    Icon(imageVector = Icons.Default.Phone, contentDescription = "Phone")
                }
            }
        }

        Button(modifier = Modifier.align(Alignment.Center),
            onClick = {
                currentState = !currentState
            }) {
            Text(text = "Degis")
        }
    }
}
//--------------------------

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EncapsulatedTransition() {
    var currentState by remember { mutableStateOf(BoxState.Expanded) }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()

    ) {
        AnimatingBox(boxState = currentState)

        Button(modifier = Modifier.align(Alignment.Center),
            onClick = {
                currentState =
                    if (currentState == BoxState.Collapsed) BoxState.Expanded else BoxState.Collapsed
            }) {
            Text(text = "Degis")
        }
    }
}

@Composable
fun AnimatingBox(boxState: BoxState) {
    val transitionData = updateTransitionData(boxState)
    // UI tree
    Box(
        modifier = Modifier
            .background(transitionData.color)
            .size(transitionData.size)
    )
}

// Holds the animation values.
private class TransitionData(
    color: State<Color>,
    size: State<Dp>
) {
    val color by color
    val size by size
}

// Create a Transition and return its animation values.
@Composable
private fun updateTransitionData(boxState: BoxState): TransitionData {
    val transition = updateTransition(boxState)
    val color = transition.animateColor(label = "") { state ->
        when (state) {
            BoxState.Collapsed -> Color.Gray
            BoxState.Expanded -> Color.Red
        }
    }
    val size = transition.animateDp(label = "") { state ->
        when (state) {
            BoxState.Collapsed -> 64.dp
            BoxState.Expanded -> 128.dp
        }
    }
    return remember(transition) { TransitionData(color, size) }
}

/**
 * Remember Infinite Transition
 * */


@Composable
fun RememberInfiniteTransition(){
    val infiniteTransition = rememberInfiniteTransition()
    val color by infiniteTransition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Green,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(Modifier.fillMaxSize().background(color))
}