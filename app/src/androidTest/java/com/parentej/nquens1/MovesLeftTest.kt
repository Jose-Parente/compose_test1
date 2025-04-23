package com.parentej.nquens1

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.isNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

private const val MIN_BOARD_SIZE = 4
private const val MAX_BOARD_SIZE = 8

@RunWith(Parameterized::class)
class MovesLeftTest(private val boardSize: Int) {
  companion object {
    @JvmStatic
    @Parameterized.Parameters(name = "Board Size = {0}")
    fun data() = (MIN_BOARD_SIZE .. MAX_BOARD_SIZE).toList()
  }

  @get:Rule
  val composeTestRule = createAndroidComposeRule<MainActivity>()

  @Test
  fun checkQueensLeftMovesForBoardSize() {
    selectBoardSize(boardSize)

    composeTestRule.onNodeWithText("Moves left: $boardSize")
      .assertIsDisplayed()
  }

  @Test
  fun checkRooksLeftMovesForBoardSize() {
    selectBoardSize(boardSize)
    selectPiece("Rooks")

    composeTestRule.onNodeWithText("Moves left: $boardSize")
      .assertIsDisplayed()
  }

  @Test
  fun checkKnightsLeftMovesForBoardSize() {
    selectBoardSize(boardSize)
    selectPiece("Knights")

    val numMoves = when(boardSize) {
      4 -> 8
      5 -> 13
      6 -> 18
      7 -> 25
      8 -> 32
      else -> throw RuntimeException("Unknown board size")
    }

    composeTestRule.onNodeWithText("Moves left: $numMoves")
      .assertIsDisplayed()
  }

  /**
   * Assumes the current selected is MIN_BOARD_SIZE
   */
  private fun selectBoardSize(size: Int) {
    if (size == MIN_BOARD_SIZE) return

    val initialButtonText = "$MIN_BOARD_SIZE x $MIN_BOARD_SIZE"
    composeTestRule
      .onNodeWithText(initialButtonText)
      .performClick()

    composeTestRule.waitUntil(timeoutMillis = 1_000) {
      composeTestRule.onNodeWithText("$size x $size").isDisplayed()
    }

    composeTestRule
      .onNodeWithText("$size x $size")
      .performClick()

    composeTestRule.waitUntil(timeoutMillis = 1_000) {
      composeTestRule.onNodeWithText(initialButtonText).isNotDisplayed()
    }
  }

  private fun selectPiece(pieceName: String) {
    composeTestRule
      .onNodeWithTag("PieceDropDownMenu")
      .performClick()

    composeTestRule.waitUntil(timeoutMillis = 1_000) {
      composeTestRule.onNodeWithText(pieceName).isDisplayed()
    }

    composeTestRule
      .onNodeWithText(pieceName)
      .performClick()

    composeTestRule.waitUntil(timeoutMillis = 1_000) {
      composeTestRule.onNodeWithText(pieceName).isNotDisplayed()
    }
  }
}