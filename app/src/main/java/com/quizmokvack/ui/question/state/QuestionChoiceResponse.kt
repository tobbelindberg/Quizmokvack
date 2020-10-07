package com.quizmokvack.ui.question.state

import com.quizmokvack.base.state.PartialState
import com.quizmokvack.data.managers.model.ChoiceResponse


class QuestionChoiceResponse(private val choiceResponse: ChoiceResponse) :
    PartialState<QuestionState> {

    override fun reduceState(previousState: QuestionState): QuestionState {

        return previousState.copy(choiceResponse = choiceResponse)
    }

}