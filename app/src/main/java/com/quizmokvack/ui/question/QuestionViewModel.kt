package com.quizmokvack.ui.question

import android.content.res.Resources
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.quizmokvack.R
import com.quizmokvack.base.vm.BaseViewModel
import com.quizmokvack.domain.util.ConsumableState
import com.quizmokvack.domain.util.Empty
import com.quizmokvack.ui.question.state.QuestionState
import com.quizmokvack.utils.loge
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class QuestionViewModel
@Inject constructor(
    private val interactor: QuestionInteractor,
    private val resources: Resources
) : BaseViewModel() {

    private val stateSubject = BehaviorSubject.create<QuestionState>()

    val questionPageNumber = ObservableField<String>()
    val category = ObservableField<String>()
    val imageUrl = ObservableField<String>()
    val question = ObservableField<String>()

    val choice0 = ObservableField<String>()
    val choice0Visible = ObservableBoolean(true)
    val choice1 = ObservableField<String>()
    val choice1Visible = ObservableBoolean(true)
    val choice2 = ObservableField<String>()
    val choice2Visible = ObservableBoolean(true)
    val choice3 = ObservableField<String>()
    val choice3Visible = ObservableBoolean(true)


    private val choices = listOf(choice0, choice1, choice2, choice3)
    private val choicesVisible =
        listOf(choice0Visible, choice1Visible, choice2Visible, choice3Visible)

    val choicesEnabled = ObservableBoolean(true)
    val choiceFeedback = ObservableField<String>()
    val choiceFeedbackVisible = ObservableBoolean(false)

    private val startTimerData = MutableLiveData<ConsumableState<Empty>>()
    val startTimerLiveData: LiveData<ConsumableState<Empty>>
        get() = startTimerData

    private var lastPage = false

    val isLastPage: Boolean
        get() = lastPage

    val fiftyFiftyEnabled = ObservableBoolean(false)

    val boostTimeEnabled = ObservableBoolean(false)

    val nextButton = ObservableField<String>()
    val nextButtonEnabled = ObservableBoolean(false)

    val errorVisible = ObservableBoolean(false)
    val loadingVisible = ObservableBoolean(false)
    val contentVisible = ObservableBoolean(false)

    var startTimeMillis = System.currentTimeMillis()

    override fun initializeSubscriptions() {
        interactor.stateObservable()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext(stateSubject::onNext)
            .subscribeBy(onNext = ::render, onError = { it.loge() })
            .addTo(subscriptions)
    }

    private fun render(state: QuestionState) {
        if (state.error != null) {
            System.out.println(state.error)
            state.error.loge()
        }
        state.questionPage?.also {
            questionPageNumber.set(
                resources.getString(
                    R.string.question_page_number,
                    it.questionIndex + 1,
                    it.questionsCount
                )
            )

        }
        category.set(state.questionPage?.category)
        imageUrl.set(state.questionPage?.imageUrl)
        question.set(state.questionPage?.question)

        state.questionPage?.choices?.forEachIndexed { index, choice ->
            choices[index].set(choice)
        }

        state.choiceResponse?.also { choiceResponse ->
            choiceFeedback.set(
                if (choiceResponse.isCorrect) {
                    resources.getString(R.string.correct_answer)
                } else {
                    resources.getString(R.string.wrong_answer, choiceResponse.correctAnswer)
                }
            )
            choiceFeedbackVisible.set(true)
            choicesEnabled.set(false)
            fiftyFiftyEnabled.set(false)
            boostTimeEnabled.set(false)
            nextButtonEnabled.set(true)
        } ?: run {
            fiftyFiftyEnabled.set(state.fiftyFiftyEnabled)
            boostTimeEnabled.set(state.boostTimeEnabled)
        }

        state.questionPage?.also { questionPage ->
            lastPage = questionPage.questionIndex == (questionPage.questionsCount - 1)
            nextButton.set(
                if (isLastPage) {
                    resources.getString(R.string.see_results)
                } else {
                    resources.getString(R.string.next_question)
                }
            )

        }

        contentVisible.set(state.questionPage != null)

        loadingVisible.set(state.loading)

        errorVisible.set(state.error != null && !state.loading)

        state.startTimer.consume {
            startTimerData.value = ConsumableState.of(it)
            startTimeMillis = System.currentTimeMillis()
        }
    }

    fun onTimeIsUpCallback() {
        interactor.onQuestionTimeout(responseTime())
        nextButtonEnabled.set(true)
        choicesEnabled.set(false)
        fiftyFiftyEnabled.set(false)
        boostTimeEnabled.set(false)
    }

    fun onChoice0Click() {
        stateSubject.value?.questionPage?.also {
            interactor.onCheckQuestionChoice(it, 0, responseTime())
        }
    }

    fun onChoice1Click() {
        stateSubject.value?.questionPage?.also {
            interactor.onCheckQuestionChoice(it, 1, responseTime())
        }
    }

    fun onChoice2Click() {
        stateSubject.value?.questionPage?.also {
            interactor.onCheckQuestionChoice(it, 2, responseTime())
        }
    }

    fun onChoice3Click() {
        stateSubject.value?.questionPage?.also {
            interactor.onCheckQuestionChoice(it, 3, responseTime())
        }
    }

    private fun responseTime(): Long = System.currentTimeMillis() - startTimeMillis

    fun onRefresh() {
        interactor.onRefresh()
    }

    fun onFiftyFiftyClick() {
        stateSubject.value?.questionPage?.also { questionPage ->
            var hiddenChoiceCount = 0
            val hiddenChoices = mutableSetOf<Int>()
            while (hiddenChoiceCount < 2) {
                val randomIndex = (0..3).random()
                if (randomIndex != questionPage.correctAnswerIndex &&
                    hiddenChoices.contains(randomIndex).not()
                ) {
                    choicesVisible[randomIndex].set(false)
                    hiddenChoices.add(randomIndex)
                    hiddenChoiceCount++
                }

            }
        }
        interactor.onDisableFiftyFifty()
    }

    fun onBoostTimeClick() {
        interactor.onDisableBoostTime()
    }

}


