package com.quizmokvack.data.network

import com.quizmokvack.data.network.model.QuestionDTO
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET


interface Endpoints {

    @GET("/quiz.json")
    fun getQuiz(): Observable<List<QuestionDTO>>

}