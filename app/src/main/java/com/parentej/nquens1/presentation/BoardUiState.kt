package com.parentej.nquens1.presentation

import com.parentej.nquens1.domain.model.PieceType
import com.parentej.nquens1.domain.model.SquareDetail

data class BoardUiState(
  val boardSize: Int,
  val board: List<SquareDetail>,
  val pieceType: PieceType,
)
