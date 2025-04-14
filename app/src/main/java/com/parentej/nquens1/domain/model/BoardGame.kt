package com.parentej.nquens1.domain.model

interface BoardGame {
  fun togglePosition(squareIdx: Int)
  fun getBoardState(): List<SquareDetail>
}