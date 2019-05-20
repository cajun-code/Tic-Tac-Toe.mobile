package com.example.tictactoe.di.module

import com.example.tictactoe.view.gameboard.GameBoardFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun provideGameBoardFragment(): GameBoardFragment
}