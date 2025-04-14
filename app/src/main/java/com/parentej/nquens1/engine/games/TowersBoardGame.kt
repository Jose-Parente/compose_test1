package com.parentej.nquens1.engine.games

import com.parentej.nquens1.domain.model.BoardGame
import com.parentej.nquens1.domain.model.BoardState
import com.parentej.nquens1.domain.model.SquareDetail

class TowersBoardGame(private val size: Int) : BoardGame {
  private var nPiecesLeft = size
  private val rows = IntArray(size) // Y
  private val cols = IntArray(size) // X
  private val board = Array(size) { BooleanArray(size) }

  override fun togglePosition(squareIdx: Int) {
    val x = squareIdx % size
    val y = squareIdx / size

    if (!board[y][x] && nPiecesLeft == 0) return

    board[y][x] = !board[y][x]

    val inc = if (board[y][x]) 1 else -1
    nPiecesLeft -= inc
    cols[x] += inc
    rows[y] += inc
  }

  override fun getBoardState(): BoardState {
    val squareDetails = ArrayList<SquareDetail>(size * size)
    var isFinished = (nPiecesLeft == 0)
    for (y in 0 until size) {
      for (x in 0 until size) {
        squareDetails.add(
          if (board[y][x]) {
            if (cols[x] == 1 && rows[y] == 1) {
              SquareDetail.PIECE
            } else {
              isFinished = false
              SquareDetail.PIECE_TARGETED
            }
          } else if (cols[x] == 0 && rows[y] == 0) {
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