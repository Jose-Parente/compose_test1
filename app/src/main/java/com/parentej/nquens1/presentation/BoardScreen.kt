package com.parentej.nquens1.presentation

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.parentej.nquens1.domain.model.SquareDetail

@Composable
fun BoardScreen(modifier: Modifier = Modifier, uiState: BoardUiState) {

}

@Composable
fun Board(modifier: Modifier = Modifier, board: Array<Array<SquareDetail>>) {
  LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(board[0].size)) { }
}

@Preview
@Composable
fun BoardScreenPreview() {
  Board(board = Array(4) {
    arrayOf(
      SquareDetail(
        hasPiece = true,
        isValid = true,
        isTargeted = false
      ),

    )
  })
}