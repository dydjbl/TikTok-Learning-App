package com.example.nvapp.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.nvapp.R
import com.example.nvapp.ui.theme2.TikTokTheme
import com.example.nvapp.ui.theme2.White
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier
){
    val tabItems = arrayListOf(R.string.following, R.string.foryou)
    /*
    创建一个可感知可组合函数的协程，默认运行在主线程上
     */
    val coroutineScope = rememberCoroutineScope()
    //使用HorizontalPager与TabRow去实现水平分页效果
    /*
    HorizontalPager对pagerState进行监听，根据其状态展示不同的内容
     */
    val pagerState = rememberPagerState(initialPage = 1){tabItems.size}
    TikTokTheme (
        darkTheme = true
    ){
        Box(
            modifier = modifier.fillMaxSize()
        ){
            HorizontalPager(
                state = pagerState
            ) {
                when(it){
                    0 -> FollwingScreen()
                    1 -> ForyouScreen(navController)
                }
            }
            val edgePadding = LocalConfiguration.current.screenWidthDp.dp.div(2).minus(100.dp)
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color.Transparent,
                divider = {},
                modifier = Modifier.padding(top = 8.dp),
                indicator = {
                    val modifier = Modifier
                        .tabIndicatorOffset(it[pagerState.currentPage])
                        .padding(horizontal = 38.dp)
                    TabRowDefaults.Indicator(
                        modifier,
                        color = White
                    )
                },
                edgePadding = edgePadding
            ) {
                tabItems.forEachIndexed { index, i ->
                    val isSelected = pagerState.currentPage == index
                    Tab(
                        selected = isSelected,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            val textStyle = if (isSelected) MaterialTheme.typography.titleMedium.merge(
                                TextStyle(color = Color.White)
                            ) else TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 17.sp,
                                color = White.copy(alpha = 0.6f)
                            )
                            Text(text = stringResource(id = i), style = textStyle)
                        }
                    )
                }
            }
        }
    }
}