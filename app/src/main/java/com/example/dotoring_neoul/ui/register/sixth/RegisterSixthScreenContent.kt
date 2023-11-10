package com.example.dotoring_neoul.ui.register.sixth

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dotoring.R
import com.example.dotoring_neoul.ui.util.EffectiveCheckButton
import com.example.dotoring_neoul.ui.util.register.CommonTextField
import java.util.regex.Pattern

@Composable
fun RegisterSixthScreenContent(
    registerSixthViewModel: RegisterSixthViewModel = viewModel(),
    registerSixthUiState: RegisterSixthUiState,
    isMentor: Boolean = true
) {
    val focusManager = LocalFocusManager.current

    val textFieldWidth: Dp = 370.dp
    val spaceBetweenTitleAndContent: Dp = 15.dp
    val spaceBetweenSurface: Dp = 15.dp

    val answerTextSize = 20.sp
    val guideTextSize = 12.sp

    SetId(
        registerSixthViewModel = registerSixthViewModel,
        registerSixthUiState = registerSixthUiState,
        spaceBetweenTitleAndContent = spaceBetweenTitleAndContent,
        textFieldWidth = textFieldWidth,
        focusManager = focusManager,
        answerTextSize = answerTextSize,
        guideTextSize = guideTextSize,
        isMentor = isMentor
    )
    Spacer(modifier = Modifier.size(spaceBetweenSurface))


    SetPassword(
        registerSixthViewModel = registerSixthViewModel,
        registerSixthUiState = registerSixthUiState,
        spaceBetweenTitleAndContent = spaceBetweenTitleAndContent,
        textFieldWidth = textFieldWidth,
        focusManager = focusManager,
        answerTextSize = answerTextSize,
        guideTextSize = guideTextSize,
        isMentor = isMentor
    )
    Spacer(modifier = Modifier.size(spaceBetweenSurface))


    SetEmail(
        registerSixthViewModel = registerSixthViewModel,
        registerSixthUiState = registerSixthUiState,
        spaceBetweenTitleAndContent = spaceBetweenTitleAndContent,
        textFieldWidth = textFieldWidth,
        focusManager = focusManager,
        answerTextSize = answerTextSize,
        guideTextSize = guideTextSize,
        isMentor = isMentor
    )
}

@Composable
private fun SetId(
    registerSixthViewModel: RegisterSixthViewModel = viewModel(),
    registerSixthUiState: RegisterSixthUiState,
    spaceBetweenTitleAndContent: Dp,
    textFieldWidth: Dp,
    focusManager: FocusManager,
    answerTextSize: TextUnit,
    guideTextSize: TextUnit,
    isMentor: Boolean
) {
    val idPattern = "^(?=.*?[0-9])(?=.*?[a-zA-z])[a-zA-Z0-9]{8,12}\$"
    val primaryColor = if(isMentor) {
        colorResource(R.color.green)
    } else {
        colorResource(R.color.navy)
    }


    Column {
        Text(
            text = stringResource(id = R.string.register6_set_ID),
            fontSize = answerTextSize
        )
        Spacer(modifier = Modifier.size(spaceBetweenTitleAndContent))


        Column() {
            TextFieldWithEffectiveCheckButton(
                value = registerSixthUiState.memberId,
                onValueChange = {
                    val idCondition = Pattern.matches(idPattern, it)
                    Log.d("아이디 조건 만족 상태", "idCondition: $idCondition")

                    registerSixthViewModel.updateUserId(it)
                    Log.d("아이디 조건 만족 상태", "it: $it")
                    registerSixthViewModel.updateIdConditionState(idCondition)
                    Log.d("아이디 조건 만족 상태", "registerSixthUiState.isIdConditionSatisfied: ${registerSixthUiState.isIdConditionSatisfied}")
                    registerSixthViewModel.updateIdValidationState(isIdAvailable = IdDuplicationCheckState.NonChecked)
                },
                placeholder = stringResource(id = R.string.register6_ID),
                btnText = stringResource(R.string.register_nickname_duplication_check),
                width = textFieldWidth,
                onClick = { registerSixthViewModel.userIdDuplicationCheck() },
                onNext = { focusManager.moveFocus(FocusDirection.Next)},
                enabled = registerSixthUiState.isIdConditionSatisfied
            )
            Text(
                text = stringResource(id = R.string.register6_ID_guide),
                modifier = Modifier
                    .padding(start = 2.dp, top = 3.dp),
                color = colorResource(id = R.color.grey_500),
                fontSize = guideTextSize
            )
            Text(
                text =
                if(registerSixthUiState.isIdAvailable == IdDuplicationCheckState.DuplicationCheckSuccess && registerSixthUiState.isIdConditionSatisfied) {
                    stringResource(R.string.register6_ID_certified)
                } else if(registerSixthUiState.isIdAvailable == IdDuplicationCheckState.DuplicationCheckFail && registerSixthUiState.isIdConditionSatisfied) {
                    stringResource(id = R.string.register6_ID_error_id_duplicated)
                } else if (!registerSixthUiState.isIdConditionSatisfied && registerSixthUiState.isIdAvailable == IdDuplicationCheckState.NonChecked){
                    stringResource(id = R.string.register6_ID_error_condition_not_satisfied)
                } else {
                    stringResource(id = R.string.register6_ID_error_condition_satisfied)
                },
                modifier = Modifier
                    .padding(start = 2.dp, top = 3.dp),
                color = if(registerSixthUiState.isIdAvailable == IdDuplicationCheckState.DuplicationCheckSuccess) {
                    primaryColor
                } else {
                    colorResource(id = R.color.error)
                       }
                ,
                fontSize = guideTextSize
            )
        }
    }
}

@Composable
private fun SetPassword(
    registerSixthViewModel: RegisterSixthViewModel = viewModel(),
    registerSixthUiState: RegisterSixthUiState,
    spaceBetweenTitleAndContent: Dp,
    textFieldWidth: Dp,
    focusManager: FocusManager,
    answerTextSize: TextUnit,
    guideTextSize: TextUnit,
    isMentor: Boolean
) {
    val passwordPattern = "^(?=.*?[a-zA-Z])(?=.*?[0-9])(?=.*?[*!@#$])[a-zA-Z0-9*!@#$]{7,12}\$"
    val primaryColor = if(isMentor) {
        colorResource(R.color.green)
    } else {
        colorResource(R.color.navy)
    }

    Column() {
        Text(
            text = stringResource(id = R.string.register6_set_pass_word),
            fontSize = answerTextSize
        )
        Spacer(modifier = Modifier.size(spaceBetweenTitleAndContent))


        CommonTextField(
            value = registerSixthUiState.password,
            onValueChange = {
                val passwordCondition = Pattern.matches(passwordPattern, it)

                registerSixthViewModel.updateUserPassword(it)
                registerSixthViewModel.updatePasswordConditionState(passwordCondition)
            },
            placeholder = stringResource(id = R.string.register6_pass_word),
            width = textFieldWidth,
            imeAction = ImeAction.Next,
            onNext = { focusManager.moveFocus(FocusDirection.Next)},
            visualTransformation = PasswordVisualTransformation()
        )
        Text(
            text = stringResource(id = R.string.register6_pass_word_guide),
            modifier = Modifier
                .padding(start = 2.dp, top = 3.dp),
            color = colorResource(id = R.color.grey_500),
            fontSize = guideTextSize
        )
        Text(
            text = if(registerSixthUiState.isPasswordConditionSatisfied) {
                stringResource(id = R.string.register6_pass_word_certified)
            } else {
                stringResource(id = R.string.register6_pass_word_error_condition_not_satisfied)
            },
            modifier = Modifier
                .padding(start = 2.dp, top = 3.dp),
            color = if(registerSixthUiState.isPasswordConditionSatisfied) {
                primaryColor
            } else {
                colorResource(id = R.color.error)
            },
            fontSize = guideTextSize
        )


        Column() {
            Box(
                contentAlignment = Alignment.CenterEnd
            ) {
                CommonTextField(
                    value = registerSixthUiState.passwordCertification,
                    onValueChange = {
                        registerSixthViewModel.updatePasswordCertification(it)
                        registerSixthViewModel.passwordErrorCheck()
                    },
                    placeholder = stringResource(id = R.string.register6_pass_word_check),
                    width = textFieldWidth,
                    imeAction = ImeAction.Next,
                    onNext = {
                        registerSixthViewModel.passwordErrorCheck()
                        focusManager.moveFocus(FocusDirection.Next)
                    },
                    modifier = Modifier.onFocusChanged {
                        if( it.isCaptured ) {
                            registerSixthViewModel.passwordErrorCheck()
                        } },
                    visualTransformation = PasswordVisualTransformation()
                )
                Icon(
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = if (registerSixthUiState.isPasswordCertified) {
                        "비밀번호와 다릅니다."
                    } else {
                        "비밀번호와 같습니다."
                    },
                    tint = if (registerSixthUiState.isPasswordCertified) {
                        primaryColor
                    } else {
                        colorResource(R.color.grey_300)
                    }
                )
            }
            Text(
                text = if (!registerSixthUiState.isPasswordCertified) {
                    stringResource(id = R.string.register6_pass_word_error)
                } else { "" },
                modifier = Modifier
                    .padding(start = 2.dp, top = 3.dp),
                color = colorResource(R.color.error),
                fontSize = guideTextSize
            )
        }
    }
}

@Composable
private fun SetEmail(
    registerSixthViewModel: RegisterSixthViewModel = viewModel(),
    registerSixthUiState: RegisterSixthUiState,
    spaceBetweenTitleAndContent: Dp,
    textFieldWidth: Dp,
    focusManager: FocusManager,
    answerTextSize: TextUnit,
    guideTextSize: TextUnit,
    isMentor: Boolean
) {
    val emailPattern = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))\$"
    val primaryColor = if(isMentor) {
        colorResource(R.color.green)
    } else {
        colorResource(R.color.navy)
    }

    Column {
        Text(
            text = stringResource(id = R.string.register6_set_email),
            fontSize = answerTextSize
        )
        Spacer(modifier = Modifier.size(spaceBetweenTitleAndContent))


        Column() {
            TextFieldWithEffectiveCheckButton(
                value = registerSixthUiState.email,
                onValueChange = {
                    val emailCondition = Pattern.matches(emailPattern,it)
                    Log.d("이메일 조건 검증", "onValueChange - emailCondition: $emailCondition")

                    registerSixthViewModel.updateEmail(it)
                    registerSixthViewModel.updateEmailConditionState(emailCondition)
                    registerSixthViewModel.updateCodeState(CodeState.NonSent)
                    registerSixthViewModel.updateEmailValidationButtonState(false)
                },
                placeholder = stringResource(id = R.string.register6_email),
                btnText = stringResource(R.string.register6_send_verification_code),
                width = textFieldWidth,
                onClick = {
                    registerSixthViewModel.sendAuthenticationCode()
                    registerSixthViewModel.startTimer()
                }, //countdown & 코드 발송
                onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                enabled = registerSixthUiState.isEmailConditionSatisfied
            )
            Row(
                modifier = Modifier.width(textFieldWidth),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (registerSixthUiState.isEmailConditionSatisfied) {
                        ""
                    } else {
                        stringResource(R.string.register6_email_error_condition_not_satisfied)
                    },
                    modifier = Modifier
                        .padding(top = 3.dp),
                    color = colorResource(id = R.color.error),
                    fontSize = guideTextSize
                )
                Text(
                    text = registerSixthUiState.certificationPeriod,
                    modifier = Modifier
                        .padding(end = 15.dp, top = 3.dp),
                    color = colorResource(id = R.color.error),
                    fontSize = guideTextSize
                )
            }


            Column() {
                TextFieldWithEffectiveCheckButton(
                    value = registerSixthUiState.validationCode,
                    onValueChange = {
                        registerSixthViewModel.updateValidationCode(it)
                        registerSixthViewModel.updateEmailValidationState(EmailValidationState.NonChecked)
                    },
                    placeholder = stringResource(id = R.string.register6_verification_code),
                    btnText = stringResource(R.string.register6_verify),
                    width = textFieldWidth,
                    onClick = {
                        registerSixthViewModel.codeCertification()
                    },
                    onDone = { focusManager.clearFocus() },
                    imeAction = ImeAction.Done,
                    enabled = registerSixthUiState.isValidationButtonEnabled // registerSixthUiState.codeState == CodeState.Valid인 경우 true, 이외에는 false
                )
                Text(
                    text = when (registerSixthUiState.emailValidationState) {
                        EmailValidationState.CodeInvalid -> {
                            stringResource(id = R.string.register6_verification_error)
                        }
                        EmailValidationState.Valid -> {
                            stringResource(id = R.string.register6_verification_successed)
                        }
                        EmailValidationState.AlreadySigned -> {
                            stringResource(id = R.string.register6_verification_duplicated)
                        }
                        else -> {
                            ""
                        }
                    },
                    modifier = Modifier
                        .padding(start = 2.dp, top = 3.dp),
                    color = if(registerSixthUiState.emailValidationState == EmailValidationState.CodeInvalid ||
                        registerSixthUiState.emailValidationState == EmailValidationState.AlreadySigned) {
                        colorResource(id = R.color.error)
                    } else {
                        primaryColor
                    },
                    fontSize = guideTextSize
                )
            }
        }
    }
}

@Composable
private fun TextFieldWithEffectiveCheckButton(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    btnText: String,
    width: Dp,
    onClick: () -> Unit = {},
    onNext: (KeyboardActionScope.() -> Unit)? = {},
    onDone: (KeyboardActionScope.() -> Unit)? = {},
    imeAction: ImeAction = ImeAction.Next,
    enabled: Boolean = false
) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.CenterEnd
    ) {
        CommonTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder,
            width = width,
            imeAction = imeAction,
            onNext = onNext,
            onDone = onDone
        )

        EffectiveCheckButton(onClick = onClick, text = btnText, enabled = enabled)
    }
}