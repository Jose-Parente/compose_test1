package com.parentej.nquens1.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.parentej.nquens1.R
import com.parentej.nquens1.domain.model.PieceType
import com.parentej.nquens1.domain.model.SquareDetail
import kotlin.math.min

@Composable
fun Board(
  modifier: Modifier = Modifier,
  boardSize: Int,
  pieceType: PieceType,
  board: List<SquareDetail>,
  onClick: (squareIdx: Int) -> Unit,
) {
  val pieceIcon = pieceType.toVectorResource()
  val painter = rememberVectorPainter(image = pieceIcon)

  val animatedColors = board.map {
    animateColorAsState(
      targetValue = it.getBackgroundColor(), animationSpec = tween(durationMillis = 1000)
    )
  }

  Canvas(modifier = modifier.pointerInput(boardSize) {
    detectTapGestures { p ->
      val squareSize = min(size.width, size.height) / boardSize
      val idx = (p.y.toInt() / squareSize) * boardSize + (p.x.toInt() / squareSize)
      onClick(idx)
    }
  }) {
    val squareSize = size.minDimension / boardSize

    for (y in 0 until boardSize) {
      for (x in 0 until boardSize) {
        val idx = y * boardSize + x
        val square = board[idx]
        drawRect(
          color = animatedColors[idx].value,
          topLeft = Offset(x * squareSize, y * squareSize),
          size = Size(squareSize, squareSize),
        )
        if (square == SquareDetail.PIECE || square == SquareDetail.PIECE_TARGETED) {
          translate(x * squareSize + 0.25f * squareSize, y * squareSize + 0.25f * squareSize) {
            with(painter) {
              val tint = if (square == SquareDetail.PIECE_TARGETED) Color.Red else Color.Black
              draw(
                size = Size(0.5f * squareSize, 0.5f * squareSize),
                colorFilter = ColorFilter.tint(tint, BlendMode.SrcIn)
              )
            }
          }
        }
        if (square == SquareDetail.EMPTY_TARGETED) {
          drawCircle(
            color = Color.Black,
            radius = 4.dp.toPx(),
            center = Offset(x * squareSize + 0.5f * squareSize, y * squareSize + 0.5f * squareSize)
          )
        }
      }
    }

    for (x in 0 until boardSize) {
      drawLine(
        color = Color.Black,
        start = Offset(x = x * squareSize, y = 0f),
        end = Offset(x = x * squareSize, y = boardSize * squareSize),
        strokeWidth = 1f
      )
    }

    for (y in 0 until boardSize) {
      drawLine(
        color = Color.Black,
        start = Offset(x = 0f, y = y * squareSize),
        end = Offset(x = boardSize * squareSize, y = y * squareSize),
        strokeWidth = 1f
      )
    }
  }
}

@Composable
fun Board2(
  modifier: Modifier = Modifier,
  boardSize: Int,
  pieceType: PieceType,
  board: List<SquareDetail>,
  onClick: (squareIdx: Int) -> Unit,
) {
  val pieceIcon = pieceType.toVectorResource()

  LazyVerticalGrid(modifier = modifier, columns = GridCells.Fixed(boardSize)) {
    items(board.size) { idx ->
      val square = board[idx]
      Box(
        modifier = Modifier
          .aspectRatio(1f)
          .background(
            animateColorAsState(
              targetValue = square.getBackgroundColor(),
              animationSpec = tween(durationMillis = 1000),
            ).value
          )
          .border(1.dp, Color.Black)
          .drawBehind {
            if (square == SquareDetail.EMPTY_TARGETED) {
              drawCircle(
                color = Color.Black,
                radius = 4.dp.toPx(),
                center = Offset(size.width / 2, size.height / 2)
              )
            }
          }
          .clickable { onClick(idx) }, contentAlignment = Alignment.Center
      ) {
        if (square == SquareDetail.PIECE || square == SquareDetail.PIECE_TARGETED) {
          Icon(
            modifier = Modifier.fillMaxSize(0.50f),
            tint = if (square == SquareDetail.PIECE_TARGETED) Color.Red else Color.Black,
            imageVector = pieceIcon,
            contentDescription = null
          )
        }
      }
    }
  }
}

private fun SquareDetail.getBackgroundColor(): Color =
  if (this == SquareDetail.EMPTY) Color.LightGray else Color.Green

@Composable
fun PieceType.toVectorResource() =
  ImageVector.vectorResource(
    when (this) {
      PieceType.QUEEN -> R.drawable.queen
      PieceType.ROOK -> R.drawable.rook
      PieceType.KNIGHT -> R.drawable.knight
    }
  )

@Preview
@Composable
fun BoardScreenPreview() {
  Board(
    modifier = Modifier.fillMaxSize(),
    boardSize = 2, pieceType = PieceType.QUEEN,
    board = listOf(
      SquareDetail.PIECE,
      SquareDetail.EMPTY,
      SquareDetail.EMPTY_TARGETED,
      SquareDetail.PIECE_TARGETED,
    ),
    onClick = { },
  )
}

@Preview
@Composable
fun Board2ScreenPreview() {
  Board2(
    modifier = Modifier.fillMaxSize(),
    boardSize = 2, pieceType = PieceType.QUEEN,
    board = listOf(
      SquareDetail.PIECE,
      SquareDetail.EMPTY,
      SquareDetail.EMPTY_TARGETED,
      SquareDetail.PIECE_TARGETED,
    ),
    onClick = { },
  )
}