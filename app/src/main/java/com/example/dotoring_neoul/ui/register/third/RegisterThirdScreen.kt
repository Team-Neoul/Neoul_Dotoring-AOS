package com.example.dotoring_neoul.ui.register.third

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dotoring.R
import com.example.dotoring_neoul.navigation.AuthScreen
import com.example.dotoring_neoul.ui.theme.DotoringTheme
import com.example.dotoring_neoul.ui.util.EffectiveCheckButton
import com.example.dotoring_neoul.ui.util.RegisterScreenTop
import com.example.dotoring_neoul.ui.util.register.CommonTextField
import com.example.dotoring_neoul.ui.util.register.MentoInformation
import com.example.dotoring_neoul.ui.util.register.RegisterScreenNextButton
import java.util.regex.Pattern

@Composable
fun ThirdRegisterScreen(
    registerThirdViewModel: RegisterThirdViewModel = viewModel(),
    navController: NavHostController,
    mentoInformation: MentoInformation
) {

    Log.d("uri","mentoInformation.employmentCertification: ${mentoInformation.employmentCertification}")
    val registerThirdUiState by registerThirdViewModel.uiState.collectAsState()

    val focusManager = LocalFocusManager.current

    val nicknamePattern = "^(?=.*?[0-9])[a-zA-Z0-9가-힣]{3,8}\$"

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 50.dp)
    ) {

        RegisterScreenTop(
            screenNumber = 3,
            question = R.string.register3_q3,
            stringResource(id = R.string.register3_guide)
        )

        Spacer(modifier = Modifier.weight(1.5f))

        Row() {

            Text(
                stringResource(id = R.string.register_A),
                modifier = Modifier.padding(8.dp)
            )

            Column() {

                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.CenterEnd
                ) {
                    CommonTextField(
                        value = registerThirdUiState.nickname,
                        onValueChange = {
                            val nicknameCondition = Pattern.matches(nicknamePattern, it)

                            registerThirdViewModel.updateNickname(it)
                            registerThirdViewModel.updateNicknameConditionErrorState(!nicknameCondition)
                            registerThirdViewModel.updateNicknameDuplicationErrorState(DuplicationCheckState.NonChecked)
                        },
                        placeholder = "닉네임",
                        width = 250.dp,
                        imeAction = ImeAction.Done,
                        onDone = {focusManager.clearFocus()})

                    EffectiveCheckButton(
                        onClick = {
                            registerThirdViewModel.verifyNickname()
                        },
                        text = stringResource(id = R.string.register_nickname_duplication_check),
                        enabled = registerThirdUiState.duplicationCheckButtonState
                    )
                }

                Text(
                    text = if(registerThirdUiState.nicknameConditionError && registerThirdUiState.nicknameDuplicationError == DuplicationCheckState.NonChecked) {
                        stringResource(R.string.register3_error_condition_not_satisfied)
                    } else if (!registerThirdUiState.nicknameConditionError && registerThirdUiState.nicknameDuplicationError == DuplicationCheckState.NonChecked){
                        "중복확인 버튼을 눌러주세요."
                    } else if (!registerThirdUiState.nicknameConditionError && registerThirdUiState.nicknameDuplicationError == DuplicationCheckState.DuplicationCheckFail){
                        stringResource(R.string.register3_error_nickname_duplication)
                    } else if (!registerThirdUiState.nicknameConditionError && registerThirdUiState.nicknameDuplicationError == DuplicationCheckState.DuplicationCheckSuccess){
                        stringResource(R.string.register3_nickname_certified)
                    } else { "" },
                    modifier = Modifier
                        .padding(start = 2.dp, top = 3.dp),
                    color = if(registerThirdUiState.nicknameConditionError
                        || registerThirdUiState.nicknameDuplicationError != DuplicationCheckState.DuplicationCheckSuccess) {
                        colorResource(id = R.color.error)
                    } else {
                           colorResource(R.color.green)
                                 },
                    fontSize = 10.sp
                )

                Spacer(modifier = Modifier.size(15.dp))

                Text(
                    text = stringResource(R.string.register3_),
                    modifier = Modifier
                        .wrapContentWidth(Alignment.Start)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1.5f))

        RegisterScreenNextButton(onClick = {
            val mentoInfo = MentoInformation(
                company = mentoInformation.company,
                careerLevel = mentoInformation.careerLevel,
                field = mentoInformation.field,
                major = mentoInformation.major,
                employmentCertification = mentoInformation.employmentCertification,
                graduateCertification = mentoInformation.graduateCertification,
                nickname = registerThirdUiState.nickname
            )
            navController.currentBackStackEntry?.savedStateHandle?.set(
                key = "mentoInfo",
                value = mentoInfo
            )
            navController.navigate(AuthScreen.Register4.route)
        },enabled = registerThirdUiState.nextButtonState)

        Spacer(modifier = Modifier.weight(8f))
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    DotoringTheme {
        ThirdRegisterScreen(navController = rememberNavController(), mentoInformation = MentoInformation())
    }
}