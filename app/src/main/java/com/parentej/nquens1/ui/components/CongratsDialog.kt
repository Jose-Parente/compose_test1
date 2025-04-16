package com.parentej.nquens1.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CongratsDialog(time: String, onDismiss: () -> Unit) {
  var startAnimation by remember { mutableStateOf(false) }
  val rotation by animateFloatAsState(
    targetValue = if (startAnimation) 2 * 360f else 0f,
    animationSpec = tween(durationMillis = 1000),
    label = "fontSize"
  )

  LaunchedEffect(Unit) {
    startAnimation = true
  }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = "Congrats",
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
      )
    },
    text = {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Text(
          modifier = Modifier
            .graphicsLayer {
              rotationZ = rotation
            },
          textAlign = TextAlign.Center,
          text = "\uD83C\uDFC6", // Trophy Emoji
          fontSize = 128.sp
        )
        TimeScore(modifier = Modifier.padding(16.dp), highScore = "", time = time)
      }

    },
    confirmButton = {
      TextButton(onClick = onDismiss) {
        Text("OK")
      }
    }
  )
}

@Preview
@Composable
fun DialogPreview() {
  CongratsDialog(time = "10", onDismiss = {})
}