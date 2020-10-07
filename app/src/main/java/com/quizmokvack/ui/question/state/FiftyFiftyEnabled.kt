package com.quizmokvack.ui.question.state

import com.quizmokvack.base.state.PartialState


class FiftyFiftyEnabled(private val fiftyFiftyEnabled: Boolean) : PartialState<QuestionState> {

    override fun reduceState(previousState: QuestionState): QuestionState {
        return previousState.copy(fiftyFiftyEnabled = fiftyFiftyEnabled)
    }

}