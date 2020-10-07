package com.quizmokvack.ui.quizsplash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.quizmokvack.R
import com.quizmokvack.base.BaseFragment
import com.quizmokvack.base.QuizmokvackApplication
import com.quizmokvack.databinding.FragmentQuizSplashBinding
import com.quizmokvack.utils.bindingProvider
import com.quizmokvack.utils.viewModelProvider


class QuizSplashFragment : BaseFragment<QuizSplashViewModel>() {

    override val viewModel: QuizSplashViewModel by viewModelProvider(
        { viewModelFactory },
        false
    )

    private val binding: FragmentQuizSplashBinding by bindingProvider(R.layout.fragment_quiz_splash)

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
        viewModel.launchQuizLiveData.observe(viewLifecycleOwner, Observer {
            it.consume {
                val direction =
                    QuizSplashFragmentDirections.actionQuizSplashFragmentToQuestionFragment()
                navController.navigate(direction)
            }
        })
    }

    fun onErrorRetry(view: View) {
        viewModel.onRefresh()
    }

}
