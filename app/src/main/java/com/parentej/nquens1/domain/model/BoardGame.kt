package com.parentej.nquens1.domain.model

interface BoardGame {
  fun togglePosition(x: Int, y: Int)
  fun getAllSquares(): Array<Array<SquareDetail>>
}