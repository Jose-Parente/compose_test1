package com.parentej.nquens1.presentation

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.parentej.nquens1.R
import com.parentej.nquens1.domain.model.PieceType
import com.parentej.nquens1.domain.model.SquareDetail
import kotlin.math.min

@Composable
fun BoardScreen(modifier: Modifier = Modifier, viewModel: BoardViewModel) {
  if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
    BoardScreenLandscape(modifier = modifier, viewModel = viewModel)
  } else {
    BoardScreenPortrait(modifier = modifier, viewModel = viewModel)
  }
}

@Composable
fun BoardScreenPortrait(modifier: Modifier = Modifier, viewModel: BoardViewModel) {
  val uiState = viewModel.uiState.collectAsStateWithLifecycle().value
  val timer = viewModel.elapsedTime.collectAsStateWithLifecycle().value

  Column(modifier = modifier) {
    Row {
      SelectBoardSizeMenu(
        currentSize = uiState.boardSize,
        onBoardSizeSelected = { size -> viewModel.changeBoardSize(size) })
      SelectBoardPieceMenu(
        currentPiece = uiState.pieceType,
        onBoardPieceSelected = { type -> viewModel.changeBoardPieceType(type) },
      )
    }

    Row {
      Text(
        modifier = Modifier.padding(16.dp),
        text = "Moves left: ${uiState.boardState.nPiecesLeft}"
      )
      Text(modifier = Modifier.padding(16.dp), text = "Time: $timer")
    }

    Text(modifier = Modifier.padding(16.dp), text = "High Score: ")

    Board(
      modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(1f),
      boardSize = uiState.boardSize,
      pieceType = uiState.pieceType,
      board = uiState.boardState.squares
    ) { squareIdx -> viewModel.togglePosition(squareIdx) }
  }
}

@Composable
fun BoardScreenLandscape(modifier: Modifier = Modifier, viewModel: BoardViewModel) {
  val uiState = viewModel.uiState.collectAsStateWithLifecycle()
  Row(modifier = modifier) {
    Column {
      SelectBoardSizeMenu(
        currentSize = uiState.value.boardSize,
        onBoardSizeSelected = { size -> viewModel.changeBoardSize(size) })
      SelectBoardPieceMenu(
        currentPiece = uiState.value.pieceType,
        onBoardPieceSelected = { type -> viewModel.changeBoardPieceType(type) })
    }

    Board(
      modifier = Modifier
        .fillMaxHeight()
        .aspectRatio(1f),
      boardSize = uiState.value.boardSize,
      pieceType = uiState.value.pieceType,
      board = uiState.value.boardState.squares
    ) { squareIdx ->
      viewModel.togglePosition(squareIdx)
    }
  }
}

@Composable
fun Board(
  modifier: Modifier = Modifier,
  boardSize: Int,
  pieceType: PieceType,
  board: List<SquareDetail>,
  onClick: (squareIdx: Int) -> Unit,
) {
  val pieceIcon = ImageVector.vectorResource(
    when (pieceType) {
      PieceType.QUEEN -> R.drawable.queen
      PieceType.ROOK -> R.drawable.rook
      PieceType.KNIGHT -> R.drawable.knight
    }
  )
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
  val pieceIcon = ImageVector.vectorResource(
    when (pieceType) {
      PieceType.QUEEN -> R.drawable.queen
      PieceType.ROOK -> R.drawable.rook
      PieceType.KNIGHT -> R.drawable.knight
    }
  )

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

@Composable
fun SelectBoardSizeMenu(
  modifier: Modifier = Modifier, currentSize: Int, onBoardSizeSelected: (boardSize: Int) -> Unit
) {
  var expanded by remember { mutableStateOf(false) }
  Box(
    modifier = modifier.padding(16.dp)
  ) {
    Button(onClick = { expanded = !expanded }, contentPadding = PaddingValues(horizontal = 8.dp)) {
      Icon(Icons.Default.ArrowDropDown, contentDescription = stringResource(R.string.board_size))
      Text(text = "$currentSize x $currentSize")
    }
    DropdownMenu(
      expanded = expanded, onDismissRequest = { expanded = false }) {
      for (i in 4..8) {
        DropdownMenuItem(
          enabled = (i != currentSize),
          text = { Text(text = "$i x $i") },
          onClick = {
            expanded = false
            onBoardSizeSelected(i)
          })
      }
    }
  }
}

@Composable
fun SelectBoardPieceMenu(
  modifier: Modifier = Modifier,
  currentPiece: PieceType,
  onBoardPieceSelected: (pieceType: PieceType) -> Unit,
) {
  var expanded by remember { mutableStateOf(false) }
  Box(
    modifier = modifier.padding(16.dp)
  ) {
    Button(
      modifier = Modifier,
      onClick = { expanded = !expanded },
      contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
      Icon(Icons.Default.ArrowDropDown, contentDescription = stringResource(R.string.board_piece))
      Text(text = currentPiece.toStringResource())
    }
    DropdownMenu(
      expanded = expanded, onDismissRequest = { expanded = false }) {
      for (piece in PieceType.entries) {
        DropdownMenuItem(
          enabled = (piece != currentPiece),
          text = { Text(text = piece.toStringResource()) },
          onClick = {
            expanded = false
            onBoardPieceSelected(piece)
          })
      }
    }
  }
}

fun SquareDetail.getBackgroundColor(): Color =
  if (this == SquareDetail.EMPTY) Color.LightGray else Color.Green

@Composable
fun PieceType.toStringResource() = stringResource(
  when (this) {
    PieceType.QUEEN -> R.string.piece_queen
    PieceType.ROOK -> R.string.piece_rook
    PieceType.KNIGHT -> R.string.piece_knight
  }
)

@Preview
@Composable
fun BoardScreenPreview() {
  Board(
    boardSize = 1, pieceType = PieceType.QUEEN,
    board = listOf(
      SquareDetail.PIECE,
    ),
    onClick = { _ -> },
  )
}