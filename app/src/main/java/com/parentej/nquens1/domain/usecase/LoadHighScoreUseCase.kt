package com.parentej.nquens1.domain.usecase

import com.parentej.nquens1.domain.model.PieceType
import com.parentej.nquens1.domain.repository.GameRepository

class LoadHighScoreUseCase(
  private val gameRepository: GameRepository,
) {
  suspend operator fun invoke(pieceType: PieceType, boardSize: Int): Long =
    gameRepository.getHighScoreTime(pieceType, boardSize)

}