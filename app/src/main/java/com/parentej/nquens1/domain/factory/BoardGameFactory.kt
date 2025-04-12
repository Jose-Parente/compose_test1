package com.parentej.nquens1.domain.factory

import com.parentej.nquens1.domain.model.BoardGame
import com.parentej.nquens1.domain.model.PieceType

interface BoardGameFactory {
  fun createBoardGame(pieceType: PieceType, size: Int): BoardGame
}