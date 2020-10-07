package com.quizmokvack.di

import com.quizmokvack.ui.MainActivity
import com.quizmokvack.ui.question.QuestionFragment
import com.quizmokvack.ui.quizsplash.QuizSplashFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Main component that brings all the dagger stuff to life.
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)

    fun inject(quizSplashFragment: QuizSplashFragment)

    fun inject(questionFragment: QuestionFragment)

}