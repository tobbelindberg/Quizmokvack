package com.quizmokvack.ui.quizsplash.state

import com.quizmokvack.base.state.PartialState


class PageLoading : PartialState<QuizSplashState> {

    override fun reduceState(previousState: QuizSplashState): QuizSplashState {

        return previousState.copy(loading = true)
    }

}