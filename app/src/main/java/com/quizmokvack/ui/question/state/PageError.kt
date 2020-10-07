package com.quizmokvack.ui.question.state

import com.quizmokvack.base.state.PartialState

class PageError(private val error: Throwable) : PartialState<QuestionState> {

    override fun reduceState(previousState: QuestionState): QuestionState {

        return previousState.copy(error = error, loading = false)
    }

}