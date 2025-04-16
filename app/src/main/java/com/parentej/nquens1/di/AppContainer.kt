package com.parentej.nquens1.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.parentej.nquens1.data.local.LocalGameRepositoryImpl
import com.parentej.nquens1.domain.usecase.CreateBoardUseCase
import com.parentej.nquens1.domain.usecase.LoadHighScoreUseCase
import com.parentej.nquens1.domain.usecase.SaveHighScoreUseCase
import com.parentej.nquens1.engine.factory.BoardGameFactoryImpl
import com.parentej.nquens1.ui.viewmodels.BoardViewModel

class AppContainer(context: Context) {
  private val gameRepository = LocalGameRepositoryImpl(context)

  val boardViewModelFactory: ViewModelProvider.Factory = viewModelFactory {
    initializer {
      BoardViewModel(
        savedStateHandle = createSavedStateHandle(),
        createBoardUseCase = CreateBoardUseCase(boardGameFactory = BoardGameFactoryImpl()),
        loadHighScoreUseCase = LoadHighScoreUseCase(gameRepository),
        saveHighScoreUseCase = SaveHighScoreUseCase(gameRepository),
      ).also { it.newGame() }
    }
  }
}