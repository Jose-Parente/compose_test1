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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parentej.nquens1.R
import com.parentej.nquens1.domain.model.SquareDetail

@Composable
fun BoardScreen(modifier: Modifier = Modifier, viewModel: BoardViewModel) {
  val uiState = viewModel.uiState.collectAsStateWithLifecycle()
  Board(
    modifier = modifier,
    boardSize = uiState.value.boardSize,
    board = uiState.value.board
  ) { squareIdx ->
    viewModel.togglePosition(squareIdx)
  }
}

@Composable
fun Board(
  modifier: Modifier = Modifier,
  boardSize: Int,
  board: List<SquareDetail>,
  onClick: (squareIdx: Int) -> Unit
) {
  val pieceIcon = ImageVector.vectorResource(R.drawable.rook)

  LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(boardSize)) {
    items(board.size) { idx ->
      val square = board[idx]
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
          .drawBehind {
            if (square == SquareDetail.EMPTY_TARGETED) {
              drawCircle(
                color = Color.Black,
                radius = 4.dp.toPx(),
                center = Offset(size.width / 2, size.height / 2)
              )
            }
          }
          .clickable { onClick(idx) },
        contentAlignment = Alignment.Center
      ) {
        if (square == SquareDetail.PIECE || square == SquareDetail.PIECE_TARGETED) {
          Icon(
            modifier = Modifier.fillMaxSize(0.50f),
            tint = if (square == SquareDetail.PIECE_TARGETED) Color.Red else Color.Black,
            imageVector = pieceIcon,
            contentDescription = null
          )
        }
      }
    }
  }
}

fun SquareDetail.getBackgroundColor(): Color =
  if (this == SquareDetail.EMPTY) Color.LightGray else Color.Yellow


@Preview
@Composable
fun BoardScreenPreview() {
  Board(
    boardSize = 1, board = listOf(
      SquareDetail.PIECE,
    )
  ) { _ -> }
}