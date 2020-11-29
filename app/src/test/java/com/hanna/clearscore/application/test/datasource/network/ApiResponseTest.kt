package com.hanna.clearscore.application.test.datasource.network

import com.google.common.truth.Truth.assertThat
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response

class ApiResponseTest {

    @Test
    fun `create ApiResponse with Throwable, returns an ApiErrorResponse`() {
        val throwable = Throwable("This is an error")
        val response = ApiResponse.create<Int>(throwable)
        assertThat(response).isInstanceOf(ApiErrorResponse::class.java)
    }

    @Test
    fun `create ApiResponse with Throwable, returns an ApiErrorResponse with throwable message`() {
        val throwable = Throwable("This is an error")
        val response = ApiResponse.create<Int>(throwable)
        assertThat(response.errorMessage).isEqualTo("This is an error")
    }

    @Test
    fun `create ApiResponse with Throwable, with no message, returns an ApiErrorResponse with unknown error`() {
        val throwable = Throwable()
        val response = ApiResponse.create<Int>(throwable)
        assertThat(response.errorMessage).isEqualTo("unknown error")
    }

    @Test
    fun `create ApiResponse with a successful Response, with no body, returns ApiEmptyResponse`() {
        val response = Response.success(null)
        val apiResponse = ApiResponse.create(response)
        assertThat(apiResponse).isInstanceOf(ApiEmptyResponse::class.java)
    }

    @Test
    fun `create ApiResponse with a successful Response, with a body, returns ApiSuccessResponse`() {
        val response = Response.success(200, 30)
        val apiResponse = ApiResponse.create(response)
        assertThat(apiResponse).isInstanceOf(ApiSuccessResponse::class.java)
    }

    @Test
    fun `create ApiResponse with a successful Response, with a body, returned ApiSuccessResponse, has correct data`() {
        val response = Response.success(200, 30)
        val apiResponse = ApiResponse.create(response)
        assertThat(apiResponse).isInstanceOf(ApiSuccessResponse::class.java)
        assertThat((apiResponse as ApiSuccessResponse).body).isEqualTo(30)
    }


    @Test
    fun `create ApiResponse with an error Response, with a body, returns ApiErrorResponse`() {
        val errorResponse ="What you were looking for isn't here.}"
        val errorResponseBody = errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
        val response = Response.error<Int>(400, errorResponseBody)
        val apiResponse = ApiResponse.create(response)
        assertThat(apiResponse).isInstanceOf(ApiErrorResponse::class.java)
    }

    @Test
    fun `create ApiResponse with an error Response, with a message, returns ApiErrorResponse with correct error message`() {
        val errorResponse = "What you were looking for isn't here."
        val errorResponseBody = errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
        val response = Response.error<Int>(400, errorResponseBody)
        val apiResponse = ApiResponse.create(response)
        assertThat(apiResponse).isInstanceOf(ApiErrorResponse::class.java)
        assertThat((apiResponse as ApiErrorResponse).errorMessage).isEqualTo("What you were looking for isn't here.")
    }
}