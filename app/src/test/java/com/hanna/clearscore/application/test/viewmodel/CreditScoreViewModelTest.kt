package com.hanna.clearscore.application.test.viewmodel

import com.hanna.clearscore.application.test.datasource.network.Resource
import com.hanna.clearscore.application.test.models.CreditReportInfo
import com.hanna.clearscore.application.test.repository.CreditScoreRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Test

class CreditScoreViewModelTest {

    private lateinit var viewModel: CreditScoreViewModel

    private val repositoryCreditScoreFlow = MutableStateFlow(
        Resource.success(CreditReportInfo(3, 4, 55))
    )

    private val creditScoreRepository: CreditScoreRepository = mock {
        on { getCreditScore() } doReturn repositoryCreditScoreFlow
    }

    @Before
    fun setup() {
        viewModel = CreditScoreViewModel(repository = creditScoreRepository)
    }

    @Test
    fun `getCreditScore data on view model instantiation`() {
        verify(creditScoreRepository).getCreditScore()
    }
}