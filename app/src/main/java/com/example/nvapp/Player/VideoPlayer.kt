package com.example.nvapp.Player

import android.graphics.Bitmap
import android.net.Uri
import android.os.FileUtils
import android.util.Log
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.nvapp.Data.VideoModel
import com.example.nvapp.Utils.extractThumbNail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    video: VideoModel,
    pager: PagerState,
    pageIndex: Int,
    onSingleTap: (exoPlayer: ExoPlayer) -> Unit = {},
    onDoubleTap: (exoPlayer: ExoPlayer, offset: Offset) -> Unit = {
        _, _ ->
    },
    onVideoDispose: () -> Unit = {},
    onVideoGoBackGround: () -> Unit = {},
    modifier: Modifier = Modifier,
){
    val context = LocalContext.current
    var thumbNail by remember {
        mutableStateOf<Pair<Bitmap?, Boolean>>(Pair(null, true))
    }

    /*
    提取MP4文件的缩略图
     */
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            val bitmap = extractThumbNail(context.assets.openFd("videos/${video.videoLink}"),1)
            withContext(Dispatchers.Main) {
                thumbNail = thumbNail.copy(first = bitmap, second = thumbNail.second)
            }
        }
    }

    /*
    在 PagerState 中，settledPage 属性表示当前“已固定”的页面，即用户在滑动操作完成后，最终停留的页面索引
    也就是说这个值只有在动画和滑动结束之后才会进行更新
     */
    if (pager.settledPage == pageIndex) {
        val exoPlayer = remember(context){
            ExoPlayer.Builder(context).build().apply {
                videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT
                repeatMode = Player.REPEAT_MODE_ONE
                setMediaItem(
                    MediaItem.fromUri(Uri.parse("asset:///videos/${video.videoLink}"))
                )
                playWhenReady = true
                prepare()
                play()
                addListener(
                    object : Player.Listener{
                        /*
                        对播放器进行监听，当第一帧渲染出来时，则停止缩略图的展示
                         */
                        override fun onRenderedFirstFrame() {
                            super.onRenderedFirstFrame()
                            thumbNail = thumbNail.copy(second = false)
                        }
                    }
                )
            }
        }

        /*
        这个值保证是最新的，而普通的remember在组合过程是不会发生改变的（即使外界发生了改变）
        对播放器所在的生命周期进行监听，适时的移除播放器
         */
        val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)
        DisposableEffect(lifecycleOwner) {
            val lifeCycleObserver = LifecycleEventObserver{_, event ->
                when (event) {
                    /*
                    监听owner的生命周期，以实现切换到后台再切换回来时不会发生视频的重置，而是沿着之前播放的时刻继续播放
                     */
                    Lifecycle.Event.ON_STOP -> {
                        exoPlayer.pause()
                        onVideoGoBackGround
                    }
                    Lifecycle.Event.ON_START -> {
                        exoPlayer.play()
                    }
                    else -> {}
                }
            }
            lifecycleOwner.lifecycle.addObserver(lifeCycleObserver)
            onDispose {
                lifecycleOwner.lifecycle.removeObserver(lifeCycleObserver)
            }
        }

        /*
        useController设置取消了视频控制的按钮
         */
        val playerView = remember {
            PlayerView(context).apply {
                player = exoPlayer
                useController = false
                resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }
        AndroidView(
            factory = {
                playerView
            },
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        onSingleTap(exoPlayer)
                }, onDoubleTap = {
                    offset ->
                    onDoubleTap(exoPlayer, offset)
                })
            }
        )
        DisposableEffect(Unit) {
            onDispose {
                thumbNail = thumbNail.copy(second = true)
                exoPlayer.release()
                onVideoDispose()
            }
        }
    }
    if (thumbNail.second) {
        AsyncImage(
            model = thumbNail.first,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale =  ContentScale.Crop
        )
    }

}