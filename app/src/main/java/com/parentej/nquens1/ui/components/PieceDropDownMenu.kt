package com.parentej.nquens1.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.parentej.nquens1.R
import com.parentej.nquens1.domain.model.PieceType

@Composable
fun PieceDropDownMenu(
  modifier: Modifier = Modifier,
  expand: Boolean = false,
  currentPiece: PieceType,
  onBoardPieceSelected: (pieceType: PieceType) -> Unit,
) {
  var expanded by remember { mutableStateOf(expand) }
  Box(
    modifier = modifier
  ) {
    Button(
      modifier = Modifier.testTag("PieceDropDownMenu"),
      onClick = { expanded = !expanded },
      contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
      Icon(Icons.Default.ArrowDropDown, contentDescription = stringResource(R.string.board_piece))
      Icon(
        modifier = Modifier.size(32.dp),
        painter = currentPiece.toPainterResource(),
        contentDescription = currentPiece.toStringResource(),
      )
    }
    DropdownMenu(
      expanded = expanded, onDismissRequest = { expanded = false }) {
      for (piece in PieceType.entries) {
        DropdownMenuItem(
          enabled = (piece != currentPiece),
          text = { Text(text = piece.toStringResource()) },
          leadingIcon = {
            Icon(
              modifier = Modifier.size(32.dp),
              painter = piece.toPainterResource(),
              contentDescription = piece.toStringResource(),
            )
          },
          onClick = {
            expanded = false
            onBoardPieceSelected(piece)
          })
      }
    }
  }
}

@Composable
private fun PieceType.toStringResource() = stringResource(
  when (this) {
    PieceType.QUEEN -> R.string.piece_queen
    PieceType.ROOK -> R.string.piece_rook
    PieceType.KNIGHT -> R.string.piece_knight
  }
)

@Composable
private fun PieceType.toPainterResource() = painterResource(
  when (this) {
    PieceType.QUEEN -> R.drawable.queen
    PieceType.ROOK -> R.drawable.rook
    PieceType.KNIGHT -> R.drawable.knight
  }
)

@Preview
@Composable
fun PieceDropDownPreview() {
  PieceDropDownMenu(currentPiece = PieceType.QUEEN, onBoardPieceSelected = {})
}

@Preview
@Composable
fun PieceDropDownExpandedPreview() {
  PieceDropDownMenu(expand = true, currentPiece = PieceType.KNIGHT, onBoardPieceSelected = {})
}