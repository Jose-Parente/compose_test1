package com.parentej.nquens1

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.parentej.nquens1.ui.screens.BoardScreen
import com.parentej.nquens1.ui.viewmodels.BoardViewModel
import com.parentej.nquens1.ui.theme.NQueenV1Theme

class MainActivity : ComponentActivity() {
  private var soundPool: SoundPool? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    createSoundPool()
    val clickSoundId = soundPool?.load(this, R.raw.click, 1) ?: 0

    val appContainer = (application as MainApplication).appContainer
    val viewModel: BoardViewModel by viewModels { appContainer.boardViewModelFactory }
    setContent {
      NQueenV1Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          BoardScreen(modifier = Modifier.padding(innerPadding), viewModel = viewModel) { idx ->
            viewModel.togglePosition(idx)
            soundPool?.play(clickSoundId, 1f, 1f, 1, 0, 1f)
          }
        }
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    soundPool?.release()
  }

  private fun createSoundPool() {
    val attributes = AudioAttributes.Builder()
      .setUsage(AudioAttributes.USAGE_GAME)
      .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
      .build()

    soundPool = SoundPool.Builder()
      .setAudioAttributes(attributes)
      .build()
  }
}
