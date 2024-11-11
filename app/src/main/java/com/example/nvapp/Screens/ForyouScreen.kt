package com.example.nvapp.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nvapp.Data.UserModel
import com.example.nvapp.Data.VideoModel
import com.example.nvapp.Data.VideoStats
import com.example.nvapp.Data.ViewerInteraction
import com.example.nvapp.Player.VerticalVideoPager
import com.example.nvapp.R
import com.example.nvapp.ViewModel.ForyouViewModel
import com.example.nvapp.ui.theme2.DarkBlue
import com.example.nvapp.ui.theme2.DarkPink

@Composable
fun ForyouScreen(
    navController: NavController,
    viewModel: ForyouViewModel = ForyouViewModel(),
    modifier: Modifier = Modifier
){
    //TODO:完善ViewModel视频流观察播放逻辑
    val user = UserModel("","",1L,1L,1L)
    val videoStats = VideoStats(1L, 1L, 1L)
    val viewerInteraction = ViewerInteraction()
    val video = VideoModel("1",user,videoStats,"daniel_vid6.mp4", "",viewerInteraction)
    val context = LocalContext.current
    Box(
        //brush实现一个画笔的渐变色效果
        modifier = modifier.fillMaxSize().background(
            brush = Brush.horizontalGradient(
                listOf(DarkPink, DarkBlue)
            )
        )
    ){
        VerticalVideoPager(
           videos = mutableListOf(video),
            {
                navController.navigate(context.getString(R.string.commentRoute)){
                    //这个参数保证了使用导航的时候，容器内界面只会创建一次
                    launchSingleTop = true
                }
            },
            {},
            {},
            {}
        )
    }

}