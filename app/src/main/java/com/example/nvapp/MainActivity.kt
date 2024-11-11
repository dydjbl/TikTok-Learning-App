package com.example.nvapp

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.compose.NVAppTheme
import com.example.nvapp.ui.theme2.TikTokTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        设置app启动动画的效果，可以进行动画效果设置等
         */
        installSplashScreen()
        /*
        设置系统UI颜色背景等.隐藏状态栏和系统底部导航栏
         */
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        enableEdgeToEdge()
        setContent {
             TikTokTheme {
                 RootScreen()
            }
        }
    }
}
