package com.parentej.nquens1.presentation

import com.parentej.nquens1.domain.model.BoardState
import com.parentej.nquens1.domain.model.PieceType

data class BoardUiState(
  val boardSize: Int,
  val boardState: BoardState,
  val pieceType: PieceType,
  val highScore: String,
)
