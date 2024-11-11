package com.example.nvapp.Player

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateSizeAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.nvapp.Data.VideoModel
import com.example.nvapp.Extensions.Space
import com.example.nvapp.R
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


/*
构建一个竖直方向上的分页音频播放器
 */
@Composable
fun VerticalVideoPager(
    videos: List<VideoModel>,
    onClickComment: () -> Unit,
    onClickLike: () -> Unit,
    onClickShare: () -> Unit,
    onClickUser: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(initialPage = 0){videos.size}
    val coroutineScope = rememberCoroutineScope()
    val localDensity = LocalDensity.current

    /*
    定义分页组件在快速滑动时的动画效果，其中定义了页面快照的动画效果：
    例如，如果用户从第 1 页快速滑动到第 3 页，快照动画会使第 3 页平滑过渡到视图中，而不是展示第 1 页和第 2 页的内容
     */
    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(1),
        decayAnimationSpec = rememberSplineBasedDecay(),
        snapAnimationSpec = spring(
            stiffness = Spring.StiffnessMediumLow,
            visibilityThreshold = Int.VisibilityThreshold.toFloat()
        ),
        snapPositionalThreshold = 0.5f
    )
    VerticalPager(
        state = pagerState,
        flingBehavior = fling,
        modifier = modifier
    ) {
        var pauseButtonVisibility by remember {
            mutableStateOf(false)
        }

        var doubleLikeVisibility by remember {
            mutableStateOf(
                Triple(
                    Offset.Unspecified,
                    false,
                    0f
                )
            )
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ){
            VideoPlayer(videos[it], pagerState, it,
                onSingleTap = {
                    exoPlayer ->
                    pauseButtonVisibility = !pauseButtonVisibility
                    exoPlayer.playWhenReady = !pauseButtonVisibility
                },
                onDoubleTap = {
                    exoPlayer, offset ->
                    val rotationAngle = (-10..10).random()
                    doubleLikeVisibility = Triple(offset, true, rotationAngle.toFloat())
                    videos[it].currentViewerInteraction.isLikedByyou = true
                    Log.d("zhuzihang", videos[it].currentViewerInteraction.isLikedByyou.toString())
                    coroutineScope.launch {
                        delay(400)
                        doubleLikeVisibility = Triple(offset, false, rotationAngle.toFloat())
                    }
                },
                onVideoDispose = {
                    pauseButtonVisibility = false
                },
                onVideoGoBackGround = {
                    pauseButtonVisibility = false
                }
                )
            Column (
                modifier = Modifier.align(Alignment.BottomCenter)
            ){
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.Bottom
                ){
                    Footer(
                        "hhh","hh","HH",{},
                        modifier = Modifier.fillMaxWidth().weight(1f),
                    )
                    SideItems(
                        item = videos[it],
                        onClickLike = {
                            videos[it].currentViewerInteraction.isLikedByyou = !videos[it].currentViewerInteraction.isLikedByyou
                        },
                        onClickComment = onClickComment
                    )
                }
                12.dp.Space()
            }

            /*
            tween是基于时间的动画效果，
            spring是基于物理弹簧机械的动画效果
             */

            /*
            暂停按钮
             */
            AnimatedVisibility(
                visible = pauseButtonVisibility,
                modifier = Modifier.align(Alignment.Center),
                enter = scaleIn(
                    spring(Spring.DampingRatioMediumBouncy), initialScale = 1.5f
                ),
                exit = scaleOut(tween())
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_play),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(36.dp)
                )
            }

            /*
            为了实现跳动的红星感，在scaleOut时设置了动画效果，改变爱心的大小。同时还加上了fadeOut的渐变效果
             */
            val iconSize = 110.dp
            AnimatedVisibility(
                visible = doubleLikeVisibility.second,
                enter = scaleIn(
                    spring(Spring.DampingRatioMediumBouncy), initialScale = 1.5f
                ),
                exit = scaleOut(tween(600), targetScale = 1.6f)+ fadeOut(tween(600))+ slideOutVertically(
                    tween(600)
                ),
                modifier = Modifier.offset(
                    x = localDensity.run {
                        //这里很特别提供了一个density上下文给toDp拓展函数，从而可以直接转为dp
                        doubleLikeVisibility.first.x.toInt().toDp().minus(iconSize/2)
                    },
                    y = localDensity.run {
                        doubleLikeVisibility.first.y.toInt().toDp().minus(iconSize/2)
                    }
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_like),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .size(iconSize)
                        .rotate(doubleLikeVisibility.third)
                )
            }
        }
    }
}

@Composable
fun SideItems(
    item: VideoModel,
    onClickComment: () -> Unit = {},
    onClickLike: () -> Unit = {},
    onClickShare: () -> Unit = {},
    onClickUser: () -> Unit = {},
    modifier: Modifier = Modifier
){
    val context = LocalContext.current
    /*依次是
    1. 用户头像
    2. 喜爱
    3. 评论
    4. 转发
     */
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.img_effect_placeholder),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .border(
                    BorderStroke(1.dp, Color.White)
                )
                .clip(shape = CircleShape)
                .clickable {
                    //点击用户头像跳转用户详情界面
                    onClickUser()
                },
            contentScale = ContentScale.Crop
        )
        Image(
            painter = painterResource(id = R.drawable.ic_plus),
            contentDescription = null,
            modifier = Modifier
                .offset(y = (-10).dp)
                .size(20.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(5.5.dp),
            colorFilter = ColorFilter.tint(Color.White)
        )
        mySpacer()
        //TODO:补充点赞数据逻辑
        var isLiked by remember {
            mutableStateOf(false)
        }
        LikeIconButton(
            isLiked = isLiked,
            likeCount = "999",
            onLikeClick = {
                isLiked = !isLiked
            }
        )
        mySpacer()
        SideItemButton("1k", onClickComment, R.drawable.ic_comment)
        mySpacer()
        SideItemButton("22k", onClickShare, R.drawable.ic_bookmark)
        mySpacer()
        SideItemButton("100", onClickShare, R.drawable.ic_share)
    }
}

@Composable
fun Footer(
    authorName: String,
    description: String,
    audioInfo: String,
    onClickAudio:() -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom
    ){
        Text(
            text = authorName,
            style = MaterialTheme.typography.bodyMedium
        )
        5.dp.Space()
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth(0.85f)
        )
        10.dp.Space()
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.clickable {
                onClickAudio()
            }
        ){
            Icon(
                painter = painterResource(id = R.drawable.ic_music_note),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(12.dp)
            )
            Text(text = audioInfo,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .basicMarquee())
        }
    }
}
@Composable
fun LikeIconButton(
    isLiked: Boolean,
    likeCount: String,
    onLikeClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val maxSize = 38.dp
    /*
    使用动画dp去实现一个跳跃的红心点赞效果
     */
    val iconSize by animateDpAsState(
        targetValue = if (isLiked) 33.dp else 32.dp,
        animationSpec = keyframes {
            durationMillis = 400
            24.dp.at(50)
            maxSize.at(190)
            26.dp.at(330)
            32.dp.at(400).with(FastOutLinearInEasing)
        }
    )
    Box (
        modifier = Modifier
            .size(maxSize)
            .clickable{
                onLikeClick()
            },
        contentAlignment = Alignment.Center
    ){
        Icon(
            painter = painterResource(R.drawable.ic_like),
            contentDescription = null,
            tint = if (isLiked) MaterialTheme.colorScheme.primary else Color.White,
            modifier = Modifier.size(iconSize)
        )
    }
    Text(
        text = likeCount,
        style = MaterialTheme.typography.labelMedium
    )
}

@Composable
fun SideItemButton(
    count: String,
    onClick: () -> Unit,
    @DrawableRes image: Int,
    modifier: Modifier = Modifier
){
    Icon(
        painter = painterResource(
            id = image
        ),
        contentDescription = null,
        tint = Color.Unspecified,
        modifier = modifier
            .size(33.dp)
            .clickable {
                onClick()
            }
    )
    Text(
        text = count,
        style = MaterialTheme.typography.labelMedium
    )
}
@Composable
fun mySpacer(){
    Spacer(
        modifier = Modifier.height(10.dp)
    )
}
