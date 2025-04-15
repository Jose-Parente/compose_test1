package com.parentej.nquens1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.parentej.nquens1.presentation.BoardScreen
import com.parentej.nquens1.presentation.BoardViewModel
import com.parentej.nquens1.ui.theme.NQueenV1Theme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    val appContainer = (application as MainApplication).appContainer
    val viewModel: BoardViewModel by viewModels { appContainer.boardViewModelFactory }
    setContent {
      NQueenV1Theme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          BoardScreen(modifier = Modifier.padding(innerPadding), viewModel = viewModel)
        }
      }
    }
  }
}
