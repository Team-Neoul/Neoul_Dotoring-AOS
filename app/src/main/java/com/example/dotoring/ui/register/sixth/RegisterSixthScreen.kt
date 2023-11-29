package com.example.dotoring.ui.register.sixth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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


@Composable
fun SixthRegisterScreen(
    registerSixthViewModel: RegisterSixthViewModel = viewModel(),
    navController: NavHostController,
    mentorInformation: MentorInformation?,
    menteeInformation: MenteeInformation?,
    isMentor: Boolean
) {
    val registerSixthUiState by registerSixthViewModel.uiState.collectAsState()
    val question = if(isMentor) {
        R.string.register6_q6_mentor
    } else {
        R.string.register6_q6_mentee
    }
    Row {
        Spacer(modifier = Modifier.weight(1f))
        Column {
            TopRegisterScreen(
                screenNumber = 6,
                question = question,
                isMentor = isMentor
            )
            Spacer(modifier = Modifier.weight(1f))


            RegisterSixthScreenContent(
                registerSixthViewModel = registerSixthViewModel,
                registerSixthUiState = registerSixthUiState,
                isMentor = isMentor
            )
            Spacer(modifier = Modifier.weight(1f))


            RegisterScreenNextButton(
                onClick = {
                    if(isMentor) {
                        if(mentorInformation != null) {
                            registerSixthViewModel.mentorRegister(mentorInformation)
                        }
                    } else {
                        if(menteeInformation != null) {
                            registerSixthViewModel.menteeRegister(menteeInformation)
                        }
                    }
                    navController.navigate(AuthScreen.Login.route)
                },
                enabled = registerSixthUiState.isToLoginButtonEnabled,
                isMentor = isMentor,
                innerText = stringResource(R.string.register6_to_login_page),
                width = 370.dp
            )
            Spacer(modifier = Modifier.weight(3f))
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    DotoringTheme {
        SixthRegisterScreen(
            navController = rememberNavController(),
            mentorInformation = MentorInformation(),
            menteeInformation = MenteeInformation(),
            isMentor = false
        )
    }
}