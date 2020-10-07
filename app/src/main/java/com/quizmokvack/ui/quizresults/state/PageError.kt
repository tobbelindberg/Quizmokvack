package com.quizmokvack.ui.quizresults.state

import com.quizmokvack.base.state.PartialState

class PageError(private val error: Throwable) : PartialState<QuizResultsState> {

    override fun reduceState(previousState: QuizResultsState): QuizResultsState {

        return previousState.copy(error = error, loading = false)
    }

}