package com.parentej.nquens1.engine.games

import com.parentej.nquens1.domain.model.BoardGame
import com.parentej.nquens1.domain.model.SquareDetail

class HorsesBoardGame(private val size: Int) : BoardGame {
  private val board = Array(size) { BooleanArray(size) }
  private val targeted = Array(size) { IntArray(size) }

  override fun togglePosition(squareIdx: Int) {
    val x = squareIdx % size
    val y = squareIdx / size

    board[y][x] = !board[y][x]

    val inc = if (board[y][x]) 1 else -1
    val dx = listOf(0,+1,+2,+2,+1,-1,-2,-2,-1)
    val dy = listOf(0,-2,-1,+1,+2,+2,+1,-1,-2)
    for (i in dx.indices) {
      val targetX = x + dx[i]
      val targetY = y + dy[i]
      if (targetX >= 0 && targetY >= 0 && targetX < size && targetY < size) {
        targeted[targetY][targetX] += inc
      }
    }
  }

  override fun getGameState(): List<SquareDetail> {
    val res = ArrayList<SquareDetail>(size * size)
    for (y in 0 until size) {
      for (x in 0 until size) {
        res.add(
          if (board[y][x]) {
            if (targeted[y][x] == 1) SquareDetail.PIECE else SquareDetail.PIECE_TARGETED
          } else if (targeted[y][x] == 0) {
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