package com.android.gb.redditlist.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class RedditResponse (
    val kind: String,
    val data: Data
)

data class Data (
    val after: String?,
    val dist: Long,
    val modhash: String,
    val children: List<Child>,
    val before: String? = null
)

data class Child (
    val data: RedditPost
)

@Entity(tableName = "posts")
data class RedditPost(
    @PrimaryKey
    val name: String,
    val title: String,
    val score: Int,
    val author: String,
    val subreddit: String,
    val num_comments: Int,
    val created: Long,
    val thumbnail: String?,
    val url: String?)