package com.parentej.nquens1.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.parentej.nquens1.R

@Composable
fun BoardSizeDropDownMenu(
  modifier: Modifier = Modifier,
  expand: Boolean = false,
  currentSize: Int,
  onBoardSizeSelected: (boardSize: Int) -> Unit,
) {
  var expanded by remember { mutableStateOf(expand) }
  Box(
    modifier = modifier
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

@Preview
@Composable
fun BoardSizeDropDownPreview() {
  BoardSizeDropDownMenu(currentSize = 4, onBoardSizeSelected = {})
}

@Preview
@Composable
fun BoardSizeDropDownExpandedPreview() {
  BoardSizeDropDownMenu(expand = true, currentSize = 4, onBoardSizeSelected = {})
}