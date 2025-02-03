package com.dljonesapps.fetchtest.data.api

import com.dljonesapps.fetchtest.data.model.Item
import retrofit2.http.GET

interface FetchApi {
    @GET("hiring.json")
    suspend fun getItems(): List<Item>
    
    companion object {
        const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"
    }
}
