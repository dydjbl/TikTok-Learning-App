package com.example.nvapp.Component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.serialization.generateRouteWithArgs
import com.example.nvapp.R

@Composable
fun BottomBar(
    navController: NavController,
    currentDestination: NavDestination?,
    onClick: (destination: BottomBarDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    /*
    底部导航栏：导航到home friends camera message和profile
     */
    NavigationBar (
        modifier = modifier.offset(y = 5.dp).height(52.dp).shadow(16.dp)
    ){
        BottomBarDestination.entries.forEach{
            BottomItem(it, currentDestination, {
                onClick(it)
            })
        }
    }
}


/*
定义一个相当于在Row函数里面调用的可组合函数，可以使用row布局的一些属性：对齐呀等等
 */
@Composable
fun RowScope.BottomItem(
    screen: BottomBarDestination,
    currentDestination: NavDestination?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){

    //拍摄图标位置要稍微高出底部导航栏一点
    val (iconSize, offsetY) = if (screen == BottomBarDestination.CAMERA) Pair(42.dp, 0.dp) else Pair(22.dp, 2.dp)
    val isItemSelected = stringResource(screen.route)  == currentDestination?.route

    var icon = screen.unFilledIcon
    screen.apply {
        if (isItemSelected) {
            filledIcon?.let {
                icon = it
            }
        }
    }
    NavigationBarItem(
        modifier = modifier,
        label = {
            Text(
                text = stringResource(id = screen.title),
                style = MaterialTheme.typography.labelSmall,
                softWrap = false,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = if (isItemSelected) 1.0f else 0.7f)
            )
        },
        icon = {
            Icon(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = modifier.size(iconSize),
                tint = Color.Unspecified
            )
        },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.surface,
            selectedIconColor = MaterialTheme.colorScheme.secondary,
            selectedTextColor = MaterialTheme.colorScheme.secondary
        ),
        selected = isItemSelected,
        onClick = {
           onClick()
        }
    )
}

enum class BottomBarDestination(
    @StringRes val route: Int,
    @StringRes val title: Int,
    @DrawableRes val unFilledIcon: Int,
    @DrawableRes val filledIcon: Int,
){
    /*
    我的导航界面及底部弹窗（都用同一个导航来控制）有以下几个
    home：展示视频流
    friends：展示好友信息
    comment：视频评论信息
    createVideo：创造视频
    profile：个人信息
    createProfile：创建个人信息界面
    camera：拍摄界面
    setting：系统app设置界面
    login：登录或者注册界面
    message: IM通讯界面

    在routescreen中只使用以下五个就好
     */
    HOME(
        route = R.string.homeRoute,
        title = R.string.homeTitle,
        unFilledIcon = R.drawable.ic_home,
        filledIcon = R.drawable.ic_home_fill
    ),
    FRIENDS(
        route = R.string.friendsRoute,
        title = R.string.friendsTitle,
        unFilledIcon = R.drawable.ic_friends,
        filledIcon = R.drawable.ic_friends
    ),
    CAMERA(
        route = R.string.cameraRoute,
        title = R.string.cameraTitle,
        unFilledIcon = R.drawable.ic_add_dark,
        filledIcon = R.drawable.ic_add_light
    ),
    MESSAGE(
        route = R.string.messagesRoute,
        title = R.string.messageTitle,
        unFilledIcon = R.drawable.ic_inbox,
        filledIcon = R.drawable.ic_inbox
    ),
    PROFILE(
        route = R.string.profileRoute,
        title = R.string.profileTitle,
        unFilledIcon = R.drawable.ic_profile,
        filledIcon = R.drawable.ic_profile_fill
    )
}

val BottomBarItemVerticalOffset = 10.dp

