package com.android.gb.redditlist.di

import android.content.Context
import androidx.room.Room
import com.android.gb.redditlist.db.RedditDatabase
import com.android.gb.redditlist.db.RedditPostDao
import com.android.gb.redditlist.db.RemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMoviesDatabase(@ApplicationContext appContext: Context): RedditDatabase {
        return Room.databaseBuilder(
            appContext,
            RedditDatabase::class.java,
            "Movies.db"
        ).build()
    }

    @Provides
    fun provideRedditPostDao(redditDatabase: RedditDatabase): RedditPostDao {
        return redditDatabase.redditPostDao()
    }

    @Provides
    fun provideRemoteKeysDao(redditDatabase: RedditDatabase): RemoteKeyDao {
        return redditDatabase.remoteKeysDao()
    }

}