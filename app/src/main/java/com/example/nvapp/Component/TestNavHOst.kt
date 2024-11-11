package com.example.nvapp.Component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.navigation.BottomSheetNavigator
import androidx.compose.material.navigation.bottomSheet
import androidx.compose.material3.Button
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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


@Composable
fun NewAppNavHost(
    navController: NavHostController,
    startDestination : String = stringResource(R.string.homeRoute),
    modifier: Modifier = Modifier
){
    val home = stringResource(R.string.homeRoute)
    val authentication = stringResource(R.string.autnenticationRoute)
    val friends = stringResource(R.string.friendsRoute)
    val camera = stringResource(R.string.cameraRoute)
    val message = stringResource(R.string.messagesRoute)
    val profile = stringResource(R.string.profileRoute)
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
        composable(
            route = home
        ){
            HomeScreen(navController)
        }
        bottomSheet(
            route = authentication
        ) {
            BottomSheet {
                navController.navigateUp()
            }
        }
        bottomSheet(
            route = comment
        ) {
            BottomSheet {
                navController.navigateUp()
            }
        }
    }
}