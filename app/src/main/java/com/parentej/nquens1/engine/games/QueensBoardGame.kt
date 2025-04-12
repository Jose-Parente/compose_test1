package com.parentej.nquens1.engine.games

import com.parentej.nquens1.domain.model.BoardGame
import com.parentej.nquens1.domain.model.SquareDetail

class QueensBoardGame(private val size: Int) : BoardGame {


  private val rows = IntArray(size) // Y
  private val cols = IntArray(size) // X
  private val board = Array(size) { BooleanArray(size) }

  override fun togglePosition(squareIdx: Int) {
    val x = squareIdx % size
    val y = squareIdx % size

    board[y][x] = !board[y][x]

    val inc = if (board[y][x]) 1 else -1
    cols[x] += inc
    rows[y] += inc
    // TODO: Missing diagonals
  }

  override fun getAllSquares(): List<SquareDetail> {
    val res = ArrayList<SquareDetail>(size * size)
    for (y in 0 until size) {
      for (x in 0 until size) {
        val targetCount = if (board[y][x]) 1 else 0
        res.add(
          SquareDetail(
            hasPiece = board[y][x],
            isTargeted = cols[x] > targetCount || rows[y] > targetCount
          )
        )
      }
    }
    return res
  }
}