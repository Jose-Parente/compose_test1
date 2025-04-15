package com.parentej.nquens1.domain.usecase

import com.parentej.nquens1.domain.model.PieceType
import com.parentej.nquens1.domain.repository.GameRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class SaveHighScoreUseCaseTest {
  private lateinit var repository: GameRepository
  private lateinit var saveHighScoreUseCase: SaveHighScoreUseCase

  @Before
  fun setUp() = runTest {
    repository = mock<GameRepository>()
    saveHighScoreUseCase = SaveHighScoreUseCase(repository)
  }

  @Test
  fun ignoreInvalidScoreTimes() = runTest {
    saveHighScoreUseCase(PieceType.QUEEN, 4, -1L)
    saveHighScoreUseCase(PieceType.QUEEN, 4, 0L)

    verify(repository, times(0)).getHighScoreTime(any(), any())
    verify(repository, times(0)).saveHighScoreTime(any(), any(), any())
  }
}