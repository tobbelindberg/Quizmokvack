package com.quizmokvack.ui.quizresults

import android.content.res.Resources
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.quizmokvack.R
import com.quizmokvack.base.vm.BaseViewModel
import com.quizmokvack.ui.quizresults.state.QuizResultsState
import com.quizmokvack.utils.loge
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import kotlin.math.roundToInt

class QuizResultsViewModel
@Inject constructor(
    private val interactor: QuizResultsInteractor,
    private val resources: Resources
) : BaseViewModel() {

    val contentVisible = ObservableBoolean(false)

    val score = ObservableField<String>()
    val avarageTime = ObservableField<String>()

    val errorVisible = ObservableBoolean(false)
    val loadingVisible = ObservableBoolean(false)

    override fun initializeSubscriptions() {
        interactor.stateObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = ::render, onError = { it.loge() })
            .addTo(subscriptions)
    }

    private fun render(state: QuizResultsState) {
        if (state.error != null) {
            state.error.loge()
        }

        state.scoreBoard?.also {
            score.set(resources.getString(R.string.score_message, it.score))
            avarageTime.set(
                resources.getString(
                    R.string.avarage_response_time_message,
                    (it.avarageResponseTime / 1000.0).roundToInt()
                )
            )
            contentVisible.set(true)
        }

        loadingVisible.set(state.loading)

        errorVisible.set(state.error != null && !state.loading)
    }

    fun onRefresh() {
        interactor.onRefresh()
    }

}


