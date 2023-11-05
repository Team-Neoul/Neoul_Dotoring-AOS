package com.example.dotoring_neoul.ui.detail


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.dotoring.R
import com.example.dotoring_neoul.ui.theme.DotoringTheme

@Composable
fun MemberDetailedScreen(
    isMentor: Boolean = true,
    memberDetailedViewModel: MemberDetailedViewModel = viewModel(),
    memberDetailInformation: MemberDetailInformation
    ) {

    LaunchedEffect(Unit) {
        memberDetailedViewModel.loadMemberInfo(memberDetailInformation)
    }

    val memberDetailedUiState by memberDetailedViewModel.uiState.collectAsState()

    Column {
        Top(
            memberDetailedUiState = memberDetailedUiState,
            isMentor = isMentor
        )

        Contents(
            memberDetailedUiState = memberDetailedUiState,
            isMentor = isMentor
        )
    }
}


@Composable
private fun Top(
    memberDetailedUiState: MemberDetailedUiState,
    isMentor: Boolean
) {
    val mainColorResource = if(isMentor) {
        colorResource(id = R.color.navy)
    } else {
        colorResource(id = R.color.green)
    }
    val subColorResource = if(isMentor) {
        colorResource(id = R.color.paragraph_navy)
    } else {
        colorResource(id = R.color.paragraph_green)
    }
    val painterResource = if(isMentor) {
        painterResource(id = R.drawable.home_profile_sample_mentor)
    } else {
        painterResource(id = R.drawable.home_profile_sample_mentee)
    }
    val spaceBetweenImageAndText = 10.dp
    val spaceBetweenText = 3.dp

    Box {
        Card(
            modifier = Modifier
                .height(256.dp)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 20.dp,
                        bottomEnd = 20.dp
                    )
                )
                .background(mainColorResource),
            backgroundColor = mainColorResource,
        ) {
            Image(
                painter = painterResource(R.drawable.member_detailed_title_dotoring),
                contentDescription = "null",
                contentScale = ContentScale.None,
                modifier = Modifier.size(280.dp),
                alignment = Alignment.BottomEnd
            )

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(15.dp)

            ) {
                AsyncImage(
                    model = memberDetailedUiState.profileImage ,
                    contentDescription = "멘티 사진",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = 126.dp, height = 138.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    placeholder = painterResource
                )
                Spacer(modifier = Modifier.size(spaceBetweenImageAndText))


                Column(
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Text(
                        text = memberDetailedUiState.major,
                        color = subColorResource,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.size(spaceBetweenText))
                    Text(
                        text = memberDetailedUiState.grade,
                        color = subColorResource,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.size(spaceBetweenText))
                    Text(
                        text = memberDetailedUiState.nickname,
                        color = colorResource(R.color.white),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
    }
}

@Composable
private fun Contents(
    memberDetailedUiState: MemberDetailedUiState,
    isMentor: Boolean
) {
    val spaceBetweenContents = 50.dp
    val spaceBetweenText = 10.dp

    Column(
        modifier = Modifier
            .padding(top = 20.dp, start = 20.dp)
    ) {
        Column {
            Text(
                text = stringResource(R.string.member_detail_mentoring_field),
                color = colorResource(R.color.black),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.size(spaceBetweenText))
            FieldChip(
                isMentor = isMentor,
                mentoringFields = memberDetailedUiState.mentoringField
            )
        }
        Spacer(modifier = Modifier.size(spaceBetweenContents))


        Column {
            Text(
                text = stringResource(R.string.member_detail_introduce),
                color = colorResource(R.color.black),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.size(spaceBetweenText))
            Text(
                text = memberDetailedUiState.introduction,
                color = colorResource(R.color.black),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
private fun FieldChip(
    isMentor:Boolean,
    mentoringFields: String
) {
    val fieldList: List<String> = mentoringFields.split(",").map { it.trim() }
    Log.d("fieldList", "fieldList: ${fieldList}")
    val fontColor = if(isMentor) {
        colorResource(R.color.navy)
    } else {
        colorResource(R.color.paragraph_green)
    }

    Row {
        fieldList.forEach {field ->
            Log.d("fieldList", "for문 실행 횟수 확인: $field")

            Text(
                text = field,
                color = fontColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .border(
                        width = 0.5.dp,
                        color = fontColor,
                        shape = RoundedCornerShape(100.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun MemberDetailedScreenPreview() {
    DotoringTheme {
        MemberDetailedScreen(
            isMentor = false,
            memberDetailInformation = MemberDetailInformation(
                profileImage = "",
                major = "소프트웨어공학과",
 //               grade = "3학년",
                nickname = "제 이름이에요",
                mentoringField = "mento mento meeee",
                introduction = "집인데 집에가고싶다"
            )
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun FieldChipsPreview() {
    DotoringTheme {
        FieldChip(
            isMentor = true,
            mentoringFields = "직무, 개발_언어"
        )
    }
}