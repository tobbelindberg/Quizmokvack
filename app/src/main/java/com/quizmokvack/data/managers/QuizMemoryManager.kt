package com.quizmokvack.data.managers

import com.quizmokvack.data.managers.model.ChoiceResponse
import com.quizmokvack.data.managers.model.QuestionPage
import com.quizmokvack.data.managers.model.ScoreBoard
import com.quizmokvack.data.repositories.QuizRepo
import com.quizmokvack.domain.util.Empty
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizMemoryManager
@Inject constructor(private val quizRepo: QuizRepo): QuizManager {
    companion object {
        private const val QUIZ_SIZE = 10
    }

    private val quiz = BehaviorSubject.create<List<QuestionPage>>()
    private val quizSerialized = quiz.toSerialized()

    private var scoreBoard = ScoreBoard()
        @Synchronized get
        @Synchronized set

    private var currentQuestionIndex = 0
        @Synchronized get
        @Synchronized set

    private var fiftyFiftyEnabled = true
        @Synchronized get
        @Synchronized set

    private var boostTimeEnabled = true
        @Synchronized get
        @Synchronized set

    override fun getQuiz(): Observable<List<QuestionPage>> {
        return if (quiz.hasValue()) {
            quiz
        } else {
            initializeQuiz()
        }
    }

    override fun initializeQuiz(): Observable<List<QuestionPage>> {
        return resetGameAttributes()
            .flatMap {
                quizRepo.getQuiz()
                    .map { quiz ->
                        quiz.shuffled().take(QUIZ_SIZE).mapIndexed { index, question ->
                            val choices =
                                question.incorrectAnswers.plus(question.correctAnswer).shuffled()
                            QuestionPage(
                                index, QUIZ_SIZE, question.category,
                                question.question, question.imageUrl, question.correctAnswer,
                                choices.indexOf(question.correctAnswer),
                                choices
                            )
                        }
                    }
            }
            .doOnNext(quizSerialized::onNext)
    }

    private fun resetGameAttributes(): Observable<Empty> {
        return Observable.fromCallable {
            scoreBoard = ScoreBoard()
            currentQuestionIndex = 0
            fiftyFiftyEnabled = true
            boostTimeEnabled = true

            Empty
        }
    }

    override fun getCurrentQuestion(): Observable<QuestionPage> {
        return getQuiz()
            .map {
                it[currentQuestionIndex]
            }
            .doOnNext {
                if (currentQuestionIndex < (QUIZ_SIZE - 1)) {
                    currentQuestionIndex++
                }
            }

    }

    override fun getScoreBoard(): Observable<ScoreBoard> {
        return Observable.just(scoreBoard)
    }

    override fun checkQuestionChoice(
        currentQuestionPage: QuestionPage,
        chosenChoiceIndex: Int, responseTime: Long
    ): Observable<ChoiceResponse> {
        return Observable.fromCallable {
            ChoiceResponse(
                chosenChoiceIndex == currentQuestionPage.correctAnswerIndex,
                currentQuestionPage.correctAnswer
            )
        }
            .doOnNext { response ->
                val score = scoreBoard.score.takeIf { response.isCorrect }?.let {
                    it + 1
                } ?: scoreBoard.score
                val avarageResponseTime =
                    scoreBoard.avarageResponseTime + responseTime / QUIZ_SIZE

                scoreBoard = ScoreBoard(score, avarageResponseTime)
            }
    }

    override fun questionTimeout(responseTime: Long): Observable<Empty> {
        return Observable.fromCallable {
            scoreBoard = ScoreBoard(
                scoreBoard.score,
                scoreBoard.avarageResponseTime + responseTime / QUIZ_SIZE
            )

            Empty
        }
    }

    override fun fiftyFiftyEnabled(): Observable<Boolean> {
        return Observable.just(fiftyFiftyEnabled)
    }

    override fun disableFiftyFifty(): Observable<Boolean> {
        return Observable.fromCallable {
            fiftyFiftyEnabled = false

            fiftyFiftyEnabled
        }
    }

    override fun boostTimeEnabled(): Observable<Boolean> {
        return Observable.just(boostTimeEnabled)
    }

    override fun disableBoostTime(): Observable<Boolean> {
        return Observable.fromCallable {
            boostTimeEnabled = false


            boostTimeEnabled
        }
    }

}