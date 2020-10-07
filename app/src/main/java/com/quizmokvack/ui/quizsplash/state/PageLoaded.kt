package com.quizmokvack.ui.quizsplash.state

import com.quizmokvack.base.state.PartialState
import com.quizmokvack.domain.util.ConsumableState
import com.quizmokvack.domain.util.Empty


class PageLoaded :
    PartialState<QuizSplashState> {

    override fun reduceState(previousState: QuizSplashState): QuizSplashState {

        return previousState.copy(
            launchQuizEvent = ConsumableState.of(Empty),
            loading = false, error = null
        )
    }

}