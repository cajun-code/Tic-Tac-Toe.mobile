package com.example.tictactoe.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tictactoe.di.utils.MyViewModelFactory
import com.example.tictactoe.di.utils.ViewModelKey
import com.example.tictactoe.view.main.MainActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Suppress
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewModel(mainActivityViewModel: MainActivityViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(myViewModelFactory: MyViewModelFactory): ViewModelProvider.Factory
}