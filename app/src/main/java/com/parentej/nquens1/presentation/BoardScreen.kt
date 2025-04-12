package com.parentej.nquens1.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parentej.nquens1.domain.model.SquareDetail
import com.parentej.nquens1.ui.icons.QueenIcon

@Composable
fun BoardScreen(modifier: Modifier = Modifier, viewModel: BoardViewModel) {
  val uiState = viewModel.uiState.collectAsStateWithLifecycle()
  Board(modifier = modifier, board = uiState.value.board)
}

@Composable
fun Board(modifier: Modifier = Modifier, board: Array<Array<SquareDetail>>) {
  val flatList = board.flatten()
  if (flatList.isEmpty()) return

  LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(board[0].size)) {
    items(flatList.size /*key = { idx -> flatList[idx] }*/) {
      Box(
        modifier = Modifier
          .aspectRatio(1f)
          .background(Color.LightGray)
          .border(1.dp, Color.Black),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          modifier = Modifier.fillMaxSize(0.50f),
          imageVector = QueenIcon, contentDescription = null
        )
      }
//      Text(
//        modifier = Modifier
//          .aspectRatio(1f)
//          .background(Color.LightGray)
//          .border(1.dp, Color.Black).al(Alignment.CenterVertically),
//        textAlign = TextAlign.Center,
//        text = "A"
//      )
    }
  }
}

@Preview
@Composable
fun BoardScreenPreview() {
  Board(board = Array(4) {
    arrayOf(
      SquareDetail(
        hasPiece = true,
        isTargeted = false
      ),

      )
  })
}