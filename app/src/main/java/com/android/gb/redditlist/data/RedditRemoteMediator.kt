package com.android.gb.redditlist.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.android.gb.redditlist.db.RedditDatabase
import com.android.gb.redditlist.db.RedditPostDao
import com.android.gb.redditlist.db.RemoteKeyDao
import com.android.gb.redditlist.model.RedditPost
import com.android.gb.redditlist.model.RemoteKeys
import com.android.gb.redditlist.network.api.RedditService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RedditRemoteMediator @Inject constructor(
    private val service: RedditService,
    private val redditDatabase: RedditDatabase
) : RemoteMediator<Int, RedditPost>()  {

    private val postDao: RedditPostDao = redditDatabase.redditPostDao()
    private val remoteKeyDao: RemoteKeyDao = redditDatabase.remoteKeysDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RedditPost>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey =
                        remoteKeyDao.remoteKey()

                    if (remoteKey.nextPageKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.nextPageKey
                }
            }

            val data = service.getHotList(
                after = loadKey,
                before = null,
                limit = when (loadType) {
                    LoadType.REFRESH -> state.config.initialLoadSize
                    else -> state.config.pageSize
                }
            ).data

            val items = data.children.map { it.data }

            redditDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    postDao.clearPosts()
                    remoteKeyDao.clearRemoteKeys()
                }

                remoteKeyDao.insert(RemoteKeys(data.after))
                postDao.insertAll(items)
            }

            return MediatorResult.Success(endOfPaginationReached = items.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }


}