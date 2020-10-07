package com.quizmokvack.ui.quizresults.state

import com.quizmokvack.base.state.State
import com.quizmokvack.data.managers.model.ScoreBoard

data class QuizResultsState(
    val scoreBoard: ScoreBoard? = null,
    val error: Throwable? = null,
    val loading: Boolean = false

) : State