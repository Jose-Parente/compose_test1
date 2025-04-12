package com.parentej.nquens1.infrastructure.games

import com.parentej.nquens1.domain.model.BoardGame
import com.parentej.nquens1.domain.model.SquareDetail

class QueensBoardGame(private val size: Int) : BoardGame {


  private val rows = IntArray(size) // Y
  private val cols = IntArray(size) // X
  private val board = Array(size) { BooleanArray(size) }

  override fun togglePosition(x: Int, y: Int) {
    val inc = if (board[y][x]) 1 else -1
    cols[x] += inc
    rows[y] += inc
    // TODO: Missing diagonals
  }

  override fun getAllSquares(): Array<Array<SquareDetail>> {
    return Array(size) { y ->
      Array(size) { x ->
        SquareDetail(
          hasPiece = board[y][x],
          isValid = cols[x] == 1 && rows[y] == 1, // DO WE NEED THIS FIELD?!
          isTargeted = cols[x] > 1 || rows[y] > 1
        )
      }
    }
  }
}