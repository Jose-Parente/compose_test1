package com.parentej.nquens1.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parentej.nquens1.domain.model.SquareDetail
import com.parentej.nquens1.ui.icons.Icons

@Composable
fun BoardScreen(modifier: Modifier = Modifier, viewModel: BoardViewModel) {
  val uiState = viewModel.uiState.collectAsStateWithLifecycle()
  Board(modifier = modifier, board = uiState.value.board) { x, y ->
    viewModel.togglePosition(x, y)
  }
}

@Composable
fun Board(
  modifier: Modifier = Modifier,
  board: Array<Array<SquareDetail>>,
  onClick: (x: Int, y: Int) -> Unit
) {
  val flatList = board.flatten()
  if (flatList.isEmpty()) return

  val boardSize = board[0].size
  LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(boardSize)) {
    items(flatList.size /*key = { idx -> flatList[idx] }*/) { idx ->
      val square = flatList[idx]
      Box(
        modifier = Modifier
          .aspectRatio(1f)
          .background(
            animateColorAsState(
              targetValue = square.getBackgroundColor(),
              animationSpec = tween(durationMillis = 1000),
            ).value
          )
          .border(1.dp, Color.Black)
          .clickable { onClick(idx % boardSize, idx / boardSize) },
        contentAlignment = Alignment.Center
      ) {
        if (square.hasPiece) {
          Icon(
            modifier = Modifier.fillMaxSize(0.50f),
            tint = if (square.isTargeted) Color.Red else Color.Black,
            imageVector = Icons.Queen,
            contentDescription = null
          )
        } else if (square.isTargeted) {
          Icon(
            modifier = Modifier.fillMaxSize(0.2f),
            imageVector = Icons.Circle,
            contentDescription = null
          )
        }
      }
    }
  }
}

fun SquareDetail.getBackgroundColor(): Color = if (isTargeted || hasPiece) Color.Yellow else Color.LightGray


@Preview
@Composable
fun BoardScreenPreview() {
  Board(board = Array(4) {
    arrayOf(
      SquareDetail(
        hasPiece = true, isTargeted = false
      ),

      )
  }) { _, _ -> }
}