package com.example.nvapp.Data

import com.example.nvapp.Extensions.formattedCount

/*
一个视频所包含的属性有：
1. 视频编号
2. 用户信息
3. 视频状态：点赞数等等
4. 视频链接
5. 视频描述
6. 当前观看者对视频的态度
7. 发布时间
8. 音频信息
 */
data class VideoModel(
    val videoId: String,
    val authorDetails: UserModel,
    val videoStats: VideoStats,
    val videoLink: String,
    val description: String,
    val currentViewerInteraction: ViewerInteraction,
    val createdAt: String = randomUploadDate(),
)

/*
用户信息有以下属性：
1. 用户id
2. 用户昵称
3. 关注的人数
4. 粉丝数
5. 被点赞数
 */
data class UserModel(
    var useId: String,
    var nickName:String,
    var followingNum: Long,
    var followers: Long,
    var likes: Long,
)

data class VideoStats(
    var like: Long,
    var comment: Long,
    var share: Long,
    var views: Long = 0
)


data class ViewerInteraction(
    var isLikedByyou: Boolean = false,
    var isAddToFavorite: Boolean = false
)

fun randomUploadDate(): String = "${(1..24).random()}h"
