package com.example.tagskotlinproject.network

import com.example.tagskotlinproject.pojo.TokenResponse
import com.example.tagskotlinproject.pojo.TagModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestAPI {
    @GET("/oauth2/token?")
    fun userAuthorization(
            @Query("grant_type") client_credentials: String,
            @Query("client_id") client_id: Int,
            @Query("client_secret") client_secret: String,
    ): Call<TokenResponse>


    @GET("/api/v1/oauth2/browse/tags?")
    fun getTags(@Query("tag") tag: String,
                @Query("limit") limit:Int,
                @Query("token") access_token: String): Call<TagModel>
}