package com.parentej.nquens1.infrastructure.games

import com.parentej.nquens1.domain.model.BoardGame
import com.parentej.nquens1.domain.model.SquareDetail

class TowersBoardGame(private val size: Int): BoardGame {
  override fun togglePosition(x: Int, y: Int) {
    TODO("Not yet implemented")
  }

  override fun getAllSquares(): Array<Array<SquareDetail>> {
    TODO("Not yet implemented")
  }
}