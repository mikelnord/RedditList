package com.android.gb.redditlist.network.api

import com.android.gb.redditlist.model.RedditResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditService {

    @GET("hot.json")
    suspend fun getHotList(
        @Query("limit") limit: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): RedditResponse

}