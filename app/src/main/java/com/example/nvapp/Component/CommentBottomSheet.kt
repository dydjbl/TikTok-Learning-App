package com.example.nvapp.Component

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.example.nvapp.Data.CommentList
import com.example.nvapp.Data.HighlightedEmoji
import com.example.nvapp.Data.tesetComments
import com.example.nvapp.Extensions.Space
import com.example.nvapp.R
import com.example.nvapp.ui.theme2.DarkBlue
import com.example.nvapp.ui.theme2.GrayMainColor
import com.example.nvapp.ui.theme2.SubTextColor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CommentBottomSheet(
    onClickCancel: () -> Unit,
    modifier: Modifier = Modifier
){
  Column(
      modifier = modifier.fillMaxHeight(0.5f)
  ) {
      12.dp.Space()
      Box(
          modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp)
      ) {
          Text(
              text = "110条${stringResource(R.string.commentTitle)}",
              style = MaterialTheme.typography.titleSmall,
              modifier = Modifier.align(Alignment.Center)
          )
          Icon(
              painter = painterResource(id = R.drawable.ic_cancel),
              contentDescription = null,
              tint = Color.Unspecified,
              modifier = Modifier
                  .align(Alignment.TopEnd)
                  .clickable {
                      onClickCancel()
                  }
          )
      }
      6.dp.Space()
      LazyColumn (
          contentPadding = PaddingValues(top = 4.dp),
          modifier = Modifier.weight(1f)
      ){
          tesetComments.let { comments ->
              items(comments.size){
                  CommentItem(tesetComments[it])
                  24.dp.Space()
              }
          }
      }
      CommentUserField()
  }
}

@Composable
fun CommentItem(
    item: CommentList.Comment
){
    ConstraintLayout (
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ){
        val (profileImg, name, comment, createdOn, reply, like, unLike) = createRefs()
        AsyncImage(
            model = R.drawable.img_effect_placeholder,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(GrayMainColor)
                .constrainAs(profileImg){
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                }
        )
        Text(
            text = item.commentBy.nickName,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.constrainAs(name){
                start.linkTo(profileImg.end, margin = 12.dp)
                top.linkTo(profileImg.top)
                width = Dimension.wrapContent
            }
        )
        Text(
            text = item.comment,
            style = MaterialTheme.typography.bodySmall,
            color = DarkBlue,
            modifier = Modifier.constrainAs(comment){
                start.linkTo(name.start)
                top.linkTo(name.bottom, margin = 5.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
        Text(
            text = item.createdAt,
            modifier = Modifier.constrainAs(createdOn){
                start.linkTo(name.start)
                top.linkTo(comment.bottom, margin = 5.dp)
            }
        )
        Text(
            text = "reply",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.constrainAs(reply){
                start.linkTo(createdOn.end, margin = 16.dp)
                top.linkTo(createdOn.top)
                end.linkTo(like.end, margin = 4.dp)
                width = Dimension.fillToConstraints
            }
        )
        Row (
            modifier = Modifier.constrainAs(like){
                bottom.linkTo(reply.bottom)
                end.linkTo(unLike.start, margin = 24.dp)
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ){
            Icon(
                painter = painterResource(id = R.drawable.ic_like_outline),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            item.totalLike.takeIf {
                it!=0L
            }?.let {
                Text(
                    text = it.toString(),
                    fontSize = 13.sp,
                    color = SubTextColor)
            }
        }
        Row (
            modifier = Modifier.constrainAs(unLike){
                bottom.linkTo(reply.bottom)
                end.linkTo(parent.end)
            },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ){
            Icon(
                painter = painterResource(id = R.drawable.ic_like_outline),
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
//            item.totalLike.takeIf {
//                it!=0L
//            }?.let {
//                Text(
//                    text = it.toString(),
//                    fontSize = 13.sp,
//                    color = SubTextColor)
//            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentUserField(

){
    Column (
        modifier = Modifier
            .shadow(elevation = (0.4).dp)
            .padding(horizontal = 16.dp)
    ){
        HighlightedEmoji.values().toList().let {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                it.forEach { emoji ->
                    Text(text = emoji.unicode, fontSize = 25.sp)
                }
            }
        }
        Row (
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 5.dp)
        ){
            AsyncImage(
                model = "",
                contentDescription = null,
                modifier = Modifier.size(38.dp)
                    .background(shape = CircleShape, color = GrayMainColor)
            )
            OutlinedTextField(
                value = "",
                onValueChange = {},
                shape = RoundedCornerShape(36.dp),
                placeholder = {
                    Text(
                        text = stringResource(R.string.addComment),
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = GrayMainColor,
                    unfocusedBorderColor = Color.Transparent
                ),
                modifier = Modifier.height(56.dp),
                enabled = true,
                trailingIcon = {
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                        modifier = Modifier.padding(end = 10.dp, start = 2.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_mention),
                            contentDescription = null
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_emoji),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    }
}