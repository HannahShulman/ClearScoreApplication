package com.hanna.clearscore.application.test.datasource.network

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ResourceTests {

    @Test
    fun `create a loading resource with no data, return resource with correct info `() {
        val resource = Resource.loading(null)
        assertThat(resource).isEqualTo(Resource(Status.LOADING, null, null))
    }

    @Test
    fun `create a loading resource with data, return resource with correct info `() {
        val resource = Resource.loading("data")
        assertThat(resource).isEqualTo(Resource(Status.LOADING, "data", null))
    }

    @Test
    fun `create an error resource with no data, return resource with correct info `() {
        val resource = Resource.error("error message", null)
        assertThat(resource).isEqualTo(Resource(Status.ERROR, null, "error message"))
    }

    @Test
    fun `create an error resource with data, return resource with correct info `() {
        val resource = Resource.error("error message", "data")
        assertThat(resource).isEqualTo(Resource(Status.ERROR, "data", "error message"))
    }

    @Test
    fun `create a success resource with no data, return resource with correct info `() {
        val resource = Resource.success(null)
        assertThat(resource).isEqualTo(Resource(Status.SUCCESS, null, null))
    }

    @Test
    fun `create a success resource with data, return resource with correct info `() {
        val resource = Resource.success("DATA")
        assertThat(resource).isEqualTo(Resource(Status.SUCCESS, "DATA", null))
    }
}