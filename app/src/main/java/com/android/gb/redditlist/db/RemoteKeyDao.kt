package com.android.gb.redditlist.db

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.gb.redditlist.model.RemoteKeys

interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: RemoteKeys)

    @Query("SELECT * FROM remote_keys")
    suspend fun remoteKey(): RemoteKeys

    @Query("DELETE FROM remote_keys ")
    suspend fun clearRemoteKeys()

}