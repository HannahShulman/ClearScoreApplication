package com.hanna.clearscore.application.test.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.google.common.truth.Truth.assertThat
import com.hanna.clearscore.application.test.datasource.network.Api
import com.hanna.clearscore.application.test.datasource.network.ApiSuccessResponse
import com.hanna.clearscore.application.test.datasource.network.responses.ResponseModel
import com.hanna.clearscore.application.test.models.CreditReportInfo
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
class CreditScoreRepositoryTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val api: Api = mock {
        onBlocking { getData() } doReturn MutableStateFlow(
            ApiSuccessResponse(
                ResponseModel(
                    CreditReportInfo(3, 4, 55)
                )
            )
        )
    }
    private val repository = CreditScoreRepositoryImpl(api)

    @ExperimentalTime
    @Test
    fun `getCreditScore(), invokes api getData and returns expected result`() {
        runBlockingTest {
            repository.getCreditScore().asLiveData().observeForever {
                verify(api).getData()
                assertThat(it.data).isEqualTo(CreditReportInfo(3, 4, 55))
            }
        }
    }

}