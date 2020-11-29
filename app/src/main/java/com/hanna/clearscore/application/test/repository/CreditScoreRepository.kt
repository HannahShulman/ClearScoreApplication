package com.hanna.clearscore.application.test.repository

import com.hanna.clearscore.application.test.datasource.network.Resource
import com.hanna.clearscore.application.test.datasource.network.responses.ResponseModel
import com.hanna.clearscore.application.test.models.CreditReportInfo
import kotlinx.coroutines.flow.Flow


//this interface defines the functionality that will be required in the implemented repository.
//having this interface, helps us with mocking when we test, and helps
// faking implementations until be is ready, so can work parallel (be, and client)
interface CreditScoreRepository {
    fun getCreditScore(): Flow<Resource<CreditReportInfo>>
}