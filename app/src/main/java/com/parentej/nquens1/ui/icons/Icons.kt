package com.parentej.nquens1.ui.icons

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.parentej.nquens1.R

object Icons {

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
