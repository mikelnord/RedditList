package com.android.gb.redditlist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.gb.redditlist.model.RemoteKeys

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: RemoteKeys)

    @Query("SELECT *  FROM remote_keys order by id desc limit 1")
    suspend fun remoteKey(): RemoteKeys

    @Query("DELETE FROM remote_keys ")
    suspend fun clearRemoteKeys()

}