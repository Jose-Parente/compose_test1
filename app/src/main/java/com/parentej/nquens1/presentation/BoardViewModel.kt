package com.parentej.nquens1.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.parentej.nquens1.domain.model.BoardGame
import com.parentej.nquens1.domain.model.PieceType
import com.parentej.nquens1.domain.usecase.CreateBoardUseCase
import com.parentej.nquens1.engine.factory.BoardGameFactoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class BoardViewModel(
  private val savedStateHandle: SavedStateHandle,
  private val createBoardUseCase: CreateBoardUseCase
) : ViewModel() {
  private lateinit var gameEngine: BoardGame

  private val _uiState =
    MutableStateFlow(BoardUiState(boardSize = 0, board = emptyList(), pieceType = PieceType.QUEEN))
  val uiState: StateFlow<BoardUiState> = _uiState

  fun togglePosition(squareIdx: Int) {
    gameEngine.togglePosition(squareIdx)
    _uiState.update { it.copy(board = gameEngine.getAllSquares()) }
  }

  fun changeBoardSize(newSize: Int) {
    createGame(newSize, _uiState.value.pieceType)
  }

  fun changeBoardPieceType(pieceType: PieceType) {
    createGame(_uiState.value.boardSize, pieceType)
  }

  private fun createGame(boardSize: Int, pieceType: PieceType) {
    gameEngine = createBoardUseCase(pieceType, boardSize)
    _uiState.update {
      BoardUiState(
        boardSize = boardSize,
        board = gameEngine.getAllSquares(),
        pieceType = pieceType,
      )
    }
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