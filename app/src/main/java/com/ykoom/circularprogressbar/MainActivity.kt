package com.ykoom.circularprogressbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressBar(number = 100, percentage = 0.8f)
            }
        }
    }
}

@Composable
fun CircularProgressBar(
    number: Int,
    percentage: Float,
    fontSize: TextUnit = 28.sp,
    color: Color = Color.Green,
    strokeWidth: Dp = 8.dp,
    radius: Dp = 50.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    /*
    * Two things to be animated:
    * 1- Number
    * 2- Arc stroke
    * */
    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val curPercentage by animateFloatAsState(
        targetValue = if(animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )

    // This coroutine will change targetValue to percentage thus triggering the animation
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        modifier = Modifier.size(radius * 2),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(radius * 2)) {
            drawArc(
                color = color,
                startAngle = 270f,
                sweepAngle = 360 * curPercentage,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = (curPercentage * number).toInt().toString(),
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
fun PreviewCircularProgressBar() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressBar(number = 100, percentage = 0.8f)
    }
}