package com.example.dotoring.ui.mypage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dotoring.R
import com.example.dotoring.ui.theme.DotoringTheme
import com.example.dotoring.ui.util.common.BottomButtonLong

@Composable
fun MemberInfoUpdateScreen() {
    /* 멤버 Data, 멘토링 분야, 학과 Data */

    @OptIn(ExperimentalMaterialApi::class)
    BottomSheetScaffold(
        sheetContent = {},
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column {
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    text = "정보 수정",
                    fontSize = 34.sp,
                    fontWeight = FontWeight(800),
                    color = colorResource(R.color.black_500),
                )
                Spacer(modifier = Modifier.height(73.dp))
                Column {
                    MemberInfo()
                    Spacer(modifier = Modifier.height(12.dp))
                    MemberTagRow()
                }
                Spacer(modifier = Modifier.height(116.dp))
                Column {
                    BottomButtonLong(
                        innerText = "수정 취소"
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    BottomButtonLong(
                        innerText = "서류 제출하기"
                    )
                }
            }
        }
    }
}

@Composable
private fun MemberInfo() {
    MemberInfoRow("학교", "학교")
    Spacer(modifier = Modifier.height(22.dp))
    MemberInfoRow("분야", "분야")
    Spacer(modifier = Modifier.height(22.dp))
    MemberInfoRow("학년", "학년")
    Spacer(modifier = Modifier.height(22.dp))
    MemberInfoRow("학과", "학과")
}

@Composable
private fun MemberTagRow(tagList: List<String> = listOf("태그1", "태그2", "태그3")) {
    Column {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "태그",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.grey_500)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = "6자 이하, 3개 이하 작성",
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(R.color.grey_300)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        tagList.forEach { tag ->
            MemberTag(tag)
            Spacer(modifier = Modifier.height(11.dp))
        }
    }
}

@Composable
private fun MemberTag(tag: String) {
    Card(
        modifier = Modifier.size(width = 96.dp, height = 36.dp),
        shape = RoundedCornerShape(size = 10.dp),
        backgroundColor = colorResource(id = R.color.grey_100),
        contentColor = colorResource(R.color.black_500)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "#$tag",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(R.color.black_500),
                letterSpacing = (-1).sp
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                painter = painterResource(R.drawable.tag_erase_button_12dp),
                tint = colorResource(R.color.grey_700),
                contentDescription = "태그 삭제",
                modifier = Modifier.clickable { /*TODO*/ }
            )
        }
    }
}

@Composable
private fun MemberInfoRow(item: String, data: String) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.grey_500)
            )
            Spacer(modifier = Modifier.width(5.dp))
            Divider(
                modifier = Modifier.size(width = 1.dp, height = 12.dp),
                color = colorResource(R.color.grey_500)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = data,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal,
                color = colorResource(R.color.black_500)
            )
        }
        Spacer(
            modifier = Modifier.height(15.dp)
        )
        Divider(
            color = colorResource(R.color.grey_500),
            modifier = Modifier.width(320.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MemberInfoUpdateScreenPreview() {
    DotoringTheme {
        MemberInfoUpdateScreen()
    }
}