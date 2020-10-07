package com.quizmokvack.ui.quizsplash.state

import com.quizmokvack.base.state.State
import com.quizmokvack.domain.util.ConsumableState
import com.quizmokvack.domain.util.Empty

data class QuizSplashState(
    val launchQuizEvent: ConsumableState<Empty> = ConsumableState.of(),
    val error: Throwable? = null,
    val loading: Boolean = false

) : State