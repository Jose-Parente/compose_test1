package com.parentej.nquens1.data.local

import android.content.Context
import com.parentej.nquens1.domain.model.PieceType
import com.parentej.nquens1.domain.repository.GameRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalGameRepositoryImpl(
  private val context: Context,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : GameRepository {
  override suspend fun getHighScoreTime(pieceType: PieceType, boardSize: Int): Long =
    withContext(dispatcher) {
      val sharedPref =
        context.getSharedPreferences(pieceType.getPreferencesName(), Context.MODE_PRIVATE)
      sharedPref.getLong(boardSize.toString(), -1L)
    }

  override suspend fun saveHighScoreTime(
    pieceType: PieceType,
    boardSize: Int,
    timeMillis: Long,
  ): Unit =
    withContext(dispatcher) {
      val sharedPref =
        context.getSharedPreferences(pieceType.getPreferencesName(), Context.MODE_PRIVATE)
      with(sharedPref.edit()) {
        putLong(boardSize.toString(), timeMillis)
        commit()
      }
    }

  private fun PieceType.getPreferencesName(): String =
    when (this) {
      PieceType.QUEEN -> "queens_high_scores"
      PieceType.ROOK -> "rooks_high_scores"
      PieceType.KNIGHT -> "knights_high_scores"
    }
}