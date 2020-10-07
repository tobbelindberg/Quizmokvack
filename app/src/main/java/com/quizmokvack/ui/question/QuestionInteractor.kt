package com.quizmokvack.ui.question

import com.quizmokvack.base.state.PartialState
import com.quizmokvack.data.managers.QuizManager
import com.quizmokvack.data.managers.model.QuestionPage
import com.quizmokvack.domain.util.Empty
import com.quizmokvack.domain.util.first
import com.quizmokvack.ui.question.state.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class QuestionInteractor
@Inject constructor(private val quizManager: QuizManager) {

    private val refresh = PublishSubject.create<Empty>()
    private val checkQuestionChoice = PublishSubject.create<Triple<QuestionPage, Int, Long>>()
    private val questionTimout = PublishSubject.create<Long>()
    private val disableFiftyFifty = PublishSubject.create<Empty>()
    private val disableBoostTime = PublishSubject.create<Empty>()

    fun stateObservable(): Observable<QuestionState> {
        return Observable.mergeArray(
            page(),
            refresh(),
            checkQuestionChoice(),
            fiftyFiftyEnabled(),
            disableFiftyFifty(),
            boostTimeEnabled(),
            disableBoostTime(),
            questionTimout()
        )
            .scan(QuestionState(), ::reduce)
    }

    fun reduce(
        previousState: QuestionState,
        partialState: PartialState<QuestionState>
    ): QuestionState {
        return partialState.reduceState(previousState)
    }

    fun onRefresh() {
        refresh.onNext(Empty)
    }

    private fun page(): Observable<PartialState<QuestionState>> {
        return quizManager.getCurrentQuestion().first()
            .subscribeOn(Schedulers.io())
            .map<PartialState<QuestionState>> { PageLoaded(it) }
            .onErrorReturn { PageError(it) }
            .startWithItem(PageLoading())
    }

    private fun refresh(): Observable<PartialState<QuestionState>> {
        return refresh.switchMap { _ ->
            quizManager.getCurrentQuestion().first()
                .subscribeOn(Schedulers.io())
                .map<PartialState<QuestionState>> { PageLoaded(it) }
                .onErrorReturn { PageError(it) }
                .startWithItem(PageLoading())
        }
    }

    fun onCheckQuestionChoice(
        questionPage: QuestionPage,
        chosenChoiceIndex: Int,
        responseTime: Long
    ) {
        checkQuestionChoice.onNext(Triple(questionPage, chosenChoiceIndex, responseTime))
    }

    private fun checkQuestionChoice(): Observable<PartialState<QuestionState>> {
        return checkQuestionChoice.switchMap { chosenChoice ->
            quizManager.checkQuestionChoice(
                chosenChoice.first,
                chosenChoice.second,
                chosenChoice.third
            )
                .subscribeOn(Schedulers.io())
                .map<PartialState<QuestionState>> { QuestionChoiceResponse(it) }
        }
    }

    fun onQuestionTimeout(responseTime: Long) {
        questionTimout.onNext(responseTime)
    }

    private fun questionTimout(): Observable<PartialState<QuestionState>> {
        return questionTimout.switchMap { responseTime ->
            quizManager.questionTimeout(responseTime)
                .subscribeOn(Schedulers.io())
                .map<PartialState<QuestionState>> { TimeoutUploaded() }
        }
    }

    fun fiftyFiftyEnabled(): Observable<PartialState<QuestionState>> {
        return quizManager.fiftyFiftyEnabled().first()
            .subscribeOn(Schedulers.io())
            .map<PartialState<QuestionState>> { FiftyFiftyEnabled(it) }
    }

    fun onDisableFiftyFifty() {
        disableFiftyFifty.onNext(Empty)
    }

    private fun disableFiftyFifty(): Observable<PartialState<QuestionState>> {
        return disableFiftyFifty.switchMap { _ ->
            quizManager.disableFiftyFifty()
                .subscribeOn(Schedulers.io())
                .map<PartialState<QuestionState>> { FiftyFiftyEnabled(it) }
        }
    }

    fun boostTimeEnabled(): Observable<PartialState<QuestionState>> {
        return quizManager.boostTimeEnabled().first()
            .subscribeOn(Schedulers.io())
            .map<PartialState<QuestionState>> { BoostTimeEnabled(it) }
    }

    fun onDisableBoostTime() {
        disableBoostTime.onNext(Empty)
    }

    private fun disableBoostTime(): Observable<PartialState<QuestionState>> {
        return disableBoostTime.switchMap { _ ->
            quizManager.disableBoostTime()
                .subscribeOn(Schedulers.io())
                .map<PartialState<QuestionState>> { BoostTimeEnabled(it) }
        }
    }

}
