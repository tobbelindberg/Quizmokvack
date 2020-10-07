package com.quizmokvack.ui.question.state

import com.quizmokvack.base.state.State
import com.quizmokvack.data.managers.model.ChoiceResponse
import com.quizmokvack.data.managers.model.QuestionPage
import com.quizmokvack.domain.util.ConsumableState
import com.quizmokvack.domain.util.Empty

data class QuestionState(
    val startTimer: ConsumableState<Empty> = ConsumableState.of(),
    val questionPage: QuestionPage? = null,
    val choiceResponse: ChoiceResponse? = null,
    val boostTimeEnabled: Boolean = false,
    val fiftyFiftyEnabled: Boolean = false,
    val enableNextButton: Boolean = false,
    val error: Throwable? = null,
    val loading: Boolean = false,
    val timedOut: Boolean = false
) : State