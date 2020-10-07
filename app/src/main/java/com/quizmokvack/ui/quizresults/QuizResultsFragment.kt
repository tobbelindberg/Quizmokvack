package com.quizmokvack.ui.quizresults

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quizmokvack.R
import com.quizmokvack.base.BaseFragment
import com.quizmokvack.base.QuizmokvackApplication
import com.quizmokvack.databinding.FragmentQuizResultsBinding
import com.quizmokvack.utils.bindingProvider
import com.quizmokvack.utils.viewModelProvider


class QuizResultsFragment : BaseFragment<QuizResultsViewModel>() {

    override val viewModel: QuizResultsViewModel by viewModelProvider(
        { viewModelFactory },
        false
    )

    private val binding: FragmentQuizResultsBinding by bindingProvider(R.layout.fragment_quiz_results)

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

    fun onErrorRetry(view: View) {
        viewModel.onRefresh()
    }

    fun onPlayAgainClick(view: View) {
        val direction =
            QuizResultsFragmentDirections.actionQuizResultsFragmentToQuizSplashFragment()
        navController.navigate(direction)
    }

}
