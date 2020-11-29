package com.hanna.clearscore.application.test.datasource.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.awaitResponse
import java.lang.reflect.Type

/**
 * This class is responsible for transforming the network responses, to ApiResponses
 */
class FlowCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, Flow<ApiResponse<R>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<R>): Flow<ApiResponse<R>> {
        return flow {
            try {
                val response = call.awaitResponse()
                if (response.isSuccessful) {
                    emit(ApiResponse.create(response))
                } else {
                    emit(ApiResponse.create<R>(Throwable("An error occurred")))
                }
            } catch (e: Exception) {
                emit(ApiResponse.create<R>(Throwable(e.message)))
            }
        }
    }
}