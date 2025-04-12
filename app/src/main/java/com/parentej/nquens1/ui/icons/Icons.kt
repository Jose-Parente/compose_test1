package com.parentej.nquens1.ui.icons

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

object Icons {
  val Queen: ImageVector
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


  val Circle: ImageVector
    get() {
      if (filledCircle != null) return filledCircle!!
      filledCircle = materialIcon(name = "CircleIcon") {
        materialPath {
          // Draw a filled circle
//        moveTo(12f, 12f)  // Start center
          moveTo(12.0f, 2.0f)
          curveTo(6.48f, 2.0f, 2.0f, 6.48f, 2.0f, 12.0f)
          reflectiveCurveToRelative(4.48f, 10.0f, 10.0f, 10.0f)
          reflectiveCurveToRelative(10.0f, -4.48f, 10.0f, -10.0f)
          reflectiveCurveTo(17.52f, 2.0f, 12.0f, 2.0f)
          close()
        }
      }
      return filledCircle!!
    }

  private var queenIcon: ImageVector? = null
  private var filledCircle: ImageVector? = null
}
