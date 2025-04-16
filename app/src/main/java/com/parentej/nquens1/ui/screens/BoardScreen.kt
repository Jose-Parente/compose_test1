package com.parentej.nquens1.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parentej.nquens1.ui.components.Board
import com.parentej.nquens1.ui.components.BoardSizeDropDownMenu
import com.parentej.nquens1.ui.components.CongratsDialog
import com.parentej.nquens1.ui.components.PieceDropDownMenu
import com.parentej.nquens1.ui.components.TimeScore
import com.parentej.nquens1.ui.viewmodels.BoardViewModel.UiState
import com.parentej.nquens1.ui.viewmodels.BoardViewModel

@Composable
fun BoardScreen(
  modifier: Modifier = Modifier,
  viewModel: BoardViewModel,
  toggleSquare: (idx: Int) -> Unit,
) {
  val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
  val timer = viewModel.elapsedTime.collectAsStateWithLifecycle().value

  if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
    BoardScreenLandscape(modifier, uiState, timer, viewModel, toggleSquare)
  } else {
    BoardScreenPortrait(modifier, uiState, timer, viewModel, toggleSquare)
  }

  if (uiState.boardState.isFinished) {
    CongratsDialog(
      time = timer,
      onDismiss = { viewModel.resetGame() }
    )
  }
}

@Composable
fun BoardScreenPortrait(
  modifier: Modifier = Modifier,
  uiState: UiState,
  timer: String,
  viewModel: BoardViewModel,
  toggleSquare: (idx: Int) -> Unit,
) {
  Column(modifier = modifier.padding(16.dp)) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
      BoardSizeDropDownMenu(
        currentSize = uiState.boardSize,
        onBoardSizeSelected = { size -> viewModel.changeBoardSize(size) })
      PieceDropDownMenu(
        currentPiece = uiState.pieceType,
        onBoardPieceSelected = { type -> viewModel.changeBoardPieceType(type) },
      )
      Button(onClick = viewModel::resetGame) {
        Text(text = "Restart")
      }
    }
    Row {
      Text(
        modifier = Modifier.padding(16.dp),
        fontWeight = FontWeight.Bold,
        text = "Moves left: ${uiState.boardState.nPiecesLeft}"
      )

      TimeScore(modifier = Modifier.padding(16.dp), highScore = uiState.highScore, time = timer)
    }

    Board(
      modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f),
      boardSize = uiState.boardSize,
      pieceType = uiState.pieceType,
      board = uiState.boardState.squares
    ) { squareIdx -> toggleSquare(squareIdx) }
  }
}

@Composable
fun BoardScreenLandscape(
  modifier: Modifier = Modifier,
  uiState: UiState,
  timer: String,
  viewModel: BoardViewModel,
  toggleSquare: (idx: Int) -> Unit,
) {
  Row(modifier = modifier.padding(horizontal = 64.dp, vertical = 8.dp)) {
    Board(
      modifier = Modifier.aspectRatio(1f),
      boardSize = uiState.boardSize,
      pieceType = uiState.pieceType,
      board = uiState.boardState.squares
    ) { squareIdx -> toggleSquare(squareIdx) }

    Column(modifier = Modifier.weight(0.4f), horizontalAlignment = Alignment.CenterHorizontally) {
      BoardSizeDropDownMenu(
        currentSize = uiState.boardSize,
        onBoardSizeSelected = { size -> viewModel.changeBoardSize(size) })
      PieceDropDownMenu(
        currentPiece = uiState.pieceType,
        onBoardPieceSelected = { type -> viewModel.changeBoardPieceType(type) })
      Button(onClick = viewModel::resetGame) {
        Text(text = "Restart")
      }
      Text(
        modifier = Modifier.padding(16.dp),
        fontWeight = FontWeight.Bold,
        text = "Moves left: ${uiState.boardState.nPiecesLeft}"
      )

      TimeScore(highScore = uiState.highScore, time = timer)
    }
  }
}
