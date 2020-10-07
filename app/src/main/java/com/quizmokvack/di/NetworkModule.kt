package com.quizmokvack.di

import com.quizmokvack.data.services.QuizNetworkService
import com.quizmokvack.domain.services.QuizService
import dagger.Binds
import dagger.Module
import dagger.Reusable

/**
 * Provides Network services.
 */
@Module
abstract class NetworkModule {

    @Binds
    @Reusable
    abstract fun providePostService(service: QuizNetworkService): QuizService
}