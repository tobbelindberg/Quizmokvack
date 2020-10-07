package com.quizmokvack.ui.quizsplash

import com.quizmokvack.base.state.PartialState
import com.quizmokvack.data.managers.QuizManager
import com.quizmokvack.domain.util.Empty
import com.quizmokvack.domain.util.first
import com.quizmokvack.ui.quizsplash.state.PageError
import com.quizmokvack.ui.quizsplash.state.PageLoaded
import com.quizmokvack.ui.quizsplash.state.PageLoading
import com.quizmokvack.ui.quizsplash.state.QuizSplashState
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class QuizInteractor
@Inject constructor(private val quizManager: QuizManager) {

    private val refresh = PublishSubject.create<Empty>()

    fun stateObservable(): Observable<QuizSplashState> {
        return Observable.merge(
            page(),
            refresh()
        )
            .scan(QuizSplashState(), ::reduce)
    }

    fun reduce(
        previousState: QuizSplashState,
        partialState: PartialState<QuizSplashState>
    ): QuizSplashState {
        return partialState.reduceState(previousState)
    }

    fun onRefresh() {
        refresh.onNext(Empty)
    }

    private fun page(): Observable<PartialState<QuizSplashState>> {
        return quizManager.initializeQuiz().first()
            .subscribeOn(Schedulers.io())
            .map<PartialState<QuizSplashState>> { PageLoaded() }
            .onErrorReturn { PageError(it) }
            .startWithItem(PageLoading())
    }

    private fun refresh(): Observable<PartialState<QuizSplashState>> {
        return refresh.switchMap { _ ->
            quizManager.initializeQuiz().first()
                .subscribeOn(Schedulers.io())
                .map<PartialState<QuizSplashState>> { PageLoaded() }
                .onErrorReturn { PageError(it) }
                .startWithItem(PageLoading())
        }
    }

}
