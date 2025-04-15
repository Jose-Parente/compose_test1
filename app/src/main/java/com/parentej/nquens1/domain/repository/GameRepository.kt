package com.parentej.nquens1.domain.repository

import com.parentej.nquens1.domain.model.PieceType

interface GameRepository {
  /**
   * Get the high score time for the specified piece and board size.
   * @return -1 if no high score was saved before, or the previous saved value in milli seconds
   */
  suspend fun getHighScoreTime(pieceType: PieceType, boardSize: Int): Long

  suspend fun saveHighScoreTime(pieceType: PieceType, boardSize: Int, timeMillis: Long)
}