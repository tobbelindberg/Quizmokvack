package com.quizmokvack.ui.question.state

import com.quizmokvack.base.state.PartialState
import com.quizmokvack.data.managers.model.QuestionPage
import com.quizmokvack.domain.util.ConsumableState
import com.quizmokvack.domain.util.Empty


class PageLoaded(private val questionPage: QuestionPage) :
    PartialState<QuestionState> {

    override fun reduceState(previousState: QuestionState): QuestionState {

        return previousState.copy(
            questionPage = questionPage,
            loading = false,
            error = null,
            startTimer = ConsumableState.of(Empty)
        )
    }

}