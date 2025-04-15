package com.parentej.nquens1.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parentej.nquens1.domain.model.BoardGame
import com.parentej.nquens1.domain.model.BoardState
import com.parentej.nquens1.domain.model.PieceType
import com.parentej.nquens1.domain.usecase.CreateBoardUseCase
import com.parentej.nquens1.domain.usecase.LoadHighScoreUseCase
import com.parentej.nquens1.domain.usecase.SaveHighScoreUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BoardViewModel(
  private val savedStateHandle: SavedStateHandle,
  private val createBoardUseCase: CreateBoardUseCase,
  private val saveHighScoreUseCase: SaveHighScoreUseCase,
  private val loadHighScoreUseCase: LoadHighScoreUseCase,
) : ViewModel() {
  private lateinit var gameEngine: BoardGame
  private var timerJob: Job? = null

  private val _uiState =
    MutableStateFlow(
      BoardUiState(
        boardSize = 0,
        boardState = BoardState(emptyList(), 0, false),
        pieceType = PieceType.QUEEN,
        highScore = ""
      )
    )
  val uiState: StateFlow<BoardUiState> = _uiState

  private val _elapsedTime = MutableStateFlow(-1L)
  val elapsedTime: StateFlow<String> =
    _elapsedTime
      .map { if (it > 0L) it.millisToDeciSecond() else "" }
      .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "")

  fun newGame(boardSize: Int = 4, pieceType: PieceType = PieceType.QUEEN) {
    viewModelScope.launch {
      gameEngine = createBoardUseCase(pieceType, boardSize)
      val highScoreTime = loadHighScoreUseCase(pieceType, boardSize)

      _uiState.update {
        BoardUiState(
          boardSize = boardSize,
          boardState = gameEngine.getBoardState(),
          pieceType = pieceType,
          highScore = if (highScoreTime > 0) highScoreTime.millisToDeciSecond() else "",
        )
      }
      stopTimer()
      _elapsedTime.update { -1L }
    }
  }

  fun togglePosition(squareIdx: Int) {
    gameEngine.togglePosition(squareIdx)
    val boardState = gameEngine.getBoardState()
    _uiState.update { it.copy(boardState = boardState) }

    if (boardState.isFinished) {
      stopTimer()
      viewModelScope.launch {
        saveHighScoreUseCase(_uiState.value.pieceType, _uiState.value.boardSize, _elapsedTime.value)
      }
    } else {
      startTimer()
    }
  }

  fun changeBoardSize(newSize: Int) {
    newGame(newSize, _uiState.value.pieceType)
  }

  fun changeBoardPieceType(pieceType: PieceType) {
    newGame(_uiState.value.boardSize, pieceType)
  }

  fun resetGame() {
    newGame(_uiState.value.boardSize, _uiState.value.pieceType)
  }

  private fun startTimer() {
    if (timerJob != null) return // Already started

    timerJob = viewModelScope.launch {
      val startTime = System.currentTimeMillis()
      while (true) {
        val elapsed = System.currentTimeMillis() - startTime
        _elapsedTime.emit(elapsed)
        delay(100)
      }
    }
  }

  private fun stopTimer() {
    timerJob?.cancel()
    timerJob = null
  }

  private fun Long.millisToDeciSecond() = "${this / 1000}.${(this % 1000) / 100}"
}