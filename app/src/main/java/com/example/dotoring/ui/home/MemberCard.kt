package com.example.dotoring.ui.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.imageLoader
import coil.util.DebugLogger
import com.example.dotoring.R
import com.example.dotoring.navigation.Graph
import com.example.dotoring.ui.theme.DotoringTheme

@Composable
fun MemberCard(
    member: Member,
    navController: NavHostController,
    isMentor: Boolean
) {
    val spaceBetweenText: Dp = 5.dp
    val spaceBetweenPhotoAndDescription: Dp = 15.dp
    val textWidth = Modifier.width(159.dp)

    val nicknameColor = if(isMentor) {
        colorResource(id = R.color.navy)
    } else {
        colorResource(R.color.green)
    }

    val memberId = member.id
    val nickname = member.nickname
    val profileImage = member.profileImage
        .replace("\\", "")
        .replace("localhost", "10.0.2.2")
    Log.d("프로필 이미지", "profileImage: $profileImage")

    val majors = member.majors
        .replace("[", "")
        .replace("]", "")
        .replace("\"", "")
    val fields = member.fields
        .replace("[", "")
        .replace("]", "")
        .replace("\"", "")
    val introduction = member.introduction

    Column() {
        Card(
            modifier = Modifier
                .size(width = 330.dp, height = 164.dp)
                .shadow(
                    elevation = 50.dp,
                    ambientColor = Color.DarkGray,
                    spotColor = Color.LightGray,
                    shape = RoundedCornerShape(20.dp)
                )
                .clickable {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "memberId",
                        value = memberId
                    )
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "isMentor",
                        value = isMentor
                    )
                    navController.navigate(Graph.MENTI_DETAILS)
                },
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(width = 0.2.dp, color = Color.LightGray),
            elevation = 4.dp,
            backgroundColor = colorResource(R.color.white)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                val imageLoader = LocalContext.current.imageLoader.newBuilder()
                    .logger(DebugLogger())
                    .build()

                Image(
                    painter = if (isMentor) {
                        painterResource(R.drawable.home_profile_sample_mentor)
                    } else {
                        painterResource(R.drawable.home_profile_sample_mentee)
                    },
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = 126.dp, height = 138.dp)
                        .clip(RoundedCornerShape(20.dp)),
                )

                Spacer(modifier = Modifier.size(spaceBetweenPhotoAndDescription))

                Column() {
                    Text(
                        text = nickname,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = nicknameColor,
                        modifier = textWidth
                    )
                    Spacer(modifier = Modifier.size(spaceBetweenText))
                    Text(
                        text = majors,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = textWidth
                    )
                    Spacer(modifier = Modifier.size(spaceBetweenText))
                    Text(
                        text = fields,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = colorResource(R.color.grey_700),
                        modifier = textWidth
                    )
                    Spacer(modifier = Modifier.size(spaceBetweenText))
                    Text(
                        text = introduction,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light,
                        color = colorResource(R.color.grey_700),
                        modifier = textWidth
                    )
                }
            }
        }
        Spacer(modifier = Modifier.size(1.dp))
    }
}

@Composable
fun SampleCard() {
    val spaceBetweenText: Dp = 5.dp

    Card(
        modifier = Modifier
            .size(width = 320.dp, height = 164.dp)
            .shadow(
                elevation = 50.dp,
                ambientColor = Color.DarkGray,
                spotColor = Color.LightGray,
                shape = RoundedCornerShape(20.dp)
            )
            .background(colorResource(id = R.color.white_200)),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 0.2.dp, color = Color.LightGray),
        elevation = 4.dp
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.size(15.dp))
            Image(
                painter = painterResource(id = R.drawable.home_profile_sample_mentor),
                contentDescription = "",
                modifier = Modifier.size(
                    width = 124.dp, height = 136.dp
                )
            )
            Spacer(modifier = Modifier.size(10.dp))


            Column() {
                Text(
                    text = "나는 도토리",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = colorResource(id = R.color.navy)
                )
                Spacer(modifier = Modifier.size(spaceBetweenText))
                Text(
                    text = "소프트웨어공학과",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(spaceBetweenText))
                Text(
                    text = "진로, 개발_언어",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(R.color.grey_700)
                )
                Spacer(modifier = Modifier.size(spaceBetweenText))
                Text(
                    text = "안녕하세요, 장도토리입니다. 저는 도토리를 좋아해요",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light,
                    color = colorResource(R.color.grey_700),
                    modifier = Modifier.width(159.dp)
                )
            }
            Spacer(modifier = Modifier.size(25.dp))

        }
    }
}

@Preview(showBackground = true)
@Composable
fun SampleCardPreview() {
    DotoringTheme {
        SampleCard()
    }
}
