package com.parentej.nquens1.presentation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parentej.nquens1.R
import com.parentej.nquens1.domain.model.PieceType
import com.parentej.nquens1.domain.model.SquareDetail

@Composable
fun BoardScreen(modifier: Modifier = Modifier, viewModel: BoardViewModel) {
  val uiState = viewModel.uiState.collectAsStateWithLifecycle()
  Column(modifier = modifier) {
    Row {
      SelectBoardSizeMenu(onBoardSizeSelected = { size -> viewModel.changeBoardSize(size) })
      SelectBoardPieceMenu(onBoardPieceSelected = { type -> viewModel.changeBoardPieceType(type) })
    }

    Board(
      boardSize = uiState.value.boardSize,
      pieceType = uiState.value.pieceType,
      board = uiState.value.board
    ) { squareIdx ->
      viewModel.togglePosition(squareIdx)
    }
  }
}

@Composable
fun Board(
  modifier: Modifier = Modifier,
  boardSize: Int,
  pieceType: PieceType,
  board: List<SquareDetail>,
  onClick: (squareIdx: Int) -> Unit,
) {
  val pieceIcon = ImageVector.vectorResource(
    when (pieceType) {
      PieceType.QUEEN -> R.drawable.queen
      PieceType.ROOK -> R.drawable.rook
      PieceType.KNIGHT -> R.drawable.knight
    }
  )

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
          .clickable { onClick(idx) }, contentAlignment = Alignment.Center
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

@Composable
fun SelectBoardSizeMenu(
  modifier: Modifier = Modifier, onBoardSizeSelected: (boardSize: Int) -> Unit
) {
  var expanded by remember { mutableStateOf(false) }
  Box(
    modifier = modifier.padding(16.dp)
  ) {
    Button(onClick = { expanded = !expanded }, contentPadding = PaddingValues(horizontal = 8.dp)) {
      Icon(Icons.Default.ArrowDropDown, contentDescription = stringResource(R.string.board_size))
      Text(text = stringResource(R.string.board_size))
    }
    DropdownMenu(
      expanded = expanded, onDismissRequest = { expanded = false }) {
      for (i in 4..8) {
        DropdownMenuItem(text = { Text("$i x $i") }, onClick = {
          expanded = false
          onBoardSizeSelected(i)
        })
      }
    }
  }
}

@Composable
fun SelectBoardPieceMenu(
  modifier: Modifier = Modifier, onBoardPieceSelected: (pieceType: PieceType) -> Unit
) {
  var expanded by remember { mutableStateOf(false) }
  Box(
    modifier = modifier.padding(16.dp)
  ) {
    Button(onClick = { expanded = !expanded }, contentPadding = PaddingValues(horizontal = 8.dp)) {
      Icon(Icons.Default.ArrowDropDown, contentDescription = stringResource(R.string.board_piece))
      Text(text = stringResource(R.string.board_piece))
    }
    DropdownMenu(
      expanded = expanded, onDismissRequest = { expanded = false }) {
      for (piece in PieceType.entries) {
        DropdownMenuItem(
          text = { Text(text = piece.toStringResource()) },
          onClick = {
            expanded = false
            onBoardPieceSelected(piece)
          })
      }
    }
  }
}

fun SquareDetail.getBackgroundColor(): Color =
  if (this == SquareDetail.EMPTY) Color.LightGray else Color.Yellow

@Composable
fun PieceType.toStringResource() =
  stringResource(
    when (this) {
      PieceType.QUEEN -> R.string.piece_queen
      PieceType.ROOK -> R.string.piece_rook
      PieceType.KNIGHT -> R.string.piece_knight
    }
  )

@Preview
@Composable
fun BoardScreenPreview() {
  Board(
    boardSize = 1, pieceType = PieceType.QUEEN,
    board = listOf(
      SquareDetail.PIECE,
    ),
    onClick = { _ -> },
  )
}