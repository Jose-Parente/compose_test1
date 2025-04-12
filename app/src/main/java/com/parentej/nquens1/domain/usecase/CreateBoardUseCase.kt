package com.parentej.nquens1.domain.usecase

import com.parentej.nquens1.domain.model.BoardGame
import com.parentej.nquens1.domain.model.PieceType
import com.parentej.nquens1.domain.factory.BoardGameFactory

class CreateBoardUseCase(private val boardGameFactory: BoardGameFactory) {
  operator fun invoke(pieceType: PieceType, boardSize: Int): BoardGame {
    return boardGameFactory.createBoardGame(pieceType, boardSize)
  }
}