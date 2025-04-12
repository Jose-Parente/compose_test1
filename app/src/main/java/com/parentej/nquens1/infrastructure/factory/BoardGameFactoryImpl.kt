package com.parentej.nquens1.infrastructure.factory

import com.parentej.nquens1.domain.model.BoardGame
import com.parentej.nquens1.domain.model.PieceType
import com.parentej.nquens1.domain.factory.BoardGameFactory
import com.parentej.nquens1.infrastructure.games.HorsesBoardGame
import com.parentej.nquens1.infrastructure.games.QueensBoardGame
import com.parentej.nquens1.infrastructure.games.TowersBoardGame

class BoardGameFactoryImpl: BoardGameFactory {
  override fun createBoardGame(pieceType: PieceType, size: Int): BoardGame {
    return when (pieceType) {
      PieceType.QUEEN -> QueensBoardGame(size)
      PieceType.TOWER -> TowersBoardGame(size)
      PieceType.HORSE -> HorsesBoardGame(size)
    }
  }
}