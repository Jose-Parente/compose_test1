package com.parentej.nquens1.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.parentej.nquens1.domain.model.PieceType
import com.parentej.nquens1.domain.usecase.CreateBoardUseCase
import com.parentej.nquens1.engine.factory.BoardGameFactoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class BoardViewModel(
  val savedStateHandle: SavedStateHandle,
  val createBoardUseCase: CreateBoardUseCase
) : ViewModel() {
  private val _uiState = MutableStateFlow(BoardUiState(board = emptyArray(), pieceType = PieceType.QUEEN))
  val uiState: StateFlow<BoardUiState> = _uiState


  private fun createGame() {
    val gameEngine = createBoardUseCase(PieceType.QUEEN, 4)
    _uiState.update { it.copy(board = gameEngine.getAllSquares()) }
  }


  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        BoardViewModel(
          savedStateHandle = createSavedStateHandle(),
          createBoardUseCase = CreateBoardUseCase(boardGameFactory = BoardGameFactoryImpl())
        ).also { it.createGame() }
      }
    }
  }
}