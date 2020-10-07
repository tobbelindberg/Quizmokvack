package com.quizmokvack.di

import com.quizmokvack.ui.question.QuestionViewModelTest
import dagger.Component
import javax.inject.Singleton

/**
 * Main Test component that brings all the dagger stuff to life.
 */
@Singleton
@Component(modules = [AppModule::class])
interface TestAppComponent : AppComponent {

    fun inject(questionViewModelTest: QuestionViewModelTest)


}