package com.example.dotoring.ui.register.third

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun ThirdRegisterScreen(
    registerThirdViewModel: RegisterThirdViewModel = viewModel(),
    navController: NavHostController,
    mentorInformation: MentorInformation?,
    menteeInformation: MenteeInformation?,
    isMentor: Boolean
) {
    Log.d("uri","mentoInformation.employmentCertification: ${mentorInformation?.employmentCertification}")
    val registerThirdUiState by registerThirdViewModel.uiState.collectAsState()
    val nicknamePattern = "^(?=.*?[0-9])[a-zA-Z0-9가-힣]{3,8}\$"
    val question = if(isMentor) {
        R.string.register3_q3_mentor
    } else {
        R.string.register3_q3_mentee
    }

    Row {
        Spacer(modifier = Modifier.weight(1f))
        Column {
            TopRegisterScreen(
                screenNumber = 3,
                question = question,
                guide = stringResource(id = R.string.register3_guide),
                isMentor = isMentor
            )
            Spacer(modifier = Modifier.weight(1.5f))


            RegisterThirdScreenContent(
                registerThirdViewModel = registerThirdViewModel,
                registerThirdUiState = registerThirdUiState,
                pattern = nicknamePattern,
                isMentor = isMentor
            )
            Spacer(modifier = Modifier.weight(1.5f))


            RegisterScreenNextButton(
                onClick = {
                    if(isMentor) {
                        if (mentorInformation != null) {
                            val mentorInfo = MentorInformation(
                                company = mentorInformation.company,
                                careerLevel = mentorInformation.careerLevel,
                                field = mentorInformation.field,
                                major = mentorInformation.major,
                                employmentCertification = mentorInformation.employmentCertification,
                                graduateCertification = mentorInformation.graduateCertification,
                                nickname = registerThirdUiState.nickname
                            )
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "mentorInfo",
                                value = mentorInfo
                            )
                            navController.navigate(AuthScreen.Register4.passScreenState(isMentor))
                            Log.d("네비게이션", "네비게이션 테스트 - onClick isMentor")
                        }
                    } else {
                        if (menteeInformation != null) {
                            val menteeInfo = MenteeInformation(
                                school = menteeInformation.school,
                                grade = menteeInformation.grade,
                                field = menteeInformation.field,
                                major = menteeInformation.major,
                                enrollmentCertification = menteeInformation.enrollmentCertification,
                                nickname = registerThirdUiState.nickname
                            )
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "menteeInfo",
                                value = menteeInfo
                            )
                            navController.navigate(AuthScreen.Register4.passScreenState(isMentor))
                            Log.d("네비게이션", "네비게이션 테스트 - onClick !isMentor")
                        }
                    }
                },
                enabled = registerThirdUiState.nextButtonState,
                isMentor = isMentor
            )
            Spacer(modifier = Modifier.weight(8f))
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    DotoringTheme {
        ThirdRegisterScreen(
            navController = rememberNavController(),
            mentorInformation = MentorInformation(),
            menteeInformation = MenteeInformation(),
            isMentor = true
        )
    }
}