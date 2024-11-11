package com.example.nvapp

import android.app.Activity
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.navigation.BottomSheetNavigator
import androidx.compose.material.navigation.ModalBottomSheetLayout
import androidx.compose.material.navigation.rememberBottomSheetNavigator
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nvapp.Component.AppNavHost
import com.example.nvapp.Component.BottomBar
import com.example.nvapp.Component.NewAppNavHost
import com.example.nvapp.ui.theme2.TikTokTheme


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootScreen() {

    val isShowBottomBar = true
    /*
    设置弹出栏导航，采用material中的底部导航栏和navController，navHost，navGraph结合起来，
    这样就通过一个导航控制器控制了所有导航信息
     */
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheetNavigator)
    val currentBackStackEntryState by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntryState?.destination
    val context = LocalContext.current
    val homeString = context.getString(R.string.homeRoute)
    val comment = context.getString(R.string.commentRoute)
    val authentication = context.getString(R.string.autnenticationRoute)
    val darkMode = when(currentDestination?.route){
        homeString,  comment, authentication-> true
        else -> false
    }

//    if (currentDestination?.route == stringResource(R.string.homeRoute)) {
//        //即在导航到home界面时，对系统返回事件进行监听，检测到则直接结束activity
//        BackHandler {
//            (context as? Activity ) ?.finish()
//        }
//    }
    //这里有个隐藏的坑是：navigation在涉及背景颜色切换的时候会非常卡。而且对于bottomsheet来说，会引起莫名的crash，不确定是因为google线程的问题还是什么
    TikTokTheme(darkMode) {
       ModalBottomSheetLayout(bottomSheetNavigator = bottomSheetNavigator) {
            Scaffold (
                topBar = {

                },
                bottomBar = {
                    if (isShowBottomBar) {
                        BottomBar(navController, currentDestination, {
                                destination ->
                            destination.route.let {
                                navController.navigate(context.getString(it)){
                                    //这个参数保证了使用导航的时候，容器内界面只会创建一次
                                    launchSingleTop = true
                                }
                            }
                        })
                    }
                }
            ){
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(it)
                ){
                    AppNavHost(navController)
                }
            }
        }
    }

}