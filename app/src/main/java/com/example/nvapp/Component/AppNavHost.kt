package com.example.nvapp.Component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.navigation.BottomSheetNavigator
import androidx.compose.material.navigation.bottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.get
import com.example.nvapp.Commponent.BottomSheet
import com.example.nvapp.R
import com.example.nvapp.Screens.CameraScreen
import com.example.nvapp.Screens.FriendsScreen
import com.example.nvapp.Screens.HomeScreen
import com.example.nvapp.Screens.MessageScreen
import com.example.nvapp.Screens.ProfileScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination : String = stringResource(R.string.homeRoute),
    modifier: Modifier = Modifier
){
    val home = stringResource(R.string.homeRoute)
    val friends = stringResource(R.string.friendsRoute)
    val camera = stringResource(R.string.cameraRoute)
    val message = stringResource(R.string.messagesRoute)
    val profile = stringResource(R.string.profileRoute)
    val authentication = stringResource(R.string.autnenticationRoute)
    val comment = stringResource(R.string.commentRoute)
  NavHost(
      navController = navController,
      startDestination = startDestination,
      modifier = modifier
  ){
      /*
      先定义最重要的五个导航
       */
      composable(
          route = home
      ){
          //TODO:主页
          HomeScreen(navController)
      }
      composable(
          route = friends
      ){
          //TODO:好友
          FriendsScreen(navController)
      }
      composable(
          route = camera
      ){
          //TODO:拍摄
          CameraScreen()
      }
      composable(
          route = message
      ){
          //TODO:信息
          MessageScreen()
      }
      composable(
          route = profile
      ){
          //TODO:个人中心
          ProfileScreen()
      }
      //TODO:完善视频转发逻辑、用户个人管理界面和个人创作界面（这个可以先等一等，）,学习HIlt可以设计数据ViewModel，搭建数据流
      addDestination(
          BottomSheetNavigator.Destination(
              provider[BottomSheetNavigator::class]
          ) {
              BottomSheet {
                  //返回上一个导航目的地
                  navController.navigateUp()
              }
          }.apply {
              this.route = authentication
          }
      )
      addDestination(
          BottomSheetNavigator.Destination(
              provider[BottomSheetNavigator::class]
          ){
              CommentBottomSheet(
                  onClickCancel = {
                      navController.navigateUp()
                  }
              )
          }.apply {
              this.route = comment
          }
      )
  }
}



