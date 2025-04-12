package com.parentej.nquens1.engine.factory

import com.parentej.nquens1.domain.model.BoardGame
import com.parentej.nquens1.domain.model.PieceType
import com.parentej.nquens1.domain.factory.BoardGameFactory
import com.parentej.nquens1.engine.games.HorsesBoardGame
import com.parentej.nquens1.engine.games.QueensBoardGame
import com.parentej.nquens1.engine.games.TowersBoardGame

class BoardGameFactoryImpl: BoardGameFactory {
  override fun createBoardGame(pieceType: PieceType, size: Int): BoardGame {
    return when (pieceType) {
      PieceType.QUEEN -> QueensBoardGame(size)
      PieceType.ROOK -> TowersBoardGame(size)
      PieceType.KNIGHT -> HorsesBoardGame(size)
    }
  }
}