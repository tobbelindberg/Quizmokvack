package com.quizmokvack.data.repositories

import com.quizmokvack.domain.model.Question
import com.quizmokvack.domain.services.QuizService
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * In the repos is where I would also inject a cache that would cache the repositories and such.
 */
@Singleton
class QuizRepo
@Inject constructor(private val quizService: QuizService) {

    fun getQuiz(): Observable<List<Question>> {
        return quizService.getQuiz()
    }

}