package com.parentej.nquens1.presentation

import com.parentej.nquens1.domain.model.PieceType
import com.parentej.nquens1.domain.model.SquareDetail

data class BoardUiState(
  val board: Array<Array<SquareDetail>>,
  val pieceType: PieceType,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as BoardUiState

    if (!board.contentDeepEquals(other.board)) return false
    if (pieceType != other.pieceType) return false

    return true
  }

  override fun hashCode(): Int {
    var result = board.contentDeepHashCode()
    result = 31 * result + pieceType.hashCode()
    return result
  }
}
