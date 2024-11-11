package com.example.nvapp.Commponent

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.nvapp.Extensions.LoginOption
import com.example.nvapp.Extensions.Space
import com.example.nvapp.R
import com.example.nvapp.ui.theme2.SeparatorColor
import com.example.nvapp.ui.theme2.SubTextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    onCancel: () -> Unit
){
        // Sheet content
        Column (
            modifier = Modifier.fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(horizontal = 28.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
           Row (
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(top = 16.dp),
               horizontalArrangement = Arrangement.SpaceBetween,
               verticalAlignment = Alignment.CenterVertically
           ){
               Icon(
                   painter = painterResource(id = R.drawable.ic_cancel),
                   modifier = Modifier
                       .clickable {
                           onCancel()
                       }
                       .size(24.dp),
                   contentDescription = null
               )
               Icon(
                   painter = painterResource(id = R.drawable.ic_question_circle),
                   contentDescription = null
               )
           }
            56.dp.Space()
            Text(
                text = stringResource(R.string.authentextTitle),
                style = MaterialTheme.typography.displayMedium
            )
            20.dp.Space()
            Text(
                text = stringResource(R.string.authentextLabel),
                color = SubTextColor,
                textAlign = TextAlign.Center
            )
            28.dp.Space()
            AuthenticationButton {

            }
            //TODO:完善登录注册部分的隐私协议政策
        }
}

@Composable
fun AuthenticationButton(
    onClick: () -> Unit
){
    LoginOption.values().forEach {
        BottomButton(stringResource(it.title), it.icon, DpSize(24.dp,24.dp), containerColor = it.containerColor, contentColor = it.contentColor, onClick = {
            onClick()
        })
        12.dp.Space()
    }
}

@Composable
fun BottomButton(
    text: String,
    @DrawableRes icon: Int,
    iconSize: DpSize ,
    iconTint: Color = Color.Unspecified,
    style: TextStyle = MaterialTheme.typography.labelLarge,
    shape: RoundedCornerShape = RoundedCornerShape(2.dp),
    height: Dp = 44.dp,
    borderStroke: BorderStroke = BorderStroke(1.dp, color = SeparatorColor),
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
Button(
    onClick = {
        onClick()
    },
    modifier = modifier.height(height),
    shape = shape,
    border = borderStroke,
    colors = ButtonDefaults.buttonColors(
        containerColor = containerColor,
        contentColor = contentColor
    )
) {
    Row (
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(iconSize)
        )
        Text(
            text = text,
            style = style,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
    }
}
}