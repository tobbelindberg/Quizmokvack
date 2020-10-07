package com.quizmokvack.ui.quizsplash

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quizmokvack.base.vm.BaseViewModel
import com.quizmokvack.domain.util.ConsumableState
import com.quizmokvack.domain.util.Empty
import com.quizmokvack.ui.quizsplash.state.QuizSplashState
import com.quizmokvack.utils.loge
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class QuizSplashViewModel
@Inject constructor(
    private val interactor: QuizInteractor
) : BaseViewModel() {

    private val launchQuizData = MutableLiveData<ConsumableState<Empty>>()
    val launchQuizLiveData: LiveData<ConsumableState<Empty>>
        get() = launchQuizData

    val errorVisible = ObservableBoolean(false)
    val loadingVisible = ObservableBoolean(false)

    override fun initializeSubscriptions() {
        interactor.stateObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(onNext = ::render, onError = { it.loge() })
            .addTo(subscriptions)
    }

    private fun render(state: QuizSplashState) {
        if (state.error != null) {
            state.error.loge()
        }

        loadingVisible.set(state.loading)

        errorVisible.set(state.error != null && !state.loading)

        state.launchQuizEvent.consume {
            launchQuizData.value = ConsumableState.of(it)
        }
    }

    fun onRefresh() {
        interactor.onRefresh()
    }

}


