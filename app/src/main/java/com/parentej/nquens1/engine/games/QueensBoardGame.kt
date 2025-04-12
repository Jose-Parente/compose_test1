package com.parentej.nquens1.engine.games

import com.parentej.nquens1.domain.model.BoardGame
import com.parentej.nquens1.domain.model.SquareDetail

class QueensBoardGame(private val size: Int) : BoardGame {


  private val rows = IntArray(size) // Y
  private val cols = IntArray(size) // X
  private val board = Array(size) { BooleanArray(size) }

  override fun togglePosition(x: Int, y: Int) {
    board[y][x] = !board[y][x]

    val inc = if (board[y][x]) 1 else -1
    cols[x] += inc
    rows[y] += inc
    // TODO: Missing diagonals
  }

  override fun getAllSquares(): Array<Array<SquareDetail>> {
    return Array(size) { y ->
      Array(size) { x ->
        val targetCount = if (board[y][x]) 1 else 0
        SquareDetail(
          hasPiece = board[y][x],
          isTargeted = cols[x] > targetCount || rows[y] > targetCount
        )
      }
    }
  }
}