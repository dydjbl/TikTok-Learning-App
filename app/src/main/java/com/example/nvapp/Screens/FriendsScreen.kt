package com.example.nvapp.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.nvapp.Commponent.TopBar
import com.example.nvapp.R

@Composable
fun FriendsScreen(
    navController: NavController
){
    Scaffold (
        topBar = {
            TopBar(
                navIcon = null,
                title = stringResource(R.string.friendsTitle)
            )
        }
    ){
        Column (
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ){

        }
    }
    val authentication = stringResource(R.string.autnenticationRoute)
    LaunchedEffect(Unit) {
        navController.apply {
            popBackStack()
            navController.navigate(authentication)
        }
    }
}