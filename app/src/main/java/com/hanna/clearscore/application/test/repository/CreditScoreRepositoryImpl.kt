package com.hanna.clearscore.application.test.repository

import com.hanna.clearscore.application.test.datasource.network.Api
import com.hanna.clearscore.application.test.datasource.network.ApiResponse
import com.hanna.clearscore.application.test.datasource.network.NetworkOnlyResource
import com.hanna.clearscore.application.test.datasource.network.Resource
import com.hanna.clearscore.application.test.datasource.network.responses.ResponseModel
import com.hanna.clearscore.application.test.models.CreditReportInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreditScoreRepositoryImpl @Inject constructor(val api: Api) : CreditScoreRepository {
    override fun getCreditScore(): Flow<Resource<CreditReportInfo>> {
        return object : NetworkOnlyResource<CreditReportInfo, ResponseModel>() {
            override suspend fun saveNetworkResult(item: ResponseModel) {}

            override suspend fun fetchFromNetwork(): Flow<ApiResponse<ResponseModel>> {
                return api.getData()
            }

            override fun convertRequest(request: ResponseModel): CreditReportInfo {
                return request.creditReportInfo
            }
        }.asFlow()
    }
}