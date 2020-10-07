package com.quizmokvack.di

import com.quizmokvack.data.managers.QuizManager
import com.quizmokvack.data.managers.QuizMemoryManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Provides Network services.
 */
@Module
abstract class ManagerModule {

    @Binds
    @Singleton
    abstract fun provideQuizManager(manager: QuizMemoryManager): QuizManager
}