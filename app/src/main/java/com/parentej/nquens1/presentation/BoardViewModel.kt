package com.parentej.nquens1.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.parentej.nquens1.domain.model.BoardGame
import com.parentej.nquens1.domain.model.BoardState
import com.parentej.nquens1.domain.model.PieceType
import com.parentej.nquens1.domain.usecase.CreateBoardUseCase
import com.parentej.nquens1.engine.factory.BoardGameFactoryImpl
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BoardViewModel(
  private val savedStateHandle: SavedStateHandle,
  private val createBoardUseCase: CreateBoardUseCase
) : ViewModel() {
  private lateinit var gameEngine: BoardGame
  private var timerJob: Job? = null

  private val _uiState =
    MutableStateFlow(
      BoardUiState(
        boardSize = 0,
        boardState = BoardState(emptyList(), 0, false),
        pieceType = PieceType.QUEEN,
      )
    )
  val uiState: StateFlow<BoardUiState> = _uiState

  private val _elapsedTime = MutableStateFlow("")
  val elapsedTime: StateFlow<String> = _elapsedTime

  fun togglePosition(squareIdx: Int) {
    gameEngine.togglePosition(squareIdx)
    _uiState.update { it.copy(boardState = gameEngine.getBoardState()) }
  }

  fun changeBoardSize(newSize: Int) {
    createGame(newSize, _uiState.value.pieceType)
  }

  fun changeBoardPieceType(pieceType: PieceType) {
    createGame(_uiState.value.boardSize, pieceType)
  }

  private fun startTimer() {
    stopTimer()
    timerJob = viewModelScope.launch {
      val startTime = System.currentTimeMillis()
      while (true) {
        val elapsed = System.currentTimeMillis() - startTime
        _elapsedTime.emit("${elapsed / 1000}.${(elapsed % 1000) / 100}")
        delay(100)
      }
    }
  }

  private fun stopTimer() {
    timerJob?.cancel()
    timerJob = null
  }

  private fun createGame(boardSize: Int, pieceType: PieceType) {
    gameEngine = createBoardUseCase(pieceType, boardSize)
    _uiState.update {
      BoardUiState(
        boardSize = boardSize,
        boardState = gameEngine.getBoardState(),
        pieceType = pieceType,
      )
    }
    startTimer()
  }

  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        BoardViewModel(
          savedStateHandle = createSavedStateHandle(),
          createBoardUseCase = CreateBoardUseCase(boardGameFactory = BoardGameFactoryImpl())
        ).also { it.createGame(boardSize = 4, pieceType = PieceType.QUEEN) }
      }
    }
  }
}