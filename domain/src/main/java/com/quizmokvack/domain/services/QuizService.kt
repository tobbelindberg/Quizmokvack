package com.quizmokvack.domain.services

import com.quizmokvack.domain.model.Question
import io.reactivex.rxjava3.core.Observable

interface QuizService {

    fun getQuiz(): Observable<List<Question>>


}