package com.hanna.clearscore.application.test.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.hanna.clearscore.application.test.R
import com.hanna.clearscore.application.test.databinding.FragmentCreditScoreDisplayBinding
import com.hanna.clearscore.application.test.datasource.network.Status
import com.hanna.clearscore.application.test.di.DaggerInjectHelper
import com.hanna.clearscore.application.test.extensions.provideViewModel
import com.hanna.clearscore.application.test.extensions.viewBinding
import com.hanna.clearscore.application.test.viewmodel.CreditScoreViewModel
import com.hanna.clearscore.application.test.viewmodel.CreditScoreViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

//Prototypes - V
//Tests - V
class CreditScoreDisplayFragment : Fragment(R.layout.fragment_credit_score_display) {

    @Inject
    lateinit var factory: CreditScoreViewModelFactory
    val viewModel: CreditScoreViewModel by provideViewModel { factory }//using an extension, to enable mocking the viewModel when we come to test.
    private val binding: FragmentCreditScoreDisplayBinding by viewBinding(
        FragmentCreditScoreDisplayBinding::bind
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerInjectHelper.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressCircular.setBarColor(//set some colors for the progress bar
            ContextCompat.getColor(requireContext(), R.color.purple_200),
            ContextCompat.getColor(requireContext(), R.color.purple_500),
            ContextCompat.getColor(requireContext(), R.color.purple_200)
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getCreditScore.collect { resource ->
                binding.mainView.isVisible = resource.status != Status.ERROR
                binding.creditScoreTitle.isVisible = resource.status == Status.SUCCESS
                binding.creditScoreOutOfTv.isVisible = resource.status == Status.SUCCESS
                when (resource.status) {
                    Status.LOADING -> {
                        binding.progressCircular.setValueAnimated(0F)
                        binding.scoreTv.text = getString(R.string.loading_placeholder)
                    }
                    Status.SUCCESS -> {
                        resource.data?.let { score ->
                            binding.progressCircular.setValueAnimated(score.percentageCreditUsed.toFloat())
                            binding.scoreTv.text = score.score.toString()
                            binding.creditScoreOutOfTv.text =
                                resources.getString(
                                    R.string.display_maximum_credits,
                                    score.maxScoreValue
                                )
                        }
                    }
                    Status.ERROR -> {
                        Snackbar.make(
                            view,
                            resource.message
                                ?: resources.getString(R.string.default_load_credits_failure_message),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }
}