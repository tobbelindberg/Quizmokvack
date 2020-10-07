package com.quizmokvack.ui.question.state

import com.quizmokvack.base.state.PartialState


class PageLoading : PartialState<QuestionState> {

    override fun reduceState(previousState: QuestionState): QuestionState {

        return previousState.copy(loading = true)
    }

}