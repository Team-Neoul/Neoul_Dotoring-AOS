package com.example.dotoring.ui.register.fifth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dotoring.R
import com.example.dotoring.navigation.AuthScreen
import com.example.dotoring.ui.theme.DotoringTheme
import com.example.dotoring.ui.util.TopRegisterScreen
import com.example.dotoring.ui.util.register.MenteeInformation
import com.example.dotoring.ui.util.register.MentorInformation
import com.example.dotoring.ui.util.register.RegisterScreenNextButton

/**
 * 회원가입 다섯번째 화면 Composable
 */
@Composable
fun FifthRegisterScreen(
    registerFifthViewModel: RegisterFifthViewModel = viewModel(),
    navController: NavHostController,
    mentorInformation: MentorInformation?,
    menteeInformation: MenteeInformation?,
    isMentor: Boolean
) {
    val registerFifthUiState by registerFifthViewModel.uiState.collectAsState()
    val question = if(isMentor) {
        R.string.register5_q5_mentor
    } else {
        R.string.register5_q5_mentee
    }
    val iconTint = if(isMentor) {
        colorResource(id = R.color.green)
    } else {
        colorResource(R.color.navy)
    }

    Row {
        Spacer(modifier = Modifier.weight(1f))
        Column {
            TopRegisterScreen(
                screenNumber = 5,
                question = question,
                guide = stringResource(id = R.string.register5_guide),
                isMentor = isMentor
            )
            Spacer(modifier = Modifier.weight(1f))


            Column {
                Image(
                    painter = painterResource(id = R.drawable.sample_docs),
                    contentDescription = "약관",
                    modifier = Modifier.size(width = 284.dp, height = 103.dp)
                )
                Spacer(modifier = Modifier.size(10.dp))
                Row(
                    modifier = Modifier.width(320.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.register5_accept))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_check),
                        contentDescription = "약관 동의 여부를 표시해주세요.",
                        tint = if(registerFifthUiState.acceptance) {
                            iconTint
                        } else {
                            colorResource(id = R.color.grey_500)
                               }
                        ,
                        modifier = Modifier
                            .clickable {
                                registerFifthViewModel.accept()
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))


            RegisterScreenNextButton(
                onClick = {
                    if(isMentor) {
                        val mentorInfo = mentorInformation

                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "mentorInfo",
                            value = mentorInfo
                        )
                    } else {
                        val menteeInfo = menteeInformation

                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "menteeInfo",
                            value = menteeInfo
                        )
                    }
                    navController.navigate(AuthScreen.Register6.passScreenState(isMentor))
                },
                enabled = registerFifthUiState.btnState,
                isMentor = isMentor
            )
            Spacer(modifier = Modifier.weight(3f))



        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

/**
 * 회원 가입 다섯번째 화면 미리보기
 */
@Preview(showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    DotoringTheme {
        FifthRegisterScreen(
            navController = rememberNavController(),
            mentorInformation = MentorInformation(),
            menteeInformation = MenteeInformation(),
            isMentor = false
        )
    }
}