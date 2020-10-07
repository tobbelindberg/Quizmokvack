package com.quizmokvack.data.services

import com.quizmokvack.data.network.Endpoints
import com.quizmokvack.domain.model.Question
import com.quizmokvack.domain.services.QuizService
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class QuizNetworkService
@Inject constructor(
    private val restEndpoints: Endpoints
) : QuizService {

    override fun getQuiz(): Observable<List<Question>> {
        return restEndpoints.getQuiz()
            .map { posts ->
                posts.map {
                    it.toDomain()
                }
            }
    }

}