package com.quizmokvack.ui.question.state

import com.quizmokvack.base.state.PartialState


class BoostTimeEnabled(private val boostTimeEnabled: Boolean) : PartialState<QuestionState> {

    override fun reduceState(previousState: QuestionState): QuestionState {
        return previousState.copy(boostTimeEnabled = boostTimeEnabled)
    }

}