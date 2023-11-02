package com.example.dotoring_neoul.ui.register.branch

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dotoring.R
import com.example.dotoring_neoul.navigation.AuthScreen
import com.example.dotoring_neoul.ui.theme.DotoringTheme
import com.example.dotoring_neoul.ui.util.register.branch.SelectMemberTypeButton

/**
 * 분기 화면을 그리기 위한 Composable 함수가 담긴 파일입니다.
 *
 * @auther hyeonji
 * @version 1.0
 */

/**
 * 분기 화면을 그리기 위한 Composable 함수
 *
 * @param NavHostController navController - 네비게이션 호스트 컨트롤러
 */
@Composable
fun RegisterBranchScreen(
    navController: NavHostController
) {
    val subTitle = stringResource(R.string.branch_logo_our_mentoring)
    val subTitleSize = 20.sp

    val title = stringResource(R.string.branch_logo_app_name)
    val titleSize = 30.sp

    val letterSpacing = (-1).sp
    val logoSize = Modifier.size(100.dp)

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = subTitle,
                fontSize = subTitleSize,
                letterSpacing = letterSpacing
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = title,
                fontSize = titleSize,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = letterSpacing
            )
            Spacer(modifier = Modifier.size(50.dp))


            Image(
                painter = painterResource(id = R.drawable.ic_dotoring_colored),
                contentDescription = null,
                modifier = logoSize
            )
        }
        Spacer(modifier = Modifier.size(50.dp))


        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SelectMemberTypeButton(
                isMentor = true,
                onClick = { navController.navigate(AuthScreen.Register1.passScreenState(isMentor = true)) }
            )
            Spacer(modifier = Modifier.size(10.dp))
            SelectMemberTypeButton(
                isMentor = false,
                onClick = { navController.navigate(AuthScreen.Register1.passScreenState(isMentor = false)) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    DotoringTheme {
        RegisterBranchScreen(
            navController = rememberNavController()
        )
    }
}