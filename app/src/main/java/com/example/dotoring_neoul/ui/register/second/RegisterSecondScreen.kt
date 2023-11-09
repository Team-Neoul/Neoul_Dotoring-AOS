package com.example.dotoring_neoul.ui.register.second

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dotoring.R
import com.example.dotoring_neoul.navigation.AuthScreen
import com.example.dotoring_neoul.ui.theme.DotoringTheme
import com.example.dotoring_neoul.ui.util.TopRegisterScreen
import com.example.dotoring_neoul.ui.util.register.MenteeInformation
import com.example.dotoring_neoul.ui.util.register.MentorInformation
import com.example.dotoring_neoul.ui.util.register.RegisterScreenNextButton

@Composable
fun SecondRegisterScreen(
    navController: NavHostController,
    registerSecondViewModel: RegisterSecondViewModel = viewModel(),
    mentorInformation: MentorInformation?,
    menteeInformation: MenteeInformation?,
    isMentor: Boolean
) {
    val registerSecondUiState by registerSecondViewModel.uiState.collectAsState()

    val question = if(isMentor) {
        R.string.register2_q2_mentor
    } else {
        R.string.register2_q2_mentee
    }

    LaunchedEffect(Unit) {
            if (mentorInformation != null) {
                Log.d("리스트 확인", "SecondRegisterScreen - mentoInformation.field: ${mentorInformation.field.toList()}")
            }
            if (mentorInformation != null) {
                Log.d("리스트 확인", "SecondRegisterScreen - mentoInformation.major: ${mentorInformation.major.toList()}")
            }
    }

    Row {
        Spacer(modifier = Modifier.weight(1f))
        Column {
            TopRegisterScreen(screenNumber = 2, question = question, isMentor = isMentor)
            Spacer(modifier = Modifier.weight(1f))


            SecondRegisterScreenContent(isMentor = isMentor)
            Spacer(modifier = Modifier.weight(1f))


            if(isMentor) {
                RegisterScreenNextButton(
                    onClick = {
                        Log.d("파일 업로드 테스트", "employmentFileUploaded: ${registerSecondUiState.employmentFileUploaded}")
                        if (mentorInformation != null) {
                            val mentorInfo = MentorInformation(
                                company = mentorInformation.company,
                                careerLevel = mentorInformation.careerLevel,
                                field = mentorInformation.field,
                                major = mentorInformation.major,
                                employmentCertification = registerSecondUiState.employmentCertification,
                                graduateCertification = registerSecondUiState.graduationCertification
                            )
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "mentorInfo",
                                value = mentorInfo
                            )
                            navController.navigate(AuthScreen.Register3.passScreenState(isMentor = isMentor))
                        }
                    },
                    enabled = registerSecondUiState.employmentFileUploaded,
                    isMentor = true
                )
            } else {
                RegisterScreenNextButton(
                    onClick = {
                        Log.d("파일 업로드 테스트", "employmentFileUploaded: ${registerSecondUiState.employmentFileUploaded}")
                        if (menteeInformation != null) {
                            val menteeInfo = MenteeInformation(
                                school = menteeInformation.school,
                                grade = menteeInformation.grade,
                                field = menteeInformation.field,
                                major = menteeInformation.major,
                                enrollmentCertification = registerSecondUiState.employmentCertification,
                            )
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                key = "menteeInfo",
                                value = menteeInfo
                            )
                            navController.navigate(AuthScreen.Register3.passScreenState(isMentor = isMentor))
                        }
                    },
                    enabled = registerSecondUiState.employmentFileUploaded,
                    isMentor = false
                )
            }
            Spacer(modifier = Modifier.weight(3f))
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    DotoringTheme {
        SecondRegisterScreen(navController = rememberNavController(), mentorInformation = MentorInformation(), menteeInformation = MenteeInformation(), isMentor = false)
    }
}