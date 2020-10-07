package com.quizmokvack.ui.quizresults.state

import com.quizmokvack.base.state.PartialState
import com.quizmokvack.data.managers.model.ScoreBoard


class PageLoaded(private val scoreBoard: ScoreBoard) :
    PartialState<QuizResultsState> {

    override fun reduceState(previousState: QuizResultsState): QuizResultsState {

        return previousState.copy(scoreBoard = scoreBoard, loading = false, error = null)
    }

}