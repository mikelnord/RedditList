package com.android.gb.redditlist.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.android.gb.redditlist.db.RedditDatabase
import com.android.gb.redditlist.model.RedditPost
import com.android.gb.redditlist.network.api.RedditService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    private val redditService: RedditService,
    private val database: RedditDatabase
) {

    private val NETWORK_PAGE_SIZE = 50

    fun getResultStream(): Flow<PagingData<RedditPost>> {
        val pagingSourceFactory = { database.redditPostDao().postsAll() }
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = RedditRemoteMediator(
                redditService,
                database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

}