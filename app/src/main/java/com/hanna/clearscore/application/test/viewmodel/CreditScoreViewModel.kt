package com.hanna.clearscore.application.test.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hanna.clearscore.application.test.OpenForTesting
import com.hanna.clearscore.application.test.repository.CreditScoreRepository
import javax.inject.Inject

@OpenForTesting
class CreditScoreViewModel @Inject constructor(repository: CreditScoreRepository) :
    ViewModel() {

    val getCreditScore = repository.getCreditScore()
}

class CreditScoreViewModelFactory @Inject constructor(private val repository: CreditScoreRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreditScoreViewModel(repository) as T
    }
}