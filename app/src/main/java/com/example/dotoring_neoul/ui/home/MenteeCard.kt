package com.example.dotoring_neoul.ui.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.getValue
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.dotoring.R
import com.example.dotoring_neoul.navigation.Graph
import com.example.dotoring_neoul.ui.detail.MenteeDetail
import com.example.dotoring_neoul.ui.detail.MentiDetailedViewModel

@Composable
fun MemberCard(
    member: Member,
    navController: NavHostController,
    menteeDetailedViewModel: MentiDetailedViewModel = viewModel(),
    isMentor: Boolean
) {
    val menteeDetailedUiState by menteeDetailedViewModel.uiState.collectAsState()

    val spaceBetweenText: Dp = 5.dp
    val spaceBetweenPhotoAndDescription: Dp = 10.dp

    val nicknameColor = if(isMentor) {
        colorResource(id = R.color.navy)
    } else {
        colorResource(R.color.green)
    }

    val nickname = member.nickname
    val profileImage = "http://192.168.0.32:8080/files/${member.profileImage}"
    Log.d("profileImage", "$profileImage")
    val major = member.major
    val mentoringField = member.mentoringField
    val introduction = member.introduction

    Card(
        modifier = Modifier
            .size(width = 320.dp, height = 164.dp)
            .shadow(
                elevation = 50.dp,
                ambientColor = Color.DarkGray,
                spotColor = Color.LightGray,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable {
                val menteeDetail = MenteeDetail(
                    profileImage = profileImage,
                    nickname = nickname,
                    major = major,
                    mentoringField = mentoringField,
                    introduction = introduction
                )
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "menteeDetail",
                    value = menteeDetail
                )
                navController.navigate(Graph.MENTI_DETAILS)
            },
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(width = 0.2.dp, color = Color.LightGray),
        elevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.size(25.dp))

            AsyncImage(
                model = profileImage,
                contentDescription = "유저 사진",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(20.dp)),
                placeholder = if(isMentor) {
                    painterResource(id = R.drawable.home_profile_sample_mentor)
                } else {
                    painterResource(id = R.drawable.home_profile_sample_mentee)
                }
            )
            Spacer(modifier = Modifier.size(spaceBetweenPhotoAndDescription))

            Column() {
                Text(
                    text = nickname,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = nicknameColor
                )
                Spacer(modifier = Modifier.size(spaceBetweenText))
                Text(
                    text = major,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(spaceBetweenText))
                Text(
                    text = mentoringField,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(R.color.grey_700)
                )
                Spacer(modifier = Modifier.size(spaceBetweenText))
                Text(
                    text = introduction,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light,
                    color = colorResource(R.color.grey_700)
                )
            }
        }
    }
}
