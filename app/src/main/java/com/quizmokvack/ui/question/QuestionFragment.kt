package com.quizmokvack.ui.question

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.quizmokvack.R
import com.quizmokvack.base.BaseFragment
import com.quizmokvack.base.QuizmokvackApplication
import com.quizmokvack.databinding.FragmentQuestionBinding
import com.quizmokvack.utils.bindingProvider
import com.quizmokvack.utils.viewModelProvider


class QuestionFragment : BaseFragment<QuestionViewModel>() {

    override val viewModel: QuestionViewModel by viewModelProvider(
        { viewModelFactory },
        false
    )

    private val binding: FragmentQuestionBinding by bindingProvider(R.layout.fragment_question)

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as QuizmokvackApplication).appComponent.inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.fragment = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.startTimerLiveData.observe(viewLifecycleOwner, Observer {
            binding.countDownTimer.start()
        })
    }

    fun onErrorRetry(view: View) {
        viewModel.onRefresh()
    }

    fun onChoice0Click(view: View) {
        viewModel.onChoice0Click()
    }

    fun onChoice1Click(view: View) {
        viewModel.onChoice1Click()
    }

    fun onChoice2Click(view: View) {
        viewModel.onChoice2Click()
    }

    fun onChoice3Click(view: View) {
        viewModel.onChoice3Click()
    }

    fun onFiftyFityClick(view: View) {
        viewModel.onFiftyFiftyClick()
    }

    fun onBoostTimeClick(view: View) {
        binding.countDownTimer.boostTime()
        viewModel.onBoostTimeClick()
    }

    fun onNextClick(view: View) {
        val direction = if (viewModel.isLastPage) {
            QuestionFragmentDirections.actionQuestionFragmentToQuizResultsFragment()
        } else {
            QuestionFragmentDirections.actionToQuestionFragment()
        }
        navController.navigate(direction)

    }

}
