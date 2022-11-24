package com.android.gb.redditlist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.android.gb.redditlist.model.RedditPost
import com.android.gb.redditlist.model.RemoteKeys

@Database(
    entities = [RedditPost::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class RedditDatabase : RoomDatabase() {

    abstract fun redditPostDao(): RedditPostDao
    abstract fun remoteKeysDao(): RemoteKeyDao

}