package com.quizmokvack.ui.quizresults.state

import com.quizmokvack.base.state.PartialState


class PageLoading : PartialState<QuizResultsState> {

    override fun reduceState(previousState: QuizResultsState): QuizResultsState {

        return previousState.copy(loading = true)
    }

}