package com.parentej.nquens1

import android.app.Application
import com.parentej.nquens1.di.AppContainer

class MainApplication: Application() {
  val appContainer = AppContainer(this)
}