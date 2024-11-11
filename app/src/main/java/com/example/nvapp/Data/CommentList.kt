package com.example.nvapp.Data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class CommentList(
    val videoId: String,
    val totalComment: Int,
    val comments: List<Comment>
){
    data class Comment(
        val commentBy: UserModel,
        val comment: String,
        val createdAt: String,
        val totalLike: Long,
        val totalDisLike:Long,
        val threadCount: Int,
        val thread: List<Comment>?
    )
}

data class CommentText(
    val comment: String
)
val user = UserModel("","zhuzihang",1L,1L,1L)
// 获取当前的当地时间
@RequiresApi(Build.VERSION_CODES.O)
val currentTime = LocalDateTime.now()

// 定义时间格式
@RequiresApi(Build.VERSION_CODES.O)
val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
@RequiresApi(Build.VERSION_CODES.O)
val tesetComments = List(10){"这是一个超酷的视频"}.map{
    CommentList.Comment(
        commentBy = user,
        comment = it,
        createdAt = currentTime.format(formatter),
        totalLike = 10L,
        totalDisLike = 10L,
        threadCount = 1,
        thread = null,
    )
}