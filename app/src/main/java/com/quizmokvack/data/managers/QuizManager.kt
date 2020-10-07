package com.quizmokvack.data.managers

import com.quizmokvack.data.managers.model.ChoiceResponse
import com.quizmokvack.data.managers.model.QuestionPage
import com.quizmokvack.data.managers.model.ScoreBoard
import com.quizmokvack.domain.util.Empty
import io.reactivex.rxjava3.core.Observable

interface QuizManager {
    fun getQuiz(): Observable<List<QuestionPage>>

    fun initializeQuiz(): Observable<List<QuestionPage>>


    fun getCurrentQuestion(): Observable<QuestionPage>

    fun getScoreBoard(): Observable<ScoreBoard>

    fun checkQuestionChoice(
        currentQuestionPage: QuestionPage,
        chosenChoiceIndex: Int, responseTime: Long
    ): Observable<ChoiceResponse>

    fun questionTimeout(responseTime: Long): Observable<Empty>

    fun fiftyFiftyEnabled(): Observable<Boolean>

    fun disableFiftyFifty(): Observable<Boolean>

    fun boostTimeEnabled(): Observable<Boolean>

    fun disableBoostTime(): Observable<Boolean>

}