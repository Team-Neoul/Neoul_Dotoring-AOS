package com.example.dotoring_neoul.ui.register.third

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.example.dotoring.R
import com.example.dotoring_neoul.ui.theme.DotoringTheme
import com.example.dotoring_neoul.ui.util.EffectiveCheckButton
import com.example.dotoring_neoul.ui.util.register.CommonTextField
import java.util.regex.Pattern

@Composable
fun RegisterThirdScreenContent(
    registerThirdViewModel: RegisterThirdViewModel = viewModel(),
    registerThirdUiState: RegisterThirdUiState,
    pattern: String,
    isMentor: Boolean
) {
    val focusManager = LocalFocusManager.current

    val placeHolder = stringResource(R.string.register3_input)
    val textFieldWidth = 280.dp
    val aFontSize = 20.sp
    val guideFontSize = 12.sp
    val placeHolderFontSize = 15.sp
    val buttonFontSize = 10.sp

    val defaultColor = if(isMentor) {
        colorResource(R.color.green)
    } else {
        colorResource(R.color.navy)
    }

    Row {
        Text(
            text = stringResource(id = R.string.register_A),
            fontSize = aFontSize,
            modifier = Modifier.padding(8.dp),
        )
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Column {
                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.CenterEnd
                ) {
                    CommonTextField(
                        value = registerThirdUiState.nickname,
                        onValueChange = {
                            val fieldCondition = Pattern.matches(pattern, it)

                            registerThirdViewModel.updateNickname(it)
                            registerThirdViewModel.updateNicknameConditionErrorState(!fieldCondition)
                            registerThirdViewModel.updateNicknameDuplicationErrorState(DuplicationCheckState.NonChecked)
                        },
                        placeholder = placeHolder,
                        width = textFieldWidth,
                        imeAction = ImeAction.Done,
                        onDone = { focusManager.clearFocus() },
                        placeholderFontSize = placeHolderFontSize
                    )
                    EffectiveCheckButton(
                        onClick = {
                            if(isMentor) {
                                registerThirdViewModel.verifyMentorNickname()
                            } else {
                                registerThirdViewModel.verifyMenteeNickname()
                            }
                        },
                        text = stringResource(id = R.string.register_nickname_duplication_check),
                        enabled = registerThirdUiState.duplicationCheckButtonState,
                        fontSize = buttonFontSize
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
                        defaultColor
                    },
                    fontSize = guideFontSize
                )
            }
            Spacer(modifier = Modifier.size(15.dp))

            Text(
                text = stringResource(R.string.register3_),
                fontSize = 20.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterScreenPreview() {
    val nicknamePattern = "^(?=.*?[0-9])[a-zA-Z0-9가-힣]{3,8}\$"

    DotoringTheme {
        RegisterThirdScreenContent(
            registerThirdUiState = RegisterThirdUiState(),
            pattern = nicknamePattern,
            isMentor = true
        )
    }
}