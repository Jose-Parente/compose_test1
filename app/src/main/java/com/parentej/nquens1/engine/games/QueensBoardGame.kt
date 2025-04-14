package com.parentej.nquens1.engine.games

import com.parentej.nquens1.domain.model.BoardGame
import com.parentej.nquens1.domain.model.SquareDetail

class QueensBoardGame(private val size: Int) : BoardGame {

  private val rows = IntArray(size) // Y
  private val cols = IntArray(size) // X
  private val diagonal1 = IntArray(2 * size) // Top-Left to Bottom-Right diagonal
  private val diagonal2 = IntArray(2 * size) // Top-Right to Bottom-Left diagonal
  private val board = Array(size) { BooleanArray(size) }

  override fun togglePosition(squareIdx: Int) {
    val x = squareIdx % size
    val y = squareIdx / size

    board[y][x] = !board[y][x]

    val inc = if (board[y][x]) 1 else -1
    cols[x] += inc
    rows[y] += inc
    diagonal1[x - y + size] += inc
    diagonal2[x + y] += inc
  }

  override fun getBoardState(): List<SquareDetail> {
    val res = ArrayList<SquareDetail>(size * size)
    for (y in 0 until size) {
      for (x in 0 until size) {
        val d1 = diagonal1[x - y + size]
        val d2 = diagonal2[x + y]
        res.add(
          if (board[y][x]) {
            if (cols[x] == 1 && rows[y] == 1 && d1 == 1 && d2 == 1) SquareDetail.PIECE else SquareDetail.PIECE_TARGETED
          } else if (cols[x] == 0 && rows[y] == 0 && d1 == 0 && d2 == 0) {
            SquareDetail.EMPTY
          } else {
            SquareDetail.EMPTY_TARGETED
          }
        )
      }
    }
    return res
  }
}