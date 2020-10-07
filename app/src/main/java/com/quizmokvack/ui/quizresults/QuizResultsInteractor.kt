package com.quizmokvack.ui.quizresults

import com.quizmokvack.base.state.PartialState
import com.quizmokvack.data.managers.QuizManager
import com.quizmokvack.domain.util.Empty
import com.quizmokvack.domain.util.first
import com.quizmokvack.ui.quizresults.state.PageError
import com.quizmokvack.ui.quizresults.state.PageLoaded
import com.quizmokvack.ui.quizresults.state.PageLoading
import com.quizmokvack.ui.quizresults.state.QuizResultsState
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

class QuizResultsInteractor
@Inject constructor(
    private val quizManager: QuizManager
) {

    private val refresh = PublishSubject.create<Empty>()

    fun stateObservable(): Observable<QuizResultsState> {
        return Observable.merge(
            page(),
            refresh()
        )
            .scan(QuizResultsState(), ::reduce)
    }

    fun reduce(
        previousState: QuizResultsState,
        partialState: PartialState<QuizResultsState>
    ): QuizResultsState {
        return partialState.reduceState(previousState)
    }

    fun onRefresh() {
        refresh.onNext(Empty)
    }

    private fun page(): Observable<PartialState<QuizResultsState>> {
        return quizManager.getScoreBoard().first()
            .subscribeOn(Schedulers.io())
            .map<PartialState<QuizResultsState>> (::PageLoaded)
            .onErrorReturn { PageError(it) }
            .startWithItem(PageLoading())
    }

    private fun refresh(): Observable<PartialState<QuizResultsState>> {
        return refresh.switchMap { _ ->
            quizManager.getScoreBoard().first()
                .subscribeOn(Schedulers.io())
                .map<PartialState<QuizResultsState>> (::PageLoaded)
                .onErrorReturn { PageError(it) }
                .startWithItem(PageLoading())
        }
    }

}
