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
fun MenteeCard(mentee: Mentee, navController: NavHostController, menteeDetailedViewModel: MentiDetailedViewModel = viewModel()) {

    val menteeDetailedUiState by menteeDetailedViewModel.uiState.collectAsState()

    val space: Dp = 5.dp
    val spaceBetweenPhotoAndDescription: Dp = 10.dp

    val nickname = mentee.nickname
    val profileImage = "http://192.168.0.32:8080/files/${mentee.profileImage}"
    Log.d("profileImage", "$profileImage")
    val major = mentee.major
    val job = mentee.job
    val introduction = mentee.introduction

    Card(
        modifier = Modifier
            .size(width = 284.dp, height = 127.dp)
            .clickable {
                val menteeDetail = MenteeDetail(profileImage = profileImage, nickname = nickname, major = major, job = job, introduction = introduction, mentoring = "비대면 멘토링 희망합니다.")
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
            val img = R.drawable.home_profile_sample

            Spacer(modifier = Modifier.size(25.dp))

            AsyncImage(
                model = profileImage,
                contentDescription = "멘티 사진",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(20.dp)),
                placeholder = painterResource(id = R.drawable.home_profile_sample)
            )

            /*            Image(
                            painter = painterResource(id=R.drawable.menti2),
                            contentDescription = "멘티 사진",
                            modifier = Modifier
                                .size(width = 83.dp, height = 91.dp)
                                .clip(RoundedCornerShape(20.dp)),
                            contentScale = ContentScale.Crop
                        )*/

            Spacer(modifier = Modifier.size(spaceBetweenPhotoAndDescription))

            Column() {
                Text(
                    text = nickname,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.navy)
                )

                Spacer(modifier = Modifier.size(space))

                Text(
                    text = major,
                    fontSize = 10.sp
                )

                Spacer(modifier = Modifier.size(space))

                Text(
                    text = job,
                    fontSize = 10.sp
                )

                Spacer(modifier = Modifier.size(space))

                Text(
                    text = introduction,
                    fontSize = 10.sp
                )
            }


        }
    }
}


/*
@Preview
@Composable
private fun HomePreview() {
    DotoringTheme {
        MenteeCard((Mentee(nickname = "현지", profileImage = R.drawable.sample, major = "소프트웨어공학과", job = "개발자", introduction = "하이")), navController = rememberNavController())
    }
}*/
