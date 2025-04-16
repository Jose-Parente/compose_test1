package com.parentej.nquens1.ui.components

import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TimeScore(modifier: Modifier = Modifier, highScore: String, time: String) {
  val highScoreValue = if (highScore.isBlank()) "n/a" else "${highScore}s"
  val timeOrHighScore = if (time.isBlank()) "High Score: $highScoreValue" else "Time: ${time}s"
  Text(modifier = modifier, fontWeight = FontWeight.Bold, text = timeOrHighScore)
}

@Preview
@Composable
fun HighScorePreview() {
  TimeScore(modifier = Modifier.background(Color.White), highScore = "10", time = "")
}

@Preview
@Composable
fun HighScoreNAPreview() {
  TimeScore(modifier = Modifier.background(Color.White), highScore = "", time = "")
}

@Preview
@Composable
fun TimeScorePreview() {
  TimeScore(modifier = Modifier.background(Color.White), highScore = "", time = "10")
}

