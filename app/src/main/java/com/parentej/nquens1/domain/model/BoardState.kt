package com.parentej.nquens1.domain.model

data class BoardState(
  val squares: List<SquareDetail>,
  val nPiecesLeft: Int,
  val isFinished: Boolean,
)
