package com.parentej.nquens1.ui.icons

import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

val QueenIcon: ImageVector
  get() {
    if (queenIcon != null) return queenIcon!!
    queenIcon = ImageVector.Builder(
      name = "QueenIcon", defaultWidth = 24.dp, defaultHeight = 24.dp,
      viewportWidth = 24f, viewportHeight = 24f,
    ).materialPath {
      // Simplified queen shape: a crown-like structure + base
      moveTo(12f, 2f)
      lineTo(14f, 6f)
      lineTo(18f, 7f)
      lineTo(15f, 10f)
      lineTo(16f, 15f)
      horizontalLineTo(8f)
      lineTo(9f, 10f)
      lineTo(6f, 7f)
      lineTo(10f, 6f)
      close()

      moveTo(6f, 18f)
      horizontalLineTo(18f)
      verticalLineTo(20f)
      horizontalLineTo(6f)
      close()
    }.build()
    return queenIcon!!
  }

private var queenIcon: ImageVector? = null