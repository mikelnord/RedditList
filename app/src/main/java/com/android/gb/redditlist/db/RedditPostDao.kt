package com.android.gb.redditlist.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.gb.redditlist.model.RedditPost

@Dao
interface RedditPostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<RedditPost>)

    @Query("SELECT * FROM posts")
    fun postsAll(): PagingSource<Int, RedditPost>

    @Query("DELETE FROM posts")
    suspend fun clearPosts()

}