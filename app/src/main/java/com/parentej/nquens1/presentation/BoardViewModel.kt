package com.parentej.nquens1.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.parentej.nquens1.domain.usecase.CreateBoardUseCase
import com.parentej.nquens1.infrastructure.factory.BoardGameFactoryImpl

class BoardViewModel(
  val savedStateHandle: SavedStateHandle,
  val createBoardUseCase: CreateBoardUseCase
) : ViewModel() {



  companion object {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
      initializer {
        BoardViewModel(
          savedStateHandle = createSavedStateHandle(),
          createBoardUseCase = CreateBoardUseCase(boardGameFactory = BoardGameFactoryImpl())
        )
      }
    }
  }
}