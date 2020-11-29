package com.hanna.clearscore.application.test.datasource.network

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take

abstract class NetworkOnlyResource<ResultType, RequestType> {

    fun asFlow() = flow {
        emit(Resource.loading(null))
        fetchFromNetwork().take(1).collect { apiResponse ->
            when (apiResponse) {
                is ApiSuccessResponse -> {
                    val data = apiResponse.body
                    saveNetworkResult(apiResponse.body)
                    emit(Resource.success(convertRequest(data)))
                }
                is ApiErrorResponse -> {
                    emit(Resource.error(apiResponse.errorMessage, null))
                }
                else -> {
                }
            }
        }
    }

    protected open fun onFetchFailed() {
        // Implement in sub-classes to handle errors
    }

    @WorkerThread
    protected abstract suspend fun saveNetworkResult(item: RequestType)

    @MainThread
    protected abstract suspend fun fetchFromNetwork(): Flow<ApiResponse<RequestType>>

    @WorkerThread
    protected abstract fun convertRequest(request: RequestType): ResultType
}
