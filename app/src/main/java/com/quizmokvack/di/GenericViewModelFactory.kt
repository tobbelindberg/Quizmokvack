package com.quizmokvack.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class GenericViewModelFactory<T : ViewModel>
@Inject constructor(private val viewModelProvider: Provider<T>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelProvider.get() as T
    }

}