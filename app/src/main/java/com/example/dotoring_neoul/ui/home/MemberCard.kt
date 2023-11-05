package com.example.dotoring_neoul.ui.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.dotoring.R
import com.example.dotoring_neoul.navigation.Graph
import com.example.dotoring_neoul.ui.detail.MemberDetailInformation
import com.example.dotoring_neoul.ui.detail.MemberDetailedViewModel
import com.example.dotoring_neoul.ui.theme.DotoringTheme

@Composable
fun MemberCard(
    member: Member,
    navController: NavHostController,
    menteeDetailedViewModel: MemberDetailedViewModel = viewModel(),
    isMentor: Boolean
) {
    val menteeDetailedUiState by menteeDetailedViewModel.uiState.collectAsState()

    val spaceBetweenText: Dp = 5.dp
    val spaceBetweenPhotoAndDescription: Dp = 10.dp
    val textWidth = Modifier.width(159.dp)

    val nicknameColor = if(isMentor) {
        colorResource(id = R.color.navy)
    } else {
        colorResource(R.color.green)
    }

    val nickname = member.nickname
    val profileImage = "http://192.168.0.60:8080/profile/${member.profileImage}"
    val majors = member.majors
        .replace("[", "")
        .replace("]", "")
        .replace("\"", "")
    val fields = member.fields
        .replace("[", "")
        .replace("]", "")
        .replace("\"", "")
    val introduction = member.introduction
 //   val grade = member.grade.toString() + "학년"

    Column() {
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
                    val memberDetail = MemberDetailInformation(
                        profileImage = profileImage,
                        nickname = nickname,
                        major = majors,
                        mentoringField = fields,
                        introduction = introduction,
     //                   grade = grade
                    )
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "menteeDetail",
                        value = memberDetail
                    )
                    navController.navigate(Graph.MENTI_DETAILS)
                }
                .background(colorResource(id = R.color.white_200)),
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
                        .size(width = 124.dp, 136.dp)
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
