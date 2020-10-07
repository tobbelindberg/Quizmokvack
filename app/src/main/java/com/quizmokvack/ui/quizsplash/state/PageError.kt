package com.quizmokvack.ui.quizsplash.state

import com.quizmokvack.base.state.PartialState

class PageError(private val error: Throwable) : PartialState<QuizSplashState> {

    override fun reduceState(previousState: QuizSplashState): QuizSplashState {

        return previousState.copy(error = error, loading = false)
    }

}