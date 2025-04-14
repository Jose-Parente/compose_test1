package com.parentej.nquens1.engine.games

import com.parentej.nquens1.domain.model.BoardGame
import com.parentej.nquens1.domain.model.BoardState
import com.parentej.nquens1.domain.model.SquareDetail

class HorsesBoardGame(private val size: Int) : BoardGame {
  // For Knights, we can put them in squares with same color.
  // 4x4 = 16 squares => 8 knights
  // 5x5 = 25 squares => 13 knights
  private var nPiecesLeft = (size * size + 1) / 2

  private val board = Array(size) { BooleanArray(size) }
  private val targeted = Array(size) { IntArray(size) }

  override fun togglePosition(squareIdx: Int) {
    val x = squareIdx % size
    val y = squareIdx / size

    if (!board[y][x] && nPiecesLeft == 0) return

    board[y][x] = !board[y][x]

    val inc = if (board[y][x]) 1 else -1
    nPiecesLeft -= inc

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

  override fun getBoardState(): BoardState {
    var isFinished = (nPiecesLeft == 0)
    val squareDetails = ArrayList<SquareDetail>(size * size)
    for (y in 0 until size) {
      for (x in 0 until size) {
        squareDetails.add(
          if (board[y][x]) {
            if (targeted[y][x] == 1) {
              SquareDetail.PIECE
            } else {
              isFinished = false
              SquareDetail.PIECE_TARGETED
            }
          } else if (targeted[y][x] == 0) {
            SquareDetail.EMPTY
          } else {
            SquareDetail.EMPTY_TARGETED
          }
        )
      }
    }
    return BoardState(squareDetails, nPiecesLeft, isFinished)
  }
}