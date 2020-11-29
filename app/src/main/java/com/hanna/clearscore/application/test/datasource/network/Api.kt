package com.hanna.clearscore.application.test.datasource.network

import com.hanna.clearscore.application.test.datasource.network.responses.ResponseModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

//Prototypes - N/A
//Tests - N/A
interface Api {

    @GET("endpoint.json")
    fun getData(): Flow<ApiResponse<ResponseModel>>
}