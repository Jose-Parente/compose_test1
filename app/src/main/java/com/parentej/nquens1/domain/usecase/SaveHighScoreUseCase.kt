package com.parentej.nquens1.domain.usecase

import com.parentej.nquens1.domain.model.PieceType
import com.parentej.nquens1.domain.repository.GameRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveHighScoreUseCase(
  private val gameRepository: GameRepository,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
  suspend operator fun invoke(pieceType: PieceType, boardSize: Int, timeMillis: Long) {
    if (timeMillis <= 0L) return
    withContext(dispatcher) {
      val currentHighScore = gameRepository.getHighScoreTime(pieceType, boardSize)
      if (currentHighScore <= 0 || timeMillis < currentHighScore) {
        gameRepository.saveHighScoreTime(pieceType, boardSize, timeMillis)
      }
    }
  }
}